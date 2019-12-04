package com.brighton.nio.chat;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * <p>
 * </p>
 *
 * @author Brigh
 * @version ChatClient:  16:34 2019-11-13 Brigh Exp $
 */
// 聊天程序客户端
public class ChatClient {
    private final String HOST = "127.0.0.1"; //服务器地址
    private int PORT = 9999; //服务器端口
    private SocketChannel socketChannel; // 网络通道
    private String userName; //聊天用户名

    public ChatClient() throws IOException {
        // 1. 得到一个网格通道
        socketChannel = SocketChannel.open();
        // 2. 设置非阻塞方式
        socketChannel.configureBlocking(false);
        // 3. 提供服务器端的IP地址和端口号
        InetSocketAddress address = new InetSocketAddress(HOST, PORT);
        // 4. 连接服务器端
        if (!socketChannel.connect(address)) {
            while (!socketChannel.finishConnect()) {
                System.out.println("Client:连接服务器端的同时,我还可以干别的一些事情");
            }
        }
        // 5. 得到客户端IP地址和端口信息,作为聊天用户名使用
        userName = socketChannel.getLocalAddress().toString().substring(1);
        System.out.println("--------------------Client(" + userName + ") is ready----------------");
    }

    // 向服务器端发送数据
    public void sendMsg(String msg) throws Exception {
        if (msg.equalsIgnoreCase("bye")) {
            // 如果信息是再见就结束对话
            socketChannel.close();
            return;
        }
        msg = userName + "说: " + msg;
        // 往通道里写数据
        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
        socketChannel.write(buffer);
    }

    // 从服务器端接收数据
    public void receiveMsg() throws Exception {
        // 得到一个缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // 读取数据并存储到缓冲区
        int size = socketChannel.read(buffer);
        if (size > 0) {
            // 把缓冲区数据转换成字符串
            String msg = new String(buffer.array());
            System.out.println(msg.trim());
        }
    }
}
