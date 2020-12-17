package com.zcprog.arithmetic;

import java.util.HashSet;
import java.util.Set;

/**
 * @Description 模拟行走机器人
 * @Author zhaochao
 * @Date 2020/12/17 14:24
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0874_WalkingRobotSimulation {
    /**
     * LeetCode地址：https://leetcode-cn.com/problems/walking-robot-simulation/submissions/
     * 机器人在一个无限大小的网格上行走，从点 (0, 0) 处开始出发，面向北方。该机器人可以接收以下三种类型的命令：
     * -2：向左转 90 度
     * -1：向右转 90 度
     * 1 <= x <= 9：向前移动 x 个单位长度
     * 在网格上有一些格子被视为障碍物。
     * 第 i 个障碍物位于网格点  (obstacles[i][0], obstacles[i][1])
     * 如果机器人试图走到障碍物上方，那么它将停留在障碍物的前一个网格方块上，但仍然可以继续该路线的其余部分。
     * 返回从原点到机器人的最大欧式距离的平方。
     * <p>
     * 示例 1：
     * 输入: commands = [4,-1,3], obstacles = []
     * 输出: 25
     * 解释: 机器人将会到达 (3, 4)
     * <p>
     * 示例 2：
     * 输入: commands = [4,-1,4,-2,4], obstacles = [[2,4]]
     * 输出: 65
     * 解释: 机器人在左转走到 (1, 8) 之前将被困在 (1, 4) 处
     * <p>
     * 提示：
     * 0 <= commands.length <= 10000
     * 0 <= obstacles.length <= 10000
     * -30000 <= obstacle[i][0] <= 30000
     * -30000 <= obstacle[i][1] <= 30000
     * 答案保证小于 2 ^ 31
     */
    public static void main(String[] args) {
        int[] commands = {4, -1, 4, -2, 4};
        int[][] obstacles = {{2, 4}};
//        int[][] obstacles = {{}};
        System.out.println(hashSolve(commands, obstacles));
    }

    /**
     * 哈希表
     * 时间复杂度：O(n+k)
     * 空间复杂度: O(k)
     */
    private static int hashSolve(int[] commands, int[][] obstacles) {
        // 单位矢量
        // (0,1)代表向北
        // (1,0)代表向东
        // (0,-1)代表向南
        // (-1,0)代表向西
        int[] dx = new int[]{0, 1, 0, -1};
        int[] dy = new int[]{1, 0, -1, 0};
        // 当前的坐标点(x,y)
        int x = 0;
        int y = 0;
        // 代表方位：0-向北；1-向东；2-向南；3-向西
        int di = 0;

        // 将二维坐标点映射到一个散点值，并且保证不发生碰撞（类似于哈希值）
        Set<Long> obstacleSet = new HashSet();
        for (int[] obstacle : obstacles) {
            if (obstacle.length >= 1) {
                // +30000保证obstacle[i][0]和obstacle[i][1]在[0,60000]以内,正好可以用一个long的低16位存储
                // 且避免处理负数
                long ox = (long) obstacle[0] + 30000;
                long oy = (long) obstacle[1] + 30000;
                // 左移保证long值的二进制的低位第0-15位存储oy,低位第16-47位存储ox
                obstacleSet.add((ox << 16) + oy);
            }
        }

        int ans = 0;
        for (int cmd : commands) {
            if (cmd == -2) {
                di = (di + 3) % 4;
            } else if (cmd == -1) {
                di = (di + 1) % 4;
            } else {
                for (int k = 0; k < cmd; ++k) {
                    int nx = x + dx[di];
                    int ny = y + dy[di];
                    long code = (((long) nx + 30000) << 16) + ((long) ny + 30000);
                    if (!obstacleSet.contains(code)) {
                        x = nx;
                        y = ny;
                        ans = Math.max(ans, x * x + y * y);
                    }
                }

            }
        }

        return ans;
    }
}
