package com.progzc.flyweight;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 封装创建享元的复杂机制
 * @Author zhaochao
 * @Date 2020/12/12 20:39
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class TreeFactory {
    static Map<String, TreeType> treeTypes = new HashMap<>();

    public static TreeType getTreeType(String name, Color color, String otherTreeData) {
        TreeType result = treeTypes.get(name);
        if (result == null) {
            result = new TreeType(name, color, otherTreeData);
            treeTypes.put(name, result);
        }
        return result;
    }
}
