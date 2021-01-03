package org.fenixsoft.jvm;

/**
 * @Description 成员变量与局部变量测试
 * @Author zhaochao
 * @Date 2021/1/3 10:26
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class VariableTest {
    static int s;
    int i;
    int j;

    {
        int i = 1;
        i++;
        j++;
        s++;
    }

    public static void main(String[] args) {
        VariableTest var1 = new VariableTest();
        VariableTest var2 = new VariableTest();
        var1.test(10);
        var1.test(20);
        var2.test(30);
        System.out.println(var1.i + "," + var1.j + "," + var1.s);
        System.out.println(var2.i + "," + var2.j + "," + var2.s);
    }

    public void test(int j) {
        j++;
        i++;
        s++;
    }
}
