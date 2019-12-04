package com.brighton.netty.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * <p>
 * </p>
 *
 * @author Brigh
 * @version :  10:50 2019-12-03 Brigh Exp $
 */
// 客户端业务处理类
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    // 通道就绪事件
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        BookMessage.Book book = BookMessage.Book.newBuilder().setId(1).setName("Java从入门到精通").build();
        ctx.writeAndFlush(book);
    }

    // 读取数据事件
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) {
//        ByteBuf buf = (ByteBuf) msg;
//        System.out.println("服务器端发来的消息: " + buf.toString(CharsetUtil.UTF_8));
//    }
}
