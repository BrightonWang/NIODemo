package com.brighton.nio.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

/**
 * <p>
 * </p>
 *
 * @author Brigh
 * @version ChatServer: 14:23 2019-11-13 Brigh Exp $
 */
// 聊天程序服务器端
public class ChatServer {
    private ServerSocketChannel listenerChannel; // 监听通道   老大
    private Selector selector; // 选择器对象   间谍
    private static final int PORT = 9999;  //服务器端口

    // 构造方法
    public ChatServer() {
        try {
            // 1. 得到监听通道   老大
            listenerChannel = ServerSocketChannel.open();
            // 2. 得到选择器   间谍
            selector = Selector.open();
            // 3. 绑定端口
            listenerChannel.bind(new InetSocketAddress(PORT));
            // 4. 设置为非阻塞模式
            listenerChannel.configureBlocking(false);
            // 5. 将选择器绑定到监听通道并监听accept事件
            listenerChannel.register(selector, SelectionKey.OP_ACCEPT);
            printInfo("Chat Server is ready......");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 6. 服务器开始处理消息
    public void start() throws Exception {
        try {
            while (true) { // 不停监控
                if (selector.select(2000) == 0) {
                    System.out.println("Server:没有客户端找我, 我就干点别的");
                    continue;
                }
                // 遍历selectedKey
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isAcceptable()) { // 连接请求事件
                        SocketChannel sc = listenerChannel.accept();
                        sc.configureBlocking(false);
                        sc.register(selector, SelectionKey.OP_READ);
                        System.out.println(sc.getRemoteAddress().toString().substring(1) + "上线了...");
                    }
                    if (key.isReadable()) { // 读取数据事件
                        readMsg(key);
                    }
                    // 一定要把当前key删掉,方式重复处理
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 读取
    private void readMsg(SelectionKey key) {
        SocketChannel channel = null;
        try {
            // 得到关联的通道
            channel = (SocketChannel) key.channel();
            // 设置 buffer 缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            // 从通道中读取数据并存储到缓存区中
            int count = channel.read(buffer);
            //如果读取到了数据
            if (count > 0) {
                // 把缓冲区数据转换为字符串
                String msg = new String(buffer.array());
                printInfo(msg);
                // 发广播
                broadCast(channel, msg);
            }
            buffer.clear();
        } catch (Exception e) {
            try {
                // 当客户端关闭channel时,进行异常处理
                printInfo(channel.getRemoteAddress().toString().substring(1) + "下线了...");
                key.cancel(); //取消注册
                channel.close(); //关闭通道
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    // 给所有的客户端发广播
    private void broadCast(SocketChannel except, String msg) throws Exception {
        System.out.println("服务器发送了广播...");
        // 广播数据到所有的SocketChannel中
        for (SelectionKey key : selector.keys()) {
            Channel targetChannel = key.channel();
            // 排除自身
            if (targetChannel instanceof SocketChannel && targetChannel != except) {
                // 把数据存储到缓冲区中
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                // 往通道中写数据
                ((SocketChannel) targetChannel).write(buffer);
            }
        }
    }

    // 往控制台打印消息
    private void printInfo(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("[" + sdf.format(new Date()) + "] ->" + str);
    }

    public static void main(String[] args) throws Exception {
        new ChatServer().start();
    }
}
