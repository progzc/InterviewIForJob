package org.fenixsoft.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 频繁GC导致的OOM
 * @Author zhaochao
 * @Date 2021/1/6 9:55
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
//-Xms10m -Xmx10m -XX:MaxDirectMemorySize=5m -XX:+PrintGCDetails
public class GCOverheadDemo {
    public static void main(String[] args) {
        int i = 0;
        List<String> list = new ArrayList<>();
        try {

            while (true) {
                list.add(String.valueOf(++i).intern());
            }
        } catch (Exception e) {
            System.out.println("************i" + i);
            e.printStackTrace();
            throw e;
        }
    }
}
