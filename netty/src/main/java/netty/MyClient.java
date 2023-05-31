package netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jensen
 */
@Slf4j
public class MyClient {

  public static void main(String[] args) throws Exception {
    int clientPort = 6666;

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
      ChannelFuture channelFuture =
          bootstrap
              .connect("127.0.0.1", clientPort)
              .addListener(
                  future -> {
                    if (future.isSuccess()) {
                      log.info("Connection successful!");
                    } else {
                      log.info("Connection failure!");
                      // 重新连接
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
}
