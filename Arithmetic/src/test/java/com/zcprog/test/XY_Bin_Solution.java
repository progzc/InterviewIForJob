package com.zcprog.test;

/**
 * @Description 兴业银行二进制考试题
 * @Author zhaochao
 * @Date 2021/3/11 10:28
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class XY_Bin_Solution {
    public static void main(String[] args) {
        System.out.println(changeFormatNumber("16"));
    }

    public static String changeFormatNumber(String number) {
        int num;
        try {
            num = Integer.parseInt(number, 10);
        } catch (NumberFormatException e) {
            return "INPUTERROR";
        }

        if (num > 32767 || num < -32767) {
            return "NODATA";
        }
        String bin = Integer.toBinaryString(num);
        String hex = Integer.toHexString(num).toUpperCase();

        int len1 = bin.length();
        int len2 = hex.length();

        if (len1 > 16) {
            bin = bin.substring(len1 - 16);
            hex = hex.substring(len2 - 4);
        } else {
            int m = num >= 0 ? 16 - len1 : 16 - len1 - 1;
            int n = num >= 0 ? 4 - len2 : 4 - len2 - 1;

            StringBuilder sb1 = new StringBuilder();
            for (int i = 0; i < m; i++) {
                sb1.append("0");
            }
            bin = sb1.toString() + bin;
            bin = num >= 0 ? "0" + bin : "1" + bin;

            StringBuilder sb2 = new StringBuilder();
            for (int i = 0; i < n; i++) {
                sb2.append("0");
            }
            hex = sb2.toString() + hex;
        }
        return bin + "," + hex;
    }
}
