package netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jensen
 */
@Slf4j
public class MyClientHandler extends ChannelInboundHandlerAdapter {

  @Override
  public void channelActive(ChannelHandlerContext ctx) {
    // 发送消息到服务端
    ctx.writeAndFlush(Unpooled.copiedBuffer("send to server's content", CharsetUtil.UTF_8));
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) {
    // 接收服务端发送过来的消息
    ByteBuf byteBuf = (ByteBuf) msg;
    log.info("收到服务端{}的消息：{}", ctx.channel().remoteAddress(), byteBuf.toString(CharsetUtil.UTF_8));
  }
}
