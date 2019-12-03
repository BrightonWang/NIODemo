package com.brighton.netty.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/**
 * <p>
 * </p>
 *
 * @author Brigh
 * @version : 10:58 2019-12-03 Brigh Exp $
 */
// 网络客户端
public class ChatClient {
    private final String host; //服务器端口IP地址
    private final int port; //服务器端端口号

    public ChatClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() throws Exception {
        //1. 创建一个线程组
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            //2. 创建客户端的启动助手,完成相关配置
            Bootstrap b = new Bootstrap();
            b.group(group)  //3. 设置线程组
                    .channel(NioSocketChannel.class) //4.设置客户端通道的实现类
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast("decode", new StringDecoder()); // 往Pipeline链中添加一个解码器
                            socketChannel.pipeline().addLast("encoder", new StringEncoder()); // 往Pipeline链中添加一个编码器
                            socketChannel.pipeline().addLast(new ChatClientHandler()); //6. 往Pipeline链中添加自定义的handler
                        }
                    });
            System.out.println("......Client is ready......");
            //7. 启动客户端去连接服务器端 connect方法是异步的   sync方法是同步阻塞的
            ChannelFuture cf = b.connect(host, port).sync();
            //8. 关闭连接(异步非阻塞)
//        cf.channel().closeFuture().sync();
            Channel channel = cf.channel();
            System.out.println("-------" + channel.localAddress().toString().substring(1) + "------");
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String msg = scanner.nextLine();
                channel.writeAndFlush(msg + "\r\n");
            }
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new ChatClient("127.0.0.1", 9999).run();
    }
}
