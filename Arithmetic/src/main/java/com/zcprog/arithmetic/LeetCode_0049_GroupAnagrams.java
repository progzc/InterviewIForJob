package com.zcprog.arithmetic;

import java.util.*;

/**
 * @Description 字母异位词分组
 * @Author zhaochao
 * @Date 2020/12/22 13:11
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0049_GroupAnagrams {
    /**
     * LeetCode地址：https://leetcode-cn.com/problems/group-anagrams/
     * 给定一个字符串数组，将字母异位词组合在一起。字母异位词指字母相同，但排列不同的字符串。
     * <p>
     * 示例:
     * 输入: ["eat", "tea", "tan", "ate", "nat", "bat"]
     * 输出:
     * [
     * ["ate","eat","tea"],
     * ["nat","tan"],
     * ["bat"]
     * ]
     * 说明：
     * 所有输入均为小写字母。
     * 不考虑答案输出的顺序。
     */
    public static void main(String[] args) {
        String[] strs = {"eat", "tea", "tan", "ate", "nat", "bat"};
//        String[] strs = {"", ""};
        // 排序+哈希表
        System.out.println(violenceSolve1(strs));
        // 排序+哈希表+优化
        System.out.println(violenceSolve2(strs));
        // 计数+哈希表
        System.out.println(countSolve(strs));
    }

    /**
     * 计数+哈希表
     * 时间复杂度：O(n*(k+26))
     * 空间复杂度：O(n*(k+26))
     */
    private static List<List<String>> countSolve(String[] strs) {
        if (strs == null) {
            return null;
        }
        if (strs.length < 1) {
            return new ArrayList<List<String>>();
        }
        Map<String, List<String>> map = new HashMap<>(16);
        for (String str : strs) {
            int[] counts = new int[26];
            int length = str.length();
            // 1.统计每个字母出现的次数
            for (int i = 0; i < length; i++) {
                counts[str.charAt(i) - 'a']++;
            }
            // 2.将每个出现次数大于0的字母和出现次数按顺序拼接成字符串，作为哈希表的键
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < 26; i++) {
                if (counts[i] != 0) {
                    sb.append((char) ('a' + i));
                    sb.append(counts[i]);
                }
            }
            String key = sb.toString();
            List<String> list = map.getOrDefault(key, new ArrayList<String>());
            list.add(str);
            map.put(key, list);
        }
        // 3.取出哈希表中存储的值
        return new ArrayList<List<String>>(map.values());
    }


    /**
     * 排序+哈希表+优化
     * 时间复杂度：O(n*k*log(k))
     * 空间复杂度：O(n*k)
     */
    private static List<List<String>> violenceSolve2(String[] strs) {
        if (strs == null) {
            return null;
        }
        if (strs.length < 1) {
            return new ArrayList<List<String>>();
        }
        Map<String, List<String>> map = new HashMap<>(16);
        for (String str : strs) {
            char[] array = str.toCharArray();
            Arrays.sort(array);
            // 直接通过构造方法将字符数组转换为字符串
            String key = new String(array);
            List<String> list = map.getOrDefault(key, new ArrayList<String>());
            list.add(str);
            map.put(key, list);
        }
        // 直接将entries放入List中
        return new ArrayList<List<String>>(map.values());
    }


    /**
     * 排序+哈希表
     * 时间复杂度：O(n*k*log(k))
     * 空间复杂度：O(n*k)
     */
    private static List<List<String>> violenceSolve1(String[] strs) {
        if (strs == null) {
            return null;
        }
        List<List<String>> ans = new ArrayList<>();
        if (strs.length < 1) {
            return ans;
        }
        Map<String, List<String>> map = new HashMap<>(16);
        for (int i = 0; i < strs.length; i++) {
            String str = sort(strs[i]);
            if (!map.containsKey(str)) {
                map.put(str, new ArrayList<>(Arrays.asList(strs[i])));
            } else {
                map.get(str).add(strs[i]);
            }
        }

        Set<Map.Entry<String, List<String>>> entries = map.entrySet();
        for (Map.Entry<String, List<String>> entry : entries) {
            ans.add(new ArrayList<String>(entry.getValue()));
        }
        return ans;
    }

    private static String sort(String str) {
        if (str == null || str.length() < 1) {
            return str;
        }
        char[] chs = str.toCharArray();
        Arrays.sort(chs);
        return Arrays.toString(chs).replaceAll("[\\s\\[\\],]", "");
    }
}
