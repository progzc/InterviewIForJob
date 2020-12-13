package com.zcprog.arithmetic;

import java.util.*;

/**
 * @Description 有效的括号
 * @Author zhaochao
 * @Date 2020/12/13 14:56
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0020_ValidParentheses {
    /**
     * LeetCode地址：https://leetcode-cn.com/problems/valid-parentheses/description
     * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
     * <p>
     * 有效字符串需满足：
     * 左括号必须用相同类型的右括号闭合。
     * 左括号必须以正确的顺序闭合。
     * 注意空字符串可被认为是有效字符串。
     * <p>
     * 示例 1:
     * 输入: "()"
     * 输出: true
     * <p>
     * 示例 2:
     * 输入: "()[]{}"
     * 输出: true
     * <p>
     * 示例 3:
     * 输入: "(]"
     * 输出: false
     * <p>
     * 示例 4:
     * 输入: "([)]"
     * 输出: false
     * <p>
     * 示例 5:
     * 输入: "{[]}"
     * 输出: true
     */
    public static void main(String[] args) {
        String[] strs = {"()[]{}", "(]", "([)]", "{[]}"};
        String[] strs2 = {"()[]{}", "(]", "([)]", "{[]}"};
        System.out.println("--------------栈解法--------------");
        long start1 = System.currentTimeMillis();
        Arrays.stream(strs).forEach(str -> System.out.println(stackSolve(str)));
        long end1 = System.currentTimeMillis();
        System.out.println("栈解法共耗时：" + (end1 - start1) + "ms");
        System.out.println("--------------正则表达式解法--------------");
        long start2 = System.currentTimeMillis();
        Arrays.stream(strs2).forEach(str -> System.out.println(regexSolve(str)));
        long end2 = System.currentTimeMillis();
        System.out.println("正则表达式解法共耗时：" + (end2 - start2) + "ms");
    }

    /**
     * 栈
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    private static boolean stackSolve(String str) {
        int n = str.length();
        if (n % 2 == 1) {
            return false;
        }

        Map<Character, Character> pairs = new HashMap<Character, Character>(16) {
            {
                put(')', '(');
                put(']', '[');
                put('}', '{');
            }
        };

        Deque<Character> stack = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            char ch = str.charAt(i);
            if (pairs.containsKey(ch)) {
                if (stack.isEmpty() || !stack.peek().equals(pairs.get(ch))) {
                    return false;
                }
                stack.pop();
            } else {
                stack.push(ch);
            }
        }
        return stack.isEmpty();
    }

    /**
     * 正则表达式
     * 这里测试，正则表达式解法比栈解法快很多
     */
    private static boolean regexSolve(String str) {
        int n = str.length();
        if (n % 2 == 1) {
            return false;
        }
        while (str.contains("[]") || str.contains("{}") || str.contains("()")) {
            str = str.replace("[]", "").replace("{}", "").replace("()", "");
        }
        return str.length() == 0;
    }
}
