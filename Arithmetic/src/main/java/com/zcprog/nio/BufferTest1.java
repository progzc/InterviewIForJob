package com.zcprog.nio;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

/**
 * @Description 缓冲区测试
 * @Author zhaochao
 * @Date 2020/12/30 15:15
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class BufferTest1 {

    @Test
    public void test1() {
        byte[] byteArray = new byte[]{1, 2, 3};
        short[] shortArray = new short[]{1, 2, 3, 4};
        int[] intArray = new int[]{1, 2, 3, 4, 5};
        long[] longArray = new long[]{1, 2, 3, 4, 5, 6};
        float[] floatArray = new float[]{1, 2, 3, 4, 5, 6, 7};
        double[] doubleArray = new double[]{1, 2, 3, 4, 5, 6, 7, 8};
        char[] charArray = new char[]{'a', 'b', 'c', 'd'};
        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArray);
        CharBuffer charBuffer = CharBuffer.wrap(charArray);
        System.out.println(byteBuffer.getClass().getName());
        System.out.println(charBuffer.getClass().getName());
        System.out.println();
        System.out.println(byteBuffer.capacity());
        System.out.println(charBuffer.capacity());
    }

    @Test
    public void test2() {
        char[] charArray = new char[]{'a', 'b', 'c', 'd', 'e'};
        CharBuffer buffer = CharBuffer.wrap(charArray);
        System.out.println("A capacity () =" + buffer.capacity() + "limit()=" + buffer.limit());
        buffer.limit(5);
        System.out.println();
        System.out.println("B capacity () =" + buffer.capacity() + "limit()=" + buffer.limit());
        buffer.put(0, 'o');
        buffer.put(1, 'p');
        buffer.put(2, 'q');
//        buffer.put(3, 'r');
//        buffer.put(4, 's');
//        buffer.put(5, 't');
//        buffer.put(6, 'u');
        System.out.println(buffer);
    }
}
