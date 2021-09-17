package com.cisco.webex.day001;



import lombok.extern.slf4j.Slf4j;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @program: Netty-demo
 * @description: 测试Java-NIO
 * @author: hemwang
 * @create: 2021-09-14 22:06
 **/

public class Demo01 {
    public static void main(String[] args){

        // 1，创建输入流
        try(FileChannel channel =  new FileInputStream("word.txt").getChannel()) {
            // 2，构建buffer
            ByteBuffer byteBuffer = ByteBuffer.allocate(10);
            int len = 0;
            // 3，从输入流中读取数据到buffer
            do {
                len = channel.read(byteBuffer);
                // 4，将buffer设置成可读状态
                byteBuffer.flip();
                while (byteBuffer.hasRemaining()) {
                    System.out.println((char) byteBuffer.get());
                }
                // 5，将buffer设置成可写状态
                byteBuffer.clear();
            }while(len != -1);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
