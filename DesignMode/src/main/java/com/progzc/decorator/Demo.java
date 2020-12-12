package com.progzc.decorator;

import org.junit.Test;

/**
 * @Description 客户端对象
 * @Author zhaochao
 * @Date 2020/12/12 11:04
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class Demo {
    @Test
    public void test() {
        String path = System.getProperty("user.dir").replaceAll("\\\\", "/")
                + "/src/main/resources/DecoratorPattern_OutputDemo.txt";
        String salaryRecords = "Name,Salary\nJohn Smith,100000\nSteven Jobs,912000";
        DataSource encoded = new CompressionDecorator(
                new EncryptionDecorator(new FileDataSource(path)));
        encoded.writeData(salaryRecords);
        DataSource plain = new FileDataSource(path);

        System.out.println("- Input ----------------");
        System.out.println(salaryRecords);
        System.out.println("- Encoded --------------");
        System.out.println(plain.readData());
        System.out.println("- Decoded --------------");
        System.out.println(encoded.readData());
    }
}
