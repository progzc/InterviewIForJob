package com.progzc.advance;

/**
 * @Description 常量池的测试
 * @Author zhaochao
 * @Date 2021/1/7 0:17
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class StringPoolDemo {
    public static void main(String[] args) {
        String str1 = new StringBuilder("ja").append("va").toString();
        System.out.println(str1.intern() == str1); // JDK1.6：false；JDK1.7及以上：false

        String str2 = new StringBuilder("软件").append("工程").toString();
        System.out.println(str2.intern() == str2); // JDK1.6: false; JDK1.7及以上：true
        String str3 = new String(new StringBuilder("物联网").append("工程").toString());
        System.out.println(str3.intern() == str3); // JDK1.6: false; JDK1.7及以上：true
        String str4 = new String("物联网");
        System.out.println(str4.intern() == str4); // JDK1.6: false; JDK1.7及以上：false
    }
}
