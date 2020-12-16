package com.zcprog.arithmetic;

import java.util.Stack;

/**
 * @Description 用栈实现队列
 * @Author zhaochao
 * @Date 2020/12/16 16:48
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0232_ImplementQueueUsingStacks {
    /**
     * LeetCode地址：https://leetcode-cn.com/problems/implement-queue-using-stacks/
     * 使用栈实现队列的下列操作：
     * push(x) -- 将一个元素放入队列的尾部。
     * pop() -- 从队列首部移除元素。
     * peek() -- 返回队列首部的元素。
     * empty() -- 返回队列是否为空。
     * <p>
     * 示例:
     * MyQueue queue = new MyQueue();
     * queue.push(1);
     * queue.push(2);
     * queue.peek();  // 返回 1
     * queue.pop();   // 返回 1
     * queue.empty(); // 返回 false
     * 说明:
     * 你只能使用标准的栈操作 -- 也就是只有 push to top, peek/pop from top, size, 和 is empty 操作是合法的。
     * 你所使用的语言也许不支持栈。你可以使用 list 或者 deque（双端队列）来模拟一个栈，只要是标准的栈操作即可。
     * 假设所有操作都是有效的 （例如，一个空的队列不会调用 pop 或者 peek 操作）。
     */
    public static void main(String[] args) {
        System.out.println("-------------双栈模拟队列-------------");
        MyQueue obj = new MyQueue();
        obj.push(1);
        obj.push(2);
        obj.push(3);
        System.out.println(obj.peek());
        System.out.println(obj.pop());
        System.out.println(obj.pop());
        System.out.println(obj.empty());
        System.out.println("-------------双栈模拟队列（优化）-------------");
        MyQueue2 obj2 = new MyQueue2();
        obj2.push(1);
        obj2.push(2);
        obj2.push(3);
        System.out.println(obj2.peek());
        System.out.println(obj2.pop());
        System.out.println(obj2.pop());
        System.out.println(obj2.empty());
    }

    /**
     * 入队时间复杂度O(1)
     * 出队时间复杂度O(1)
     */
    public static class MyQueue2 {
        private Stack<Integer> s1 = new Stack<>();
        private Stack<Integer> s2 = new Stack<>();
        private int front = 0;

        public MyQueue2() {
        }

        /**
         * 入队:使用两个栈
         * 时间复杂度：O(1)
         * 空间复杂度：O(1)
         */
        public void push(int x) {
            if (s1.empty()) {
                front = x;
            }
            s1.push(x);
        }

        /**
         * 出队
         * 时间复杂度：O(n)
         * 空间复杂度：O(1)
         */
        public int pop() {
            if (s2.isEmpty()) {
                while (!s1.isEmpty()) {
                    s2.push(s1.pop());
                }
            }
            return s2.pop();
        }

        /**
         * 获取元素
         * 时间复杂度：O(1)
         * 空间复杂度：O(1)
         */
        public int peek() {
            if (!s2.isEmpty()) {
                return s2.peek();
            }
            return front;
        }

        /**
         * 判断是否为空
         * 时间复杂度：O(1)
         * 空间复杂度：O(1)
         */
        public boolean empty() {
            return s1.isEmpty() && s2.isEmpty();
        }
    }

    /**
     * 入队时间复杂度O(n)
     * 出队时间复杂度O(1)
     */
    public static class MyQueue {
        private Stack<Integer> s1 = new Stack<>();
        private Stack<Integer> s2 = new Stack<>();

        public MyQueue() {
        }

        /**
         * 入队:使用两个栈
         * 时间复杂度：O(n)
         * 空间复杂度：O(n)
         */
        public void push(int x) {
            while (!s1.isEmpty()) {
                s2.push(s1.pop());
            }
            s1.push(x);
            while (!s2.isEmpty()) {
                s1.push(s2.pop());
            }
        }

        /**
         * 出队
         * 时间复杂度：O(1)
         * 空间复杂度：O(1)
         */
        public int pop() {
            return s1.pop();
        }

        /**
         * 获取元素
         * 时间复杂度：O(1)
         * 空间复杂度：O(1)
         */
        public int peek() {
            return s1.peek();
        }


        /**
         * 判断是否为空
         * 时间复杂度：O(1)
         * 空间复杂度：O(1)
         */
        public boolean empty() {
            return s1.isEmpty();
        }
    }
}
