package com.brighton.rpc.server;

/**
 * <p>
 * </p>
 *
 * @author Brigh
 * @version : 15:32 2019-12-04 Brigh Exp $
 */
public class HelloRPCImpl implements HelloRPC {
    @Override
    public String hello(String name) {
        return "hello, " + name;
    }
}
