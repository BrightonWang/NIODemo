package com.brighton.netty.chat;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * <p>
 * </p>
 *
 * @author Brigh
 * @version :  10:50 2019-12-03 Brigh Exp $
 */
// 客户端业务处理类
public class ChatClientHandler extends SimpleChannelInboundHandler<String> {
    // 通道就绪事件
//    @Override
//    public void channelActive(ChannelHandlerContext ctx) {
//        System.out.println("Client: " + ctx);
//        ctx.writeAndFlush(Unpooled.copiedBuffer("我是客户端", CharsetUtil.UTF_8));
//    }
//
//    // 读取数据事件
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) {
//        ByteBuf buf = (ByteBuf) msg;
//        System.out.println("服务器端发来的消息: " + buf.toString(CharsetUtil.UTF_8));
//    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println(s.trim());
    }
}
