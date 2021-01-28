package com.zcprog.basic;

import org.junit.Test;

import java.io.*;

/**
 * @Description 序列化与反序列化的测试
 * @Author zhaochao
 * @Date 2021/1/28 12:06
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class StudentSerializable {
    /**
     * 1.通过序列化与反序列化创建对象
     * 2.通过new创建对象
     */
    @Test
    public void test1() throws IOException, ClassNotFoundException {
        Student student = new Student();
        student.setId(1);
        student.setName("张三");

        Address addr = new Address();
        addr.setName("北京市海淀区xx街道");
        student.setAddress(addr);

        // 序列化就是将对象变为输出字节流
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("student.obj"));
        os.writeObject(student);
        os.writeInt(10);
        os.writeObject(null);
        os.close();

        // 反序列化
        ObjectInputStream is = new ObjectInputStream(new FileInputStream("student.obj"));
        Student s = (Student) is.readObject();
        System.out.println(s.getId());
        System.out.println(s.getName());
        System.out.println(s.getAddress());
        System.out.println(is.readInt());
        if (is.readObject() == null) {
            System.out.println("结束了");
        }
        is.close();
        System.out.println(student == s);
    }

    /**
     * 1.通过序列化与反序列化创建对象：写入写出在内存中
     */
    @Test
    public void test4() throws IOException, ClassNotFoundException {
        Student student = new Student();
        student.setId(1);
        student.setName("张三");

        Address addr = new Address();
        addr.setName("北京市海淀区xx街道");
        student.setAddress(addr);
        System.out.println(student);
        System.out.println(student.getAddress());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);

        oos.writeObject(student);
        oos.close();
        baos.close();

        byte[] bytes = baos.toByteArray();
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Student copy = (Student) ois.readObject();
        System.out.println(copy);
        System.out.println(copy.getAddress());
        ois.close();
        bais.close();
    }

    /**
     * 3.通过反射创建对象
     */
    @Test
    public void test2() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<?> clazz = Class.forName("com.zcprog.basic.Student");
        Student student = (Student) clazz.newInstance();
        System.out.println(student);
    }

    /**
     * 4.通过clone方法创建对象：浅拷贝/深拷贝
     */
    @Test
    public void test3() throws CloneNotSupportedException {
        Student student1 = new Student();
        student1.setId(1);
        student1.setName("张三");
        Address addr = new Address();
        addr.setName("北京市海淀区xx街道");
        student1.setAddress(addr);

        Student student2 = (Student) student1.clone();
        System.out.println(student1);
        System.out.println(student2);
        System.out.println(student1.getAddress());
        System.out.println(student2.getAddress());
    }
}
