package com.brighton.nio.chat;

import java.util.Scanner;

/**
 * <p>
 * </p>
 *
 * @author Brigh
 * @version : 16:56 2019-11-13 Brigh Exp $
 */
// 启动聊天程序客户端
public class TestChat {
    public static void main(String[] args) throws Exception {
        // 创建一个聊天客户端对象
        ChatClient chatClient = new ChatClient();
        new Thread() { //单独开一个线程不断的接收服务器广播的数据
            @Override
            public void run() {
                while (true) {
                    try {
                        chatClient.receiveMsg();
                        Thread.sleep(2000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        Scanner scanner = new Scanner(System.in);
        // 在控制台输入数据并发送到服务器端
        while (scanner.hasNextLine()) {
            String msg = scanner.nextLine();
            chatClient.sendMsg(msg);
        }
    }
}
