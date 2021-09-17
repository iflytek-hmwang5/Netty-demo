package com.cisco.webex.day001;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @program: Netty-demo
 * @description: 底层如何处理粘包半包的
 * @author: hemwang
 * @create: 2021-09-15 21:58
 **/
public class Demo02 {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(32);
        byteBuffer.put("hello world\nhemwang\nde".getBytes());
        split(byteBuffer);
        byteBuffer.put("ng ya\n".getBytes());
        split(byteBuffer);
    }

    private static void split(ByteBuffer byteBuffer) {
        byteBuffer.flip();
        for (int i = 0; i < byteBuffer.limit(); i++) {
            if(byteBuffer.get(i) == '\n'){
                int length = i - byteBuffer.position() + 1;
                ByteBuffer b1 = ByteBuffer.allocate(length);
                for (int j = 0; j < length; j++) {
                    b1.put(byteBuffer.get());
                }
                b1.flip();
                System.out.println(StandardCharsets.UTF_8.decode(b1));
            }
        }
        byteBuffer.compact();
    }
}
