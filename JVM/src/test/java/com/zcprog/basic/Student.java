package com.zcprog.basic;

import java.io.Serializable;

/**
 * @Description 实体类
 * @Author zhaochao
 * @Date 2021/1/28 12:00
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class Student implements Serializable, Cloneable {

    private static final long serialVersionUID = 6032950553551847250L;
    // 要求对象中的所有属性也都是可以序列化
    private int id;
    private String name;
    private transient Address address; // 临时的，瞬时的transient

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Student student = (Student) super.clone();
        student.address = (Address) address.clone();
        return student;
    }
}

class Address implements Serializable, Cloneable {
    private static final long serialVersionUID = -492328707130378802L;
    private String name;

    public Address() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
