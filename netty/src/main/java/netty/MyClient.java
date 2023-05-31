package netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jensen
 */
@Slf4j
public class MyClient {

  public static final int MAX_RETRY = 5;

  public static void main(String[] args) throws Exception {
    int clientPort = 6666;
    int serverPort = 8000;

    NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
    try {
      // 创建bootstrap对象，配置参数
      Bootstrap bootstrap = new Bootstrap();
      // 设置线程组
      ChannelInitializer<SocketChannel> handler =
          new ChannelInitializer<>() {
            @Override
            protected void initChannel(SocketChannel ch) {
              // 添加客户端通道的处理器
              ch.pipeline().addLast(new MyClientHandler());
            }
          };

      bootstrap
          .group(eventExecutors)
          // 设置客户端的通道实现类型
          .channel(NioSocketChannel.class)
          // 使用匿名内部类初始化通道
          .handler(handler);
      log.info("客户端准备就绪，随时可以起飞~");
      // 连接服务端
      String host = "127.0.0.1";
      ChannelFuture channelFuture =
          bootstrap
              .connect(host, clientPort)
              .addListener(
                  future -> {
                    if (future.isSuccess()) {
                      log.info("Connection successful!");
                    } else {
                      log.info("Connection failure!");
                      // 重新连接
                      connect(bootstrap, host, serverPort, MAX_RETRY);
                    }
                  })
              .sync();

      // 对通道关闭进行监听
      channelFuture.channel().closeFuture().sync();
    } finally {
      // 关闭线程组
      eventExecutors.shutdownGracefully();
    }
  }

  private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
    bootstrap
        .connect(host, port)
        .addListener(
            future -> {
              if (future.isSuccess()) {
                log.info("连接成功!");
              } else if (retry == 0) {
                log.info("重试次数已用完，放弃连接！");
              } else {
                // 第几次重连
                int order = (MAX_RETRY - retry) + 1;
                // 本次重连的间隔，1<<order相当于1乘以2的order次方
                int delay = 1 << order;
                log.info(LocalDateTime.now() + ": 连接失败，第" + order + "次重连……");
                bootstrap
                    .config()
                    .group()
                    .schedule(
                        () -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
              }
            });
  }
}
