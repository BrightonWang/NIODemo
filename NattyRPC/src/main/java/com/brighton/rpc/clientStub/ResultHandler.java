package com.brighton.rpc.clientStub;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * <p>
 * 客户端业务处理类
 * </p>
 *
 * @author Brigh
 * @version : 13:30 2019-12-06 Brigh Exp $
 */
public class ResultHandler extends ChannelInboundHandlerAdapter {
    private Object response;

    public Object getResponse() {
        return response;
    }

    //读取服务器端返回的数据(远程调用的结果)
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        response = msg;
        ctx.close();
    }
}
