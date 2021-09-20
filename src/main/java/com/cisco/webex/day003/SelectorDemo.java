package com.cisco.webex.day003;

import com.cisco.webex.utils.ByteBufferUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @program: Netty-demo
 * @description: nio selector使用
 * @author: hemwang
 * @create: 2021-09-19 10:53
 **/
@Slf4j
public class SelectorDemo {

    public static void main(String[] args) throws IOException {
        // 1，构建服务器端
        ServerSocketChannel ssc = ServerSocketChannel.open();
        // 2，设置非阻塞模式
        ssc.configureBlocking(false);
        // 3，绑定端口
        ssc.bind(new InetSocketAddress(9999));
        // 4，创建Selector
        Selector selector = Selector.open();
        // 5，将ssc注册到selector上
        SelectionKey sscKey = ssc.register(selector, 0, null);
        // 6，设置sscKey的关注事件类型
        sscKey.interestOps(SelectionKey.OP_ACCEPT);
        log.debug("sscKey: {}",sscKey);
        while (true){
            // 7，阻塞方法
            selector.select();
            // 8，处理事件
            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
            while(iter.hasNext()){
                SelectionKey key = iter.next();
                iter.remove();
                log.debug("key: {}",key);
                // 9 ，区分事件类型
                if(key.isAcceptable()){
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel sc = channel.accept();
                    sc.configureBlocking(false);
                    // 10，将sc注册到selector中
                    SelectionKey scKey = sc.register(selector, 0, null);
                    scKey.interestOps(SelectionKey.OP_READ);
                    log.debug("{}",sc);
                    log.debug("scKey:{}",scKey);
                } else if(key.isReadable()){
                    try {
                        SocketChannel sc = (SocketChannel) key.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(16);
                        int read = sc.read(byteBuffer);
                        if(read == -1){
                            key.cancel();
                        } else {
                            byteBuffer.flip();
                            ByteBufferUtil.debugAll(byteBuffer);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        key.cancel();
                    }
                }
            }
        }
    }
}
