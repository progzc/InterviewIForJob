package com.zcprog.hw;

import java.util.Scanner;

/**
 * @Description 合法IP
 * @Author zhaochao
 * @Date 2021/3/2 19:19
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class NewCoder_middle_LegalIP {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String s = sc.nextLine();
            System.out.println(isLegalIP(s));
        }
        sc.close();
    }

    private static String isLegalIP(String s) {
        String[] ss = s.split("\\.");
        if (ss.length != 4) return "NO";
        for (int i = 0; i < ss.length; i++) {
            int num = Integer.parseInt(ss[i]);
            if (!(num >= 0 && num <= 255)) {
                return "NO";
            }
        }
        return "YES";
    }
}
