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
import java.util.Objects;

/**
 * @program: Netty-demo
 * @description: 单线程非阻塞处理多个client请求(可以，但是要保持轮询，消耗性能)
 * @author: hemwang
 * @create: 2021-09-17 22:02
 **/
@Slf4j
public class ServerSocketDemo02 {

    public static void main(String[] args) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(16);
        // 1，创建服务端
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false); // 设置ssc为非阻塞，对accept方法起作用
        // 2, 绑定端口
        ssc.bind(new InetSocketAddress(8888));
        List<SocketChannel> scList = new ArrayList<>();
        while(true){
            // 3，创建与客户端的连接
//            log.debug("connecting ...");
            SocketChannel sc = ssc.accept(); // 阻塞方法
            if(Objects.nonNull(sc)) {
                sc.configureBlocking(false); //设置sc为非阻塞，对read方法管用
                scList.add(sc);
                log.debug("connected");
            }
            for (SocketChannel socketChannel : scList) {
                // 4, 读取客户端的数据
//                log.debug("before read ...");
                int read = socketChannel.read(byteBuffer);//阻塞
                if (read > 0) {
                    log.debug("after read ...");
                    byteBuffer.flip();
                    ByteBufferUtil.debugAll(byteBuffer);
                }
            }
        }
    }
}
