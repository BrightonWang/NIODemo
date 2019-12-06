package com.brighton.rpc.client;

import com.brighton.rpc.clientStub.NettyRPCProxy;
import com.brighton.rpc.server.HelloNetty;
import com.brighton.rpc.server.HelloRPC;

/**
 * <p>
 * </p>
 *
 * @author Brigh
 * @version : 14:35 2019-12-06 Brigh Exp $
 */
public class TestNettyRPC {
    public static void main(String[] args) {
        // 第一次远程调用
        HelloNetty helloNetty = (HelloNetty) NettyRPCProxy.create(HelloNetty.class);
        System.out.println(helloNetty.hello());

        // 第二次远程调用
        HelloRPC helloRPC = (HelloRPC) NettyRPCProxy.create(HelloRPC.class);
        System.out.println(helloRPC.hello("RPC"));
    }
}
