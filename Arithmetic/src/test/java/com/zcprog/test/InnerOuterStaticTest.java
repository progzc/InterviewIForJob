package com.zcprog.test;

/**
 * @Description 内部类/静态内部类/外部类
 * @Author zhaochao
 * @Date 2021/3/13 9:50
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class InnerOuterStaticTest {
    private int number = 100;

    public static void main(String[] args) {
        InnerOuterStaticTest outer = new InnerOuterStaticTest();
        // 1. 内部类可以访问其所在类的属性（包括所在类的私有属性），内部类创建自身对象需要先创建其所在类的对象
        Inner inner = outer.new Inner();
        inner.paint();
        System.out.println("----------------------分割线--------------------------");
        // 2. 可以定义内部接口，且可以定义另外一个内部类实现这个内部接口
        Inner2 inner2 = outer.new Inner2();
        inner2.doFly();
        System.out.println("----------------------分割线--------------------------");
        // 3. 可以在方法体内定义一个内部类，方法体内的内部类可以完成一个基于虚方法形式的回调操作
        Inner3 inner3 = outer.new Inner3();
        inner3.getCar().paint();
        System.out.println("----------------------分割线--------------------------");
        // 4. 内部类不能定义static元素
        Inner4 inner4 = outer.new Inner4();
        System.out.println("----------------------分割线--------------------------");
        // 5. 内部类可以多层嵌套
        Inner5 inner5 = outer.new Inner5();
        Inner5.Inner6 inner6 = inner5.new Inner6();
        // 6. 一旦内部类使用static修饰，那么此时这个内部类就升级为顶级类
        // 除了写在一个类的内部以外，static内部类具备所有外部类的特性
        Inner7.paint();
        // 7. 与static内部类不同，内部接口自动具备静态属性
    }

    public interface Fly {
        void doFly();
    }

    public static class Inner7 {
        public static void paint() {
            System.out.println("I'm coming from Inner7");
        }
    }

    public class Inner {
        private int number = 200;

        private void paint() {
            int number = 500;
            // 局部覆盖
            System.out.println(number); // 500
            // 通过this引用内部类的成员属性
            System.out.println(this.number); // 200
            // 通过外部类名加this的方式访问外部类成员属性
            System.out.println(InnerOuterStaticTest.this.number); // 100
        }
    }

    public class Inner2 implements Fly {
        @Override
        public void doFly() {
            System.out.println("I'm SuperMan");
        }
    }

    public class Inner3 {
        public M_Car getCar() {
            class BMW extends M_Car {
                @Override
                public void paint() {
                    System.out.println("BMW");
                }
            }
            return new BMW();
        }
    }

    public class Inner4 {
//        // 内部类不能定义static元素
//        public static void paint() {
//        }
    }

    public class Inner5 {
        // 嵌套内部类
        public class Inner6 {

        }
    }
}

class M_Car {
    public void paint() {
        System.out.println("car");
    }
}

class SuperM implements InnerOuterStaticTest.Fly {
    @Override
    public void doFly() {
    }
}
