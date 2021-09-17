package com.cisco.webex.day002;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @program: Netty-demo
 * @description: 单线程阻塞处理多个client请求
 * @author: hemwang
 * @create: 2021-09-17 22:02
 **/
@Slf4j
public class ServerSocketDemo01 {

    public static void main(String[] args) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(16);
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(8888));
        while(true){
            SocketChannel sc = ssc.accept();

        }
    }
}
