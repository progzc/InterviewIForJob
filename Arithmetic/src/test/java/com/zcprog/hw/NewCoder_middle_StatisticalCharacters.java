package com.zcprog.hw;

import java.util.Scanner;

/**
 * @Description 字符统计
 * @Author zhaochao
 * @Date 2021/2/20 16:01
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class NewCoder_middle_StatisticalCharacters {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String s = sc.nextLine();
            char[] chs = new char[128];
            for (int i = 0; i < s.length(); i++) {
                chs[i]++;
            }
            int max = 0;
            for (int i = 0; i < chs.length; i++) {
                if (max < chs[i]) {
                    max = chs[i];
                }
            }
            while (max > 0) {
                for (int i = 0; i < chs.length; i++) {
                    if (chs[i] == max) {
                        System.out.print((char) i);
                    }
                }
                max--;
            }
            System.out.println();
        }
    }
}
