package com.brighton.netty.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * <p>
 * </p>
 *
 * @author Brigh
 * @version : 11:12 2019-12-04 Brigh Exp $
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        BookMessage.Book book = (BookMessage.Book)msg;
        System.out.println("客户端发来数据: "+book.getName());
        System.out.println("客户端发来数据id: "+book.getId());
    }
}
