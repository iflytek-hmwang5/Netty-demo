package com.cisco.webex.day002;

import com.cisco.webex.utils.ByteBufferUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: Netty-demo
 * @description: 单线程阻塞处理多个client请求 （无法处理， 之前BIO是一个客户端对应一个线程去处理的）
 * @author: hemwang
 * @create: 2021-09-17 22:02
 **/
@Slf4j
public class ServerSocketDemo01 {

    public static void main(String[] args) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(16);
        // 1，创建服务端
        ServerSocketChannel ssc = ServerSocketChannel.open();
        // 2, 绑定端口
        ssc.bind(new InetSocketAddress(8888));
        List<SocketChannel> scList = new ArrayList<>();
        while(true){
            // 3，创建与客户端的连接
            log.debug("connecting ...");
            SocketChannel sc = ssc.accept(); // 阻塞方法
            log.debug("connected");
            scList.add(sc);
            for (SocketChannel socketChannel : scList) {
                // 4, 读取客户端的数据
                log.debug("before read ...");
                socketChannel.read(byteBuffer); //阻塞
                log.debug("after read ...");
                byteBuffer.flip();
                ByteBufferUtil.debugAll(byteBuffer);
            }
        }
    }
}
