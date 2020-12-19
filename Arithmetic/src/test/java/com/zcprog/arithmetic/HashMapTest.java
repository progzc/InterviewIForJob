package com.zcprog.arithmetic;

/**
 * @Description HashMap七种遍历方式性能测试
 * @Author zhaochao
 * @Date 2020/12/19 10:04
 * @Email zcprog@foxmail.com
 * @Version V1.0
 * 测试结果
 * Benchmark                       Mode  Cnt  Score   Error   Units
 * HashMapTest.entrySet           thrpt    5  4.181 ± 0.170  ops/ms
 * HashMapTest.forEachEntrySet    thrpt    5  4.224 ± 0.837  ops/ms
 * HashMapTest.forEachKeySet      thrpt    5  4.334 ± 0.297  ops/ms
 * HashMapTest.keySet             thrpt    5  4.304 ± 0.440  ops/ms
 * HashMapTest.lambda             thrpt    5  4.237 ± 0.179  ops/ms
 * HashMapTest.parallelStreamApi  thrpt    5  2.426 ± 0.273  ops/ms
 * HashMapTest.streamApi          thrpt    5  4.525 ± 2.226  ops/ms
 */

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput) // 测试类型：吞吐量
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS) // 预热 2 轮，每次 1s
@Measurement(iterations = 5, time = 3, timeUnit = TimeUnit.SECONDS) // 测试 5 轮，每次 3s
@Fork(1) // fork 1 个线程
@State(Scope.Thread) // 每个测试线程一个实例
public class HashMapTest {
    static Map<Integer, String> map = new HashMap() {{
        // 添加数据
        for (int i = 0; i < 10; i++) {
            put(i, "val:" + i);
        }
    }};

    public static void main(String[] args) throws RunnerException {
        // 启动基准测试
        Options opt = new OptionsBuilder()
                .include(HashMapTest.class.getSimpleName()) // 要导入的测试类
                .output("C:/Users/zcpro/Desktop/jmh-map.log") // 输出测试结果的文件
                .build();
        new Runner(opt).run(); // 执行测试
    }

    @Benchmark
    public void entrySet() {
        // 使用迭代器（Iterator）EntrySet的方式进行遍历
        Iterator<Map.Entry<Integer, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, String> entry = iterator.next();
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }
    }

    @Benchmark
    public void keySet() {
        // 使用迭代器（Iterator）KeySet的方式进行遍历
        Iterator<Integer> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            Integer key = iterator.next();
            System.out.println(key);
            System.out.println(map.get(key));
        }
    }

    @Benchmark
    public void forEachEntrySet() {
        // 使用ForEach EntrySet的方式进行遍历
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }
    }

    @Benchmark
    public void forEachKeySet() {
        // 使用ForEach KeySet的方式进行遍历
        for (Integer key : map.keySet()) {
            System.out.println(key);
            System.out.println(map.get(key));
        }
    }

    @Benchmark
    public void lambda() {
        // 使用Lambda表达式的方式进行遍历
        map.forEach((key, value) -> {
            System.out.println(key);
            System.out.println(value);
        });
    }

    @Benchmark
    public void streamApi() {
        // 使用Streams API单线程的方式进行遍历
        map.entrySet().stream().forEach((entry) -> {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        });
    }

    @Benchmark
    public void parallelStreamApi() {
        // 使用Streams API多线程的方式进行遍历
        map.entrySet().parallelStream().forEach((entry) -> {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        });
    }
}
