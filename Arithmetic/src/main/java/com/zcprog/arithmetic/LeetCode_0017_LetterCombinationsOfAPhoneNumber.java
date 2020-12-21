package com.zcprog.arithmetic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 电话号码的字母组合
 * @Author zhaochao
 * @Date 2020/12/21 11:11
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0017_LetterCombinationsOfAPhoneNumber {
    /**
     * LeetCode地址：https://leetcode-cn.com/problems/letter-combinations-of-a-phone-number
     * 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
     * 示例:
     * 输入："23"
     * 输出：["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
     * <p>
     * 说明:
     * 尽管上面的答案是按字典序排列的，但是你可以任意选择答案输出的顺序。
     */
    public static void main(String[] args) {
        String[] strs = {"23"};
        for (String str : strs) {
            System.out.println(dfsSolve(str));
        }
    }

    private static List<String> dfsSolve(String digits) {
        List<String> ans = new ArrayList<>();
        if (digits == null || digits.length() < 1) {
            return ans;
        }
        Map<Character, String> map = new HashMap<Character, String>(16) {
            {
                put('2', "abc");
                put('3', "def");
                put('4', "ghi");
                put('5', "jkl");
                put('6', "mno");
                put('7', "pqrs");
                put('8', "tuv");
                put('9', "wxyz");
            }
        };
        dfs(digits, 0, new StringBuilder(), map, ans);
        return ans;
    }

    /**
     * @param digits 需要遍历的字符串
     * @param index  遍历到索引
     * @param s      当前取到的字符串
     * @param map    键值对
     * @param ans    存储结果
     */
    private static void dfs(String digits, int index, StringBuilder s, Map<Character, String> map, List<String> ans) {
        if (index == digits.length()) {
            ans.add(s.toString());
            return;
        }
        char ch = digits.charAt(index);
        String letters = map.get(ch);
        int length = letters.length();
        for (int i = 0; i < length; i++) {
            s.append(letters.charAt(i));
            dfs(digits, index + 1, s, map, ans);
            s.deleteCharAt(index);
        }
    }
}
