package com.cisco.webex.day002;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * @program: Netty-demo
 * @description: 网络客户端
 * @author: hemwang
 * @create: 2021-09-17 22:27
 **/
public class Client {

    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress(9999));
        System.out.println("waiting");
    }
}
