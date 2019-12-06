package com.brighton.rpc.server;

/**
 * <p>
 * </p>
 *
 * @author Brigh
 * @version : 15:29 2019-12-04 Brigh Exp $
 */
public class HelloNettyImpl implements HelloNetty {
    @Override
    public String hello() {
        return "hello ,netty";
    }
}
