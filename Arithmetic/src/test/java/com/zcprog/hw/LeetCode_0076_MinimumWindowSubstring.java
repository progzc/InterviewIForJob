package com.zcprog.hw;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Description 最小覆盖字串
 * @Author zhaochao
 * @Date 2021/3/21 17:57
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0076_MinimumWindowSubstring {
    public static void main(String[] args) {
        String s = "ADOBECODEBANC";
        String t = "ABC";
        System.out.println(minWindow(s, t));
    }

    private static String minWindow(String s, String t) {
        int tLen = t.length();
        Map<Character, Integer> ori = new HashMap<>(tLen); // 统计t的原始信息
        Map<Character, Integer> cnt = new HashMap<>(tLen); // 记录滑动过程中的匹配情况
        // 统计t的原始信息
        for (int i = 0; i < tLen; i++) {
            char c = t.charAt(i);
            ori.put(c, ori.getOrDefault(c, 0) + 1);
        }
        // 记录滑动过程中的匹配情况
        int sLen = s.length();
        int left = 0, right = -1;
        int len = Integer.MAX_VALUE, ansL = -1, ansR = -1;
        while (right < sLen) {
            right++;
            if (right < sLen && ori.containsKey(s.charAt(right))) {
                cnt.put(s.charAt(right), cnt.getOrDefault(s.charAt(right), 0) + 1);
            }
            while (check(ori, cnt) && left <= right) {
                if (right - left + 1 < len) {
                    len = right - left + 1;
                    ansL = left;
                    ansR = right;
                }
                if (ori.containsKey(s.charAt(left))) {
                    cnt.put(s.charAt(left), cnt.getOrDefault(s.charAt(left), 0) - 1);
                }
                left++;
            }
        }
        return ansL == -1 ? "" : s.substring(ansL, ansR + 1);
    }

    private static boolean check(Map<Character, Integer> ori, Map<Character, Integer> cnt) {
        Iterator<Map.Entry<Character, Integer>> iterator = ori.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Character, Integer> entry = iterator.next();
            Character key = entry.getKey();
            Integer value = entry.getValue();
            if (cnt.getOrDefault(key, 0) < value) {
                return false;
            }
        }
        return true;
    }

}
