package org.fenixsoft.jvm;

import org.junit.Test;

/**
 * @Description 字符串测试
 * @Author zhaochao
 * @Date 2021/1/3 9:54
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class StringTest {
    @Test
    public void test1() {
        String str1 = "计算机";
        String str2 = "计算机";
        System.out.println("str1==str2:" + (str1 == str2));

        String str3 = new String("计算机");
        System.out.println("str1==str3:" + (str1 == str3));
        System.out.println("str1==str3.intern():" + (str1 == str3.intern()));
        System.out.println("str2==str3.intern():" + (str2 == str3.intern()));

        String str4 = new String("计算机");
        System.out.println("str3==str4:" + (str3 == str4));
        System.out.println("str3.intern()==str4.intern():" + (str3.intern() == str4.intern()));


        String str5 = new StringBuilder("软件").append("工程").toString();
        System.out.println("str5.intern() == str5:" + (str5.intern() == str5)); // true

        String str6 = new String(new StringBuilder("物联网").append("工程").toString());
        System.out.println("str6.intern() == str6:" + (str6.intern() == str6)); // true

        String str7 = new String("物联网");
        System.out.println("str7.intern() == str7:" + (str7.intern() == str7)); // false
    }
}
