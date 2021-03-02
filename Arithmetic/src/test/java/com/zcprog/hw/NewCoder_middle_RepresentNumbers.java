package com.zcprog.hw;

import java.util.Scanner;

/**
 * @Description 表示数字
 * @Author zhaochao
 * @Date 2021/3/2 20:28
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class NewCoder_middle_RepresentNumbers {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String s = sc.nextLine();
            System.out.println(replaceUsingStar(s));
        }
    }

    private static String replaceUsingStar(String s) {
        int len = s.length();
        int i = 0;
        int j;
        StringBuilder sb = new StringBuilder();
        while (i < len) {
            if (!Character.isDigit(s.charAt(i))) {
                sb.append(s.charAt(i++));
            } else {
                sb.append("*");
                sb.append(s.charAt(i));
                j = i + 1;
                while (j < len && Character.isDigit(s.charAt(j))) {
                    sb.append(s.charAt(j++));
                }
                sb.append("*");
                i = j;
            }
        }
        return sb.toString();
    }
}
