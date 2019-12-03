package com.brighton.netty.chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * <p>
 * </p>
 *
 * @author Brigh
 * @version :  15:44 2019-11-14 Brigh Exp $
 */
public class ChatServer {

    private int port; //服务器端端口号

    public ChatServer(int port) {
        this.port = port;
    }  //构造方法

    public void run() throws Exception {
        //1. 创建一个线程组: 接收客户端连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //2. 创建一个线程组: 处理网络操作
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //3. 创建服务器端启动助手来配置参数
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup) //4. 设置两个线程组
                    .channel(NioServerSocketChannel.class) //5. 使用NioServerSocketChannel作为服务器端通道的实现
                    .option(ChannelOption.SO_BACKLOG, 128) //6. 设置线程队列中等待的个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true) //7.保存活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() { //8. 创建一个通道初始化对象
                        @Override
                        public void initChannel(SocketChannel socketChannel) {
                            // 往Pipeline链中添加一个解码器
                            socketChannel.pipeline().addLast("decoder", new StringDecoder());
                            // 往pipeline链中添加一个编码器
                            socketChannel.pipeline().addLast("encoder", new StringEncoder());
                            //9. 往Pipeline链中添加自定义的handler类
                            socketChannel.pipeline().addLast(new ChatServerHandler());
                        }
                    });
            System.out.println("......Server is ready......");
            ChannelFuture cf = b.bind(port).sync();  //10. 绑定端口bind方法是异步的  sync方法是同步阻塞的
            // 关闭通道
            cf.channel().closeFuture().sync(); // 异步
            System.out.println("......Server is starting......");
        } finally {
            //11. 关闭线程组
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new ChatServer(9999).run();
    }

}
