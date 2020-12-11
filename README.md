# 1 设计模式

## 1.1 单例设计模式

**模式定义**：保证一个类只有一个实例，并且提供一个全局访问点。

**应用场景**：重量级的对象，不需要多个实例，如线程池、数据库连接池。

### 1.1.1 懒汉模式

**概念**：延迟加载，只有在真正使用的时候，才开始实例化。

```java
public class LazySingletonTest {
    public static void main(String[] args) {
        // 单线程情况
        LazySingleton instance = LazySingleton.getInstance();
        LazySingleton instance1 = LazySingleton.getInstance();
        System.out.println(instance == instance1); // 输出 true
    }
}

class LazySingleton{
    private static LazySingleton instance;
    private LazySingleton(){

    }
    public static LazySingleton getInstance(){
        if (instance == null){
            instance = new LazySingleton();
        }
        return instance;
    }
}
```

**存在问题**：

- 多线程情况下会出现线程安全问题
- double check 加锁优化
- 编译器（JIT），CPU有可能对指令进行重新排序，导致使用到尚未初始化的实例，可以通过添加volatile关键字进行修饰；对于volatile修饰的字段，可以防止指令重排。

下面详细进行介绍：如下代码可以演示在多线程情况下会出现线程安全问题。

```java
public class LazySingletonTest {
    public static void main(String[] args) {
        //多线程情况
        new Thread( () -> {
            LazySingleton instance = LazySingleton.getInstance();
            System.out.println(instance); // 输出LazySingleton@51a22f1d
        }).start();
        new Thread( () -> {
            LazySingleton instance = LazySingleton.getInstance();
            System.out.println(instance); // 输出LazySingleton@6706a70b
        }).start();
    }
}

class LazySingleton{
    private static LazySingleton instance;
    private LazySingleton(){

    }
    public static LazySingleton getInstance(){
        if (instance == null){
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            instance = new LazySingleton();
        }
        return instance;
    }
}
```

> 解决方法一：在getInstance()方法上添加synchronized关键字可以解决这一问题，但是会导致性能降低。

```java
class LazySingleton{
    private static LazySingleton instance;
    private LazySingleton(){

    }
    public synchronized static LazySingleton getInstance(){ // 添加synchronized关键字
        if (instance == null){
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            instance = new LazySingleton();
        }
        return instance;
    }
}
```

> 解决方法二：为了解决方法一的性能问题，可以采用double check 加锁优化。

```java
class LazySingleton{
    private static LazySingleton instance;
    private LazySingleton(){

    }
    public static LazySingleton getInstance(){
        if (instance == null){
            synchronized(LazySingleton.class){ // double check 加锁优化
                if (instance == null){
                    instance = new LazySingleton();
                }
            }
        }
        return instance;
    }
}
```

> 解决办法三：方法二可能在编译层出现重排序问题，添加volatile关键字可以保证编译时先进行初始化，再引用赋值，否则可能会出现空指针问题。

```java
class LazySingleton{
    private volatile static LazySingleton instance; // 添加volatile关键字
    private LazySingleton(){

    }
    public static LazySingleton getInstance(){
        if (instance == null){
            synchronized(LazySingleton.class){
                if (instance == null){
                    // 字节码层运行细节
                    // 1.分配空间-->2.初始化-->3.引用赋值
                    // 添加volatile可以防止重排序（即先进行初始化，再引用赋值，保证二者不会颠倒执行），否则可能会出现空指针问题
                    instance = new LazySingleton();
                }
            }
        }
        return instance;
    }
}
```

### 1.1.2 饿汉模式

**概念**：类加载的初始化阶段就完成了实例的初始化，本质上就是借助于jvm类加载机制，保证实例的唯一性。

**类加载过程**：

1. 加载二进制数据到内存中，生成对应的Class数据结构。

2. 连接：a.验证；b.准备（给类的静态成员变量赋默认值）；c.解析。

3. 初始化：给类的静态变量赋初值。

   只有在真正使用对应的类时，才会触发初始化。（如当前类时启动类即main函数所在类，直接进行new操作、访问静态属性、访问静态方法、用反射访问类、初始化一个类的子类）

```java
public class HungrySingletonTest {
    public static void main(String[] args) {
        HungrySingleton instance = HungrySingleton.getInstance();
        HungrySingleton instance1 = HungrySingleton.getInstance();
        System.out.println(instance == instance1); // 输出true
    }
}
class HungrySingleton{
    private static HungrySingleton instance = new HungrySingleton();
    private HungrySingleton(){

    }
    public static HungrySingleton getInstance(){
        return instance;
    }
}s
```

### 1.1.3 静态内部类

**概念**：

1. 本质上是利用类的加载机制来保证线程安全。
2. 只有在实际使用的时候，才会触发静态内部类的初始化，所以也是懒加载的一种形式。

```java
class InnerClassSingleton{
    private static class InnerClassHolder{
        private static InnerClassSingleton instance = new InnerClassSingleton();
    }
    private InnerClassSingleton(){

    }
    public static InnerClassSingleton getInstance(){
        return InnerClassHolder.instance;
    }
}
```

> - 使用饿汉模式和静态内部类模式创建的单例类，在使用反射时不能保证单例性。
>
> ```java
> public class SingletonTest {
>     public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
>         Constructor<InnerClassSingleton> declaredConstructor = InnerClassSingleton.class.getDeclaredConstructor();
>         declaredConstructor.setAccessible(true);
>         InnerClassSingleton innerClassSingleton = declaredConstructor.newInstance();
>         InnerClassSingleton instance = InnerClassSingleton.getInstance();
>         System.out.println(innerClassSingleton == instance); // 输出false
>     } 
> }
> ```
>
> - 可以在饿汉模式和静态内部类模式对该问题进行防护，**使用懒汉模式则不能进行防护**。
>
> ```java
> class InnerClassSingleton{
>     private static class InnerClassHolder{
>         private static InnerClassSingleton instance = new InnerClassSingleton();
>     }
>     private InnerClassSingleton(){
>         if(InnerClassHolder.instance!=null){
>             throw new RuntimeException("单例不允许多个实例");
>         }
>     }
>     public static InnerClassSingleton getInstance(){
>         return InnerClassHolder.instance;
>     }
> }
> ```









## ## HeadFirst设计模式

**OO设计原则：**

1. 找出应用中可能需要变化之处，把它们独立出来，不要和那些不需要变化的代码混在一起。（策略模式）
2. 针对接口编程，而不是针对实现编程。（策略模式）
3. 多用组合，少用继承。（策略模式）
4. 为了交互对象之间的松耦合设计而努力。（观察者模式）
5. 类应该对扩展开放，对修改关闭。（装饰者模式）
6. 

### ### 策略模式

**策略模式（Strategy Pattern）**：定义了算法族，分别封装起来，让它们之间可以互相替换，此模式让算法的变化独立于使用算法的客户。

"模拟鸭子的世界"：

![StrategyPattern](README.assets/StrategyPattern.png)

### ### 观察者模式

**观察者模式（Observer Pattern）**：定义了对象之间的一对多依赖，这样一来，当一个对象改变状态时，它的所有依赖者都会收到通知并自动更新。观察者模式提供了一种对象设计，让主题和观察者之间松耦合。松耦合的设计之所以能让我们建立有弹性的OO系统，能够应对变化，是因为对象之间的互相依赖降到了最低。

"气象检测站"：

**自定义观察者模式**：

![UserDefinedObserverPattern](README.assets/UserDefinedObserverPattern.png)

**JDK观察者模式**：Java提供的观察者模式：Observable类和Observer接口。

需要注意的是：Observable是一个类，**违反了OO设计原则（针对接口编程，而不是针对实现编程）**。由于java不支持多重继承，限制了Observable的复用潜力；此外，Observable的serChange方法被定义成protected，导致只有继承Observable类，才能将创建的Observable实例组合到自己的设计对象中来，**这违反了OO设计又一个原则（多用组合，少用继承）**。

![JDKObserverPattern](README.assets/JDKObserverPattern.png)

### ### 装饰者模式

**装饰者模式（Decorator Pattern）**：动态地将责任附加到对象上；若要扩展功能，装饰者提供了比继承更有弹性的替代方案。

"咖啡与调料"

**自定义装饰者模式**：

![UserDefinedDecoratorPattern](README.assets/UserDefinedDecoratorPattern.png)

**增强Java I/O的装饰者模式**：

![EnhanceJDKIODecoratorPattern](README.assets/EnhanceJDKIODecoratorPattern.png)

### ### 工厂模式

**工厂模式（Factory Pattern）**：

"比萨工厂"















## ## 设计模式

**本部分图表使用IDEA+PlantUML+Graphviz制作：**

设计模式概览：

![image-20201211163413123](README.assets/image-20201211163413123.png)

### ### 工厂方法模式

**工厂方法模式：**一种创建型设计模式， 其在父类中提供一个创建对象的方法， 允许子类决定实例化对象的类型。

作为抽象工厂模式的孪生兄弟，工厂方法模式定义了一个创建对象的接口，但由子类决定要实例化的类是哪一个，也就是说工厂方法模式让实例化推迟到子类。工厂方法模式非常符合"开闭原则"，当需要增加一个新的产品时，我们只需要增加一个具体的产品类和与之对应的具体工厂即可，无须修改原有系统。同时在工厂方法模式中用户只需要知道生产产品的具体工厂即可，无须关系产品的创建过程，甚至连具体的产品类名称都不需要知道。虽然他很好的符合了“开闭原则”，但是由于每新增一个新产品时就需要增加两个类，这样势必会导致系统的复杂度增加。

**Java类库中的应用：**

- `java.util.Calendar#getInstance()`
- `java.util.ResourceBundle#getBundle()`
- `java.text.NumberFormat#getInstance()`
- `java.nio.charset.Charset#forName()`
- `java.net.URLStreamHandlerFactory#createURLStreamHandler(String)`
- `java.util.EnumSet#of()`
- `javax.xml.bind.JAXBContext#createMarshaller()`

![image-20201211132921630](README.assets/image-20201211132921630.png)

### ### 抽象工厂模式

**抽象工厂模式**：一种创建型设计模式， 它能创建一系列相关的对象， 而无需指定其具体类。

提供一个接口，用于创建相关或者依赖对象的家族，而不需要明确指定具体类。他允许客户端使用抽象的接口来创建一组相关的产品，而不需要关系实际产出的具体产品是什么。这样一来，客户就可以从具体的产品中被解耦。它的优点是隔离了具体类的生成，使得客户端不需要知道什么被创建了，而缺点就在于新增新的行为会比较麻烦，因为当添加一个新的产品对象时，需要更加需要更改接口及其下所有子类。

**Java类库中的应用：**

- `javax.xml.parsers.DocumentBuilderFactory#newInstance()`
- `javax.xml.transform.TransformerFactory#newInstance()`
- `javax.xml.xpath.XPathFactory#newInstance()`

![image-20201211120728931](README.assets/image-20201211120728931.png)

### ### 建造者模式

**建造者模式（也称为生成器模式）：**一种创建型设计模式， 使你能够分步骤创建复杂对象。 该模式允许你使用相同的创建代码生成不同类型和形式的对象。

对于建造者模式而言，它主要是将一个复杂对象的构建与表示分离，使得同样的构建过程可以创建不同的表示。适用于那些产品对象的内部结构比较复杂。建造者模式将复杂产品的构建过程封装分解在不同的方法中，使得创建过程非常清晰，能够让我们更加精确的控制复杂产品对象的创建过程，同时它隔离了复杂产品对象的创建和使用，使得相同的创建过程能够创建不同的产品。但是如果某个产品的内部结构过于复杂，将会导致整个系统变得非常庞大，不利于控制，同时若几个产品之间存在较大的差异，则不适用建造者模式，毕竟这个世界上存在相同点大的两个产品并不是很多，所以它的使用范围有限。

**Java类库中的应用：**

- `java.lang.StringBuilder#append()`
- `java.lang.StringBuffer#append()`
- `java.nio.ByteBuffer#put()`
- `javax.swing.GroupLayout.Group#addComponent()`
- `java.lang.Appendable的所有实现`

![image-20201211153912563](README.assets/image-20201211153912563.png)

**建造者模式的一种简化（去掉Director）：**

```java
public class Car {
    private String carType;
    private int seats;
    private String engine;
    private String transmission;
    private String tripComputer;
    private String gpsNavigator;

    /* 禁止无参构造 */
    private Car(){
        throw new RuntimeException("Can't init...");
    }
    private Car(Builder builder){
        this.carType = builder.carType;
        this.seats = builder.seats;
        this.engine = builder.engine;
        this.transmission = builder.transmission;
        this.tripComputer = builder.tripComputer;
        this.gpsNavigator = builder.gpsNavigator;
    }

    @Override
    public String toString() {
        return "Car{" +
                "carType='" + carType + '\'' +
                ", seats=" + seats +
                ", engine='" + engine + '\'' +
                ", transmission='" + transmission + '\'' +
                ", tripComputer='" + tripComputer + '\'' +
                ", gpsNavigator='" + gpsNavigator + '\'' +
                '}';
    }

    public static final class Builder{
        private String carType;
        private int seats;
        private String engine;
        private String transmission;
        private String tripComputer;
        private String gpsNavigator;

        public Builder(){ }
        public Builder setCarType(String carType){
            this.carType = carType;
            return this;
        }
        public Builder setSeats(int seats){
            this.seats = seats;
            return this;
        }
        public Builder setEngine(String engine){
            this.engine = engine;
            return this;
        }
        public Builder setTransmission(String transmission){
            this.transmission = transmission;
            return this;
        }
        public Builder setTripComputer(String tripComputer){
            this.tripComputer = tripComputer;
            return this;
        }
        public Builder setGpsNavigator(String gpsNavigator){
            this.gpsNavigator = gpsNavigator;
            return this;
        }
        public Car build(){
            return new Car(this);
        }
    }

    public static void main(String[] args) {
        Car car = new Builder()
                .setCarType("SUV")
                .setSeats(6)
                .setEngine("1.6T")
                .setTransmission("混合动力")
                .setTripComputer("12.9寸液晶大屏")
                .setGpsNavigator("北斗")
                .build();
        System.out.println(car);
    }
}
```

### ### 原型模式

**原型模式：**一种创建型设计模式， 使你能够复制已有对象， 而又无需使代码依赖它们所属的类。

在我们应用程序可能有某些对象的结构比较复杂，但是我们又需要频繁的使用它们，如果这个时候我们来不断的新建这个对象势必会大大损耗系统内存的，这个时候我们需要使用原型模式来对这个结构复杂又要频繁使用的对象进行克隆。所以原型模式就是用原型实例指定创建对象的种类，并且通过复制这些原型创建新的对象。它主要应用与那些创建新对象的成本过大时。它的主要优点就是简化了新对象的创建过程，提高了效率，同时原型模式提供了简化的创建结构。

根据拷贝方式不同，原型模式又可以分为：浅克隆和深克隆。

**Java类库中的应用：**

- `java.lang.Object#clone() `

![image-20201211174210511](README.assets/image-20201211174210511.png)









## ## UML类图

1. 泛化关系（继承关系）：**带三角箭头的实线，箭头指向父类**。

2. 实现关系：**带三角箭头的虚线，箭头指向接口**。

3. 关联关系：
   - 双向关联：**有两个普通箭头或者没有箭头的实线。**
   - 单向关联：**带普通箭头的实线，指向被拥有者。**

4. 聚合关系：是整体与部分的关系，且部分可以离开整体而单独存在。聚合是一种强的关联关系。
   - **带空心菱形的实心线，菱形指向整体**。

5. 组合关系：是整体与部分的关系，但部分不能离开整体而单独存在。组合是一种比聚合还要强的关联关系。
   - **带实心菱形的实线，菱形指向整体**。

6. 依赖关系：是一种使用的关系，即一个类的实现需要另一个类的协助。
   - **带普通箭头的虚线，指向被使用者**。

7. 可见性：
   - public：+
   - protected：#
   - private：-
   - package：~

## ## 绘制UML类图

1. IDEA安装PlantUML-integration插件。
2. 安装Graphviz软件。
3. 学习PlantUML语法。
   - 依赖关系：`..>`
   - 继承关系：`--|>`
   - 实现关系：`..|>`
   - 关联关系：`Computer --> Mouse`
   - 聚合关系：`Person o-- IDCard`
   - 组合关系：`Person *-- Head`

> 参考文章博客：[JDK里的设计模式](https://stackoverflow.com/questions/1673841/examples-of-gof-design-patterns-in-javas-core-libraries)、**[《HeadFirst设计模式》]()**、[UML类图](https://blog.csdn.net/qq_40138785/article/details/81065979)、[设计模式总结](https://www.cnblogs.com/pony1223/p/7608955.html)、[PlantUML使用教程](https://www.cnblogs.com/mikisakura/p/12978402.html)、**[PlantUML官方教程](https://plantuml.com/zh/class-diagram)**、**[设计模式的学习](https://refactoringguru.cn/design-patterns/)**

# 2 多线程

## 2.1 多线程基础

### 2.1.1 线程创建的三种方式

**程序**：是指令和数据的有序集合，本身没有任何运行含义，是一个静态的概念。

**进程**：程序执行的过程，是一个动态的概念。进程是系统资源分配的单位，一个进程中可以包含多个线程。

**线程**：线程是CPU调度和执行的单位。Java默认有2个线程（main和gc）。Java本身并不能真正开启线程，底层通过native本地方法使用C语言开启线程。

**并发**：CPU一核，模拟出来多条线程，快速交替。（查看CPU的核数：`Runtime.getRuntime().availableProcessors()`）。并发编程的本质是充分利用CPU的资源。

**并行**：CPU多核，多个线程可以同时执行。（使用线程池提高性能）

**线程创建的三种方式**：

- Thread类

```yaml
# 1. 自定义线程类继承Thread类
# 2. 重写Run方法，编写线程执行体
# 3. 创建线程对象，调用start方法启动线程
```

- Runnable接口

```yaml
# 1. 实现Runnable接口
# 2. 实现重写Run方法，编写线程执行体
# 3. 创建线程对象，调用start方法启动线程
	 这里与Thread类有区别：Runnable实现类必须作为Thread类的构造参数；本质是Thread类实现了Runnable接口。

# 注意：避免单继承局限性，推荐使用Runnable接口
```

- Callable接口

```yaml
# 1. 实现Callable接口，需要返回值类型
# 2. 重写call方法，需要抛出异常
# 3. 创建目标对象
# 4. 创建执行服务（线程池）: ExecutorService service = Executors.newFixedThreadPool(1);
# 5. 提交执行（submit方法）: Future<返回值类型> result1 = service.submit(t1);
# 6. 获取结果: 返回值类型 r1 = result1.get();
# 7. 关闭服务: service.shutdownNow();

# Callable的好处：可以定义返回值；可以抛出异常
```

**静态代理模式**：

**Lamda表达式**：

### 2.1.2 线程的六种状态

**线程的六种状态**：

- 创建(NEW)：新创建了一个线程对象，但还没有调用start()方法。
- 运行(RUNNABLE)：Java线程中将就绪(READY)和运行中(RUNNING)两种状态笼统的称为"运行"。
- 阻塞(BLOCKED)：表示线程阻塞于锁。
- 等待(WAITING)：进入该状态的线程需要等待其他线程做出一些特定动作(通知或中断）。
- 超时等待(TIMED_WAITING)：该状态不同于WAITING，它可以在指定的时间后自行返回。
- 死亡(TERMINATED)：表示该线程已经执行完毕。

<img src="README.assets/image-20201030214438799.png" alt="image-20201030214438799" style="zoom: 75%;" />

```yaml
# 1. 如何停止线程？
# 1.1 不建议使用stop方法（已废弃）
# 1.2 建议线程正常停止（可以利用循环次数控制，不建议死循环）
# 1.3 或者使用标志位

# 2. 线程休眠(sleep)
# 2.1 sleep(时间)指定当前线程阻塞的毫秒数
# 2.2 sleep存在异常InterruptedException
# 2.3 sleep时间达到后线程进入就绪状态
# 2.4 sleep可以模拟网络延时、倒计时等
# 2.5 每一个对象都有一个锁，sleep不会释放锁

# 3. 线程礼让(yield)
# 3.1 礼让线程，让当前正在执行的线程暂停，但不阻塞
# 3.2 将线程从运行状态转为就绪状态
# 3.3 让CPU重新调度，例如不一定成功，看CPU心情

# 4. 线程强制执行(join)
# 4.1 Join合并线程，待此线程执行完成后，再执行其他线程，其他线程阻塞（类似于插队）

# 5. 常见的与线程相关的方法
# 5.1 获取当前线程的名称：Thread.currentThread().getName()
# 5.2 获取线程状态的方法：thread.getState()
# 5.3 设置/获取线程的优先级：setPriority(int xxx),getPriority(),默认优先级是5
```

**wait/sleep的区别**：

1. 来自不同的类：wait来自Object类；sleep来自Thread类，企业开发中一般不会用sleep进行休眠，而会使用`TimeUnit.SECONDS.sleep(20);`。
2. 关于锁的释放：wait会释放锁；sleep不会释放锁。
3. 使用范围不同：wait必须在同步代码块中使用；sleep可以在任何地方使用。
4. 是否需要捕获异常：wait不需要捕获异常；sleep需要捕获异常。

**守护线程**：

- 线程分为用户线程和守护线程（使用**setDaemon(true)**可以设置为守护线程）。
- 虚拟机必须确保用户线程执行完毕（如main方法为用户线程）。
- 虚拟机不用等待守护线程执行完毕（如gc方法为守护线程）。
- 守护线程的应用：后台记录操作日志、监控内存、垃圾回收...

### 2.1.3 锁机制

#### 2.1.3.1 同步

**使用锁机制（synchronized）解决同步必然会带来如下问题**：

1. 一个线程持有锁会导致其他所有需要此锁的线程挂起。
2. 在多线程竞争下，加锁、释放锁会导致比较多的上下文切换和调度延时，会引起性能问题。
3. 如果一个优先级高的线程等待一个优先级低的线程释放锁，会导致优先级倒置，引起性能问题。

**同步方法**：在方法上添加synchronized关键字。若将一个大的方法声明为synchronized将会影响效率。

**同步块**：

- 在代码块上添加synchronized(同步监视器)。
- 同步监视器可以是任何对象，但是推荐使用共享资源作为同步监视器。
- 同步方法中无需指定同步监视器，因为同步方法的同步监视器就是this。

#### 2.1.3.2 死锁

**死锁**：多个线程各自占有一些共享资源，并且互相等待其他线程占有的资源才能运行，而导致两个或者多个线程都在等待对方释放资源，都停止执行的情形。某一个同步块同时拥有"两个以上对象的锁"时，就可能会发生"死锁"问题。

**产生死锁的四个必要条件**：

1. 互斥条件：即当资源被一个线程使用（占有）时，别的线程不能使用。
2. 不可抢占：资源请求者不能强制从资源占有者手中夺取资源，资源只能由资源占用者主动释放。
3. 请求和保持：当资源的请求者在请求其他的资源的同时保持对原有资源的占有。
4. 循环等待：即存在一个等待队列， P1占有P2的资源，P2占有P3的资源，P3占有P1的资源。

#### 2.1.3.3 Lock锁

**关于Lock锁**：

- 从jdk5.0开始，Java提供了更强大的线程同步机制：通过显示定义同步锁对象来实现同步，同步锁使用Lock对象充当。
- java.util.concurrent.locks.Lock接口是控制多个线程对共享资源进行访问的工具。锁提供了对共享资源的独立访问，**每次只能有一个线程对Lock对象加锁**，线程开始访问共享资源之前应先获得Lock对象。
- ReentrantLock类实现了Lock，它拥有与synchronized相同的并发性和内存语义，在实现线程安全的控制中，比较常用的是ReentrantLock，**可以显示加锁（lock()方法）和释放锁（unlock()方法）**。

**synchronized与Lock的对比**：

1. **类型不同**：synchronized是内置的java关键字；Lock是一个类。
2. **使用灵活性不同**：synchronized无法判断获取锁的状态；Lock可以判断是否获取到了锁。
3. **使用方法不用**：synchronized是隐式锁，出了作用域自动释放；Lock是显示锁（需手动开启和释放锁，如果不释放锁，会造成死锁）。
4. **锁的限制不同**：synchronized加锁后，若线程没获取到锁，会一致等待下去；Lock锁不一定会等待下去（`lock.tryLock()方法`）。
5. **锁的类型不同**：synchronized是可重入锁，不可中断的，非公平锁；Lock锁是可重入锁，但是可以判断锁，是非公平锁（可以自己设置）。（公平锁与非公平锁：公平锁遵循先来后到；非公平锁可以"插队"（Java默认是非公平锁））
6. **使用范围不同**：synchronized有代码块锁和方法锁；Lock只有代码块锁。
7. **使用场景不同**：synchronized适合锁少量的代码同步问题；Lock适合锁大量的同步代码（JVM将花费较少的时间来调度线程），此外Lock锁具有更好的扩展性（提供更多的子类）。**优先使用顺序：Lock-->同步代码块-->同步方法。**

#### 2.1.3.4 典型应用

- 线程通信：生产者与消费者问题
  - 使用以下几个方法解决线程之间的通信问题：wait()/wait(long timeout)，notify()/notifyAll()。
  - 与sleep方法不同，wait方法会释放锁。
  - 有两种解决生产者与消费者问题的方法：管程法、信号灯法。

#### 2.1.3.5 线程池

常见的线程池技术：c3p0、Druid。

**volatile**： volatile修饰的变量不允许线程内部缓存和重排序，只能直接修改内存，所以对其他线程是**可见**的；但是，volatile只能让被他修饰的内容具有可见性，但不能保证它具有原子性。例如`volatile int a = 0;` ，之后有一个操作`a++;`，这个变量a具有可见性，但是`a++;`依然是一个非原子操作，也就是这个操作同样存在线程安全的问题。此外，volatile还可以**禁止指令重排序**，可以使用volatile和synchronized两个关键字来保证线程之间操作的有序性。

**transient**：将不需要序列化的属性前添加关键字transient，序列化对象的时候，这个属性就不会被序列化。

## 2.2 JUC

JUC，是指jdk里的三个与多线程有关的工具包：`java.util.concurrent`、`java.util.concurrent.atomic`、`java.util.concurrent.locks`。

### 2.2.1 基础巩固

#### 2.2.1.1 多线程企业级开发

**关于多线程开发**：企业级开发为了降低耦合度，线程就是一个单独的资源类，没有任何附属的操作。

```java
**
 * 真正的多线程开发，公司中的开发，降低耦合性。线程就是一个单独的资源类，没有任何附属的操作！
 * 
 */
public class SaleTicketDemo01 {
    public static void main(String[] args) {
        // 并发：多线程操作同一个资源类, 把资源类丢入线程
        Ticket ticket = new Ticket();

        // @FunctionalInterface 函数式接口，jdk1.8  lambda表达式 (参数)->{ 代码 }
        new Thread(()->{
            for (int i = 1; i < 40 ; i++) {
                ticket.sale();
            }
        },"A").start();

        new Thread(()->{
            for (int i = 1; i < 40 ; i++) {
                ticket.sale();
            }
        },"B").start();

        new Thread(()->{
            for (int i = 1; i < 40 ; i++) {
                ticket.sale();
            }
        },"C").start();
    }
}

// 资源类 OOP
class Ticket {
    // 属性、方法
    private int number = 30;
    
    // 卖票的方式
    // synchronized 本质: 队列，锁
    public synchronized void sale(){ // synchronized可以换成lock锁
        if (number>0){
            System.out.println(Thread.currentThread().getName()+"卖出了"+(number--)+"票,剩余："+number);
        }
    }
}
```

**面试高频点：单例模式、排序算法、生产者和消费者、死锁。**

#### 2.2.1.2 虚假唤醒问题

生产者消费者中的虚假唤醒问题：

```java
/**
 * 线程之间的通信问题：生产者和消费者问题，等待唤醒，通知唤醒
 * 线程交替执行:A、B 操作同一个变量num = 0
 * A num+1
 * B num-1
 */
public class A {
    public static void main(String[] args) {
        Data data = new Data();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"A").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    data.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"B").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"C").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    data.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"D").start();
    }
}

// 判断等待，业务，通知
class Data{ // 数字 资源类
    private int number = 0;
    
    public synchronized void increment() throws InterruptedException { //+1
        if (number!=0){  //0 （将if改为while即可解决虚假唤醒问题）
            this.wait(); // 等待
        }
        number++;
        System.out.println(Thread.currentThread().getName()+"=>"+number);     
        this.notifyAll(); // 通知其他线程，我+1完毕了
    }
  
    public synchronized void decrement() throws InterruptedException { //-1
        if (number==0){ // 1 （将if改为while即可解决虚假唤醒问题）       
            this.wait(); // 等待
        }
        number--;
        System.out.println(Thread.currentThread().getName()+"=>"+number);   
        this.notifyAll(); // 通知其他线程，我-1完毕了
    }
}
```

若有A、B、C、D四个线程，且Data类中increment和decrement在if条件中等待唤醒，则会出现**虚假唤醒**的问题。具体原因可以参见jdk文档中的解释：

```java
// 以下解释来源于jdk文档：
// 线程也可以唤醒，而不会被通知、中断或超时，即所谓的虚假唤醒。虽然这在实践中很少发生，但应用程序必须通过测试应该使线程被唤醒的条件来防范，并且如果条件不满足则继续等待。换句话说，等待应该总是出现在循环中，类似这样：
synchronized (obj) {
   while (<condition does not hold>){
      obj.wait(timeout);
   }
   ...
}

// 关于生产者消费者中的虚假唤醒问题：将if改为while即可解决虚假唤醒问题。
```

### 2.2.2 JUC生产者消费者

**关于Condition接口**：Condition接口位于`java.util.concurrent.locks`包下。jdk文档中是这么解释Condition接口的：Condition因素出Object监视器方法（ wait ， notify和notifyAll ）成不同的对象，以得到具有多个等待集的每个对象，通过将它们与使用任意的组合的效果Lock个实现。 Lock替换synchronized方法和语句的使用， Condition取代了对象监视器方法的使用。 

<img src="README.assets/image-20201031110102876.png" alt="image-20201031110102876" style="zoom:67%;" />

```java
public class B  {
    public static void main(String[] args) {
        Data2 data = new Data2();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"A").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    data.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"B").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"C").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    data.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"D").start();

    }
}

// 判断等待，业务，通知
class Data2{ // 数字 资源类

    private int number = 0;

    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    //+1
    public void increment() throws InterruptedException {
        lock.lock();
        try {
            while (number!=0){  //0
                condition.await(); // 等待
            }
            number++;
            System.out.println(Thread.currentThread().getName()+"=>"+number);
            condition.signalAll(); // 通知其他线程，我+1完毕了
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    //-1
    public synchronized void decrement() throws InterruptedException {
        lock.lock();
        try {
            while (number==0){ // 1
                condition.await(); // 等待
            }
            number--;
            System.out.println(Thread.currentThread().getName()+"=>"+number);
            condition.signalAll();  // 通知其他线程，我-1完毕了
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
```

### 2.2.3 Condition精准通知和唤醒

使用Condition可以实现精准通知和唤醒。

```java
/**
 * A 执行完调用B，B执行完调用C，C执行完调用A
 */
public class C {

    public static void main(String[] args) {
        Data3 data = new Data3();

        new Thread(()->{
            for (int i = 0; i <10 ; i++) {
                data.printA();
            }
        },"A").start();

        new Thread(()->{
            for (int i = 0; i <10 ; i++) {
                data.printB();
            }
        },"B").start();

        new Thread(()->{
            for (int i = 0; i <10 ; i++) {
                data.printC();
            }
        },"C").start();
    }
}

class Data3{ // 资源类 Lock

    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();
    private int number = 1; // 1A  2B  3C

    public void printA(){
        lock.lock();
        try {
            // 业务，判断-> 执行-> 通知
            while (number!=1){
                // 等待
                condition1.await();
            }
            System.out.println(Thread.currentThread().getName()+"=>AAAAAAA");
            number = 2;
            condition2.signal(); // 唤醒，唤醒指定的人，B
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void printB(){
        lock.lock();
        try {
            // 业务，判断-> 执行-> 通知
            while (number!=2){
                condition2.await();
            }
            System.out.println(Thread.currentThread().getName()+"=>BBBBBBBBB");
            number = 3;
            condition3.signal(); // 唤醒，唤醒指定的人，c
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void printC(){
        lock.lock();
        try {
            // 业务，判断-> 执行-> 通知
            while (number!=3){
                condition3.await();
            }
            System.out.println(Thread.currentThread().getName()+"=>BBBBBBBBB");
            number = 1;
            condition1.signal(); // 唤醒，唤醒指定的人，A
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
```

### 2.2.4 锁的本质

synchronized 修饰方法：锁的是this对象（不唯一）。

static + synchronized 修饰方法：锁的是Class对象（唯一）。

### 2.2.5 集合类不安全

#### 2.2.5.1 CopyOnWriteArrayList

ArrayList：并发下`ArrayList`是不安全的，会抛出`java.util.ConcurrentModificationException`并发修改异常。解决方法有以下几种方式：

- 第1种解决方法：使用Vector代替ArrayList（Vector出现的比ArrayList早，那ArrayList存在的意义？）。

- 第2种解决方法：使用集合工具类`Collections.synchronizedList(List<T> list)`。
- 第3种解决方法：使用JUC下的CopyOnWriteArrayList类（CopyOnWrite写入时复制，简称COW，是计算机程序设计领域的一种优化策略，在写入的时候避免覆盖造成的数据问题）。CopyOnWriteArrayList使用Lock锁，效率更高；而Vector使用的是synchronized锁，效率低。

#### 2.2.5.2 CopyOnWriteArraySet

HashSet：并发下`HashSet`也是不安全的，会抛出`java.util.ConcurrentModificationException`并发修改异常。解决方法有以下几种方式：

- 第1种解决办法：使用集合工具类`Collections.synchronizedSet(Set<T> set)`。
- 第2种解决办法：使用JUC下的CopyOnWriteArraySet类（写入时复制）。

#### 2.2.5.3 ConcurrentHashMap

HashMap：并发下`HashMap`（**HashMap的加载因子与初始化容量？**）也是不安全的，会抛出`java.util.ConcurrentModificationException`并发修改异常。解决方法有以下几种方式：

- 第1种解决方法：使用集合工具类`Collections.synchronizedMap(Map<K,V> m) `。
- 第2种解决办法：使用JUC下的ConcurrentHashMap类（**ConcurrentHashMap的原理？**）。

### 2.2.6 Collable多线程

**Collable**：可以有返回值；可以抛出异常；call方法（类似于Runnable种的run方法）。

**注意事项**：

1. Collable的执行结果会被缓存。
2. 获取返回结果可能需要等待，从而会导致阻塞。

```java
public class CallableTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // new Thread(new Runnable()).start();
        // new Thread(new FutureTask<V>()).start();
        // new Thread(new FutureTask<V>( Callable )).start();
        new Thread().start(); // 怎么启动Callable

        MyThread thread = new MyThread();
        FutureTask futureTask = new FutureTask(thread); // 适配类

        new Thread(futureTask,"A").start();
        new Thread(futureTask,"B").start(); // 结果会被缓存，效率高

        Integer o = (Integer) futureTask.get(); //这个get 方法可能会产生阻塞！把他放到最后；或者使用异步通信来处理！
        System.out.println(o);
    }
}

class MyThread implements Callable<Integer> {

    @Override
    public Integer call() {
        System.out.println("call()"); // 会打印几个call
        // 耗时的操作
        return 1024;
    }
}
```

### 2.2.7 常用辅助类

#### 2.2.7.1 CountDownLatch

**CountDownLatch（减法计数器）**：允许一个或多个线程等待直到在其他线程中执行的一组操作完成的同步辅助。

**原理**：每次有线程调用countDown方法，则数量减1；若计数器变为0，await方法就会被唤醒，继续执行。

1. countDownLatch.countDown()：数量-1；
2. countDownLatch.await()：等待计数器归0，然后再往下执行。

```java
// 计数器
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        // 总数是6，必须要执行任务的时候，再使用！
        CountDownLatch countDownLatch = new CountDownLatch(6);

        for (int i = 1; i <=6 ; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+" Go out");
                countDownLatch.countDown(); // 数量-1
            },String.valueOf(i)).start();
        }

        countDownLatch.await(); // 等待计数器归零，然后再向下执行
        System.out.println("Close Door");
    }
}
```

#### 2.2.7.2 CyclicBarrier

**CyclicBarrier（加法计数器）**：允许一组线程全部等待彼此达到共同屏障点的同步辅助。 循环阻塞在涉及固定大小的线程方的程序中很有用，这些线程必须偶尔等待彼此。 屏障被称为循环 ，因为它可以在等待的线程被释放之后重新使用。

```java
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        /**
         * 集齐7颗龙珠召唤神龙
         */
        // 召唤龙珠的线程
        CyclicBarrier cyclicBarrier = new CyclicBarrier(8,()->{
            System.out.println("召唤神龙成功！");
        });

        for (int i = 1; i <=7 ; i++) {
            final int temp = i;
            // lambda能操作到 i 吗？不能，需要通过中间变量temp
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"收集"+temp+"个龙珠");
                try {
                    cyclicBarrier.await(); // 等待
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
```

#### 2.2.7.3 Semaphore

**Semaphore（信号量，用于限流）**：一个计数信号量。 在概念上，信号量维持一组许可证。 如果有必要，每个acquire()都会阻塞，直到许可证可用，然后才能使用它。 每个release()添加许可证，潜在地释放阻塞获取方。 但是，没有使用实际的许可证对象；Semaphore只保留可用数量的计数，并相应地执行。 

**原理**：常用于多个共享资源的互斥（例如抢车位）。

1. semaphore.acquire()：获得，如果已经满了，等待被释放为止。
2. semaphore.release()：释放，会将当前的信号量释放+1，然后唤醒等待的线程。

```java
public class SemaphoreDemo {
    public static void main(String[] args) {
        // 线程数量：停车位! 限流！
        Semaphore semaphore = new Semaphore(3);

        for (int i = 1; i <=6 ; i++) {
            new Thread(()->{
                // acquire() 得到
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName()+"抢到车位");
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println(Thread.currentThread().getName()+"离开车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release(); // release() 释放
                }
            },String.valueOf(i)).start();
        }
    }
}
```

### 2.2.8 ReadWriteLock读写锁

**ReadWriteLock**：维护一对关联的locks，一个用于只读操作，一个用于写入操作。 read lock可以由多个阅读器线程同时进行，只要没有作者。 write lock是独家的。

**独占锁**：写锁。

**共享锁**：读锁。

```java
/**
 * 独占锁（写锁） 一次只能被一个线程占有
 * 共享锁（读锁） 多个线程可以同时占有
 * ReadWriteLock
 * 读-读  可以共存！
 * 读-写  不能共存！
 * 写-写  不能共存！
 */
public class ReadWriteLockDemo {
    public static void main(String[] args) {
        MyCache myCache = new MyCache();

        // 写入
        for (int i = 1; i <= 5 ; i++) {
            final int temp = i;
            new Thread(()->{
                myCache.put(temp+"",temp+"");
            },String.valueOf(i)).start();
        }

        // 读取
        for (int i = 1; i <= 5 ; i++) {
            final int temp = i;
            new Thread(()->{
                myCache.get(temp+"");
            },String.valueOf(i)).start();
        }

    }
}

// 加锁的
class MyCacheLock{

    private volatile Map<String,Object> map = new HashMap<>();
    // 读写锁： 更加细粒度的控制
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private Lock lock = new ReentrantLock();

    // 存，写入的时候，只希望同时只有一个线程写
    public void put(String key,Object value){
        readWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"写入"+key);
            map.put(key,value);
            System.out.println(Thread.currentThread().getName()+"写入OK");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    // 取，读，所有人都可以读！
    public void get(String key){
        readWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"读取"+key);
            Object o = map.get(key);
            System.out.println(Thread.currentThread().getName()+"读取OK");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
    }
}
```

### 2.2.9 阻塞队列

#### 2.2.9.1 BlockingQueue

**阻塞队列**：遵循FIFO。放入时若队列已满，则必须阻塞等待；取出时若队列是空的，则必须阻塞等待放入。

BlockingQueue（阻塞队列）的实现类：ArrayBlockingQueue（数组阻塞队列）、LinkedBlockingQueue（链表阻塞队列）、SynchronousQueue（同步阻塞队列）。

**阻塞队列的应用**：多线程并发处理、线程池。

**阻塞队列的四组API**：

|     方式     | 抛出异常 | 有返回值，不抛出异常 | 阻塞等待 | 超时等待  |
| :----------: | :------: | :------------------: | :------: | :-------: |
|     添加     |   add    |       offer()        |  put()   | offer(,,) |
|     移除     |  remove  |        poll()        |  take()  |  poll(,)  |
| 检测队首元素 | element  |         peek         |    -     |     -     |

1. 抛出异常。
2. 不会抛出异常。
3. 阻塞、一直等待。
4. 超时等待。

```java
public class Test {
    public static void main(String[] args) throws InterruptedException {
        test4();
    }
   
    public static void test1(){ // 抛出异常
        // 队列的大小
        ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue<>(3);

        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));
        // IllegalStateException: Queue full 抛出异常！
        // System.out.println(blockingQueue.add("d"));

        System.out.println("=-===========");

        System.out.println(blockingQueue.element()); // 查看队首元素是谁
        System.out.println(blockingQueue.remove());


        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());

        // java.util.NoSuchElementException 抛出异常！
        // System.out.println(blockingQueue.remove());
    }

    public static void test2(){ // 有返回值，不会抛出异常
        // 队列的大小
        ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue<>(3);

        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));

        System.out.println(blockingQueue.peek());
        // System.out.println(blockingQueue.offer("d")); // false 不抛出异常！
        System.out.println("============================");
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll()); // null 不抛出异常！
    }

    public static void test3() throws InterruptedException { // 等待，阻塞（一直阻塞）
        // 队列的大小
        ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue<>(3);

        // 一直阻塞
        blockingQueue.put("a");
        blockingQueue.put("b");
        blockingQueue.put("c");
        // blockingQueue.put("d"); // 队列没有位置了，一直阻塞
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take()); // 没有这个元素，一直阻塞

    }

    public static void test4() throws InterruptedException { // 等待，阻塞（等待超时）
        // 队列的大小
        ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue<>(3);

        blockingQueue.offer("a");
        blockingQueue.offer("b");
        blockingQueue.offer("c");
        // blockingQueue.offer("d",2,TimeUnit.SECONDS); // 等待超过2秒就退出
        System.out.println("===============");
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        blockingQueue.poll(2,TimeUnit.SECONDS); // 等待超过2秒就退出
    }
}
```

#### 2.2.9.2 SynchronousQueue

**SynchronousQueue队列**：同步队列，和其他BlockingQueue不一样，SynchronousQueue不存储元素，put了一个元素，必须从里面先take取出来，否则不能再put进去值。

```java
/**
 * 同步队列
 * 和其他的BlockingQueue 不一样， SynchronousQueue 不存储元素
 * put了一个元素，必须从里面先take取出来，否则不能再put进去值！
 */
public class SynchronousQueueDemo {
    public static void main(String[] args) {
        BlockingQueue<String> blockingQueue = new SynchronousQueue<>(); // 同步队列

        new Thread(()->{
            try {
                System.out.println(Thread.currentThread().getName()+" put 1");
                blockingQueue.put("1");
                System.out.println(Thread.currentThread().getName()+" put 2");
                blockingQueue.put("2");
                System.out.println(Thread.currentThread().getName()+" put 3");
                blockingQueue.put("3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"T1").start();


        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName()+"=>"+blockingQueue.take());
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName()+"=>"+blockingQueue.take());
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName()+"=>"+blockingQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"T2").start();
    }
}
```

### 2.2.10 线程池

线程池面试点：**三大方法、七大参数、四种拒绝策略**。

**池化技术**：事先准备好一些资源，有人要用，就来这里取，用完之后再还回来。池化技术的本质是优化资源的使用。例如：线程池、连接池、内存池、对象池...

**线程池的好处**：降低资源的消耗；提高响应速度；方便管理。（总结好处：**线程复用、可以控制最大并发数、管理线程**）

#### 2.2.10.1 三大方法

- Executors.newSingleThreadExecutor()：创建单个线程。
- Executors.newFixedThreadPool(5)：创建一个固定大小的线程池。
- Executors.newCachedThreadPool()：创建一个可伸缩的（遇强则强、遇弱则弱）线程池。

**阿里巴巴开发手册规定**：线程池不允许使用Executors去创建，而是通过ThreadPoolExecutor的方式，这样的处理方式让写的同学更加明确线程池的运行规则，规避资源耗尽的风险。

- FixedThreadPool和SingleThreadExecutor：允许的请求队列长度为Integer.MAX_VALUE，可能会堆积大量的请求，从而导致OOM。
- CachedThreadPool和ScheduledThreadPool：允许创建线程数量为Integer.MAX_VALUE，可能会创建大量的线程，从而导致OOM。

#### 2.2.10.2 七大参数

三大方法的本质均是调用ThreadPoolExecutor创建线程池，ThreadPoolExecutor的构造器有七大参数：

- int corePoolSize：核心线程的数量。
- int maximumPoolSize：最大核心线程池大小。

> **最大线程池大小如何设置？**：
>
> 1. 若是CPU密集型应用：最大线程数=CPU的核数。
>
> 2. 若是IO密集型应用：最大线程数>IO线程的数量（一般最大线程数取IO线程的2倍即可）。

- long keepAliveTime：超时了但没有人调用就会释放。
- TimeUnit unit ： 超时时间。
- BlockingQueue<Runnable> workQueue：阻塞队列。
- ThreadFactory threadFactory：线程工厂，创建线程的，一般不用动。
- RejectedExecutionHandler handler：拒绝策略。

#### 2.2.10.3 四种拒绝策略

- new ThreadPoolExecutor.AbortPolicy() ：银行满了，还有人进来，不处理这个人的，抛出异常。
-  new ThreadPoolExecutor.CallerRunsPolicy()：哪来的回哪去，不会抛出异常。
- new ThreadPoolExecutor.DiscardPolicy()：队列满了，丢掉任务，不会抛出异常。
- new ThreadPoolExecutor.DiscardOldestPolicy() ：队列满了，尝试去和最早的竞争（若竞争四百则丢掉任务），也不会抛出异常。

```java
/**
 * new ThreadPoolExecutor.AbortPolicy() // 银行满了，还有人进来，不处理这个人的，抛出异常
 * new ThreadPoolExecutor.CallerRunsPolicy() // 哪来的去哪里！
 * new ThreadPoolExecutor.DiscardPolicy() //队列满了，丢掉任务，不会抛出异常！
 * new ThreadPoolExecutor.DiscardOldestPolicy() //队列满了，尝试去和最早的竞争，也不会抛出异常！
 */
public class Demo01 {
    public static void main(String[] args) {
        // 自定义线程池！工作 ThreadPoolExecutor

        // 最大线程到底该如何定义
        // 1、CPU 密集型，几核，就是几，可以保持CPu的效率最高！
        // 2、IO  密集型   > 判断你程序中十分耗IO的线程，
        // 程序  15个大型任务  io十分占用资源！

        // 获取CPU的核数
        System.out.println(Runtime.getRuntime().availableProcessors());

        List  list = new ArrayList();

        ExecutorService threadPool = new ThreadPoolExecutor(
                2,
                Runtime.getRuntime().availableProcessors(), // 获取电脑的核数（最大线程数=CPU的核数）
                3,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardOldestPolicy());  //队列满了，尝试去和最早的竞争，也不会抛出异常！
        try {
            // 最大承载：Deque + max
            // 超过 RejectedExecutionException
            for (int i = 1; i <= 9; i++) {
                // 使用了线程池之后，使用线程池来创建线程
                threadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName()+" ok");
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 线程池用完，程序结束，关闭线程池
            threadPool.shutdown();
        }

    }
}
```

### 2.2.11 四大函数式接口

需要重点掌握：**函数式接口、Lambda表达式、链式编程、Stream流式计算**。

**函数式接口**：只有一个方法的接口（主要用于简化编程模型，在新版本的框架底层大量应用）。**只要是函数式接口就可以用Lambda表达式简化**。

#### 2.2.11.1 Function

**Function函数式接口**：有一个输入参数，有一个输出参数。

```java
/**
 * Function 函数型接口, 有一个输入参数，有一个输出
 * 只要是函数型接口,就可以用Lambda表达式简化
 */
public class Demo01 {
    public static void main(String[] args) {
        //
//        Function<String,String> function = new Function<String,String>() {
//            @Override
//            public String apply(String str) {
//                return str;
//            }
//        };

        Function<String,String> function = str->{return str;};
        System.out.println(function.apply("asd"));
    }
}
```

#### 2.2.11.2 Predicate

**Predicate断定式接口**：有一个输入参数，返回一个boolean类型。

```java
/**
 * 断定型接口：有一个输入参数，返回值只能是 布尔值！
 */
public class Demo02 {
    public static void main(String[] args) {
        // 判断字符串是否为空
//        Predicate<String> predicate = new Predicate<String>(){
////            @Override
////            public boolean test(String str) {
////                return str.isEmpty();
////            }
////        };

        Predicate<String> predicate = (str)->{return str.isEmpty(); };
        System.out.println(predicate.test(""));
    }
}
```

#### 2.2.11.3 Consumer

**Consumer消费型接口**：只有输入，没有返回值。

```java
/**
 * Consumer 消费型接口: 只有输入，没有返回值
 */
public class Demo03 {
    public static void main(String[] args) {
//        Consumer<String> consumer = new Consumer<String>() {
//            @Override
//            public void accept(String str) {
//                System.out.println(str);
//            }
//        };
        Consumer<String> consumer = (str)->{System.out.println(str);};
        consumer.accept("sdadasd");
    }
}
```

#### 2.2.11.4 Supplier

**Supplier供给型接口**：没有输入，只有返回值。

```java
/**
 * Supplier 供给型接口 没有参数，只有返回值
 */
public class Demo04 {
    public static void main(String[] args) {
//        Supplier supplier = new Supplier<Integer>() {
//            @Override
//            public Integer get() {
//                System.out.println("get()");
//                return 1024;
//            }
//        };

        Supplier supplier = ()->{ return 1024; };
        System.out.println(supplier.get());
    }
}
```

### 2.2.12 Stream流式计算

大数据：存储 + 计算。集合、MySQL本质是存储，计算都应该交给流来操作。

```java
/**
 * 题目要求：一分钟内完成此题，只能用一行代码实现！
 * 现在有5个用户！筛选：
 * 1、ID 必须是偶数
 * 2、年龄必须大于23岁
 * 3、用户名转为大写字母
 * 4、用户名字母倒着排序
 * 5、只输出一个用户！
 */
public class Test {
    public static void main(String[] args) {
        User u1 = new User(1,"a",21);
        User u2 = new User(2,"b",22);
        User u3 = new User(3,"c",23);
        User u4 = new User(4,"d",24);
        User u5 = new User(6,"e",25);
        // 集合就是存储
        List<User> list = Arrays.asList(u1, u2, u3, u4, u5);

        // 计算交给Stream流
        // lambda表达式、链式编程、函数式接口、Stream流式计算
        list.stream()
                .filter(u->{return u.getId()%2==0;})
                .filter(u->{return u.getAge()>23;})
                .map(u->{return u.getName().toUpperCase();})
                .sorted((uu1,uu2)->{return uu2.compareTo(uu1);})
                .limit(1)
                .forEach(System.out::println);
    }
}
```

### 2.2.13 ForkJoin

**ForkJoin**： ForkJoin在jdk1.7中引入的，并行执行任务，提高效率。

**ForkJoin特点**：工作窃取。原因是底层是采用**双端队列**实现的。（**ForkJoin的具体使用？Stream并行流的具体使用？**）

```java
/**
 * 求和计算的任务！
 * 3000   6000（ForkJoin）  9000（Stream并行流）
 * // 如何使用 forkjoin
 * // 1、forkjoinPool 通过它来执行
 * // 2、计算任务 forkjoinPool.execute(ForkJoinTask task)
 * // 3. 计算类要继承 ForkJoinTask
 */
public class ForkJoinDemo extends RecursiveTask<Long> {

    private Long start;  // 1
    private Long end;    // 1990900000

    // 临界值
    private Long temp = 10000L;

    public ForkJoinDemo(Long start, Long end) {
        this.start = start;
        this.end = end;
    }

    // 计算方法
    @Override
    protected Long compute() {
        if ((end-start)<temp){
            Long sum = 0L;
            for (Long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        }else { // forkjoin 递归
            long middle = (start + end) / 2; // 中间值
            ForkJoinDemo task1 = new ForkJoinDemo(start, middle);
            task1.fork(); // 拆分任务，把任务压入线程队列
            ForkJoinDemo task2 = new ForkJoinDemo(middle+1, end);
            task2.fork(); // 拆分任务，把任务压入线程队列

            return task1.join() + task2.join();
        }
    }
}

// 比较几种不同方法的计算速度
public class Test {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // test1(); // 12224ms
        // test2(); // 10038ms
        // test3(); // 153ms
    }

    // 普通程序员
    public static void test1(){
        Long sum = 0L;
        long start = System.currentTimeMillis();
        for (Long i = 1L; i <= 10_0000_0000; i++) {
            sum += i;
        }
        long end = System.currentTimeMillis();
        System.out.println("sum="+sum+" 时间："+(end-start));
    }

    // 会使用ForkJoin
    public static void test2() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Long> task = new ForkJoinDemo(0L, 10_0000_0000L);
        ForkJoinTask<Long> submit = forkJoinPool.submit(task);// 提交任务
        Long sum = submit.get();

        long end = System.currentTimeMillis();
        System.out.println("sum="+sum+" 时间："+(end-start));
    }

    public static void test3(){
        long start = System.currentTimeMillis();
        // Stream并行流 ()  (]
        long sum = LongStream.rangeClosed(0L, 10_0000_0000L).parallel().reduce(0, Long::sum);
        long end = System.currentTimeMillis();
        System.out.println("sum="+"时间："+(end-start));
    }
}
```

### 2.2.14 异步回调

**Future的设计初衷**：对将来的某个事件的结果进行建模。**使用Future的实现类CompletableFuture可以实现异步调用**。

```java
/**
 * 异步调用： CompletableFuture
 * // 异步执行
 * // 成功回调
 * // 失败回调
 */
public class Demo01 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 没有返回值的 runAsync 异步回调
//        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(()->{
//            try {
//                TimeUnit.SECONDS.sleep(2);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println(Thread.currentThread().getName()+"runAsync=>Void");
//        });
//
//        System.out.println("1111");
//
//        completableFuture.get(); // 获取阻塞执行结果

        // 有返回值的 supplyAsync 异步回调
        // ajax，成功和失败的回调
        // 返回的是错误信息；
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(()->{
            System.out.println(Thread.currentThread().getName()+"supplyAsync=>Integer");
            int i = 10/0;
            return 1024;
        });

        System.out.println(completableFuture.whenComplete((t, u) -> {
            System.out.println("t=>" + t); // 正常的返回结果
            System.out.println("u=>" + u); // 错误信息：java.util.concurrent.CompletionException: java.lang.ArithmeticException: / by zero
        }).exceptionally((e) -> {
            System.out.println(e.getMessage());
            return 233; // 可以获取到错误的返回结果
        }).get());
    }
}
```

### 2.2.15 JMM

**JMM**： Java Memory Model，Java内存模型。

**关于JMM的一些同步的约定**：

1. 线程解锁前，必须把共享变量立刻刷回主存。
2. 线程加锁前，必须读取主存中的最新值到工作内存中。
3. 加锁和解锁是同一把锁。

**线程的工作内存与主内存之间有8种操作**：

<img src="README.assets/image-20201031222827293.png" alt="image-20201031222827293" style="zoom: 80%;" />

1. lock（锁定）：作用于主内存的变量，把一个变量标识为线程独占状态。
2. unlock（解锁）：作用于主内存的变量，它把一个处于锁定状态的变量释放出来，释放后的变量才可以被其他线程锁定。
3. read（读取）：作用于主内存变量，它把一个变量的值从主内存传输到线程的工作内存中，以便随后的load动作使用。
4. load（载入）：作用于工作内存的变量，它把read操作从主存中变量放入工作内存中。
5. use（使用）：作用于工作内存中的变量，它把工作内存中的变量传输给执行引擎，每当虚拟机遇到一个需要使用到变量的值，就会使用到这个指令。
6. assign（赋值）：作用于工作内存中的变量，它把一个从执行引擎中接受到的值放入工作内存的变量副本中。
7. store（存储）：作用于主内存中的变量，它把一个从工作内存中一个变量的值传送到主内存中，以便后续的write使用。
8. write（写入）：作用于主内存中的变量，它把store操作从工作内存中得到的变量的值放入主内存的变量中。

**JMM对这八种指令的使用，制定了如下规则**：

1. 不允许read和load、store和write操作之一单独出现。即使用了read必须load，使用了store必须write。
2. 不允许线程丢弃他最近的assign操作，即工作变量的数据改变了之后，必须告知主存。
3. 不允许一个线程将没有assign的数据从工作内存同步回主内存。
4. 一个新的变量必须在主内存中诞生，不允许工作内存直接使用一个未被初始化的变量。就是怼变量实施use、store操作之前，必须经过assign和load操作。
5. 一个变量同一时间只有一个线程能对其进行lock。多次lock后，必须执行相同次数的unlock才能解锁。
6. 如果对一个变量进行lock操作，会清空所有工作内存中此变量的值，在执行引擎使用这个变量前，必须重新load或assign操作初始化变量的值。
7. 如果一个变量没有被lock，就不能对其进行unlock操作。也不能unlock一个被其他线程锁住的变量。
8. 对一个变量进行unlock操作之前，必须把此变量同步回主内存。

**针对多个线程修改共享变量的情况**：若不引入volatile，会出现问题。（**引出下节的volatile可以解决这一问题**）

<img src="README.assets/Snipaste_2020-10-31_22-26-38.png" alt="Snipaste_2020-10-31_22-26-38" style="zoom:80%;" />

### 2.2.16 volatile

**volatile**：是java虚拟机提供的轻量级的同步机制。有如下三大特性：

1. **保证可见性**。

```java
public class JMMDemo {
    // 不加volatile程序就会死循环
    // 加volatile可以保证可见性
    private volatile static int num = 0;

    public static void main(String[] args) { // main

        new Thread(()->{ // 线程 1 对主内存的变化不知道的
            while (num==0){

            }
        }).start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        num = 1;
        System.out.println(num);
    }
}
```

2. **不保证原子性**（原子性即不可分割，表示线程在执行任务的时候，不能被打扰，也不能被分割，要么同时成功，要么同时失败）。

   （**数据库里的ACID四大原则？**）

   **如果不加lock和synchronized，怎么保证原子性？**使用原子类可以保证原子性。原子类的底层直接和操作系统挂钩，会直接在内存中修改值（Unsafe类是一个很特殊的存在）。

   **那为什么原子类可以保证原子性？**原子类的底层用的CAS，详见`2.2.18`节。

```java
// volatile 不保证原子性
public class VDemo02 {
    // volatile 不保证原子性
    // 原子类的 Integer
    private volatile static AtomicInteger num = new AtomicInteger();
    public static void add(){
        // num++; // 不是一个原子性操作
        num.getAndIncrement(); // AtomicInteger + 1 方法， CAS
    }

    public static void main(String[] args) {
        //理论上num结果应该为 2 万
        for (int i = 1; i <= 20; i++) {
            new Thread(()->{
                for (int j = 0; j < 1000 ; j++) {
                    add();
                }
            }).start();
        }

        while (Thread.activeCount()>2){ // main  gc
            Thread.yield();
        }

        System.out.println(Thread.currentThread().getName() + " " + num);
    }
}
```

3. **禁止指令重排**。

**什么是指令重排？**你写的程序，计算机并不是按照你写的那样去执行的。

**源代码到程序的过程**：源代码-->编译器优化的重排-->指令并行也可能重排-->内存系统也会重排-->执行。

**处理器在进行指令重排的时候，会考虑数据之间的依赖性，这在多线程情况下会出现问题（例如：A线程没有出现数据依赖，B线程也没有出现数据依赖，但B线程的指令重排后的结果对线程出现了依赖，就可能会出现问题）。**

举个例子：a、b、x、y默认值都是0

| 线程A | 线程B |
| ----- | ----- |
| x=a   | y=b   |
| b=1   | a=2   |

正常的结果：x = 0; y = 0; 但是可能由于指令重排：

| 线程A | 线程B |
| ----- | ----- |
| b=1   | a=2   |
| x=a   | y=b   |

指令重排导致的诡异结果：x = 2; y = 1; **volatile关键字就可以避免指令重排**。

**那为什么volatile可以避免指令重排呢？**添加volatile之后，在volatile写的时候，会在指令前后添加内存屏障，以禁止上下指令之间的交换。

普通读-->普通写-->内存屏障（禁止上下指令顺序交换）-->volatile写-->内存屏障（禁止上下指令顺序交换）。

**那volatile在那里应用最多呢？**在单例模式的饿汉模式使用最多。

### 2.2.17 单例模式

1. 饿汉模式：详见`1.1`节。
2. 懒汉模式：详见`1.1`节。（双重检测 + volatile，简称DCL模式）
3. 静态内部类：详见`1.1`节。

**注意**：饿汉模式、懒汉模式及静态内部类都是不安全的，使用**反射**可以破解这种单例模式。

```java
// 例子1：使用反射破坏原有的单例模式（饿汉/懒汉模式）
public static void main(String[] args) throws Exception {
	LazyMan instance = LazyMan.getInstance();

	Constructor<LazyMan> declaredConstructor = LazyMan.class.getDeclaredConstructor(null);
	declaredConstructor.setAccessible(true);
	LazyMan instance2 = declaredConstructor.newInstance();

	System.out.println(instance);
	System.out.println(instance2); // 输出instance与instance2不相等（反射可以破坏单例）
}


// 例子2：升级为三重判断，依然可以使用反射破坏单例模式
public class LazyMan {

    private LazyMan(){
        synchronized (LazyMan.class){
            if (lazyMan!=null){ // 使用三重判断
                throw new RuntimeException("不要试图使用反射破坏异常");
            }
        }
    }

    private volatile static LazyMan lazyMan;

    // 双重检测锁模式的 懒汉式单例  DCL懒汉式
    public static LazyMan getInstance(){
        if (lazyMan==null){
            synchronized (LazyMan.class){
                if (lazyMan==null){
                    lazyMan = new LazyMan(); // 不是一个原子性操作
                }
            }
        }
        return lazyMan;
    }

    // 反射！
    public static void main(String[] args) throws Exception {
        Constructor<LazyMan> declaredConstructor = LazyMan.class.getDeclaredConstructor(null);
        declaredConstructor.setAccessible(true);
        LazyMan instance = declaredConstructor.newInstance();
        LazyMan instance2 = declaredConstructor.newInstance();
        System.out.println(instance);
        System.out.println(instance2); // 输出instance2依然不等于instance
    }
}

// 例子3：使用红绿灯模式，通过反射依然可以破坏单例模式
public class LazyMan {

    private static boolean qinjiang = false;

    private LazyMan(){
        synchronized (LazyMan.class){
            if (qinjiang == false){
                qinjiang = true;
            }else {
                throw new RuntimeException("不要试图使用反射破坏异常");
            }
        }
    }

    private volatile static LazyMan lazyMan;

    // 双重检测锁模式的 懒汉式单例  DCL懒汉式
    public static LazyMan getInstance(){
        if (lazyMan==null){
            synchronized (LazyMan.class){
                if (lazyMan==null){
                    lazyMan = new LazyMan(); // 不是一个原子性操作
                }
            }
        }
        return lazyMan;
    }

    // 反射！
    public static void main(String[] args) throws Exception {

        Field qinjiang = LazyMan.class.getDeclaredField("qinjiang");
        qinjiang.setAccessible(true);

        Constructor<LazyMan> declaredConstructor = LazyMan.class.getDeclaredConstructor(null);
        declaredConstructor.setAccessible(true);
        LazyMan instance = declaredConstructor.newInstance();

        qinjiang.set(instance,false);

        LazyMan instance2 = declaredConstructor.newInstance();

        System.out.println(instance);
        System.out.println(instance2); // 输出instance2依然不等于instance
    }
}

// 例子4: 使用枚举（反射不会破坏枚举）单例模式，这种情况下才不可以被破坏
public enum EnumSingle {

    INSTANCE;

    public EnumSingle getInstance(){
        return INSTANCE;
    }

}

class Test{
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        EnumSingle instance1 = EnumSingle.INSTANCE;
        Constructor<EnumSingle> declaredConstructor = EnumSingle.class.getDeclaredConstructor(String.class,int.class);
        declaredConstructor.setAccessible(true);
        EnumSingle instance2 = declaredConstructor.newInstance();

        // NoSuchMethodException: com.progzc.single.EnumSingle.<init>()
        System.out.println(instance1);
        System.out.println(instance2);
    }
}
```

### 2.2.18 深入理解CAS

**CAS**： Compare and Swap，即比较再交换。比较当前工作中的值和主内存中的值，如果这个值是期望的，那么则执行操作；如果不是则一直循环（底层是自旋锁）。

**CAS原理**：CAS底层使用`Unsafe`的本地方法（C语言）去操作内存（保证了很高的效率），操作的底层采用了自旋锁。

**CAS缺点**：

1. 循环会耗事。
2. 一次性只能保证一个共享变量的原子性。
3. ABA问题。

### 2.2.19 原子引用解决ABA问题

**什么是ABA问题？**

**解决ABA问题**：引入待版本号的原子引用，对应的思想是乐观锁。

```java
public class CASDemo {
    //AtomicStampedReference 注意，如果泛型是一个包装类，注意对象的引用问题
    // 正常在业务操作，这里面比较的都是一个个对象
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(1,1);
    // CAS  compareAndSet : 比较并交换！
    public static void main(String[] args) {
        new Thread(()->{
            int stamp = atomicStampedReference.getStamp(); // 获得版本号
            System.out.println("a1=>"+stamp);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Lock lock = new ReentrantLock(true);

            atomicStampedReference.compareAndSet(1, 2,
                    atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            System.out.println("a2=>"+atomicStampedReference.getStamp());
            
            System.out.println(atomicStampedReference.compareAndSet(2, 1,
                    atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1));
            System.out.println("a3=>"+atomicStampedReference.getStamp());
        },"a").start();

        // 乐观锁的原理相同！
        new Thread(()->{
            int stamp = atomicStampedReference.getStamp(); // 获得版本号
            System.out.println("b1=>"+stamp);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicStampedReference.compareAndSet(1, 6, stamp, stamp + 1));
            System.out.println("b2=>"+atomicStampedReference.getStamp());
        },"b").start();
    }
}
```

**注意事项**：Integer使用了对象缓存机制，默认范围是-128~127，推荐使用静态工厂方法valueOf获取对象实例，而不是New，因为valueOf使用缓存，而New一定会创建新的对象，分配新的内存空间。

**阿里巴巴开发手册**：所有相同类型的包装类对象之间值得比较，全部使用equals方法比较。这是因为：对于Integer var = ? 在-128~127之间的赋值，Integer对象是在IntegerCache.cache产生，会复用已有对象，这个区间内的Integer值可以直接使用==进行判断，但是这个区间之外的所有数据，都会在堆上产生，并不会复用已有对象。**这是一个大坑，推荐使用equals方法进行判断。**

### 2.2.20 各种锁的理解

#### 2.2.20.1 公平锁/非公平锁

**公平锁**：不能插队，必须先来后到。

**非公平锁**：可以插队（默认都是非公平锁）。

```java
public ReentrantLock() { // 默认都是非公平锁
	sync = new NonfairSync();
}

public ReentrantLock(boolean fair) {
	sync = fair ? new FairSync() : new NonfairSync();
}

// 例子：非公平锁
public class FairLocked implements Runnable {
	private int seatNumber = 100;
	// 公平锁实现 ReentrantLock构造方法中设置为true:代表公平锁
	// 设置为false：代表非公平锁 默认也是非公平锁
	private ReentrantLock lock = new ReentrantLock(); 
	@Override
	public void run() {
		while (true) {
			try {
				lock.lock();
				if (seatNumber > 0) {
					Thread.sleep(100);
					--seatNumber;
					System.out.println(Thread.currentThread().getName() + "占用1个座位，还剩余 " + seatNumber + "个座位");
				} else {
					System.out.println(Thread.currentThread().getName() + ":不好意思，票卖完了！");
					break;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}
	}
 
	public static void main(String[] args) {
		FairLocked rlbr = new FairLocked();
		Thread t1 = new Thread(rlbr, "A窗口");
		Thread t2 = new Thread(rlbr, "B窗口");
		t1.start();
		t2.start();
	}
}
```

#### 2.2.20.2 可重入锁

**可重入锁**：可以理解为递归锁（拿到了外面的锁，才能获取里面的锁）。

Synchronized版的可重入锁：

```java
// Synchronized
public class Demo01 {
    public static void main(String[] args) {
        Phone phone = new Phone();

        new Thread(()->{
            phone.sms();
        },"A").start();

        new Thread(()->{
            phone.sms();
        },"B").start(); // 先输出A的sms和call，再输出B的sms和call
    }
}

class Phone{
    public synchronized void sms(){
        System.out.println(Thread.currentThread().getName() + "sms");
        call(); // 这里也有锁
    }
    public synchronized void call(){
        System.out.println(Thread.currentThread().getName() + "call");
    }
}
```

Lock版的可重入锁：

```java
public class Demo02 {
    public static void main(String[] args) {
        Phone2 phone = new Phone2();

        new Thread(()->{
            phone.sms();
        },"A").start();

        new Thread(()->{
            phone.sms();
        },"B").start();
    }
}

class Phone2{
    Lock lock = new ReentrantLock();

    public void sms(){
        lock.lock(); // 细节问题：lock.lock(); lock.unlock(); // lock 锁必须配对，否则就会死在里面
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "sms");
            call(); // 这里也有锁
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            lock.unlock();
        }
    }

    public void call(){
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "call");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
```

#### 2.2.20.3 自旋锁

**自旋锁**：加锁后陷入无限循环。自旋锁是计算机科学用于多线程同步的一种锁，线程反复检查锁变量是否可用。由于线程在这一过程中保持执行，因此是一种忙等待。

```java
// 利用CAS实现自定义自旋锁
public class SpinlockDemo {
    // int   0
    // Thread  null
    AtomicReference<Thread> atomicReference = new AtomicReference<>();
    
    public void myLock(){ // 加锁
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName() + "==> mylock");
        
        while (!atomicReference.compareAndSet(null,thread)) { // 自旋锁
        }
    }
    
    public void myUnLock(){ // 解锁
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName() + "==> myUnlock");
        atomicReference.compareAndSet(thread,null);
    }
}

// 测试自旋锁
public class TestSpinLock {
    public static void main(String[] args) throws InterruptedException {

        // 底层使用的自旋锁CAS
        SpinlockDemo lock = new SpinlockDemo();

        new Thread(()-> {
            lock.myLock();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.myUnLock();
            }
        },"T1").start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(()-> {
            lock.myLock();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.myUnLock();
            }
        },"T2").start();
    }
}
```

#### 2.2.20.4 死锁

**死锁的例子**：

```java
public class DeadLockDemo {
    public static void main(String[] args) {

        String lockA = "lockA";
        String lockB = "lockB";

        new Thread(new MyThread(lockA, lockB), "T1").start();
        new Thread(new MyThread(lockB, lockA), "T2").start();

    }
}


class MyThread implements Runnable{
    private String lockA;
    private String lockB;
    
    public MyThread(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA){
            System.out.println(Thread.currentThread().getName() + "lock:"+lockA+"=>get"+lockB);

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            synchronized (lockB){
                System.out.println(Thread.currentThread().getName() + "lock:"+lockB+"=>get"+lockA);
            }
        }
    }
}
```

**怎么排查解决死锁？**

1. 第一步：使用jps定位进程号（使用命令`jps -l`）。
2. 第二步：使用jstack查看进程信息（使用命令`jstack 进程号`）

**面试中问怎么排除问题？**

1. 查看日志。
2. 查看堆栈信息。（这样比较专业）

> [B站多线程教程](https://www.bilibili.com/video/BV1B7411L7tE)、[线程的六种状态及切换](https://blog.csdn.net/qq_22771739/article/details/82529874)、[volatile关键字](https://www.cnblogs.com/zhengbin/p/5654805.html#_label0)、[transient关键字](https://baijiahao.baidu.com/s?id=1636557218432721275&wfr=spider&for=pc)、[B站并发编程](https://www.bilibili.com/video/BV1B7411L7tE)、[Java内存模型](https://www.cnblogs.com/null-qige/p/9481900.html)、[CAS](https://blog.csdn.net/v123411739/article/details/79561458)、[周明志-深入理解JAVA虚拟机]()

## 2.3 并发编程实战

### 2.3.1 概述

**操作系统的作用**：

1. 为各个独立的进程分配资源，如内存、文件句柄、安全证书等。
2. 在进程中通过一些通信机制来交换数据，如套接字、信号处理器、共享内存、信号量、文件等。

**并发的作用**：

1. 提高系统的**资源利用率**。
2. **公平性**。使不同的用户和程序对计算机上的资源有着同等的使用权。
3. **便利性**。可以使用多个程序来执行一个任务。

**线程与进程：**

1. 线程是轻量级的进程。
2. 线程会共享进程范围内的资源（如内存句柄、文件句柄），但是每个线程都有各自的程序计数器、栈以及局部变量。

**线程的优势：**

1. 发挥多核处理器的强大能力。
2. 建模的简单性。
3. 异步事件的简化处理。
4. 响应更灵敏的用户界面。

**线程带来的风险：**

1. 安全性问题（"永远不发生糟糕的事情"）：如自增不是一个原子性操作（读/自增/写）。

   竞态条件是一种常见的并发安全问题，可以采用同步机制（synchronized）来解决。

2. 活跃性问题（"某件正确的事情最终会发生"）：如死锁、饥饿、活锁问题。

3. 性能问题：上下文切换带来的系统开销。

总结：线程无处不在：JVM、各种框架（框架通过在框架线程中调用应用程序代码将并发性引入到程序中）。

### 2.3.2 线程安全性

#### 2.3.2.1 线程安全性

**共享：**变量可以由多个线程同时访问。

**可变：**变量的值在其生命周期内可以发生变化。

当多个线程访问同一个可变的状态变量时没有使用合适的同步，那么程序可能会出现错误，解决方法有三种：

1. 不在线程之间共享该状态变量。
2. 将状态变量修改为不可变的变量。
3. 在访问状态变量时使用同步。

**线程安全性：**

1. 当多个线程访问某个类时，这个类始终都能表现出正确的行为，那么就称这个类是线程安装的。
2. 无状态对象一定是线程安全的。

#### 2.3.2.2 原子性

**竞态条件：**在并发编程中，由于不恰当的执行时序而出现不正确的结果。当某个计算的正确性取决于多个线程的交替执行时序时，就会发生竞态条件。竞态条件的本质是基于一种可能失效的观察结果来做出判断或执行某个计算。

- "先检查后执行"的一种常见情况就是延迟初始化（例如单例模式），延迟初始化中包含一个竞态条件。
- 要避免竞态条件，就必须在某个线程修改该变量时，通过某种方式防止其他线程使用这个变量。

**原子操作：**对于访问同一个状态的所有操作（包括该操作本身）来说，这个操作是一个以原子方式执行的操作。

**复合操作：**包含了**一组**必须以原子方式执行的操作，如"先检查后执行"、"读取-修改-写入"（如自增）都是复合操作。

**将复合操作变成线程安全的方法：**

1. 使用`java.util.concurrent.atomic`包下的原子变量类（线程安全状态变量，如AtomicLong），这些原子变量类用于实现在数值和对象引用上的原子状态转换（如AtomicLong的incrementAndGet方法是一种原子操作的自增）。在实际情况中，应尽可能地使用现有的线程安全对象（如AtomicLong）来管理类的状态。
2. 在方法上加锁。
3. 使用不可变对象。

#### 2.3.2.3 加锁机制

**为什么要加锁解决线程安全问题，难道使用原子变量类不行吗？**

当在不变性条件中涉及多个变量时，各个变量之间并不是彼此独立的，而是某个变量的值会对其他变量的值产生约束。因此，当更新某一个变量时，需要在同一个原子操作中对其他变量同时进行更新。这个时候锁登场了。

**内置锁：**同步代码块（Synchronized Block）。同步代码块由两部分组成：锁的对象引用+锁保护的代码块。

- 若以关键字Synchronized修饰普通方法，则该同步代码块的锁就是方法调用所在对象。
- 若以关键字Synchronized修饰静态方法，则该同步代码块的锁就是Class对象。
- 每个java对象都可以当作锁，这种锁称之为**内置锁或监视器锁**。
- java的内置锁是一种互斥锁。
- 在方法上使用同步代码块，往往可以解决线程安全问题，但是又会带来性能问题。

**重入：**

- 内置锁是可重入的。若某个线程试图获得一个已经由它自己持有的锁，那么这个请求就会成功。
- "重入"意味着获取锁的操作粒度是"线程"，而不是"调用"。
- "重入"的实现方式是为每个锁关联一个获取计数值和一个所有者线程。
- "重入"提升了加锁行为的封装性，简化了面向对象并发代码的开发。如父类有一个Synchronized方法，而子类也继承并重写了这个方法，同时也添加了Synchronized，当调用子类的Synchronized方法时，**重入避免了死锁的发生**。需要注意的是，调用子类的Synchronized方法时加的锁是父类的锁。

#### 2.3.2.4 用锁来保护状态

**用锁来保护状态：**

- 对于可能被多个线程同时访问的可变状态变量，在访问它时都需要持有同一个锁；每个共享的可变变量都应该只由一个锁来保护。
- **常见的加锁约定：**将所有的可变状态都封装在对象内部，并通过对象的内置锁对所有访问可变状态的代码路径进行同步，使得在该对象上不会发生并发访问（如Vector类）。

#### 2.3.2.5 活跃性与性能

- 通过缩小同步代码块的作用范围，可以既确保并发性，又提高性能。
- 同时使用同步代码块和原子变量，不仅会带来混乱，还会降低性能。

### 2.3.3 对象的共享

线程的安全性和对象的共享形成了构建线程安全类以及通过`java.util.concurrent`类库来构建并发应用程序的重要基础。

#### 2.3.3.1 可见性

- 为了确保多个线程之间对内存写入操作的可见性，必须使用同步机制（如在获取或改变变量的代码块上添加Synchronized关键字）。
- **重排序：**在没有同步的情况下，编译器、处理器以及运行时等都可能对操作的执行顺序进行一些意想不到的调整。
- 只要有数据在多个线程之间共享，就使用正确的同步。
- 在多线程程序中使用共享且可变的非volatile类型的long和double变量是不安全的。

**使变量可见性的方法：**

1. 在获取或改变变量的代码块上添加Synchronized关键字（效率低）。
2. 将变量声明为volatile类型（在访问volatile变量时不会执行加锁操作，因此不会导致线程阻塞；volatile变量是一种比Synchronized更轻量级的同步机制）。

**volatile的作用：**

- 使变量可见，确保将变量的更新操作通知到其他线程（volatile变量不会被缓存在寄存器或者其他处理器不可见的地方，因此在读取volatile类型的变量时总会返回最新写入的值）。
- 防止编译器对该变量进行重排序。
- volatile不能保证原子性。

**volatile本质**：在写入volatile变量之前对A可见的**所有**变量（包括非volatile修饰的变量）的值，在B读取了volatile变量之后，对B也是可见的。**从内存角度来看，写入volatile变量相当于退出了同步代码块，而读取volatile变量相当于进入了同步代码块。**不建议过度依赖volatile变量控制状态的可见性，这会比锁大的代码更脆弱，也更加难以理解。

**volatile的使用场景：**volatile变量通常用作某个操作完成、发生中断或者状态的标志。

#### 2.3.3.2 发布与逸出

**发布：**使对象能在当前作用域之外的代码中使用。

发布对象的方法：

1.  将对象的引用保存到一个公有的静态变量中，以便任何类和线程都能看到该对象。
2. 如果从非私有方法中返回一个引用，那么同样会发布返回的对象。
3. 发布一个内部的类实例。

**逸出：**某个不应该发布的对象被发布。

- 不要在构造过程中使this引用逸出。

#### 2.3.3.3 线程封闭

**线程封闭：**如果仅在单线程内访问数据，就不需要同步。

**线程封闭的例子：**

1. Swing
2. JDBC的Connection对象。

**Ad-hoc线程封闭：**指维护线程封闭性的职责完全由程序实现来承担。在程序中尽量少用Ad-hoc线程封闭。

**栈封闭：**是线程封闭的一种，在栈封闭中，只能通过局部变量才能访问对象。

1. 基本类型的局部变量始终封闭在线程内。

**ThreadLocal类：**维持线程封闭性的一种更规范方法是使用ThreadLocal，这个类能使线程中的某个值与保存值的对象关联起来。

1. ThreadLocal变量类似于全局变量，能降低代码的可重用性，并在类之间引入隐含的耦合性，因此在使用时要格外小心。

#### 2.3.3.4 不变性

**不可变对象：**使用不可变对象可以满足同步需求。

1. 不可变对象一定是状态安全的。
2. 不可变对象由构造函数来控制。
3. 在某些情况下，不可变对象能提供一种弱形式的原子性。

**不可变对象的必备条件：**

1. 对象创建以后其状态就不能修改。
2. 对象的所有域都是final类型（**String是不可变对象，StringBuilder是可变对象**）。
3. 对象是正确创建的（在对象的创建期间，this引用没有逸出）。

**final关键字：**除非需要某个域是可变的，否则应将其声明为final域。

1. final类型的域不能修改（指引用地址不变），但是域引用的对象是可变的。

2. 在java内存模型中，final域能确保初始化过程的安全性，可以不受限制地访问不可变对象，并在共享这些对象时无须同步。

#### 2.3.3.5 安全发布

安全发布的方法：

1. 在静态初始化函数中初始化一个对象引用。
2. 将对象的引用保存到volatile类型的域或者AtomicReference对象中。
3. 将对象的引用保存到某个正确构造对象的final类型域中。
4. 将对象的引用保存到一个由锁保护的域中（包括线程安装容器内部的同步）。

对象的发布需求取决于它的可变性：

1. 不可变对象可以通过任意机制来发布。
2. 事实不可变对象必须通过安全方式来发布。
3. 可变对象必须通过安全方式来发布，并且必须是线程安全的或者由某个锁保护起来。

### 2.3.4 对象的组合

#### 2.3.4.1 设计线程安全的类

在设计线程安全类的过程中，需要包含以下三个基本要素：

1. 找出构成对象状态的所有变量。
2. 找出约束状态变量的不变性条件。
3. 建立对象状态的并发访问管理策略。

#### 2.3.4.2 实例封闭

实例封闭要满足两个条件：

1. 保证被封闭对象不能超出它们既定的作用域。
2. 对象本身不会逸出。

实例封闭举例：

- 使用一些包装器的工厂方法（如：Collections.synchronizedXxx方法（装饰器模式））来进行实例封闭
- 使用监视器模式进行实例封闭（如：Vector、Hashtable）

#### 2.3.4.3 线程安全性的委托

- 将线程安全委托给其他状态变量（要求其他状态变量之间不存在耦合关系，否则（存在复合操作）委托会失效）

#### 2.3.4.4 在现有安全类中添加功能

- 扩展类加锁
- 客户端加锁机制
- 通过组合（采用java监视器模式）

### 2.3.5 基础构建模块

#### 2.3.5.1 同步容器

同步容器类包括：Vector、Hashtable以及Collections.synchronizedXxx等工厂方法创建的同步封装器类。

**同步容器类实现线程安全的原理：**将类的状态封装起来，并对每个公有方法都进行同步，使得每次只有一个线程能访问容器的状态。

注意事项：

- 同步容器上常见的复合操作：迭代（即使使用迭代器迭代）、跳转、条件运算、"若没有则添加"...

- 虽然同步容器类的单个操作是原子性的，但是对于复合操作要采用客户端加锁（一般使用同步容器类本身的锁）来保证线程安全性。

- 使用迭代器也无法避免在迭代期间对容器加锁。设计同步容器类的迭代器时并未考虑到并发修改的问题，其行为表现出是"即时失败"（当容器在迭代过程中被修改时，会抛出`ConcurrentModificationException`异常）。
- 同步容器类在进行迭代时，为了保证安全性，有两种方案：
  - 一种方案是迭代期间对同步容器加锁（性能很低）
  - 另一种方案是克隆容器，在副本上迭代（以空间换时间）

- 注意隐藏迭代器带来的线程安全问题
  - 例如：`System.out.println(set);`，其中set是一个Set集合，而set的toString方法会迭代集合。
  - 同步容器的hashCode、equals、containsAll、removeAll、和retainAll等方法以及把容器作为参数的构造方法同样也会间接执行迭代操作。

#### 2.3.5.2 并发容器

**同步容器的局限：**同步容器将所有对容器状态的访问都串行化，以实现它们的线程安全性，但代价是严重降低并发性。

**并发容器：**针对多个线程并发访问而设计，增加了对一些常见复合操作的支持（如："若没有则添加"、替换以及有条件删除等）。对比同步容器而言，并发容易可以极大地提高伸缩性并降低风险。

**对比ConcurrentHashMap与synchronizedMap：**

1. 与同步容器类不同，ConcurrentHashMap采用粒度更细的`分段锁`机制来实现更大程度的共享。分段锁机制使得ConcurrentHashMap在并发下实现更高的吞吐量，而在单线程下只损失非常小的性能。

   分段锁机制：

   - 任意数量的读线程可以并发访问Map；
   - 读线程和写线程可以并发访问Map；
   - 一定线程的写线程可以并发修改Map。

2. ConcurrentHashMap提供的迭代器不会抛出`ConcurrentModificationException`异常，因此无须在迭代过程中对容器加锁。ConcurrentHashMap的迭代器具有"弱一致性"。
3. 由于ConcurrentHashMap的"弱一致性"，在并发下其size和isEmpty方法方法一个近似值而非精确值。
4. 与Hashtable、synchronizedMap相比，ConcurrentHashMap中没有实现对Map加锁以提供独占访问（这会带来一些劣势，但是却带来性能上的巨大提升）。**总而言之，除非应用程序需要加锁Map进行独占访问才应该放弃使用ConcurrentHashMap**。
5. 由于ConcurrentHashMap中没有实现对Map加锁以提供独占访问，所以无法使用客户端加锁来创建新的原子操作，但是一些常见的复合操作，如"若没有则添加"（putIfAbsent方法）、"若相等则移除"（remove方法）、"若相等则替换"（replace方法）都以及实现了原子操作并在ConcurrentMap的接口中进行了声明。

**对比CopyOnWriteArrayList与synchronizedList：** CopyOnWriteArraySet与synchronizedSet同理。

1. CopyOnWriteArrayList在迭代时不需对容器进行加锁和复制，但是CopyOnWriteArrayList在每次迭代时都会对底层数组进行复制（这会提供更好的并发性能；但当容器特别大时，会带来空间和复制的开销）。
2. CopyOnWriteArrayList不会抛出`ConcurrentModificationException`异常，并且返回的元素与迭代器创建时的元素完全一致，而不必考虑之后修改操作所带来的影响。
3. **只有当迭代操作远远多于修改操作时，才应该使用"写入时复制"容器（即CopyOnWriteArrayList并发容器）**，典型的例子例子是事件监听器。

#### 2.3.5.3 阻塞队列和生产者消费者模式

阻塞队列（BlockingQueue）支持生成者-消费者这种设计模式，且支持任意数量的生产者和消费者。

- put方法：生产者方法；take方法：消费者方法。
- offer方法：定时的生产者方法；poll方法：定时的消费者方法。
- 实现类：LinkedBlockingQueue、ArrayBlockingQueue、PriorityBlockingQueue（可以实现排序）、SynchronousQueue（实际上不是一个真正的队列，没有存储功能，侧重"直接交付"）。

典型应用：

- 线程池与工作队列组合（如Executor任务执行框架）
- 构建资源管理机制。

Deque、BlockingDeque（双端阻塞队列）分别对Queue、BlockingQueue进行了扩展，具体实现是ArrayDeque和LinkedBlockingDeque。

> 参考博客文章：[使用BlockingQueue实现生产者-消费者模式](https://blog.csdn.net/makersy/article/details/90203587)

#### 2.3.5.4 阻塞方法域中断方法

恢复被中断的状态：`Thread.currentThread().interrupt();`

#### 2.3.5.5 同步工具类

同步工具类：可以是任何对象，只要它根据其自身的状态来协调线程的控制流。同步工具类包括阻塞队列、信号量、栅栏、闭锁。

**闭锁：**可以延迟线程的进度直到其到达终止状态。闭锁可以确保某些活动直到其他活动都完成后才继续执行。典型的闭锁有CountDownLatch。另外，FutureTask（异步任务）也可以实现闭锁。

**计数信号量：**作用是限流，可以控制任务的到达率。用来控制同时访问某个特定资源的操作数量，或者同时执行某个指定操作的数量。

**栅栏：**类似于闭锁。二者的关键区别在于，所以线程必须同时到达栅栏位置，才能继续执行。闭锁用于等待事件，而栅栏用于等待其他线程。典型的栅栏有CyclicBarrier、Exchanger。

#### 2.2.5.6 构建高效且可伸缩的结果缓存

构建高效且可伸缩的结果缓存的关键点：

1. 使用ConcurrentHashMap；
2. 使用FutureTask;
3. 使用ConcurrentHashMap的putIfAbsent方法而不是put方法。

> 参考博客文章：[Future与FutureTask](https://blog.csdn.net/sx1119183530/article/details/79735348)

### 2.3.6 任务执行

#### 2.3.6.1 在线程中执行任务

无限制创建线程的不足：

1. 线程生命周期的开销非常高。
2. 资源消耗。
3. 稳定性。

#### 2.3.6.2 Executor框架

**Executors静态工厂方法创建线程池的方法：**

- newFixedThreadPool：创建一个固定长度的线程池，每当提交一个任务时就创建一个线程，直到达到线程池的最大数量。
- newCachedThreadPool：创建一个可缓存的线程池，如果线程池的规模超过了处理需求时，那么将收回空闲的线程。
- newSingleThreadExecutor：一个单线程的Executor，它创建单个工作者线程来执行任务，如果这个线程异常结束，会创建另一个线程来替代。
- newScheduledThreadPool：创建一个固定长度的线程池，而且以延迟或定时的方式来执行任务，类似于Timer。

其中，newFixedThreadPool和newCachedThreadPool这两个工厂方法返回通用的ThreadPoolExecutor实例，这些实例可以直接用来构造专门用途的executor。

**ExecutorService接口：**解决执行服务的生命周期问题，同时还有一些用于提交的便利方法。

ExecutorService的生命周期有3种状态：运行、关闭和已终止

1. shutdown方法将执行平稳的关闭过程：不再接受新的任务，同时等待已经提交的任务执行完成，包括那些还未开始执行的任务。
2. shutdownNow方法将执行粗暴的关闭过程：它将尝试取消所有运行中的任务，并且不再启动队列中尚未开始执行的任务。

**Timer：**负责管理延迟任务（"在100ms后执行该任务"）以及周期任务（"每10ms执行一次任务"）。然而，Timer存在一些缺陷（**Timer支持基于绝对时间而不是相对时间的调度机制，因此任务的执行对系统时钟非常敏感；而ScheduledThreadPoolExecutor只支持基于相对时间的调度**），因此，实际中推荐使用ScheduledThreadPoolExecutor的构造函数或newScheduledThreadPool工厂方法来创建ScheduledThreadPoolExecutor对象。

1. Timer在执行所有定时任务时只会创建一个线程。如果某个任务的执行时间过长，那么将破坏其他TimerTask的定时精确性。
2. 若TimerTask抛出了一个未检查的异常，那么Timer将表现出糟糕的行为。Timer并不会捕获异常，当TimerTask抛出未检查的异常时将终止定时线程（已经被调度旦但尚未执行的TimerTask将不会再执行，新的任务也不能被调度）。
3. 在java5.0或更高的JDK中，将很少使用Timer。

#### 2.3.6.3 找出可利用的并行性

**Callable：**

1. Callable能返回结果并抛出异常。
2. `java.security.PrivilegedAction`能将Runnable封装为一个Callable。
3. Executor执行的任务有4个生命周期：**创建、提交、开始和完成**。

**Future：**表示一个任务的生命周期，并提供了相应的方法来判断是否已经完成或取消，以及获取任务的结果和取消任务等。

1. get方法会**阻塞**直到任务完成（或抛出异常）。
2. getCause方法获取抛出的异常。
3. 任务提交和获取结果中包含的安全发布属性确保了这个方法是线程安全的（另外，在将Runnable或Callable提交到Executor的过程中，也包含了一个安全发布过程）。

**并行任务的局限性：**

1. 通过对异构任务进行并行化来获得重大的性能提升是很困难的（**如：并行执行两个不同类型的任务获得性能提升很困难**）。
2. 如果没有在相似任务之间找出细粒度的并行性，那么这种方法带来的好处将减少（**如：任务A的执行时间是任务B的10倍**）。
3. 并行任务的适用场景：**大量相互独立且同构的任务**。

**CompletionService：**将Executor和BlockingQueue的功能融合在了一起，实现类是ExecutorCompletionService。

```java
// 从以下两点优化：
// 1. 为每幅图片的下载都创建一个独立任务，并在线程池中执行它们，从而将串行的下载过程转换为并行的过程
// 2. 通过从CompletionService中获取结果（take方法）以及使每张图片在下载完成后立刻显示出来，能使用户获得一个更加动态和更高响应性的用户界面
public abstract class Renderer {
    private final ExecutorService executor;
    Renderer(ExecutorService executor) {
        this.executor = executor;
    }

    void renderPage(CharSequence source) {
        final List<ImageInfo> info = scanForImageInfo(source);
        CompletionService<ImageData> completionService =
                new ExecutorCompletionService<ImageData>(executor);
        for (final ImageInfo imageInfo : info)
            completionService.submit(new Callable<ImageData>() {
                public ImageData call() {
                    return imageInfo.downloadImage();
                }
            });

        renderText(source);

        try {
            for (int t = 0, n = info.size(); t < n; t++) {
                Future<ImageData> f = completionService.take();
                ImageData imageData = f.get();
                renderImage(imageData);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            throw launderThrowable(e.getCause());
        }
    }
}
```

**为任务设置定时：**

1. 在支持时间限制的Future.get中支持这种需求：当结果可用时，它将立即返回；如果在指定时限内没有计算出结果，那么将抛出TimeoutException。
2. 当任务超时后应该立即停止（cancel方法），避免为继续计算一个不再使用的结果而浪费计算资源。
3. 使用限时的invokeAll：当超过指定时限后，任何未完成的任务都会取消。

### 2.3.7 取消与关闭

#### 2.3.7.1 任务取消

任务取消的原因：用户请求取消、有时间限制的操作、应用程序事件、错误、关闭。

**中断：**它并不会真正地中断一个正在运行的线程，而只是发出中断请求，然后由线程在下一个合适的时刻（取消点）中断自己（如：wait、sleep、join）。

- 解决阻塞队列中的中断问题：使用中断（如：Thread.currentThread().isInterrupted()）而不是boolean标志来请求取消。

**中断策略：**尽快退出，在必要时进行清理，通知某个所有者该线程已经退出。

**响应中断的两种策略：**传递异常、恢复中断状态。

注意事项：

- 除非清楚线程的中断策略，否则不要中断线程。当执行任务的线程是由标准的Executor创建的，它实现了一种中断策略使得任务可以通过中断被取消（此时，可以通过Future来取消中断，并设置cancel方法的参数mayInterruptIfRunning为true，表示这个线程能被中断）。
- 对于执行不可中断操作而被阻塞的线程，可以使用类似于中断的手段来停止这些线程，但这要求我们必须知道线程阻塞的原因。
- 封装非标准的取消。

#### 2.3.7.2 停止基于线程的服务

注意事项：

- 关闭ExecutorService： shutdown或shutdownNow

- 通过ExecutorService（newSingleThreadExecutor方法产生）封装单个线程。
- 使用"毒丸现象"（**毒丸是指一个放在队列上的对象，当得到这个对象时，立即停止**）关闭生产者-消费者服务。只有在生产者和消费者的数量都已知的情况下，才可以使用"毒丸"现象。
- 只执行一次的服务的生命周期受限于方法调用。
- 解决shutdownNow的局限性（shutdownNow只会返回所有已提交但尚未开始的任务）：封装ExecutorService跟踪Executor关闭时哪些任务正在执行。

#### 2.3.7.3 处理非正常的线程关闭

注意事项：

- 使用try...catch或try...catch...finally捕获非正常关闭的线程的异常。
- 使用UncaughtExceptionHandler异常处理器处理未捕获的异常（**只有通过execute提交的任务，它抛出的异常才会交给UncaughtExceptionHandler处理；而submit抛出的异常会被认为是任务返回状态的一部分**）。

#### 2.3.7.4 JVM关闭

JVM正常关闭的触发方式：

1. 线程（非守护）正常关闭时。
2. 调用了System.exit。
3. 其他平台方法：如Ctrl+C。

关闭钩子：通过Runtime.addShutdownHook注册的但尚未开始的线程。

### 2.3.8 线程池的使用

#### 2.3.8.1 任务与执行策略隐形耦合

**饥饿死锁：**只要线程池中的任务需要无限期地等待一些必须由池中其他任务才能提供的资源或条件（例如某个任务等待另一个任务的返回值或执行结果），那么除非线程池足够大，否则将发生线程饥饿死锁。

**解决无限制等待：**限定任务等待资源的时间。

#### 2.3.8.2 设置线程池的大小

**线程池的最优大小：**N_threads = N_cpu * U_cpu *  (1+W/C)，其中N_cpu为CPU的核数，U_cpu为CPU的利用率，W为等待时间，C为计算时间。

#### 2.3.8.3 配置ThreadPoolExecutor

**ThreadPoolExecutor的七大参数：**

1. corePoolSize：线程池的大小。
2. maximumPoolSize：线程池的最大大小。
3. keepAliveTime：线程的存活时间（超时了没人调用就会释放）

4. TimeUnit：存活时间的单位
5. BlockingQueue<Runnable>：阻塞队列，保存等待执行的任务（基本的任务有三种：无界队列、有界队列和同步移交），队列的选择与其他的配置参数有关（如线程池的大小）。
   - newFixedThreadPool和newSingleThreadExecutor在默认情况下将使用一个无界的LinkedBlockingQueue。
   - 有界队列（如ArrayBlockingQueue（FIFO）、有界的LinkedBlockingQueue（FIFO）、PriorityBlockingQueue（可以按照优先级处理任务））有助于避免资源耗尽的情况发生。队列的大小与线程池的大小必须一起调节，若线程池较小而队列较大，那么有助于减少内存使用量、降低CPU的使用率，同时还可以减少上下文切换，缺点是会限制吞吐量。
   - 对于非常大或者无界的线程池，可以使用SynchronousQueue来避免任务排队，直接将任务从生产者移交给工作者线程。只有当线程池是无界的或者可以拒绝任务时，SynchronousQueue才有实际价值。newCachedThreadPool工厂方法中就使用了SynchronousQueue。
6. ThreadFactory：线程工厂。每当线程池需要创建一个线程时，都是通过线程工厂方法来完成的。
   - ThreadFactory接口只有一个newThread方法。
7. RejectedExecutionHandler：饱和策略（当有界队列填满后，饱和策略开始发挥作用）。
   - AbortPolicy：**中止策略（默认的饱和策略）**，队列满了会抛出RejectedExecutionException异常。
   - CallerRunsPolicy：**调用者运行策略**（既不会抛弃任务，也不会抛出异常，而是将任务退回到调用者）
   - DiscardOldestPolicy：**抛弃最旧的策略**（队列满了就会尝试去和最早的竞争，竞争失败就会丢掉任务，且不会抛出异常）。
     - 若阻塞队列是一个优先级队列，那么抛弃最旧的策略将导致抛弃优先级最高的任务，因此不要将抛弃最旧的策略与优先级队列一起使用。
   - DiscardPolicy：**抛弃策略**（队列满了就会丢掉任务，且不会抛出异常）。

注意事项：

- corePoolSize、maximumPoolSize和keepAliveTime共同负责线程的创建与销毁。
- newFixedThreadPool工厂方法将线程池的基本大小和最大大小设置为参数中指定的值，而且创建的线程池不会超时。
- newCachedThreadPool工厂方法将线程池的最大大小设置为Integer.MAX_VALUE，而将其基本大小设置为0，超时设置为1分钟，这种线程池可以无限扩展，需求降低时会自动收缩。

#### 2.3.8.4 扩展ThreadPoolExecutor

以下方法可以扩展ThreadPoolExecutor的行为：

- beforeExecute：若beforeExecute抛出一个RuntimeException，任务将不会被执行，afterExecute也不会被调用。
- afterExecute：无论任务是从run中正常返回，还是抛出一个异常而返回，afterExecute都会被调用；若任务在完成后带有一个Error，则afterExecute不会被调用。
- terminated：在线程池完成关闭操作时调用terminated。terminated可以用来释放Executor在其生命周期里分配的各种资源，此外，还可以执行发送通知、记录日志或者收集finalize统计信息等操作。

#### 2.3.8.5 递归运算的并行化

- 若循环中的迭代操作都是独立的，并且不需要等待所有的迭代操作都完成再继续执行，那么就可以使用Executor将串行循环转化为并行循环。

### 2.3.9避免活跃性危险

#### 2.3.9.1 死锁

**死锁：**在线程A持有锁L并想获得锁M的同时，线程B持有锁M并尝试获得锁L，那么这两个线程将永远地等待下去。

- 锁顺序死锁：如果所有线程以固定的顺序来获得锁，那么在程序中就不会出现锁顺序死锁问题。
- 动态的锁顺序死锁：可以使用`System.identityHashCode`方法来**指定锁的顺序**和**加时赛锁**以解决死锁问题。
- 在协作对象之间发生死锁：通过**开放调用((解决协作对象之间的死锁。
- 资源死锁：

#### 2.3.9.2 死锁的避免与诊断

- 支持定时的锁
- 通过线程转储信息来分析死锁

#### 2.3.9.3 其他活跃性危险

- 饥饿：当线程由于无法访问它所需要的资源而不能继续执行时，就发生了"饥饿"，引发饥饿的最常见资源就是CPU时钟频率。
  - 要避免使用线程优先级，因为这回增加平台的依赖性，并可能导致活跃性问题。
- 糟糕的响应性：
- 活锁：线程将不断重复执行相同的操作，而且总会失败。

### 2.3.10 性能与可伸缩性

#### 2.3.10.1 对性能的思考

多线程带来的性能开销：

1. 线程之间的切换
2. 增加的上下文切换
3. 线程的创建和销毁
4. 线程的调度

应用程序性能的衡量指标：服务时间、延迟时间、吞吐率、效率、可伸缩性、容量...

可伸缩性：当增加计算资源时（如CPU、内存、存储容量或I/O带宽），程序的吞吐量或者处理能力能相应地增加。

#### 2.3.10.2 Amdahl定律

Amdahl定律：在增加计算资源的情况下，程序在理论上能够实现最高加速比，这个值取决于程序中可并行组件与串行组件所占的比重。

最高加速比：
$$
S<=1/(F+(1-F)/N)
$$
其中N为处理器的数量，F为串行执行的部分。

两种降低锁粒度的计数：锁分解（将一个锁分解为两个锁）、锁分段（把一个锁分解为多个锁）。

#### 2.3.10.3 线程引入的开销

1. 上下文切换：在JVM和操作系统的代码中消耗越多的CPU时钟周期，应用程序的可用CPU时钟周期就越少；但上下文切换的开销并不只包含JVM和操作系统的开销。
2. 内存同步：在synchronized和volatile提供的可见性保证中可能会使用一些特殊指令，即**内存栅栏**。内存栅栏可以刷新缓存，使缓存无效，刷新硬件的写缓冲，以及停止执行管道。在内存栅栏中，大多数操作都是不能被重排序的。
3. 阻塞：自旋等待或挂起

#### 2.3.10.4 减少锁的竞争

降低锁竞争的三种方式：

1. 减少锁的持有时间：缩小锁的范围（"快进快出"）
2. 降低锁的请求频率：减小锁的粒度（"锁分解"、"锁分段"）
   - 若程序采用锁分段技术，那么一定要表现出在锁上的竞争频率高于在锁保护的数据上发生竞争的频率。
   - 放弃独占锁，使用并发容器、读-写锁、不可变对象以及原子变量。
3. 使用带有协调机制的独占锁，这些机制允许更高的并发性

CPU没有得到充分利用的原因有：

1. 负载不充足
2. I/O密集
3. 外部限制
4. 锁竞争

#### 2.3.10.5 比较Map的性能

Map的性能的比较：

ConcurrentHashMap（锁分段+读写锁）>ConcurrentSkipListMap>synchronized HashMap（独占锁） >synchronized TreeMap

### 2.3.11 显示锁

#### 2.3.11.1 Lock与ReentrantLock

Lock：提供了一种无条件的、可轮询的、定时的以及可中断的锁获取操作，所有加锁和解锁的方式都是显式的。

ReentrantLock：实现了Lock，并提供了与synchronized相同的互斥性和内存可见性。

- 使用ReentrantLock时，一定要在finally块中释放锁。(**使用FindBugs插件可以检查这种错误**)
- 使用可定时（定时的tryLock）的与可轮询（tryLock）的锁可以避免死锁的发生
- 定时的tryLock同样能响应中断，因此当需要实现一个定时的和可中断（lockInterruptibly）的锁获取操作时，可使用tryLock方法

#### 2.3.11.2 性能考虑因素

JDK的版本也会影响锁的性能。

- JDK5.0中，从单线程变为多线程时，内置锁的性能急剧下降，而ReentrantLock的性能下降则比较平稳。
- JDK6.0中，从单线程变为多线程时，内置锁的性能不会由于竞争而急剧下降，内置锁和ReentrantLock的可伸缩性基本相当。

#### 2.3.11.3 公平性

ReentrantLock：有两种构造函数（公平锁和非公平锁）

- 公平锁：线程按照它们发出请求的顺序来获得锁。当持有锁的时间相对较长，或者请求锁的平均时间间隔较长，那么应该使用公平锁。
- 非公平锁：允许"插队"
- Semaphore也有公平锁和非公平锁
- 大多数情况下，非公平锁的性能要高于公平锁的性能

#### 2.3.11.4 锁的选择

ReentrantLock与synchronized的选择：

1. ReentrantLock提供可定时的、可轮询的与可中断的锁获取操，公平队列、以及非块结构的锁。
2. ReentrantLock使用更复杂，需要在finally块中释放锁。
3. synchronized更容易使jvm发现死锁。

#### 2.3.11.5 读-写锁

**读写锁（ReadWriteLock接口）：**一个资源可以被多个读操作访问、或者被一个写操作访问，但两者不能同时进行。

读写锁的两者实现：

- ReentrantReadWriteLock
- ReadWriteLockView

### 2.3.12 原子变量与非阻塞同步机制

#### 2.3.12.1 锁的劣势

锁与volatile：

1. 与锁相比，volatile变量是一种更轻量级的同步机制，因为在使用这些变量时**不会发生上下文切换或线程调度**等操作。
2. volatile不能构建原子的复合操作。

#### 2.3.12.2 硬件对并发的支持

现代处理器：支持**比较并交换（CAS）**或**关联加载/条件存储**指令。

比较并交换（CAS，相当于乐观锁）：包含3个操作数，需要读写的内存位置V、进行比较的值A和拟写入的新值B。

- 当且仅当V的值等于A时，CAS才会通过原子方式用新值来更新V的值，否则不会执行任何操作。
- 无论位置V的值是否等于A，都将返回V原有的值。
- 线程在竞争CAS失败时不会阻塞。
- 基于CAS的计数器在性能上远远好于基于锁的计数器。
- 在程序内部执行CAS时不需要执行JVM代码、系统调用或线程调度操作。
- CAS的缺点是需要调用者处理竞争问题（如重试、回退或放弃），而锁能够自动处理竞争问题（线程在获得锁之前一直阻塞）

#### 2.3.12.3 原子变量类

原子变量类：比锁的粒度更细、量级更轻，更适合于在多处理器系统上实现高性能的并发代码。

- 更新原子变量类不需要挂起或重新调度线程。
- 原子变量类相当于一种泛化的volatile变量，能够支持原子的和有条件的读-改-写操作。
- 在高度竞争的情况下，锁的性能会超过原子变量的性能；但由于在实际中，锁在发生竞争时会挂起线程，会降低CPU的使用率和共享内存总线上的同步通信量，从而导致在真实环境下，原子变量的性能反而会更好。

#### 2.3.12.4 非阻塞算法

非阻塞算法：一个线程的失败或挂起不会导致其他线程也失败或挂起。

### 2.3.13 java内存模型

#### 2.3.13.1 java的内存模型（JMM）







#### 2.3.13.2 发布







#### 2.3.13.3 初始化过程中的安全性



















> 参考博客文章：[《JAVA并发编程实战》]()









# 3 IO/BIO/NIO

java.nio

















# 4 集合源码

## 4.1 HashMap

**ArrayList**：底层的数据结构是**数组**。（查找、更改快；插入、删除慢）

**LinkedList**：底层的数据结构是**链表**。

**HashMap**： jdk1.7底层的数据结构是**数组+链表**、jdk1.8底层的数据结构是**数组+链表+红黑树**。

**为什么要引入红黑树？**

**HashMap的初始容量为什么是2^n？**

1. 方便做&运算。
2. 方便扩容后数据的移动。

**默认初始容量是16？**

**负载因子为什么是0.75？**负载因子太小了浪费空间并且会发生更多次数的resize，太大了哈希冲突增加会导致性能不好。负载因子取0.75本质是空间与时间成本的一种折中。















> [ArrayList源码分析](https://www.cnblogs.com/zhangyinhua/p/7687377.html)、[LinkedList源码分析](https://www.cnblogs.com/zhangyinhua/p/7688304.html)、[HashMap源码分析](https://www.cnblogs.com/zhangyinhua/p/7698642.html)、[Vector和Stack源码分析](https://www.cnblogs.com/zhangyinhua/p/7688722.html)、[深入理解Java集合框架](https://zhuanlan.zhihu.com/p/24687801)、[哈希表解决冲突的两种方式](https://blog.csdn.net/zkangaroo/article/details/64918956)

# 5 常用的工具包

Apache commons











# 6 MySQL提高

## 6.1 MySQL基础巩固

### 6.1.1 DISTINCT

**注意事项：**不能部分使用DISTINCT，DISTINCT关键字应用于所有列而不仅仅是前置它的列。如果给出`SELECT DISTINCT 字段1,字段2`，除非指定的两个列都不同，否则所有行都将被检索出来。

### 6.1.2 LIMIT

**注意事项：**

1. `LIMIT 5`：表示返回数据不多于5行（带一个值的LIMIT总是从第一行开始，给出的数为返回的行数）。
2. `LIMIT 5, 5`：表示返回从行5开始的5行（带二个值的LIMIT，第一个数为开始位置，第二个数为要检索的行数）。
3. 行0：检索出来的第一行为行0而不是行1（如LIMIT 1, 1将检索出第二行而不是第一行）。
4. `LIMIT 4 OFFSET 3 等价于 LIMIT 3, 4`： MySQL 5版本之后。

### 6.1.3 完全限定.

1. 表名.字段名：完全限定列。
2. 数据库名.表名：完全限定表。

### 6.1.4 ORDER BY

1. `ORDER BY 字段1,字段2`：表示先按字段1排序，再按照字段2排序。
2. `DESC`：降序。
3. `ASC`：升序（很少使用，因为升序是默认的）。
4. MySQL中默认字典排序中的A和a视为相同，但是数据库管理员可以变更这种行为。
5. 顺序问题：ORDER BY 需位于FROM之后，LIMIT 需位于ORDER BY之后，即`FROM...ORDER BY...LIMIT...`。

### 6.1.5 where过滤

1. 顺序问题：`FROM...where...ORDER BY...LIMIT...`。
2. 过滤：也可以再应用层过滤数据。
3. 关于!=与<>：
4. BETWEEN...AND：包含开始值与结束值。
5. IS NULL： NULL与不匹配含义不同。
6. 优先级：AND>OR。
7. IN与OR能实现相同的功能，但是IN**更优雅**、**效率更快**、**计算次序更易管理**，且**IN中可以包含其他SELECT语句**。
8. 关于NOT： MySQL支持使用NOT对IN、BETWEEN、和EXISTS子句取反，这与多数其他DBMS允许使用NOT对各种条件取反有很大的差别。

### 6.1.6 LIKE+通配符

1. %：表示任何字符出现任意次数（包含0个字符）；但是%不能匹配NULL，%可以匹配空格，但一般不这样使用（一般采用函数去掉首尾空格）。
2. _：只能匹配单个字符（不能是0个字符）。
3. 通配符效率很低，尽量少使用。

### 6.1.7 正则表达式搜索

1. REGEXP + 正则表达式
2. LIKE与REGEXP： LIKE是整个匹配，REGEXP是只要出现就算匹配；REGEXP配合使用^和$可以实现与LIKE相同的功能（可以比LIKE更精确）。

3. 正则表达式匹配默认不区分大小写，若要区分，则使用`REGEXP BINARY + 正则表达式`。

4. |：类似于OR匹配。注意`'1|2|3 Ton'`和`'(1|2|3) Ton'`的区别，时刻不要忘记加括号（务必避免歧义的出现）。
5. []：类似于枚举，另一种形式的OR匹配。
6. [a-z]：匹配范围。
7. [^a-z]：反向匹配。
8. `\\.`：匹配.特殊字符（\\\类似于转义字符）。

9. 匹配字符类：

   ```SQL
   [:alnum:]：同[a-zA-Z0-9]
   [:alpha:]：同[a-zA-Z]
   [:blank:]：同[\\t]
   [:cntrl:]：匹配ASCII控制字符（ASCII 0到31和127）
   [:digit:]：同[0-9]，例如[[:digit:]]{4}等价于[0-9]{4}
   [:lower:]：同[a-z]
   [:upper:]：同[A-Z]
   [:xdigit:]：同[a-fA-F0-9]
   ```

10. 匹配多个实例：

    ```SQL
    *  0个或多个匹配
    +  1个或多个匹配，同{1,}
    ?  0个或1个匹配，同{0,1}
    {n}  指定数目的匹配
    {n,}  不少于指定数目的匹配
    {n,m}  匹配数目的范围（m不超过255）
    ```

11. 定位符：

    ```SQL
    ^  文本的开始
    $  文本的结束
    [[:<:]]  词的开始
    [[:>:]]  词的结尾
    ```

### 6.1.8 计算字段

1. Concat()：拼接字段。
2. RTrim()： 去掉右边所有的空格。
3. AS：设置别名。
4. 执行算术计算。

### 6.1.9 使用数据处理函数

1. 文本处理函数：

   ```SQL
   RTrim()  去除列值右边的空格
   LTrim()  去除列值左边的空格
   Upper()  将文本转化为大写
   Lower()  将串转换为小写
   Length() 串的长度
   Locate()  找出串的一个字串
   Right()  返回串右边的字符
   SubString() 返回字串的字符
   Soundex()  返回串的SOUNDEX值，可以匹配发音相同的值
   ```

2. 日期和时间处理函数：

   ```SQL
   AddDate()  增加一个日期（天、周等）
   AddTime()  增加一个时间（时、分等）
   CurDate()  返回当前日期
   CurTime()  返回当前时间
   Date()  返回日期时间的日期部分（很重要）
   Time()  返回日期事件的时间部分（很重要）
   DateDiff()  计算两个日期之差
   Date_Format()  返回一个格式化的日期或时间串
   Year()  返回一个日期的年份部分（很重要）
   Month()  返回一个日期的月份部分（很重要）
   MySQL默认使用的日期格式为yyyy-mm-dd
   ```

3. 数值处理函数：

   ```SQL
   Abs()  返回绝对值
   Mod()  但会除操作数的余数
   Rand()  返回一个随机数
   ```

### 6.1.10 汇总函数

1. 聚集函数：

   ```SQL
   AVG()  返回某列的平均值（会忽略列值为NULL的行）
   COUNT()  返回某列的行数（会忽略列值为NULL的行）
   COUNT(*)  返回某列的行数（不会忽略列值为NULL的行）
   MAX()  返回某列的最大值（会忽略列值为NULL的行）
   MIN()  返回某列的最小值（会忽略列值为NULL的行）
   SUM()  返回某列值之和（会忽略列值为NULL的行）
   ```

### 6.1.11 分组数据

使用GROUP BY创建分组：

1. 顺序问题：`FROM...WHERE...GROUP BY...ORDER BY...LIMIT...`。
2. GROUP BY子句中不能使用聚集函数和别名。

3. 如果列中有多行NULL值，将被分为一组。

4. 使用WITH ROLLUP可以得到每个分组以及每个分组汇总级别。

使用HAVING过滤分组：

1. HAVING与WHERE的区别：WHERE过滤行，HAVING过滤分组。
2. 顺序问题：`FROM...WHERE...GROUP BY...HAVING...ORDER BY...LIMIT...`。

### 6.1.12 使用子查询

1. 子查询非常难以阅读，尽量少用。

### 6.1.13 联结表

1. 建立联结关系时，再引用的列可能出现二义性时，必须用完全限定列名。
2. 不使用where条件建立联结表时，会使用笛卡尔积建立联结表。
3. INNER JOIN...ON：注意这里使用ON而不是where。
4. 联结表非常耗费资源，尽量少使用或者使用联结的表越少越好。

5. 联结的种类：自联结/自然联结/外部联结。
6. 外部联结：LEFT OUTER JOIN...ON：左外联结；RIGHT OUTER JOIN...ON：右外联结。

### 6.1.14 组合查询

1. 使用UNION关键字。
2. UNION规则：
   - 必须由两条或两条以上的SELECT语句组成，语句之间用关键字UNION分隔。
   - UNION中的每个查询必须包含相同的列、表达式或聚集函数（不过各个列不需要以相同的次序列出）。
   - 列数据类型必须兼容。

3. UNION会自动去除重复的行；若不想去除重复的行，可以使用UNION ALL。
4. 对组合结果进行排序：`SELECT...WHERE...UNION...SELECT...WHERE...ORDER BY...`；组合查询时，ORDER BY只能出现在最后一条SELECT语句之后，但可以对所有组合结果进行排序。

### 6.1.15 全文本搜索（"索引"）

1. **MyISAM支持全文本搜索(MySQL的默认引擎是MyISAM)；InnoDB不支持全文本搜索**。
2. 采用通配符+正则表达式可以实现全文本搜索，有以下缺点（**正是这些缺点的存在，才会引入全文本搜索**）：
   - 性能非常低（匹配表中所有行+极少使用表索引）。
   - 不能明确控制匹配什么和不匹配什么。
   - 不能提供一种智能化的选择结果的方法（不能对结果进行优先级排序）。

3. 启用全文本搜索支持：创建表时，添加`FULLTEXT(字段名)`子句。
4. 进行全文本搜索：`SELECT...FROM...WHERE Match(字段名) Against('指定要搜索的表达式')`
5. 除非使用BINARY方式，否则全文本搜索不区分大小写。
6. 使用查询拓宽：`SELECT...FROM...WHERE Match(字段名) Against('指定要搜索的表达式' WITH QUERY EXPANSION)`。（使用查询拓展时，MySQL会进行两遍扫描来完成搜索：第一遍找出与搜索条件匹配的所有行，第二遍使用所有有用的词再次进行全文本搜索）。
7. 布尔文本搜索：`SELECT...FROM...WHERE Match(字段名) Against('指定要搜索的表达式,包含布尔操作符+(包含词)>(包含且增加等级值)-(排除词)<(排除且减少等级值)~(取消一个词的排序值)和*(截断操作符，类似于词尾的通配符)' IN BOOLEAN MODE)`。

**全文本搜索的注意事项**：

- 在索引全文本数据时，短词（3个及以下字符的词，数目可以更改）被忽略且从索引中排除。

- MySQL自带一个内建的非用词列表（可以更改覆盖这个表），这字额词在索引全文本数据时总是被忽略。

- 如果一个词出现在50%以上的行中，那么它将作为一个非用词忽略；50%规则不用于IN BOOLEAN MODE。

- 若表中的行数少于3行，则全文本搜索不返回结果（因为每个词或者不出现，或者至少出现在50%的行中）。

- 忽略词中的单引号，如don't索引为dont。

- **不具有词分隔符（包括日语和汉语）的语言不能恰当地返回全文文搜索结果**。
- 仅在MyISAM数据库引擎中支持全文本搜索。

### 6.1.16 插入数据

1. 插入完整的行：`INSERT INTO 表名 VALUES(字段1值,字段2值,...);`（高度依赖表中列的定义次序，不推荐）。
2. 插入行的一部分：`INSERT INTO 表名(字段1, 字段2..) VALUES(字段1值,字段2值,...);`。
3. 插入多行：`INSERT INTO 表名(字段1, 字段2..) VALUES(字段1值,字段2值,...),(字段1值,字段2值,...)...;`（比每次插入一行效率更高）。
4. 插入某些查询的结果：`INSERT INTO 表名(字段1, 字段2..) SELECT...FROM...WHERE...;`

### 6.1.17 更新和删除数据

1. 更新特定行：`UPDATE 表名 SET...WHERE...;`
2. 使用子查询更新：`UPDATE 表名 SELECT子查询 WHERE...;`
3. 更新表中所有的行：`UPDATE 表名 SET...;`
4. 若更新时出错即要全部撤销更新可以使用`UPDATE IGNORE`关键字。
5. 删除特定的行：`DELETE FROM 表名 WHERE...;`
6. 删除所有行：`DELETE FROM 表名;`或者`TRUNCATE 表名`（后者速度更快，因为后者的本质是删除原来的表，并重新新建一张表，而前者是逐行删除表中的数据）。

**注意事项：**

- 除非确实打算更新和删除每一行，否则绝对不要使用不带WHERE子句的UPDATE或DELETE语句。
- 保证每个表都由主键，尽可能像WHERE子句那样使用它。
- 在对UPDATE或DELETE语句使用WHERE子句前，应该先使用SELECT进行测试，保证它过滤的是正确的记录，以防编写的WHERE子句不正确。
- 使用强制实施引用完整性的数据库，这样MySQL将不允许删除具有与其他表相关联的数据的行。

### 6.1.18 创建和操作表

1. 创建表：`CREATE TABLE 表名 IF NOT EXISTS(字段1 类型, 字段2 类型,...,PRIMARY KEY(字段名))ENGINE=InnoDB;`
2. 使用多个列构成的主键：`CREATE TABLE 表名 IF NOT EXISTS(字段1 类型, 字段2 类型,...,PRIMARY KEY(字段1,字段2...))ENGINE=InnoDB;`

3. 确定AUTO_INCREMENT的值：让MySQL生成主键的一个缺点是你不知道这些值都是谁，而使用`SELECT last_insert_id()`函数可以获得这个值。
4. **引擎类型**：引擎类型可以混用（**缺点是外键不能跨引擎，即使用一个引擎的表不能引用具有使用不同引擎的表的外键**），不同表可以使用不同的引擎类型。
   - **InnoDB：一个可靠的事务处理引擎，但不支持全文本搜索**。
   - **MEMORY：在功能上等同于MyISAM，但由于数据存储在内存中，速度很快（适合于临时表）**。
   - **MyISAM：一个性能极高的引擎，不支持事务处理，但支持全文本搜索**。

5. 更改表：
   - 给表增加一个列：`ALTER TABLE 表名 ADD 字段1 数据类型;`
   - 删除表的一个列：`ALTER TABLE 表名 DROP COLUMN 字段1;`
   - 定义外键：`ALTER TABLE 表1 ADD CONSTRAINT 外键名 FOREIGN KEY(表1的字段) REFERENCES 表2 (表2的字段);`

6. 删除表：`DROP TABLE 表名`
7. 重命名表：`RENAME TABLE 表名 TO 新表名;`、`RENAME TABLE 表名1 TO 新表名1, 表名2 TO 新表名2, 表名3 TO 新表名3;`

### 6.1.19 使用视图（"虚拟表"）

**视图：**虚拟的表（**本质是一个SQL查询的结果集形成的一个虚拟表**），不包含表中应该有的任何列或数据。

**视图的作用：**

- 重用SQL语句。
- 简化复杂的SQL操作。
- 使用表的组成部分而不是整个表。
- 保护数据。可以给用户授权表的特定部分的访问权限而不是整个表的访问权限。
- 更改数据格式和表示，视图可返回与底层表的表示和格式不同的数据。

**视图的规则：**

- 唯一命名。
- 视图数目无限制。
- 创建视图需要足够的访问权限。
- 视图可以嵌套。
- 视图不能索引，也不能有关联的触发器或默认值。
- ORDER BY可以用在视图中，但如果从该视图检索数据SELECT中也包含ORDER BY，那么该视图中的ORDER BY将被覆盖。
- 视图可以和表一起使用。

**使用视图：**

1. 创建视图：`CREATE VIEW 视图名 AS SELECT语句;`。
2. 查看创建视图的语句：`SHOW CREATE VIEW 视图名;`。
3. 删除视图：`DROP VIEW 视图名;`。
4. 更新视图：`CREATE OR REPLACE VIEW 视图名 AS SELECT语句; `（也可以分两步，先DROP，再CREATE）。
5. 过滤视图：`CREATE VIEW 视图名 AS SELECT语句 视图的WHERE语句;`。
6. 更新视图：对视图增加或删除行，实际上是对其基表增加或删除行；但如果视图定义中有以下操作：分组（使用GROUP BY 或HAVING）、联结、子查询、并、聚集函数、DISTINCT、导出（计算）列，则不能进行视图的更新。

### 6.1.20 使用存储过程（"函数"）

**存储过程：**为以后的使用而保存的一条或多条MySQL语句的集合（可将其视为批文件，**本质是一种函数**）。

**存储过程的作用：**简单、安全、高性能。

- 简化复杂的操作。
- 防止错误并保证了数据的一致性。
- 简化对变动的管理。
- 提高性能（使用存储过程比使用单独的SQL语句更快）。

**存储过程的缺点：**

- 编写存储过程需要更高的技能，更丰富的经验。
- 创建存储过程需要一定的访问权限。

**使用存储过程：**

1. 执行（也称之为调用）存储过程：`CALL 存储过程名(参数1, 参数2, 参数3...);`（类似于调用函数）
2. 创建存储过程：

```SQL
# 1. 定义带参的存储过程
# OUT表示从存储过程传出值，IN表示传入值，INOUT表示传入传出
CREATE PROCEDURE 存储过程名(OUT 参数1 参数1类型, OUT 参数2 参数2类型, IN 参数3 参数3类型) 
BEGIN
	SELECT语句;
END; // 注意这里：存储过程的分隔符默认是;，可以使用DELEMITER指定分割符，如DELEMITER // 即指定// 为存储火车的分隔符

# 2. 调用存储过程
CALL 存储过程名(@参数1, @参数2, 2005)
# 3. 显示调用时传出的值
SELECT @参数值
```

3. 删除存储过程：`DROP PROCEDURE 存储过程名;`

4. 建立智能化存储过程：
   - 使用注释： `--空格 `。
   - 使用DECLARE定义变量：`DECLARE 变量名 变量类型 DEFAULT 变量默认值;`。
   - 加入程序流程语句：`IF...END IF;`。

5. 显示存储过程创建的语句：`SHOW CREATE PROCEDURE 存储过程名;`

6. 获得何时、由谁创建等详细信息：`SHOW PROCEDURE STATUS 存储过程名;`

### 6.1.21 使用游标

**游标：**是一个存储在MySQL服务器上的数据库查询，不是一条SELECT语句，而是被该语句检索出来的结果集。在存储了游标之后，应用程序可以根据需要滚动或浏览其中的数据（**MySQL游标只能用于存储过程（和函数）**）。

**游标的注意事项：**

- 使用前必须先声明定义游标（**使用声明过的游标不需要再次声明，用OPEN语句打开它就可以了**）。
- 一旦声明后，必须打开游标以供使用。
- 对于添有数据的游标，根据需要取出（检索）各行。
- 在结束游标使用时，必须关闭游标（**一个游标关闭后，若未重新打开，则不能使用它**）。

**游标的使用：**

1. 创建游标：

```sql
CREATE PROCEDURE 存储过程名()
BEGIN
	# 定义游标
	DECLARE 游标名 CURSOR
	FOR
	SELECT语句;
	OPEN 游标名;
	CLOSE 游标名;
END; # 若此前未关闭游标，到END这里，MySQL会自动帮助关闭游标
```

2. 打开游标：`OPEN 游标名;`
3. 关闭游标：`CLOSE 游标名;`
4. 使用游标数据：

### 6.1.22 使用触发器

**触发器：**MySQL响应DELETE/INSERT/UPDATE语句而自动执行的某种处理。

场景：

1. 每当增加一个顾客到某个数据库表时，都检查其电话号码格式是否正确，州的缩写是否为大写。
2. 每当订购一个产品时，都从库存数量中减去订购的数量。
3. 无论何时删除一行，都在某个存档表中保留一个副本。

**触发器的4各必要条件**：

- 唯一的触发器名（**数据库中唯一，虽然MySQL 5中要求每个表中唯一即可**）
- 触发器关联的表
- 触发器应该响应的活动（DELETE、INSERT、UPDATE）
- 触发器何时执行（处理之前或之后）

**触发器的使用：**

1. 创建触发器：`CREATE TRIGGER 触发器名 AFTER/BEFORE 响应活动(DELETE/INSERT/UPDATE) ON 表名 FOR EACH ROW SELECT '消息'; `
2. 删除触发器：`DROP TRIGGER 触发器名;`
3. 使用触发器：
   - INSERT触发器：（例：自动填充创建时间）
   - DELETE触发器：（例：逻辑删除）
   - UPDATE触发器：（例：自动填充更新时间）

**注意事项：**

1. 每个表每个事件每次只允许一个触发器（因此，每个表最多支持6个触发器，每条INSERT/UPDATE/DELETE的之前和之后）。
2. 单一触发器不能与多个事件或多个表关联。
3. 若BEFORE触发器失败，则请求操作将不被执行；若BEFORE触发器或语句本身失败，MySQL将不执行AFTER触发器（如果有的话）。

### 6.1.23 管理事务

**几个概念：**

1. 事务(transaction)：指一组SQL语句。
2. 回退(rollback)：指撤销指定SQL语句的过程。
3. 提交(commit)：指将未存储的SQL语句结果写入数据库表。
4. 保留点(savepoint)：指事务处理中设置的临时占位符，你可以对它发布回退。

**使用事务处理**：

1. 开启事务：`START TRANSACTION;`
2. 回退：`ROLLBACK;`（**当COMMIT和ROLLBACK语句执行后，事务会自动关闭，将来的更改会隐含提交**）
3. 提交事务：`COMMIT;`（**当COMMIT和ROLLBACK语句执行后，事务会自动关闭，将来的更改会隐含提交**）

4. 使用保留点：`SAVEPOINT 保留点名称;`、`ROLLBACK TO 保留点名称;`
5. 更该默认的提交行为：`SET autocommit=0;`（默认的MySQL是自动提交所有修改，这里设置为不自动提交更改）。

## 6.2 MySQL性能优化

MySQL性能优化的几个方向：**库表结构优化**、**索引优化**、**查询优化**、分区优化（与索引类似）、查询缓存优化。

### 6.2.1 并发控制的锁策略

**MySQL并发控制的锁策略：**

- **表锁**：最基本的锁策略，是开销最小的策略**（举例：MyISAM）**
  1. 会锁定整张表，当对表进行写操作（插入/删除/更新）前，需要先获取锁，这会阻塞其他用户对该表的所有读写操作；
  2. 只有没有写锁时，其他读取的用户才能获得读锁；
  3. 读锁之间是不相互阻塞的。
- **行级锁**：最大程度地支持并发处理，同时也带来了最大的锁开销**（举例：InnoDB）**
  1. 行级锁只在存储引擎层实现，而在MySQL服务器层没有实现
  2. 所有的存储引擎都以自己的方式显现了锁机制。
  3. 典型的，InnoDB引擎和XtraDB引擎实现了行级锁。

### 6.2.2 事务的ACID

1. 原子性：事务不可分隔，要么全部成功，要么全部失败。
2. 一致性：事务必须使数据库从一个一致性状态变换到另外一个一致性状态。
3. 隔离性：事务的隔离性是指一个事务的执行不能被其他事务干扰（即对其他事务不可见）。
4. 持久性：持久性是指一个事务一旦被提交，它对数据库中数据的改变就是永久性的。即使系统重启也不会丢失。

### 6.2.3 事务的隔离级别

**关于隔离级别：**较低的隔离级别（**SQL标准中定义了四种隔离级别**）可以执行更高的并发，系统的开销也越低。

1. 未提交读（READ UNCOMMITTED）：

   - 事务中的修改，即时没有提交，对其他事务也都是可见的。
   - 事务可以读取未提交的数据，称之为**脏读**。

   - 从性能上来说，不会比其他级别好太多，**实际中一般很少使用**。

2. 提交读（READ COMMITTED）：

   - 大多数数据库系统的默认隔离级别都是READ COMMITTED（但MySQL不是），READ COMMITTED满足ACID中隔离性的定义。
   - 一个事务从开始直到提交之前，所做的任何修改对其他事务都不可见。这个级别也称之为**不可重复读**。
   - 两次执行同样的查询，可能会得到不一样的结果。

3. 可重复读（REPEATABLE READ）：

   - 解决了脏读的问题；保证了在同一个事务中多次读取同样记录的结果是一致的。
   - 无法解决**幻读问题**。所谓幻读，是指当某个事务在读取某个范围内的记录时，另外一个事务又在该范围内插入了新的记录，当之前的事务再次读取该范围的记录时，会产生换行。
   - **REPEATABLE READ是MySQL的默认事务隔离级别**。InnoDB和XtraDB存储引擎通过多版本并发控制解决了幻读的问题。

4. 可串行化（SERIALIZABLE）：

   - 是最高的隔离级别，通过强制事务串行执行，避免了幻读的问题。
   - SERIALIZABLE会在读取的每一行数据上都加锁，可能导致大量的超时和锁争用问题。
   - 实际中很少使用，只有在非常需要确保数据的一致性而且可以接受没有并发的情况下，才考虑该级别。

下面是总结：

![image-20201202181252894](README.assets/image-20201202181252894.png)

### 6.2.4 多版本并发控制（MVCC）

**InnoDB的MVCC原理：**通过在每行记录后面保存两个隐藏的列来实现MVCC。一个列是保存行的创建时间，一个是保存行的过期时间（或删除时间）。当然，存储的并不是实际的时间指，而是系统版本号。每开始一个新的事务，系统版本号会自动递增；事务开始时刻的系统版本号会作为事务的版本号，用来和查询到的每行记录的版本号进行比较。

在可重复读隔离级别下，MVCC的操作：

1. SELECT： InnoDB只查找版本早于当前事务版本的数据行，这样可以确保事务读取的行，要么是事务开始前已存在，要么是事务自身插入或修改的；行的删除版本要么未定义，要么大于当前事务版本号，可以确保事务读取到的行。在事务开始之前未被删除。
2. INSERT：为新插入的每一行保存当前系统版本号作为行版本号。
3. DELETE：为删除的每一行保存当前系统版本号作为删除标识。
4. UPDATE： InnoDB为插入一行新记录，保存当前系统版本号作为行版本号，同时保存当前系统原来的行作为行删除标识。

MVCC只在可重复读和提交读两个隔离级别下工作。

### 6.2.5 MySQL的存储引擎

查看表的信息：`SHOW TABLE STATUS LIKE '表名'`

#### 6.2.5.1 InnoDB

**InnoDB的特性：**事务型引擎

- 数据存储在表空间中。
- 采用MVCC来支持高并发，实现了四个标准的隔离级别，默认级别是可重复读，并且通过间隙锁策略防止幻读的出现。
- InnoDB表是基于聚族索引建立的。

#### 6.2.5.2 MyISAM

**MyISAM的特性：**非事务型引擎

- 不支持事务和行级锁，崩溃后无法完全修复。
- 将表存储在两个文件中：数据文件和索引文件。
- **采用表锁策略**。读取时会对所有表加共享锁，写入时则对表加排他锁。
- 可以手工或者自动执行检查和修复操作。
- **支持全文索引（基于分词创建的索引），可以支持复杂的查询**。
- 延迟更新索引键，将索引写入到内存缓冲区中，提升写入性能，缺点是崩溃时会造成索引损坏。

#### 6.2.5.3 MySQL内建的其他引擎

**Archive引擎：**非事务型引擎

- 只支持INSERT和SELECT操作，每次SELECT查询都需要执行全表扫描，但是INSERT时会缓存所有的写，插入时非常快速。
- 支持行级锁和专用的缓冲区，可以实现高并发的插入。

**Memory引擎：**

- 所有的数据都保存在内存中，不需要进行磁盘IO。
- Memory表的结构在重启后还会保留，但数据会丢失。
- Memory表支持Hash索引，查找操作非常快。
- Memory是表级锁，并发写入的性能较低。

### 6.2.6 库表结构优化

#### 6.2.6.1 选择优化的数据类型

**遵循原则：**

1. **尽量选择使用可以正确存储数据的最小数据类型**。
2. **简单数据类型的操作通常需要更少的CPU周期**。
   - 整型比字符操作代价更低；
   - 应该使用MySQL内建的类型而不是字符串来存储日期和时间；
   - 应该用整型存储IP地址...
3. **尽量避免NULL（最后指定列为NOT NULL，除非真的需要存储NULL值）**。
   - 可为NULL的列使得索引、索引统计和值比较都更复杂；
   - 可为NULL的列会使用更多的存储空间；
   - 通常把可为NULL的列改为NOT NULL带来的性能提升比较小，调优时没有必要首先修改掉这种情况，除非确定这会导致问题；
   - 若计划在列上建索引，应尽量避免设计成可为NULL的列；
   - 有一点例外：InnoDB使用单独的位(bit)存储NULL值，故对于稀疏数据有很好的空间效率。

4. **选择合理的数据类型**

   4.1 整数类型

   - **MySQL可以为整数类型指定宽度，例如INT(11)，对于大多数应用这是没有意义的**，它不会限制值的合法范围，只是规定了MySQL的一些交互工具用来显示字符的个数。对于存储和计算来说，INT(1)和INT(20)是相同的。

   4.2 实数类型

   - 因为需要额外的空间和计算开销，应该尽量只在对小数进行精确计算时才使用DECIMAL，例如存储财务数据。但在数据量比较大的时候，可以考虑使用BIGINT代替DECIMAL，将需要存储的货币单位根据小数的位数乘以相应的倍数即可，这样可以同时避免浮点存储计算不精确和DECIMAL精确计算代价高的问题。

   4.3 字符串类型

   - **VARCHAR比定长类型更节省空间，它仅使用必要的空间**。有一种例外：若MySQL表使用ROW_FORMAT=FIXED创建的话，每一行都会使用定长存储，这会很浪费空间。
   - **VARCHAR需要使用1或2个额外字节记录字符串的长度**：若列的最大长度小于或等于255个字节，则只使用1个字节标识，否则使用2个字节。如假设采用latin1字符集，一个VARCHAR(10)的列需要11个字节的存储空间；一个VARCHAR(1000)的列则需要1002个字节的存储空间。
   - **VARCHAR由于行是变长的，在UPDATE时可能会带来额外的开销**。VARCHAR适合于以下情形：若字符串的最大长度比平均长度大很多，列的更新很少，所以碎片不是问题；若使用了像UTF-8这样复杂的字符集，每个字符都使用不同的字节数进行存储。
   - **CHAR适合存储很短的字符串，或者所有值都接近同一个长度**。CHAR适用于以下情形：适合存储密码的MD5值；对于经常需要变更的数据，定长的CHAR不容易产生碎片；对于很短的列，CHAR比VARCHAR在存储空间上更有效率，因为VARCHAR需要额外的字节记录字符串的长度。
   - **CHAR会截断（MySQL服务器层进行处理）末尾的空格，而VARCHAR不会**。
   - **使用枚举（ENUM）代替字符串类型**。MySQL在存储枚举时非常紧凑，会根据列表值的数量压缩到一个或者两个字节中；MySQL在内部会将每个值在列表中的位置保存为整数，并且在表的.frm文件中保存"数字-字符串"映射关系的"查找表"。此外，**枚举字段是按照内部存储的整数而不是定义的字符串进行排序的**，绕过这种限制的方式是按照需要的顺序来定义枚举列。

   4.4 日期类型

   - DATETIME和TIMESTAMP列都可以存储相同类型的数据：时间和日期，精确到秒；而**TIMESTAMP只使用DATETIME一半的存储空间**，并且会根据时区变化，具有特殊的自动更新能力；但**TIMESTAMP允许的时间范围要小的多**（TIMESTAMP存储范围为1970-2038年，而DATETIME存储范围为1001-9999年），这有时候会成为障碍。由于空间效率更高，通常应该尽量使用TIMESTAMP。
   - **TIMESTAMP显示的值依赖于时区**。若在多个时区存储或访问数据，TIMESTAMP和DATETIME的行为将很不一样，前者提供的值与时区有关系，后者则保留文本表示的日期和时间。
   - TIMESTAMP有DATETIME没有的特殊行为，在插入时若未指定值，则MySQL会设置值为当前时间。
   - TIMESTAMP列默认为NOT NULL，这也和其他数据类型不一样。

   4.5 位数据类型

   - MySQL 5.0以后，**可以使用BIT列在一列中存储一个或多个true/false值**；BIT(1)定义一个包含单个位的字段，BIT(2)存储2个位...BIT列的最大长度是64个位。
   - MySQL把BIT当作字符串类型，而不是数字类型；当检索BIT(1)的值时，结果是一个包含二进制0或1值的字符串，而不是ASCII码的"0"或"1"。例如，若存储一个值b '00111001'(二进制值等于57)到BIT(8)的列并且检索它，得到的内容是字符码为57的字符串，即得到ASCII码为57的字符串9，但是在数字上下文场景中，得到的是数字57。这令人费解，**应尽可能避免使用BIT数据类型**。

   4.6 选择标识符

   4.7 特殊类型数据

   - 例如IPv4地址，人们经常使用VARCHAR(15)列来存储IP地址；然而，IPv4实际上是32位无符号整数，不是字符串。使用小数点将地址分成4段的表示方法只是为了让人们更易于阅读，故**应该使用无符号整数存储IP地址**，MySQL提供INET_ATON()和INET_NTOA()函数在两种表示方法之间转换。

#### 6.2.6.2 MySQL schema设计陷阱

1. 不要使用太多的列。MySQL存储时需要在服务器层和存储引擎层之间通过行缓冲格式拷贝数据，然后在服务器层将缓冲内容解码成各个列，从行缓冲中将编码过的列传换成行数据结构的操作代价非常高。
2. 不要使用太多的关联。**MySQL限制了每个关联操作最多只能有61张表**。一个经验法则是：**如果希望查询执行得快速且并发性好，单个查询最好在12个表以内做关联**。

3. 不要过度使用枚举。例如：country enum('','0', '1', '2',...,'31')这种设计不可取。
4. 尽可能使用枚举代替SET集合。
5. 尽量避免使用NULL，但是实际业务需求要求使用NULL，那就大胆使用，因为使用神奇常数会导致代码更复杂，也越容易引入Bug。

#### 6.2.6.3 范式与反范式

**范式化的优点：**

1. 更新操作通常比反范式更快。
2. 当数据较好地范式化时，就只有很少或者没有重复数据，故只需修改更少得数据。
3. 范式化得表通常更小，可以更好地放在内存中，故执行操作会更快。
4. 很少有多余的数据意味着检索列表数据时更少需要DISTINCT或者GROUP BY语句。

**范式化的缺点：**

1. 通常需要关联，复杂查询代价昂贵。

**反范式化的优点：**

1. 当数据比内存大时会比关联要快得多，可以避免随机IO。
2. 反范式化的表也能使用更有效的索引策略。

**反范式化的缺点：**

1. 表结构不清晰，有很多重复数据。
2. 插入和删除代价昂贵。

**实际应用：混用范式化与反范式化：**

1. 最常见的反范式化数据的方法是复制或者缓存，在不同的表中存储相同的特定列。
2. 避免完全反范式化带来的插入和删除问题。

#### 6.2.6.4 缓存表和汇总表

**缓存表：**表示存储哪些可以比较简单地从其他表获取（但是每次获取的速度比较慢）数据的表（例如，逻辑上冗余的数据）。

**汇总表：**保存的是使用GROUP BY语句聚合数据的表（例如，数据不是逻辑上冗余的）。

#### 6.2.6.5 加快ALTER TABLE速度

1. 主库切换
2. 影子拷贝
3. 使用ALTER COLUMN
4. 只修改.frm文件
5. 快速创建MyISAM索引

### 6.2.7 索引优化

**索引：**在MySQL中也叫做key，是存储引擎用于快速找到记录的一种数据结构。

**索引的特点：**

- 索引优化应该是对查询性能优化最有效的手段，能够轻易将查询性能提高几个数量级。
- 最优的索引有时比一个好的索引性能要好两个数量级。
- 创建一个真正最优的索引经常需要重写查询。
- 索引可以包含一个或多个列的值。若索引包含多个列，那么列的顺序也十分重要，因为MySQL只能高效地使用索引的最左前缀列。

#### 6.2.7.1 索引的类型

MySQL支持的索引类型：B-Tree索引、哈希索引、R-Tree索引、全文索引...

##### 6.2.7.1.1 B-Tree索引（平衡搜索树）

B-Tree索引（**多路搜索树结构**）适用于全键值、键值范围或键前缀查找，其中键前缀查找只适用于根据最左前缀的查找。**InnoDB引擎使用的是B-Tree索引**。

**B-Tree原理：**多路搜索树结构的实现。

**B-Tree索引的应用：**

1. 全值匹配：指和索引中的所有列进行匹配。
2. 匹配最左前缀。
3. 匹配列前缀。
4. 匹配范围值。
5. 精确匹配某一列并范围匹配另外一列。
6. 只访问索引的查询。
7. 用于查询中的ORDER BY操作（即可用于排序）。
8. 索引中存储了实际的列值，查询获取速度非常快。

**B-Tree索引的限制：**索引列的顺序很重要！

1. 若不是按照索引的最左列开始查找，则无法使用索引。
2. 不能跳过索引中的列。
3. 若查询中有某个列的范围查询，则其右边所有列都无法使用索引优化查询。

##### 6.2.7.1.2 哈希索引

**哈希索引：**基于哈希表实现，只有精确匹配索引所有列的查询才有效。**在MySQL中只有Memory引擎支持哈希索引（如果哈希值相同，会以链表的方式存储），Memory引擎也支持B-Tree索引**。

**哈希索引的原理：**对每一行数据，存储引擎都会对所有的索引列计算出一个哈希码（不同键值的行计算出的哈希码不同）；哈希索引将所有的哈希码存储在索引中，同时在哈希索引表中保存指向每个数据行的指针。

**哈希索引的优点：**结构紧凑、查询速度快。

**哈希索引的限制：**

1. 哈希索引只包含哈希值和行指针，而不存储字段值，**不能使用索引中的值来避免读取行**；但由于访问内存中的行速度很快，故这一点对性能影响并不明显。
2. 哈希索引数据并不是按照索引值顺序存储的，**无法用于排序**。
3. 哈希索引**不支持部分索引列匹配查找**，因为哈希索引始终是使用索引列的全部内容来计算哈希值的。
4. 哈希索引只支持等值比较查询，**不支持任何范围查询**。
5. 当出现哈希冲突时，存储引擎必须遍历**链表**中所有的行指针，逐行进行比较，直到找到所有符合条件的行。
6. 若哈希冲突很多，则索引维护操作的代价会很高。

**自适应哈希索引：**属于InnoDB的一种功能，当InnoDB注意到某些索引值被使用的非常频繁时，会在内存中基于B-Tree索引之上再创建一个哈希索引，这样就让B-Tree索引也具有哈希索引的一些优点，比如快速的哈希查找。但这是一种完全自动的、内部的行为，用户无法控制或配置。

**创建自定义哈希索引：**在B-Tree基础上创建一个伪哈希索引，使用B-Tree进行查找时，是使用哈希值而不是键本身进行索引查找，需要做的就是在WHERE子句中手动指定使用哈希函数，然后采用触发器维护哈希值。常用的生成哈希的函数有CRC32（**若数据表太大，CRC32会产生大量的哈希冲突**），不要使用SHA1和MD5函数来生成哈希值（**字符串太长，会浪费大量空间**），可以使用MD5函数的部分返回值作为哈希值（**会比自己写一个哈希算法的性能要差**），还可以使用FNV64作为哈希函数，其哈希值为64位，速度快，且冲突比CRC32要少很多。

**处理哈希冲突的方法：**为了处理哈希冲突，必须在WHERE子句中带入哈希值和对应列值；若只是进行不精确的统计计数，那可以不带对应列值。

##### 6.2.7.1.3 R-Tree索引

**R-Tree索引：**即空间索引，可以用作地理数据存储。**MyISAM引擎支持空间索引**。

**R-Tree特点：**

1. 无须前缀查询，空间索引会从所有维度来索引数据；
2. 查询时，可以有效地使用任意维度来组合查询；
3. 必须使用MySQL的GIS相关函数如MBRCONTAINS()等来维护数据。

##### 6.2.7.1.4 全文索引

**全文索引：**一种特殊类型的索引，查找的是文本中的关键词，而不是直接比较索引的值。全文索引需要注意的细节有：停用词、词干和复数、布尔搜索等。全文索引更类似于搜索引擎做的事情，而不是简单的WHERE条件匹配。

#### 6.2.7.2 索引的优点

1. 索引大大减少了服务器需要扫描的数据量，可以让服务器快速地定位到表的指定位置。
2. 索引可以帮助服务器避免排序和临时表。
3. 索引可以将随机I/O变为顺序I/O。

#### 6.2.7.3 高性能的索引策略

##### 6.2.7.3.1 独立的列

"独立的列"是指索引列不能是表达式的一部分，也不能是函数的参数；应该始终将索引列单独放在比较符号的一侧。

##### 6.2.7.3.2 前缀索引和索引选择性

- 通过索引开始的部分字符，可以大大节约索引空间，从而提高索引效率，但是会降低**索引的选择性**（不重复的索引值和数据表的记录总数的比值；唯一索引的选择性是1，这是最好的索引选择性，性能也最好）。

- 前缀索引的关键是：保证前缀索引的选择性接近于索引整个列的选择性。

- 确定前缀索引合适长度的方法：

  - 方法一：找到组最常见的值列表，然后和最常见的前缀列表进行比较。
  - 方法二：计算完整列的选择性，并使前缀的选择性接近于完整列的选择性。

  ```sql
  -- 创建前缀索引的方法：以city字段的前7个字符作为前缀索引
  ALTER TABLE sakila.city_demo ADD KEY(city(7))
  ```

- "后缀索引"：例如找到某个域名的所有电子邮件地址。MySQL原生并不支持反向索引，但是可以把字符串反转后存储，并基于此建立前缀索引，可以通过触发器来维护这种索引。
- 前缀索引的优缺点
  - 优点：能使索引更小、更快。
  - 缺点：MySQL无法使用前缀索引做ORDER BY和GROUP BY，也无法使用前缀索引做覆盖扫描。

##### 6.2.7.3.3 多列索引

- 在多个列上建立独立的单列索引大部分情况下并不能提高MySQL的查询性能。
- MySQL 5.0 的索引合并策略：**OR条件的联合**、**AND条件的相交**、**组合前面两种情况的联合及相交**。
- 索引合并策略的使用，恰恰说明表上的索引建得很糟糕，应尽可能避免使用索引合并：
  -  当出现多个AND条件的索引合并时，通常意味着需要一个包含所有相关列的多列索引。
  - 当出现多个OR条件的索引合并时，通常意味着需要耗费大量CPU和内存资源在算法的缓存、排序和合并操作上。

##### 6.2.7.3.4 索引列顺序

- 多列B-Tree索引可以实现精确排序。
- 选择索引列顺序的经验法则：当不考虑排序和分组时，将选择性最高的列放到索引最前列。
- 性能不只依赖于索引索引列的选择性，也和查询条件的具体值有关（也就是和值的分布有关），故可能需要根据那些运行频率最高的查询来调整索引列的顺序，让这种情况下索引的选择性最高。
- 不要假设平均情况下的性能也能代表特殊情况下的性能，特殊情况可能会摧毁整个应用的性能。
- 不要忘记WHERE子句中的排序、分组和范围条件等因素，这些也可对查询的性能造成非常大的影响。

##### 6.2.7.3.5 聚簇索引

**聚簇索引：**并不是一种单独的索引类型，而是一种数据存储方式。**InnoDB的聚簇索引实际上在同一个结构中保存了B-Tree索引和数据行**。当表有聚簇索引时，它的数据行实际上存放在索引的叶子页中，而节点页则只包含索引列（**InnoDB的索引列就是主键列；若没有主键，则InnoDB会选择一个唯一的非空索引代替；若没有唯一的非空索引，则InnoDB会隐式定义一个主键来作为聚簇索引**）。因无法把数据行存放在两个不同的地方，故一个表只能有一个聚簇索引。

**聚簇索引的优缺点：**

- 优点：
  - **可以把相关数据保存在一起**。如实现电子邮箱时，可根据用户ID来聚集数据，这样只需从磁盘读取少数数据页就能获取某个用户的全部邮件；若没有聚簇索引，则每封邮件都可能导致依次磁盘I/O。
  - 数据访问更快。
  - 使用覆盖索引扫描的查询可以直接使用页节点中的主键值。

- 缺点：
  - 聚簇数据最大限度提高了I/O密集型应用的性能，若将数据全部存放在内存中，则聚簇索引就没什么优势了。
  - 插入速度严重依赖于插入顺序。
  - 更新聚簇索引列的代价高昂。
  - 插入新行时，可能会导致"页分裂"问题，而页分裂会导致表占用更多的磁盘空间。
  - 聚簇索引可能会导致全表扫描变慢，对于稀疏表尤其如此。
  - 二级索引访问需要两次索引查找。二级索引叶子节点保存的不是指向行的物理位置的指针，而是行的主键值。对于InnoDB，自适应哈希索引能够减少这样的重复工作。

**注意事项：**

1. 最好避免随机的（不连续且值的分布范围非常大）聚簇索引，特别是对于I/O密集型的应用。如使用UUID（对比AUTO_INCREMENT自增，UUID不仅耗时多，而且占用空间也更大）来作为聚簇索引就会很糟糕，它使得聚簇索引的插入变得完全随机，使得数据没有任何聚集特性。最简单的方法是使用AUTO_INCREMENT自增列作为聚簇索引。

##### 6.2.7.3.6 覆盖索引

**覆盖索引：**如果一个索引包含（或者说覆盖）所有需要查询的字段的值，我们就称之为"覆盖索引"。

**覆盖索引的优点：**

- 索引条目通常远小于数据行的大小，若只需读取索引，那MySQL就会极大地减少数据访问量。
- 索引是按照列值顺序存储的，对于I/O密集型的范围查询会比随机从磁盘读取每一行数据的I/O要少得多。
- 由于InnoDB的聚簇索引，覆盖索引对InnoDB表（特别是InnoDB的二级主键若能够覆盖查询，则可以避免对主键索引的二次查询）特别有用。

**注意事项：**

1. 覆盖索引必须存储索引列的值，而哈希索引、空间索引和全文索引等都不存储索引列的值，所以**MySQL只能使用B-Tree索引做覆盖索引**。
2. MySQL不能在索引中执行LIKE操作，但可以使用延迟关联来解决这一问题。

##### 6.2.7.3.7 使用索引扫描来排序

**索引排序：**若EXPLAIN出来的type列的值为"index"，则说明MySQL使用了索引扫描来做排序。

**注意事项：**

1. MySQL可以使用同一个索引既满足排序，又用于查找行。
2. 只有当索引的列排序和ORDER BY子句的顺序完全一致，并且所有列的排序方向（倒序或正序）都一样时，MySQL才能使用索引来对结果做排序。
3. 若查询需要关联多张表，则只有当ORDER BY子句引用的字段全部为第一个表时，才能使用索引做排序。
4. 索引排序不支持范围查询。
5. 索引排序要么要么都是正序，要么都是逆序。

##### 6.2.7.3.8 压缩索引

**压缩索引：**MyISAM使用前缀压缩来减少索引的大小，从而让更多的索引可以放入内存中，这在某些情况下能极大地提高性能。默认之压缩字符串，可以在CREATE TABLE语句中指定PACK_KEYS参数来控制索引压缩的方式。

**压缩索引原理：**先完全保存索引块中的第一个值，然后将其他值和第一个值进行比较得到相同前缀的字节数和剩余不同后缀的部分，把这部分存储起来即可。

**压缩索引的优缺点：**

- 优点：占用更少的空间。
- 缺点：
  1. 由于每个值的压缩前缀依赖于前面的值，所以MyISAM查找时**无法在索引块使用二分查找**而只能从头开始扫描。
  2. 倒序扫描的速度很慢。
  3. 在块中查找某一行的操作平均都需要扫描半个索引块。

##### 6.2.7.3.9 冗余和重复索引

**重复索引：**指在相同的列上按照相同的顺序创建的相同类型的索引。应该避免这样创建重复索引，发现以后也应该立即移除。

```SQl
-- 举例：用户可能是想创建一个主键，先加上唯一限制，然后再加上索引以供查询使用，但是却在不经意间造成了重复索引
-- 因为MySQL的唯一限制和主键限制都是通过索引实现的，因此下面的写法实际上在相同的列上创建了三个重复的索引（通常这样做没有意义，除非是在同一列上创建不同类型的索引来满足不同的查询需求）
CREATE TABLE test(
	ID INT NOT NULL PRIMARY KEY,
    A INT NOT NULL,
    UNIQUE(ID),
    INDEX(ID)
)ENGINE=InnoDB;
```

**冗余索引：**例如，如创建了索引(A,B)：若再创建索引(A)（**针对B-Tree索引而言，索引(A)是索引(A,B)的前缀索引**）就是冗余索引；若再创建索引(B,A)则不是冗余索引；若再创建索引(B)也不是冗余索引；其他不同类型的索引（如哈希索引或者全文索引）也不会是B-Tree的冗余索引（无论覆盖的索引列是什么）。

**注意事项：**

- 冗余索引通常发生在为表添加新索引的时候。
- 大多数情况下都不需要冗余索引，应该尽量扩展已有索引而不是创建新索引；但若扩展已有索引会导致其变得很大，出于性能考虑也可使用冗余索引。
- 冗余索引会造成INSERT/UPDATE/DELETE速度很慢。

##### 6.2.7.3.10 未使用的索引

分析未使用索引的工具：MariaDB、Percona Toolkit中的pt-index-usage。

##### 6.2.7.3.11 索引和锁

- InnoDB 只有在访问行的时候才会对其加锁，而索引能够减少InnoDB访问的次数，从而减少锁的数量。
- 即使使用了索引，InnoDB也可能锁定一些不需要的数据。

#### 6.2.7.4 索引案例

考虑点：

1. 首先要考虑的事情是需要使用索引来排序，还是先检索数据再排序，使用索引排序会严格限制索引和查询的设计（例如：**如果MySQL使用某个索引进行范围查询，也就无法再使用另一个索引进行排序了**）。
2. 可以在索引中加入更多的列，并通过IN()的方式覆盖那些不在WHERE子句中的列（但不可滥用，因为每增加一个IN()条件，优化器需要做的组合都将以指数形式增长，而这会极大地降低查询性能）。
3. 考虑表上所有的选项，要有大局观。
4. 尽可能将需要做范围查询的列放到索引的后面，以便优化器能使用尽可能多的索引列。
5. 对于范围条件查询（如WHERE...>...），MySQL无法再使用范围后面的其他索引列了，但是对于多个等值条件查询（如WHERE..IN...），则没有限制。

6. 为了实现多个范围条件查询，可以将其他范围条件查询（只保留一个范围条件查询）转换为多个等值条件查询。
7. 使用延迟关联优化排序。

#### 6.2.7.5 维护索引和表

维护表的三个目的：

- 找到并修复损坏的表

  1. 检查是否发生了表损坏：`CHECK TABLE`
  2. 修复损坏的表：`REPAIR TABLE`
  3. 不做任何操作的ALTER操作重建表：`ALTER TABLE 表名 ENGINE=INNODB`（INNODB为当前存储引擎）

- 更新索引统计信息

  1. 获取存储引擎的索引值的分布信息：
     - records_in_range()：通过向存储引擎传入两个边界值获取在这个范围大概有多少条记录（InnoDB是预估值）。
     - info()：返回各种类型的数据，包括索引的基数（每个键值有多少条记录）。

  2. 重新生成信息统计：`ANALYZE TABLE`（MyISAM在分析时需要锁表，InnoDB采用的时取样分析）
  3. 查看索引的基数：`SHOW INDEX FROM 表名`

- 减少索引和数据的碎片

  1. B-Tree索引的碎片化。
  2. 表存储碎片化的三种类型：**行碎片**（数据行被存储为多个地方的多个片段中；InnoDB不会出现行碎片，因为其会移动短小的行并重写到一个片段中）、**行间碎片**（指逻辑上顺序的页、或者行在磁盘上不是顺序存储的）、**剩余空间碎片**（指数据页中有大量的空余空间）。
  3. 减少碎片化的方式：
     - 重新整理数据：`OPTIMIZE TABLE`
     - 先删除、再重新创建索引
     - 不做任何操作的ALTER操作重建表：`ALTER TABLE 表名 ENGINE=INNODB`（INNODB为当前存储引擎）

#### 6.2.7.6 索引总结

选择和编写利用索引的查询时，有以下**三个原则**：

1. 单行访问很慢：可以选择合适的索引避免单行查找
2. 按顺序访问范围数据是很快的：尽可能使用原生顺序从而避免额外的排序操作
3. 索引覆盖查询是很快的：尽可能使用索引覆盖

### 6.2.8 查询优化

#### 6.2.8.1 慢查询

慢查询的分析方向：

1. **确认是否向数据库请求了不需要的数据**。

   典型案例：

   - 查询不需要的记录。解决方法：在查询后面加上LIMIT。

   - 多表关联时返回全部列。解决办法：不要使用未加限定的*。

   - 总是取出全部列。解决办法：严禁使用SELECT *，这会让优化器无法完成索引覆盖，也会带来额外的I/O、内存和CPU的消耗。

   - 重复查询相同的数据。解决方法：使用缓存。

2. **确认MySQL服务层是否在分析大量超过需要的数据行**。

   - 衡量查询开销的3个指标：**响应时间**、**扫描的行数**（检查慢日志是找出扫描行数过多的查询的好办法）、**返回的行数**。
   - 响应时间：服务时间和排队时间之和。
   - 访问类型(type)由慢到快：全表扫描(ALL)、索引扫描(index)、范围扫描(range)、唯一索引扫描(ref)、单值访问(const)...
   - 应用WHERE条件的三种方式（从好到坏依次是）
     - 在索引中使用WHERE条件来过滤不匹配的记录，这是在MySQL存储引擎层完成的。
     - 使用索引覆盖扫描来返回记录，这是在MySQL服务器层完成的，但无须再回表查询记录。
     - 从数据表中返回数据，然后过滤不满足条件的记录，这是在MySQL服务器层完成的。

#### 6.2.8.2 重构查询的方式

1. 一个复杂查询还是多个简单查询：不要害怕使用简单查询，MySQL从设计上让连接和断开连接都很轻量级；在通用服务器上，能够运行每秒超过10万的查询。
2. 切分查询：例如分页查询、分割成小事务。
3. 分解关联查询的优点：
   - 可以让缓存更高效（缓存更精细），减少冗余记录的查询（会减少网络和数据库服务器内存的消耗）
   - 执行单个查询可以减少锁的竞争
   - 在应用层做关联，可以更容易对数据库进行拆分、可扩展性更强
   - 分解后的查询往往效率会更高

#### 6.2.8.3 执行查询的基础

**查询执行路径：**

​		客户端-->MySQL查询缓存-->MySQL解析器-->生成解析树-->预处理器-->解析树-->查询优化器-->查询执行计划-->查询执行引擎-->API调用接口-->存储引擎-->数据。

**查询状态：**`SHOW FULL PROCESSLIST`

1. Sleep：线程正在等待客户端发送新的请求。
2. Query：线程正在执行查询或者正在将结果发送给客户端。
3. Locked：在MySQL服务器层，该线程正在等待表锁（在存储引擎级别实现的锁，如InnoDB的行锁，并不会体现在线程状态中）。
4. Analyzing and statistics：线程正在收集存储引擎的统计信息，并生成查询的执行计划。
5. Copy to temp table：线程正在执行查询，并将其结果集都复制到一个临时表中（这种状态一般要么是在做GROUP BY操作、要么是文件排序操作、或者是UNION操作）。
6. Sorting result：线程正在对结果集进行排序。
7. Sending data：线程可能在多个状态之间传送数据、或者在生成结果集、或者在向客户端返回数据。

**查询缓存：**通过一个对大小写敏感的哈希查找实现的。

**查询优化处理：**

1. 语法解析器和预处理

2. 查询优化器（基于成本预测的优化器）：找出最好的执行计划（此时假设读取任何数据只需依次磁盘I/O）

   MySQL中能够处理的优化类型：

   - 重新定义关联表的顺序

   - 将外连接转化成内连接

   - 使用等价变换规则

   - 优化COUNT()、MIN()、MAX()

   - 预估并转化成为常数表达式

   - 覆盖索引扫描

   - 子查询优化

   - 提前终止查询

   - 等值传播

   - 列表IN()的比较：MySQL中的IN()并不等同于多个OR()子句；**MySQL将IN()列表中的数据先进行排序，然后通过二分查找的方式来确定列表中的值是否满足要求，这是一个O(log n)的操作，等价地转换为OR查询的复杂度为O(n)**，对于IN()列表中有大量取值的时候，MySQL的处理速度将更快。

     ......

**执行计划：**MySQL并不会生成查询字节码来执行查询，而是会生成一颗指令树，然后通过存储引擎执行完成这颗指令树并返回结果，最终的执行树包含了重构查询的全部信息。若对某个查询执行`EXPLAIN EXTENDED`后，再执行`SHOW WARNINGS`就可以看到重构出的查询。**MySQL的执行计划是一颗左侧深度优先的树**。

**关联查询优化器：**通过评估不同顺序的成本来选择一个代价最小的关联顺序。

**排序优化：**排序操作成本高昂，从性能角度考虑，应尽可能避免排序或者尽可能避免对大量数据进行排序。当不能使用索引排序时，MySQL会自己进行排序，数据量小时会在内存中进行，数据量大时会使用磁盘，不过MySQL将这个过程统一称之为**文件排序**。

- MySQL的两种排序算法：
  - 两次传输排序（旧版本使用）
  - 单词传输排序（新版本使用）

**查询执行引擎：**根据执行计划完成整个查询。

#### 6.2.8.4 查询优化器的局限

1. 关联子查询：

   - 不要使用WHERE...IN...嵌套子查询，应该使用JOIN(`INNER JOIN ON`/`LEFT JOIN ON`/`RIGHT JOIN ON`)来进行关联查询，JOIN的执行效率更高。
   - 并不是所有关联查询的性能都会很差；很多时候，关联查询是一种非常合理、自然、甚至是性能最好的写法。
   - 针对含DISTINCT/GROUP BY（会产生临时表）的关联查询，子查询的效果会更高。

2. UNION的限制：**UNION会进行重复值扫描并删除重复值，而UNION ALL不会**

   - 若希望UNION的各个子句能根据LIMIT只取部分结果集，或者希望能先排好序再合并结果集，就需要在UNION的各个子句中分别使用这些子句。

   - 使用UNION时，需要注意从临时表中取出数据的顺序并不是一定的，若想获得正确的顺序，还需加上一个全局的ORDER BY和LIMIT操作。

3. 索引合并优化
4. 等值传递：避免产生一个非常大的IN()列表。
5. 并行执行：MySQL无法利用多核特性来并行执行查询。
6. 哈希关联：MySQL并不支持哈希关联，但是可以创建自定义哈希索引。
7. 松散索引扫描：MySQL不支持松散索引扫描，无法按照不连续的方式扫描一个索引。

```SQL
-- 例如：假设有如下索引(a,b)
-- 有下面的查询：因索引的前导字段是列a,但是在查询中只指定了字段b，导致MySQL无法使用索引，只能通过全表扫描找到匹配的行
SELECT ... FROM tb1 WHERE b BETWEEN 2 AND 3
```

8. 最大值和最小值优化

9. 在同一个表上查询和更新：通过使用生成表的形式达到对同一张表同时进行查询和更新。

```SQL
-- 使用生成表der来达到对同一张表同时进行查询和更新的操作
UPDATE tb1
	INNER JOIN(
    	SELECT type, count(*) AS cnt
        FROM tb1
        GROUP BY type
    ) AS der USING(type)
SET tb1.cnt = der.cnt;
```

#### 6.2.8.5 查询优化器提示（hint）

**查询优化器的提示：**通过在查询中加入相应的提示，可以控制该查询的执行计划。具体请查询MySQL官方手册。

#### 6.2.8.6 优化特定类型的查询

值得指出的是，优化技巧和特定的版本有关，对于未来的MySQL版本未必适用。

##### 6.2.8.5.1 优化COUNT()查询

COUNT()的作用：

1. 统计某个列值的数量（列值非空）
2. 统计行数：使用COUNT(*)的时候，这种情况下通配符\*并不会像我们猜想的那样扩展成所有的列，它会忽略所有的列而直接统计所有的行数。

**常见的错误：**在括号内指定了一个列，却希望统计结果集的行数。若希望知道结果集的行数，最好使用COUNT(*)，这样写意义清晰，性能也会更好。

**注意事项：**

1. 若MySQL知道某列col不可能为NULL值，那么MySQL内部会将COUNT(col)表达式优化为COUNT(*)。
2. 可以使用MyISAM在COUNT(*)全表非常快的整个特性，来加速一些特定条件的COUNT()查询。

```SQL
-- 查询所有ID大于5的城市
SELECT COUNT(*) FROM world.City WHERE ID>5
-- 优化:将扫描行数减少到5行以内
SELECT (SELECT COUNT(*) FROM world.City) - COUNT(*) FROM world.City WHERE ID <= 5

-- 在同一个查询中统计同一个列的不同值的数量，以减少查询的语句量
```

3. 使用COUNT()在同一个查询中统计同一个列的不同值的数量，以减少查询的语句量。

```sql
-- 如统计不同颜色的商品数量
SELECT COUNT(color = 'blue' OR NULL) AS blue, COUNT(color = 'red' OR NULL) AS red FROM items;

-- 使用SUM函数也可以实现同样的功能
SELECT SUM(IF(color = 'blue', 1, 0)) AS blue, SUM(IF(color = 'red', 1, 0)) AS red FROM items;
```

4. 使用近似值;
   - 若业务场景不需要完全精确的COUNT值，可以使用近似值来代替。EXPLAIN出来的优化器估算的行数就是一个不错的近似值，执行EXPLAIN并不需要真正地去执行查询，所以成本很低。
   - 某些场景，可以尝试删除DISTINCT这样的约束来避免文件排序，这样重写后的查询要比精确统计的查询快得多，返回结果近似。

##### 6.2.8.5.2 优化关联查询

1. 确保ON或USING子句中的列上有索引，并且创建索引的时候就要考虑到关联的顺序。一般来说，除非有其他利用，一般只在关联顺序中的第2个表的相应列上创建索引。

```SQL
-- 例如：当表A和表B用列C关联时，如果优化器的关联顺序是B,A，那么就不需在B表的对应列上创建索引。
```

2. 确保任何GROUP BY和ORDER BY中的表达式只涉及到一个表中的列，这样MySQL才有可能使用索引来优化这个过程。
3. 当升级MySQL时需要注意：关联语法、运算符优先级等其他可能会发生变化的地方。

##### 6.2.8.5.3 优化子查询

1. 尽可能（不绝对）使用关联查询替代子查询。

##### 6.2.8.5.4 优化GROUP BY和DISTINCT

1. 最有效的方法是采用索引来优化。
2. 无法使用索引时，在MySQL中，GROUP BY使用两种策略来完成：使用临时表、或者使用文件排序来做分组。这两种策略都可以通过使用提示SQL_BIG_RESULT和SQL_SMALL_RESULT来让优化器按照所希望的方式运行。
3. 若需要对关联查询做分组（GROUP BY），并且是按照查找表中的某个列进行分组，通常采用查找表的标识列分组的效率会比其他列更高。
4. 若没有通过ORDER BY显示指定排序列，当查询使用GROUP BY时，结果集会自动按照分组的字段进行排序；**若不关心结果集的顺序，而这种默认排序又导致了需要文件排序，则可以使用`ORDER BY NULL`，达到让MySQL不再进行文件排序的效果**；也可以在GROUP BY子句中直接使用DESC或ASC关键字，使分组的结果集按需要的方向排序。
5. GROUP BY WITH ROLLUP可以对GROUP BY的结果再做一次超级聚合，但是这会导致文件排序或临时表的产生，最好的办法是**尽可能将WITH ROLLUP功能转移到应用程序中处理**。

##### 6.2.8.5.5 优化LIMIT分页

1. 一个常见又令人头疼的问题是，使用LIMIT分页时，若偏移量非常大（如LIMIT 10000, 20），这时MySQL需要查询10020条记录然后只返回最后20条，前面10000条记录都将被抛弃，这样的代价非常高。一个最简单的优化方法是尽可能地**使用索引覆盖扫描**，而不是查询所有的列，然后根据需要做一次**延迟关联操作**再返回所需的列，对于偏移量很大的分页查询效率提升会非常大。

```SQL
-- LImIT分页优化前
SELECT film_id, description FROM sakila.film ORDER BY title LIMIT 10000, 20;

-- LIMIT分页优化后
SELECT film.film_id, film.description
FROM sakila.film
	INNER JOIN (
    	SELECT film_id FROM sakila.film
        ORDER BY title LIMIT 10000, 20
    ) AS lim USING(film_id);
```

2. 也可以将LIMIT查询转换为已知位置的查询，让MySQL通过**范围扫描**获得到对应的结果。

```sql
-- 若在一个位置列上有索引，并且预先计算出了边界值，则分页查询可以优化为
SELECT film_id, description FROM sakila.film WHERE position BETWEEN 10000 AND 10019 ORDER BY position;
```

3. LIMIT的问题本质上是OFFSET的问题，可以**使用书签记录上次取数据的位置**，下次就可以直接从该书签记录的位置开始扫描，这样就可以避免使用OFFSET（即增加一个where条件查询）。

```SQL
-- 通过记录位置优化LIMIT分页：16030为上一次查询的主键
SELECT * FROM sakila.rental WHERE rental_id < 16030 ORDER BY rental_id DESC LIMIT 20;
```

4. 采用冗余表。

##### 6.2.8.5.6 优化SQL_CALC_FOUND_ROWS

1. 分页时，可以再LIMIT语句中加上SQL_CALC_FOUND_ROWS提示（hint），这样就可以获得去掉LIMIT以后满足条件的行数，因此可以作为分页的总数。将扫描到的所有满足条件的数据存储到缓存中，提升后续的查询速度。

##### 6.2.8.6.7 优化UNION查询

1. MySQL总是通过**创建并填充临时表**的方式来执行UNION查询，因此很多优化策略在UNION查询中都没法很好地使用。经常需要手动地将WHERE、LIMIT、ORDER BY等子句"下推"到UNION的各个子查询中，以便优化器可以充分利用这些条件进行优化。
2. **除非确实需要服务器消除重复的行，否则就一定要使用UNION ALL**；若没有ALL，MySQL会给临时表加上DISTINCT选项，这会导致对整个临时表的数据做唯一性检查，代价非常高昂。

##### 6.2.8.6.8 静态查询分析

使用`Percona Toolkit的pt-query-advisor`能够解析查询日志、分析查询模式，然后给出所有可能存在潜在问题的查询及建议。

##### 6.2.8.6.9 使用用户自定义变量

用户自定义变量是一个用来存储内容的临时容器，在连接MySQL的整个过程中都存在。可以使用SET和SELECT语句来定义它们。

```sql
-- 自定义变量
@one:=1;
@min_actor:=(SELECT MIN(actor_id) FROM sakila.actor);
@last_week:=CURRENT_DATE-INTERVAL 1 WEEK;

-- 使用变量
SELECT ... WHERE col <= @last_week;
```

下面这些场景不能使用用户自定义变量：

1. 使用自定义变量的查询，**无法使用查询缓存**。
2. 不能在使用**常量或标识符**的地方使用自定义变量，例如表名、列名和LIMIT子句中。
3. 自定义变量的生命周期是一个连接中有效，**不能用来做连接间的通信**。
4. 若使用连接池或持久化连接，**自定义变量可能让看起来毫无关系的代码发生交互**。
5. 在MySQL 5.0之前，**大小写敏感**。
6. **不能显式地声明自定义变量的类型**。
7. MySQL优化器在某些场景下可能会将这些**变量优化掉**。
8. 赋值的顺序和赋值的时间点**不固定**，依赖于优化器的决定。
9. 赋值符号:=的**优先级非常低**，所以赋值表达式应该使用明确的括号。
10. **使用未定义变量不会产生任何语法错误**。

**使用场景：**

1. 优化排名语句

```sql
-- 使用变量优化排名语句
SET @curr_cnt := 0, @prev_cnt := 0, @rank := 0;
SELECT actor_id,
	@curr_cnt := cnt AS cnt,
	@rank := IF(@prev_cnt <> @curr_cnt, @rank + 1, @rank) AS rank,
	@prev_cnt := @curr_cnt AS dummy
FROM(
	SELECT actor_id, COUNT(*) AS cnt
    FROM sakila.film_actor
    GROUP BY actor_id
    ORDER BY cnt DESC
    LIMIT 10
) AS der;
```

2. **避免重复查询刚刚更新的数据**

```sql
-- 更新行的同时又希望获得行的信息
UPDATE t1 SET lastUpdated = NOW() WHERE id =1;
SELECT lastUpdated FROM t1 WHERE id = 1;

-- 使用变量优化
UPDATE t1 SET lastUpdated = NOW() WHERE id = 1 AND @now := NOW();
SELECT @now;
```

3. **统计更新和插入的数量**

```sql
-- 这里只是演示变量的用法，MySQL的协议会返回被更改的总行数，所以不需要单独统计这个值
INSERT INTO t1(c1, c2) VALUES(4, 4), (2, 1), (3, 1)
ON DUPLICATE KEY UPDATE
	c1 = VALUES(c1) + ( 0 * ( @x := @x + 1 ))
```

4. 确定取值的顺序

```sql
-- 让变量的赋值和取值发生在执行查询的同一阶段
SET @rownum := 0;
SELECT actor_id, @rownum AS rownum
FROM sakila.actor
WHERE (@rownum := @rownum +1) <= 1;
```

5. **编写偷懒的UNION**

```sql
-- 将第一个子查询作为分支条件先执行，如果找到了匹配的行，则跳过第二个分支
SELECT GREATEST(@found := -1, id) AS id, 'users' AS which_tb1
FROM users WHERE id = 1
UNION ALL
	SELECT id, 'users_archived'
	FROM users_archived WHERE id = 1 AND @found IS NULL
UNION ALL
	SELECT 1, 'reset' 
	FROM DUAL WHERE (@found := NULL) IS NOT NULL;
```

6. 用户自定义变量的其他应用
   - 查询运行时计算总数和平均值
   - 模拟GROUP语句中的函数FIRST()和LAST()
   - 对大量数据做一些数据计算
   - 计算一个大表的MD5散列值
   - 编写一个样本处理函数，当样本中的数值超过某个边界值的时候将其变成0
   - 模拟读/写游标
   - 在SHOW语句的WHERE子句中加入变量值

#### 6.2.8.7 案例

##### 6.2.8.7.1 地理位置信息

MySQL也可以实现地理位置信息，同样，Redis也实现了地理位置信息计算。

```sql
-- 地理位置坐标：(经度, 维度)
-- 现有坐标A(latA, lonA)、B(latB, lonB)，则A和B两点之间的距离计算公式如下(前提假设地球是圆的)
-- R为地球的半径：R=6371km
ACOS( COS(latA) * COS(latB) * COS(lonA-lonB) + SIN(latA) * SIN(latB) ) * R
```

- 附近的人...

解决思路：

1. 先建一个索引过过滤出近似值。
2. 再使用精确条件匹配所有的记录并移除不满足条件的记录。

3. 根据毕达哥拉斯定理来计算地理位置空间距离。







> 参考博客文章：**[《MySQL必知必会》-已读完](www.highperfmySQL)**、[《正则表达式必知必会》]()、**[关于MySQL可重复读的理解](https://blog.csdn.net/qq_32573109/article/details/98610368)**、**[《高性能MySQL》-已读完前六章]()**、[数据库之六大范式详解](https://blog.csdn.net/weixin_43433032/article/details/89293663)、[MySQL中key和index的区别](https://www.cnblogs.com/zjfjava/p/6922494.html)、[MySQL EXPLAIN type类型说明](https://blog.csdn.net/qq_27676247/article/details/79387637)

# 7 微服务













# 8 算法解题









# 9 JVM

## 9.1 走近Java

### 9.1.1 Java技术体系

**Java技术体系：**

1. Java程序设计语言
2. 各种硬件平台上的Java虚拟机实现
3. Class文件格式
4. Java类库API
5. 商业机构和开源社区的第三方Java类库

**JDK： **Java程序设计语言、Java虚拟机和Java类库API这三部分组成。

**JRE：** Java类库API中的Java SE API子集和Java虚拟机两部分组成，是支持java程序允许的标准环境。

按照技术所服务的领域来划分：

1. Java Card：支持Java小程序运行在小内存设备上的平台。
2. Java ME：支持Java程序运行在移动终端上的平台（**Android并不属于J2ME，Android程序使用Kotlin开发**）。
3. Java SE：支持面向桌面级应用的Java平台。
4. Java EE：支持使用多层架构的企业应用（**在JDK 10以后被Oracle放弃**）。

### 9.1.2 Java虚拟机

Java虚拟机发展：

1. 虚拟机的始祖：Classic VM/Exact VM。

   - 优点：准确式内存管理、热点探测、两级即时编译器、编译器与解释器混合工作模式（Exact VM）。

   - 缺点：编译器和解释器不能同时执行（Classic VM）。

2. 武林盟主：HotSpot VM。

   - 优点：准确式内存管理、热点探测、编译器与解释器协同工作。
   - **HotSpot VM在JDK 8中移除了永久代**。

3. 小家碧玉：Mobile VM/Embedded VM。
   - 针对移动端或嵌入式市场（J2ME）的虚拟机
   - 现在主要用于老人机以及功能手机上。

4. 天下第二：BEA JRockit VM/IBM J9 VM。
   - JRockit VM：内部不包含解释器实现，全部代码都靠即时编译器编译后执行；被Oracle收购，已停止开发。
   - J9 VM：职责分离与模块化。

5. 软硬合璧：BEA Liquid VM/Azul VM。
   - 与特定硬件平台绑定、软硬件配合的专有虚拟机。
   - Liquid VM：不需要操作系统，已停止开发。
   - Azul VM：运行在专有硬件Vega系统上的虚拟机，已停止开发。
6. 挑战者：Apache Harmony/Google Android Dalvik VM。
   - Apache Harmony：未通过TCK认证。
   - Dalvik VM：不能直接执行Java的Class文件，但它执行的DEX文件可以通过Class文件转化而来（因此可以使用Java语法编写应用程序，可以直接使用绝大部分的Java API）；Android 5.0以后，支持提前编译的ART虚拟机全面替换了Dalvik VM。

7. 百家争鸣：KVM/JCVM/Squawk VM//JavaInJava/Maxine VM/Jikes RVM/IKVM.NET...
   - 没有商用价值，仅用于研究、验证某种技术和观点，或是作为一些规范的标准实现。

### 9.1.3 Java技术的未来

Java技术的未来预见：

1. 无语言倾向。
   - 目前，JavaScript主要用于互联网、Python主要用于人工智能、Golang主要用于微服务。
   - Oracle Labs的黑科技Graal VM（跨语言全栈虚拟机）。

2. 新一代即时编译器（Graal编译器）。

3. 向Native迈进。
   - 从大型单体应用架构向小型微服务应用架构发展。
   - 使用提前编译替代即时编译。

4. HotSpot VM的进一步发展。
5. 语言语法特性改进。

### 9.1.3 编译JDK

JDK和OpenJDK的区别：

1. **授权的协议不同**：OpenJDK采用GPLv2（General Public License）议放出，而SUN JDK则采用JRL（Java Research License）协议放出。两者协议虽然都是开放源代码的，但是在使用上的不同在于GPLv2允许在商业上使用，而JRL只允许个人研究使用。OpenJDK不包含Deployment（部署）功能：部署的功能包括：Browser Plugin、Java Web Start、以及Java控制面板，这些功能在OpenJDK中是找不到的。
2. **源代码完整性不同**：在采用GPL协议的OpenJDK中，SUN JDK的一部分源代码因为产权的问题无法开放给OpenJDK使用，其中最主要的部份就是JMX中的可选元件SNMP部份的代码。因此这些不能开放的源代码将它作成plug，以供OpenJDK编译时使用。
3. **部分源代码用开源代码替换**：由于产权的问题，很多产权不是SUN的源代码被替换成一些功能相同的开源代码，比如说字体栅格化引擎，使用Free Type代替。
4. **OpenJDK只包含最精简的JDK**： OpenJDK不包含其他的软件包，比如Rhino Java DB JAXP...，并且可以分离的软件包也都是尽量的分离，但是这大多数都是自由软件，你可以自己下载加入。

## 9.2 Java内存区域与内存溢出异常

### 9.2.1 运行时数据区域

根据《Java虚拟机规范》，Java虚拟机所管理的内存将会包含以下几个运行时数据区域：

1. **程序计数器：**可以看作是当前线程所执行的字节码的行号指示器。
   
   - 线程私有。
   
   - 字节码解释器通过改变程序计数器的值来选取下一条需要执行的字节码指令。
- 程序计数器是程序控制流的指示器，分支、循环、跳转、异常处理、线程恢复等都依赖于程序计数器来完成。
   - 若正在执行的是本地方法，则计数器数值为空（Undefined）。
   
2. **虚拟机栈：**描述Java方法执行的线程内存模型。每个方法被执行时，Java虚拟机都会同步创建一个栈帧用于存储局部变量表、操作数栈、动态连接、方法出口等信息；每一个方法被调用直至执行完毕的过程，就对应着一个栈帧在虚拟机栈中从入栈到出栈的过程。

   - 线程私有。
   - 通常所说的"栈内存"就指的是虚拟机栈，或者更确切来说是指虚拟机栈中局部变量表部分。
   - 局部变量表中存储：计数数据类型、对象引用类型。
   - 虚拟机栈出现的两种异常：StackOverflowError异常和OutOfMemoryError异常。

3. **本地方法栈：**类似于虚拟机栈。区别是虚拟机栈为虚拟机执行Java方法服务，而本地方法栈则是为虚拟机使用到的本地方法服务。
   - 本地方法栈出现的两种异常：StackOverflowError异常和OutOfMemoryError异常。
4. **Java堆：**作用是存放对象实例（堆是虚拟机所管理内存中最大的一块）。
   - 所有线程共享。
   - 堆是垃圾收集器管理的内存区域（也被称为GC堆）。
   - 所有线程共享的Java堆中可以划分出多个线程私有的分配缓冲区以提升对象分配时的效率。
   - 堆可以处于物理上不连续的内存空间中，但在逻辑上它应该被视为连续的。
   - 堆可以被扩展。
   - 堆上出现的异常：OutOfMemoryError异常。

5. **方法区：**用于存储被虚拟机加载的类型信息、常量、静态变量、即时编译器编译后的代码缓存等数据。
   - 线程共享。
   - 《Java虚拟机规范》中把**方法区描述为堆的一个逻辑部分，但是别名为"Non-Heap"**。
   - 《Java虚拟机规范》对方法区的约束是非常宽松的，除了和Java堆一样不需要连续的内存和可以选择固定大小或者可扩展外，甚至还可以选择不实现垃圾收集。方法区的垃圾收集确实比较少见，但并非数据进入方法区后就如"永久代"的名字一样永久存在了。**方法区的内存回收目标主要是针对常量池的回收和对类型的卸载**。
   - 方法区上出现的异常：OutOfMemoryError异常。

6. **运行时常量池：**属于方法区的一部分，用于存放编译器生成的各种字面量和符号引用。
   - Java并不要求常量一定只有编译器才能产生，运行期间也可以将新的常量放如池中（如：String的intern()方法）。
   - 常量池上出现的异常：OutOfMemoryError异常。

7. **直接内存：**不是虚拟机运行时数据区的一部分，也不是《Java虚拟机规范》中定义的内存区域。
   - 直接内存主要用于本地方法的堆外内存。
   - 直接内存不会收到Java堆大小的限制，但是会受到本机物理内存和操作系统级的限制。
   - 直接内存上出现的异常：OutOfMemoryError异常。

### 9.2.2 HotSpot虚拟机对象探秘

**对象的创建过程（Java虚拟机遇到一条字节码new指令时）**：

1. 检查这个指令的参数是否能在常量池中定位到一个类的符号引用（若没有，则先执行类加载过程）。
2. 检查符号引用代表的类是否被加载（若没有，则先执行类加载过程）。
3. 虚拟机为新生对象分配内存（若Java堆规整，则采用"指针碰撞"分配方式；若Java堆不规整，则采用"空闲列表"分配方式）。
   - 如：当采用Serial、ParNew等带压缩整理的收集器时，Java堆是规整的；当采用CMS这种基于清除算法的收集器时，Java堆是不规整的。
   - 解决内存分配时的线程安全问题：
     - 方法一：对分配内存空间的动作进行同步处理（CAS+失败重试保证原子性）。
     - 方法二：把内存分配的动作按照线程划分在不同的空间之中进行（本地线程分配缓存，TLAB），虚拟机是否使用TLAB，可以通过`-XX:+/-UseTLAB`参数来设定。

4. 虚拟机将分配到的内存空间都初始化为零值。
5. 虚拟机对对象进行必要的设置（如：对象属于哪个类的实例、如何找到类的元数据信息、对象的哈希码、对象的GC分代年龄、对象头信息...）。
6. 执行Class文件中的<init()>方法。

**对象在堆中的内存存储布局：**

1. 对象头：包含两类信息。
   - 一类是用于存储对象自身的运行时数据（如：哈希码、GC分代年龄、锁状态标志、线程持有的锁、偏向线程ID、偏向时间戳...）
   - 另一类是类型指针，即对象指向它的类型元数据的指针，虚拟机通过这个指针来确定该对象是哪个类的实例。

2. 实例数据：在程序里定义的各种类型的字段内容（包括父类中继承的）。

3. 对齐填充：作用是占位符（并不必然存在）。
   - 任何对象的大小都必须是8字节的整数倍。

**对象的访问定位方式：**

1. 方式一：使用句柄
   - Java堆中会划分出一块内存作为句柄池。对象引用（存储在虚拟机栈中）中存储的是对象的句柄地址，而句柄中则包含了对象实例数据与类型数据各自具体的地址。
2. 方式二：使用直接指针（可以减少一次间接访问的开销），HotSpot虚拟机采用直接指针的方式进行对象访问。
   - 对象引用（存储在虚拟机栈中）中存储的是对象地址。

### 9.2.3 实战：OutOfMemoryError异常

实战的目的：

1. 通过代码验证《Java虚拟机规范》中描述的各个运行时区域储存的内容。
2. 在工作中，能够根据实际内存溢出异常的提示信息快速定位是哪个区域的内存溢出；知道怎么的代码可能会导致这些区域内存溢出；出现这些异常后该如何处理。

#### 9.2.3.1 Java堆溢出

注意事项：

- 将堆的最小值-Xms参数与最大值-Xmx参数设置为一样即可避免堆自动扩展。

- 通过`-XX:+HeapDumpOnOutOfMemoryError`可以让虚拟机在出现内存溢出异常时Dump出当前的内存堆转储快照以便进行事后分析。

```java
// VM options
// -verbose:gc -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
// java.lang.OutOfMemoryError: Java heap space
public class HeapOOM {
    static class OOMObject {
    }
    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<OOMObject>();
        while (true) {
            list.add(new OOMObject());
        }
    }
}
```

分析工具：

- Eclipse可以使用MAT工具（Eclipse Memory Analyzer）分析Dump出的堆转储快照。
- IDEA可以使用JProfiler分析Dump出的堆转储快照。

#### 9.2.3.2 虚拟机栈和本地方法栈溢出

注意事项：

- -Xoss可以设置本地方法栈大小，但由于HotSpot并不区分虚拟机栈和本地方法栈，实际上没有任何效果。
- HotSpot的栈容量由-Xss来设置。
- **无论是由于栈帧太大还是虚拟机栈容量太小，当新的栈帧内存无法分配的时候，HotSpot虚拟机抛出的都是StackOverflowError异常。**
- 通过不断创建线程，也可导致HotSpot虚拟机抛出OutOfMemoryError异常。

```java
// VM options
// -verbose:gc -Xss128k
// java.lang.StackOverflowError
public class JavaVMStackSOF_1 {
    private int stackLength = 1;
    public void stackLeak() {
        stackLength++;
        stackLeak();
    }
    public static void main(String[] args) throws Throwable {
        JavaVMStackSOF_1 oom = new JavaVMStackSOF_1();
        try {
            oom.stackLeak();
        } catch (Throwable e) {
            System.out.println("stack length:" + oom.stackLength);
            throw e;
        }
    }
}
```

#### 9.2.3.3 方法区和运行时常量池溢出

注意事项：

- String::intern()是一个本地方法，作用是：
  - 若字符串常量池中已经包含一个等于此String对象的字符串，则返回代表池中这个字符串的String对象的引用。
  - 若字符串常量池中不包含一个等于此String对象的字符串，则将此String对象包含的字符串添加到常量池中，并且返回此String对象的引用。

- 在JDK 6之前的HotSpot虚拟机中，常量池分配在永久代中（方法区），可以通过`-XX:PermSize`或`-XX:MaxPermSize`限制永久代的大小达到间接限制其中常量池的容量；但是在JDK 8中完全使用元空间来替代了永久代，此时字符串常量池从方法区移动到了Java堆中。

```java
// VM options
// -XX:PermSize=6M -XX:MaxPermSize=6M
// 在JDK 6中会出现OutOfMemoryError异常；在JDK 8中不会出现异常。
public class RuntimeConstantPoolOOM_1 {
    public static void main(String[] args) {
        // 使用Set保持着常量池引用，避免Full GC回收常量池行为
        Set<String> set = new HashSet<String>();
        // 在short范围内足以让6MB的PermSize产生OOM了
        short i = 0;
        while (true) {
            set.add(String.valueOf(i++).intern());
        }
    }
}
```

#### 9.2.3.4 本机直接内存溢出

注意事项：

- 直接内存的容量大小可以通过`-XX:MaxDirectMemorySize`参数来指定；若不指定，则默认与Java堆最大值（由-Xmx指定）一致。

```java
// VM options
// -Xmx20M -XX:MaxDirectMemorySize=10M
// java.lang.OutOfMemoryError
public class DirectMemoryOOM {
    private static final int _1MB = 1024 * 1024;
    public static void main(String[] args) throws Exception {
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);
        while (true) {
            unsafe.allocateMemory(_1MB);
        }
    }
}
```

## 9.3 垃圾收集器与内存分配策略

### 9.3.1 垃圾收集

**1. 哪些区域需要进行垃圾收集？**

- **程序计数器、虚拟机栈和本地方法栈**3个区域随线程而生、随线程而灭，不需要进行垃圾回收（当方法结束或线程结束时，内存就会自动被回收了）。
- Java堆和方法区只有处于运行时期，虚拟机才会知道程序会创建哪些对象、创建多少对象，这部分内存分配和回收是动态的，所以Java堆和方法区需要进行垃圾收集。

**2. 垃圾收集需要解决的几个问题：**

1. 哪些内存需要回收？
2. 什么时候回收？
3. 如何回收？

### 9.3.2 对象已死

**1. 如何确定哪些对象已死？主要有两种方法：**

1. **引用计数算法：**在对象中添加一个引用计数器，每当有一个地方引用它时，计数器就加一；当引用失效时，计数器就减一；任何时刻计数器为零的对象就是不可能再被使用的。
   - 优点：效率很高。
   - 缺点：需要占用额外的内存空间来计数；无法解决对象之间的循环引用问题。**故主流的Java虚拟机均未使用"引用计数法"来管理内存**。

2. **可达性分析算法：**通过一系列称为"GC Roots"的根对象作为起始节点集，从这些节点开始，根据引用关系向下搜索，搜索过程所走过的路径称为"引用链"，如果某个对象到GC Roots间没有任何引用链相连（或者用图论的话说就是**从GC Roots到这个对象不可达时，则证明此对象是不可能再被使用的**）。**主流的Java虚拟机均未使用"可达性分析算法"来管理内存**。GC Roots包括以下几种：
   - 虚拟机栈中引用的对象（方法堆栈中使用到的参数、局部变量、临时变量）。
   - 本地方法栈中本地方法引用的对象。
   - 在方法区中类静态属性引用的对象、常量引用的对象。
   - 虚拟机内部的引用：如基本数据类型对应的Class对象、一些常驻的异常对象、系统加载器...。
   - 所有被同步锁持有的对象。

**2. 引用的类型：**

- 强引用：类似于`Object obj = new Object()`这种引用关系。**在任何情况下，只要强引用关系存在，垃圾收集器就永远不会回收掉被引用的对象。**
- 软引用（SoftReference类）：用来描述一些还有用，但非必须的对象。**在系统将要发生内存溢出异常时，虚拟机会将被软引用关联的对象列进回收范围之中进行二次回收。**
- 弱引用（WeakReference类）：用来描述非必须的对象，强度比软引用更弱。**无论内存是否足够，只被软引用关联的对象在垃圾收集器开始工作时都会被回收。**
- 虚引用（PhantomReference类）：也称为"幽灵引用"或"幻影引用"。**为一个对象设置虚拟引用的唯一目的只是为了在这个对象被收集器回收时收到一个系统通知。**

**3. 如何判断一个对象非死不可？**

采用两次标记过程：

1. 第一次标记：对象在进行可达性分析后发现没有与GC Roots相连接的引用链，对象会被第一次标记；标记后会进行一次筛选，将**有必要执行finalize()方法的对象**放入一个名为F-Queue的队列中。
   - 没有必要执行finalize()方法的情况：对象没有覆盖finalize()方法，或finalize()方法已经被虚拟机调用过。没有必要执行finalize()方法的对象不会进行第二次标记，在第一次标记后就会被收集器收集。
2. 第二次标记：收集器在F-Queue队列中进行小规模的标记，若对象在finalize()方法中仍然未将该对象重新与引用链上的任何一个对象建立起关联，那么对象就会被回收。
   - 任何一个对象的finalize()方法只会被系统自动调用一次。

**4. 关于回收方法区：**

- 《Java虚拟机规范》中提到过可以不要求虚拟机在方法区中实现垃圾收集（如：JDK 11中的ZGC收集器就不支持类卸载）。
- 方法区垃圾收集的"性价比"比较低，而堆内存进行一次垃圾收集通常可以回收70%-99%的内存空间。
- 方法区的垃圾回收主要包括两部分内容：
  1. 废弃的常量。
  2. 不再使用的类型。判断一个类型不再使用必须满足以下三个条件：
     - 该类所有的实例（包括任何派生子类的实例）都已经被回收。
     - 加载该类的类加载器已经被回收。
     - 该类对应的Class对象没有在任何地方被引用，无法在任何地方通过反射访问该类的方法。

- 类型不再使用才**被允许**回收，但不是一定被回收，HotSpot提供了`-Xnoclassgc`参数来控制是否对类型进行回收。

### 9.3.3 垃圾收集算法

所介绍的垃圾收集算法均是基于可达性分析算法实现。

#### 9.3.3.1 分代收集理论

分代收集理论建立在以下假说之上：

1. 弱分代假说：绝大多数对象都是朝生夕灭的。
2. 强分代假说：熬过越多次垃圾收集过程的对象就越难以消亡。
3. 跨代引用假说：跨代引用相对于同代引用来说仅占极少数。

分代收集理论：兼顾了**垃圾收集的时间开销**和**内存的空间有效利用**。

1. 针对弱分代区，以较低代价回收到大量的空间。
2. 针对强分代区，以较低频率来回收该区域的内存。
3. 针对对象之间存在的跨代引用，可以在强分代区划分出若干块**记忆集**，用以标识出老年代的哪一块内存会存在跨代引用。
4. **典型的分代收集算法："标记-复制算法"、"标记-清除算法"、"标记-整理算法"。**

常见的分代名词：

1. 部分收集（Partial GC）：指目标不是整个Java堆的垃圾收集。
   - 新生代收集（Minor GC/Young GC）：指目标只是新生代的垃圾收集。
   - 老年代收集（Major GC/Old GC）：指目标只是老年代的垃圾收集。
     - 目前只有CMS收集器具有单独收集老年代的行为。
     - "Major GC"有些资料上也指整堆收集。
   - 混合收集（Mixed GC）：指目标是收集整个新生代以及部分老年代的垃圾收集。
     - 目前只有G1收集器有这种行为。

2. 整堆收集（Full GC）：收集整个Java堆和方法区的垃圾收集。

#### 9.3.3.2 标记-清除算法

"标记-清除"（Mark-Sweep）算法：首先标记出所有需要回收的对象，在标记完成后，统一回收掉所有被标记的对象。

缺点：

1. 执行效率不高：若Java堆中有大量对象，且其中大部分都需要被回收，这时必须进行大量标记和清除的动作，导致标记和清除两个过程的执行效率都随着对象数量增长而降低。
2. 内存空间的碎片化问题：标记清除后会存在大量不连续的内存碎片，导致当程序分配较大对象时又会提前触发另一次垃圾收集动作。

#### 9.3.3.3 标记-复制算法

"标记-复制"（Mark-Copy）算法：也简称为复制算法。标记-复制算法的思想是半区复制：将可用内存按容量划分为大小相等的两块，每次只使用其中一块。当这一块的内存用完了，就将还存活着的对象复制到另外一块上面，然后再把已使用过的内存空间一次清理掉。

缺点：

1. 若内存中多数对象都是存活的，将产生大量的内存间**复制开销**。
2. 将可用内存缩小为原来的一半，**空间浪费比较多**。

优点：

1. 若内存中多数对象都是可回收的，标记-复制算法比较简单和高效。
2. 没有空间碎片。

**Appel式回收：基于标记-复制算法，将新生代分为一块较大的Eden区和两块较小的Survivor区，大小比为`Eden:Survivor1:Survivor2=8:1:1`。**

#### 9.3.3.4 标记-整理算法

"标记-整理"（Mark-Compact）算法：和标记-清除算法类似，不同的是，标记后不是直接对可回收对象进行清理，而是让所有存活的对象都向内存空间一端移动，然后直接清理掉边界以外的内存。

缺点：

1. 若是在每次回收都有大量存活对象的老年代，使用标记-整理算法会带来很大的移动开销。
2. 移动时需要Stop the World。

优点：

1. 会解决空间碎片化问题。

对比"标记-清除"算法和"标记-整理"算法：

1. "标记-清除"算法不需要移动对象，会导致内存分配时更复杂；"标记-整理"算法需要移动对象，会导致内存回收时更复杂。
2. "标记-清除"算法不需要停顿时间；"标记-整理"算法需要停顿时间。
3. 综合考虑**移动**和**停顿**，从整个程序的吞吐量来说，移动对象更划算（**原因是内存分配和访问相比垃圾收集频率要高得多**）。

### 9.3.4 HotSpot的算法细节实现

**根节点枚举**：即查找GC Roots。

- 需要Stop The World。原因：不会出现分析过程中，根节点集合的对象引用关系还在不断变化的情况。
- CMS、G1、ZGC等收集器的根节点枚举都必须要停顿。
- OopMap使得HotSpot可以快速完成GC Roots枚举，缩短Stop The World的持续时间。
- 安全点（作用是解决如何停顿用户线程）使收集器等待时间不至于过长，也不能过于频繁以至于过分增大运行时的内存负担。
- 安全区域（作用是确保在一段代码片段中，引用关系不会发生变化。）

记忆集：一种用于记录从非收集区域指向收集区域的指针集合的抽象数据结构，作用是缩短GC Roots扫描范围。常见的记忆集实现方式：

1. 字长精度。
2. 对象精度。
3. 卡精度：每个记录精确到一块内存区域，该区域内有对象含有跨代指针。
   - 卡表是基于卡精度的一种方式，是目前最常用的一种记忆集的实现。
   - 在HotSpot里，卡表的状态是由"写屏障"技术维护的。
   - 卡表的缺点：
     - "写屏障"开销。
     - 在高并发下存在"伪共享"问题：解决方法是写屏障增加一次判断，只有当卡表元素未被标记过时才将其变脏。

**可达性分析**：即对象图的遍历。

- 需要Stop The World，且比根节点枚举停顿的时间要长得多。
- 采用**三色标记（Tri-color Marking）算法**来进行对象图的遍历，以降低用户线程的停顿。
  - 白色：表示对象尚未被垃圾收集器访问过。在分析结束时，仍然是白色的对象不可达。
  - 黑色：表示对象已经被垃圾收集器访问过，且对象的所有引用都扫描过。黑色对象是安全存活的；且黑色对象不可能直接（不经过灰色对象）指向某个白色对象。
  - 灰色：表示对象已经被垃圾收集器访问过，但这个对象上至少存在一个引用还没有被扫描过。

**并发的可达性：**并发过程中使用三色标记算法进行对象图的遍历，会带来2个问题：

1. 把原本消亡的对象错误标记为存活（问题不致命）。

2. 把原本存活的对象错误标记为已消亡（问题致命）。

   - 出现对象消亡的两个必要条件：
     - 条件一：赋值器插入了一条或多条从黑色对象到白色对象的新引用。
     - 条件二：赋值器删除了全部从灰色对象到该白色对象的直接或间接引用。

   - 解决对象消亡的措施：
     - 增量更新：破坏条件一（如CMS）。黑色对象一旦新插入了指向白色对象的引用之后，它就变回灰色对象了。
     - 原始快照：破坏条件二（如G1）。无论引用关系删除与否，都会按照刚刚开始扫描的那一刻的对象图快照来进行搜索。

### 9.3.5 经典垃圾收集器

#### 9.3.5.1 Serial收集器

Serial收集器：JDK1.3.1之前是HotSpot虚拟机新生代收集器的唯一选择，**原理是标记-复制算法**。

- 单线程工作。
- **常用于新生代。**
- 进行垃圾收集时，会Stop The World。
- 适用于内存资源受限的环境、单核处理器或处理器核心数较少的环境；**对于运行在客户端模式下的虚拟机来说是一个很好的选择**。

#### 9.3.5.2 ParNew收集器

ParNew收集器：是Serial收集器的多线程版本，JDK 7之前首选的新生代收集器；**原理是标记-复制算法**。

- 多线程并发收集，可以使用`-XX:ParallelGCThreads`参数来限制垃圾收集的线程数。
- **常用于新生代。**
- 进行垃圾收集时，会Stop The World。
- 适用于运行在服务端模式下的虚拟机；除了Serial收集器外，ParNew收集器是唯一能与CMS配合使用的收集器。

- 合并入CMS，成为专门处理新生代的组成部分；是HotSpot中第一款退出历史舞台的垃圾收集器。

#### 9.3.5.3 Parallel Scavenge收集器

Parallel Scavenge收集器：与ParNew收集器类似，被称作"吞吐量优先收集器"；**原理是标记-复制算法**。

- 多线程并发收集。

- 常用于新生代。

- 具有自适应调节策略。

- 适用于精确控制吞吐量的应用（控制最大垃圾收集的停顿时间：`-XX:MaxGCPauseMillis`；设置垃圾收集时间占总时间比率：`-XX:GCTimeRatio`）。

  - `吞吐量=运行用户代码时间/(运行用户代码时间+运行垃圾收集时间)`

  - `-XX:MaxGCPauseMillis`：停顿时间的缩短是以牺牲吞吐量和新生代空间为代价换取的。
  - `-XX:GCTimeRatio`：值范围为0~100的整数，默认为99。例如：把`-XX:GCTimeRatio`设置为19，则垃圾搜集时间占总时间的5%(=1/(1+19))。
  - `-XX:+UseAdaptiveSizePolicy`：一个开关参数，激活之后就不用设置新生代大小（`-Xmm`）、Eden与Survivor区的比例（`-XX:SurvivorRatio`）及晋升老年代对象的大小（`-XX:PretenureSizeThreshold`）等细节参数了（但需要设置最大堆的大小（`-Xmx`））。虚拟机会根据当前系统的运行情况收集性能监控信息、动态调整这些参数以提供最合适的停顿时间或者最大的吞吐量，这种方式称为**垃圾收集的自适应调节策略**。

#### 9.3.5.4 Serial Old收集器

Serial Old收集器：是Serial收集器的老年代版本；**原理是标记-整理算法**。

- 单线程工作。
- 用于老年代。
- 适用于客户端模式下的HotSpot虚拟机使用；若用于服务端，则一般是作为CMS收集器发生失败后的后备预案（在并发收集失败时使用）。

#### 9.3.5.5 Parallel Old收集器

Parallel Old收集器：是Parallel Scavenge收集器的老年代版本；**原理是标记-整理算法**。

-  多线程并发收集。
- 用于老年代。
- 使用Parallel Scavenge收集器+Parallel Old收集器，作为名副其实的"吞吐量优先"的垃圾收集系统。

#### 9.3.5.6 CMS收集器

CMS（Concurrent Mark Sweep）收集器：以获取最短回收停顿时间为目标的收集器；**原理是标记-清除算法**。

- 多线程并发收集。
- 适用于B/S系统的服务端，重点在于提高系统的响应速度。
- 原理：
  1. 初始标记：Stop The World。标记GC Roots能直接关联到的对象，速度很快。
  2. 并发标记：不需要停顿。对象图的遍历。
  3. 重新标记：Stop The World，停顿时间比初始标记长，但远比并发标记短。修正并发标记时因用户程序继续运行导致标记变动。
  4. 并发清除：不需要停顿。清除掉标记阶段判断的已经死亡的对象。

- 缺点：
  - 对处理器资源敏感。CMS默认启动的回收线程数=(处理器核心数量+3)/4；CMS最好用于处理器核数在4个以上的应用。
  - 无法处理浮动垃圾，可能会出现收集失败，导致另一次完全Stop The World的Full GC的产生（一般是使用Serial Old收集器作为后备预案）。
    - `-XX:CMSInitiatingOccu-pancyFraction`：设置CMS的触发百分比。
  - 会有大量空间碎片产生，导致大对象分配时提前触发一次Full GC。
    - `-XX:+UseCMSCompactAtFullCollection`：开发参数，用于Full GC时开启内存碎片的合并整理过程（JDK 9已废弃）。
    - `-XX:CMSFullGCsBeforeCompaction`：默认值是0。表示执行若干次不整理的Full GC后，下一次Full GC时会进行整理（JDK 9已废弃）。

#### 9.3.5.7 G1收集器

G1（Garbage First）收集器：开创了收集器面向局部收集的设计思路和基于Region的内存布局形式。

- 面向于服务端应用的垃圾收集器。









### 9.3.6 低延迟收集器

#### 9.3.6.1 Shenandoah收集器





#### 9.3.6.2 ZGC收集器







### 9.3.7 选择合适的收集器

#### 9.3.7.1 Epsilon收集器





#### 9.3.7.2 收集器的权衡







#### 9.3.7.3 虚拟机及垃圾收集器日志







### 9.3.8 实战：内存分配与回收策略







> 参考博客文章：[周志明-深入理解Java虚拟机]()

# 10 Effective Java

## 10.1 引言

本书主要用来更好地使用java基本类库：`java.lang`、`java.util`、`java.util.concurrent`和`java.io`。

## 10.2 创建和销毁对象

1. **考虑用静态工厂方法代替构造器**
   - 静态工厂方法有名称，更易阅读。
   - 静态工厂方法不必在每次调用它们的时候都创建一个新对象。
   - 静态工厂方法可以返回原返回类型的任何子类型的对象。
   - 静态工厂方法在创建参数化类型实例的时候会使代码变得更加简洁。

2. **遇到多个构造器参数（4个及以上）时要考虑用构建器**
   - 重叠构造器模式：安全性比较好，但当有许多参数时客户端代码会很难编写并且难以阅读。
   - JavaBean模式：可读性比较好，但阻止了把类做成不可变类的可能。
   - Builder模式：安全性和可读性都更好。
     - builder可以对其参数强加约束条件。
     - builder可以有多个可变参数，而构造器只能有一个可变参数。
     - builder可以自动填充某些域，例如可以在每次创建对象时自动增加序列号。
     - 由于需要创建构建器，builder模式会带来额外的性能开销。

3. **用私有构造器或者枚举类型强化Singleton属性**
   - 方法一：把构造器保持为私有的，并导出公有的静态成员。
   - 方法二：把构造器保持为私有的，公有的成员是一个静态工厂方法。
   - 方法三：单个元素的枚举类型（**实现单例的最佳方法**）。

4. **通过私有构造器强化不可实例化的能力**
   - 通过将构造器变为私有的，不仅会导致该类无法实例化，也会导致该类不能被子类化。

5. **避免创建不必要的对象**
   - 例如，使用`String s = "stringette";`而不是`String s = new String("stringette");`。后者每次都会创建新的实例，而前者对于所有在同一台虚拟机中运行的代码，只要它们包含相同的字符串字面量，该对象就会被重用。
   - 尽量将常用方法中的变量初始化抽取到静态代码块中进行。
   - 要优先使用基本类型而不是装箱基本类型，要当心无意识的自动装箱。

```java
// 错误的示范
public static void main(String[] args){
    // 注意这里，若为Long类型，计算时会先拆箱成long再计算，导致会创建出很多无用的Long实例
    Long sum = 0L;
    for (long i=0; i < Integer.MAX_VALUE; i++){
        sum+=i;
    }
    System.out.println(sum);
}
```

6. **消除过期的对象引用**

   - 清空对象引用应该是一种例外，而不是一种规范行为。消除过期引用的最好方法是让包含该引用的变量结束其生命周期。

   - 内存泄漏的几个常见来源：

     - 只要类是自己管理内存，程序员就应该警惕内存泄漏问题。 

     - 内存泄漏的另一个常见来源就是缓存（使用WeakHashMap）。
     - 内存泄漏的第三个常见来源是监听器和其他回调。

7. **避免使用终结方法**
   - 终结方法的缺点在于不能保证会被及时地执行，从一个对象变得不可达开始，到它的终结方法被执行，所花费的这段时间是任意长的。
   - java语言规范不仅不保证终结方法会被及时地执行，而且根本就不保证它们会被执行，故不应该依赖终结方法来更新重要的持久状态。
   - `System.gc和System.runFinalization`不保证终结方法一定会执行；唯一能保证终结方法被执行的是`System.runFinalizersOnExit`，但该方法有致命缺陷。
   - 若类确实需要终止，只需提供一个显式的终止方法（典型例子是InputStream、OutputStream、java.sql.Connection上的close方法、java.util.Timer上的cancel方法）。
   - 如果类（不是Object）有终结方法，并且子类覆盖了终结方法，子类的终结方法就必须手工调用**超类的终结方法**。

## 10.3 对于所有对象都通用的方法

8. **覆盖equals时请遵守通用约定**

   - 若不覆盖equals方法，则类的每个实例都只与它自身相等。
     - 类的每个实例本质上都是唯一的。
     - 不关心类是否提供了"逻辑相等"的测试功能。
     - 若超类已经覆盖了equals（如AbstractSet），从超类继承过来的行为对于子类也是合适的。
     - 若类是私有的或是包级私有的，可以确定它的equals方法永远不会被调用。

   - 有一种"值类"不需要覆盖equals方法，即实例受控确保"每个类至多只存在一个对象"的类（如枚举类型）。对于这种类，逻辑相同与对象等同是一回事。

   - 当覆盖equals方法时，需要遵循以下规范：

     - 自反性：`x.equals(x)`返回true。

     - 对称性：若`y.equals(x)`返回true，则`x.equals(y)`也应返回true（**`java.sql.Timestamp`继承自`java.util.Date`，`java.sql.Timestamp`的equals方法违反了对称性，故请不要混合使用`Timestamp`和`Date`对象**）

       ```java
       public class CaseInsensitiveString {
           private final String s;
           public CaseInsensitiveString(String s) {
               if(s == null)
                   throw new NullPointerException();
               this.s = s;
           }
           // 违反对称性
           @Override        
           public boolean equals(Object o) {
               if(o instanceof CaseInsensitiveString)
                   return s.equalsIgnoreCase(
                           ((CaseInsensitiveString) o).s);
               if(o instanceof String)
                   return s.equalsIgnoreCase((String) o);
               return false;
           }
       }
       
       // 举例
       CaseInsensitiveString cis = new CaseInsensitiveString("Polish");
       String s = "polish";
       System.out.println(cis.equals(s)); // 输出true
       System.out.println(s.equals(cis)); // 输出false
       ```

     - 传递性：若`x.equals(y)`返回true且`y.equals(z)`返回true，则`x.equals(z)`也返回true。

     - 一致性：多次调用结果一致。

       - 无论类是否是可变的，都不要使equals方法依赖于不可靠的资源。

     - 非空性：对于任何非null的引用值x，`x.equals(null)`必须返回false（使用instanceof而不是==null，前者包含了后者的情况）。

   - 实现高质量的equals方法的诀窍：

     - 使用==操作符检查"参数是否为这个对象的引用"（这不过是一种性能优化，如果比较操作有可能很昂贵，就值得这么做）。
     - 使用instanceof操作符检查"参数是否为正确的类型"。
     - 在类型检查通过的情况下，把参数转换为正确的类型。
     - 对于该类中的每个"关键"域，检查参数中的域是否与该对象中对应的域相匹配。
       - 对于既不是float也不是double类型的基本类型域（存在Float.NaN、-0.0f以及类似的double常量），可以使用==操作符进行比较；
       - 对于对象引用域，可以递归地调用equals方法；
       - 对于float域，可以使用Float.compare方法；对于double域，则使用Double.compare方法；
       - 对于数组域，则要将以上原则用于数组的元素上（可以使用Arrays.equals方法）。

     - 当编写完成了equals方法后，应该检查是否是对称的、传递的、一致的。

   注意事项：

   - 覆盖equals时总要覆盖hashCode。
   - 不要企图让equals方法过于智能（如：File不应试图把指向同一个文件的符号链接当作相等的对象来看待）。
   - 不要将equals方法参数声明中的Object对象替换为其他的类型（因为这并不是覆盖Object的方法，这相当于重载了equals方法）。

9. **覆盖equals时总要覆盖hashCode**

   - equals相等的对象，hashCode也必须相等
   - equals不相等的对象，hashCode可相等也可不相等（推荐不相等，这样可以提高散列表的性能）
     - 若equals相等但hashCode相等，则哈希桶会退化为链表，使得查找从线性时间变为了平方级时间。

   注意事项：

   - 计算int类型散列码的方法：
     1. 定义一个非零的常数值，如int result = 17；
     2. 对于boolean类型，计算`f ? 1 : 0`;
     3. 对于byte、char、short、int类型，计算`(int)f`;
     4. 对于long类型，计算`(int)(f^(f>>>32))`;
     5. 对于float类型，先计算`Float.floatToIntBits(f)`转换为int；
     6. 对于double类型，先计算`Double.doubleToLongBits(f)`转换为long，再计算`(int)(f^(f>>>32))`;
     7. 对于对象引用，递归计算hashCode；
     8. 对于null，返回0；
     9. 对于数组，可以对每个元素计算hashCode（或者采用Arrays.hashCode方法计算数组的散列码)；
     10. 每一步计算完后：`result = 31 * result + c;`，其中31是一个奇素数。31有个很好的特性，即用以位和减法来代替乘法，可以得到更好的性能，`31 * i == (i<<5) - i`，现代的VM可以自动完成这种优化。

   - 若一个类是不可变的，并且计算散列码的开销也比较大，就应该考虑把散列码缓存在对象内部（通过增加一个hashCode域），而不是每次请求的时候都重新计算散列码。
   - 如果这种类型的大多数对象会被用作散列键，则可以在创建实例的时候就计算散列码；否则可以选择**延迟初始化**（即第一次调用hashCode方法时才初始化）散列码。
   - 不要试图从散列码计算中排除掉一个对象的关键部分来提高性能（这可能会使散列桶的搜索成几何倍数增长）。
   - 一般来说不要使用java类库中的类（如String、Integer、Date...）的hashCode方法返回的确切值规定为该实例值得一个函数，这样会限制将来对散列函数的改进。

10. **始终要覆盖toString**
    - toString方法应该返回对象中包含的所有值得关注的信息。
    - 是否指定文档中返回值的格式（如：BigInteger、BigDecimal和绝大多数的基本类型包装类）；可以在toString方法中使用String.format方法指定格式。

11. **谨慎地覆盖clone**（比较晦涩）
    - Object的clone方法是受保护的，若不借助于反射，不能仅仅因为一个对象实现了Cloneable，就可以调用clone方法。
    - Cloneable是一个没有任何内容的接口，其作用是：若一个类实现了Cloneable，Object的clone方法就返回该对象的逐减拷贝，否则就会抛出`CloneNotSupportedException`异常（Cloneable改变了超类中受保护方法的行为，这是接口的一种极端行为，不值得效仿）。
    - 如果覆盖了非final类中的方法，则应该返回一个通过调用super.clone而得到的对象。
    - 若对象中包含的域引用了可变的对象，使用简单的clone实现可能会导致灾难性的后果（**clone架构与引用可变对象的final域的正常用法是不相兼容的**）。
    - Object的clone方法被声明为可抛出`CloneNotSupportedException`异常，但是覆盖的clone方法则应该省略这个声明。
    - 若决定用线程安全的类实现Cloneable接口，要记得它的clone方法必须得到很好的同步。
    - **所有实现了Cloneable接口的类都应该用一个公有的方法覆盖clone，此公有方法首先调用super.clone，然后修正任何需要修正的域**。
    - **最好的实现对象拷贝的方法是提供一个拷贝构造器或拷贝工厂**；此外，拷贝对象工厂还可以选择拷贝的实现类型（如：可以把一个HashSet拷贝成TreeSet）。
    - 若父类没有提供受保护的clone方法，其子类就不可能实现Cloneable接口。

12. **考虑实现Comparable接口**
    - compareTo是Comparable接口的唯一方法。
    - 使用`Arrays.sort()`方法可为实现Comparable接口的对象数组进行排序。
    - 与equals相同的是，compareTo方法同样应该遵守自反性、对称性、传递性、与equals方法等同性（非必须遵守）；与equals不同的是，在跨越不同类时，compareTo可不做比较（若两个被比较的对象引用不同类的对象，compareTo可以抛出ClassCastException异常）。
    - **有序集合**（如TreeSet）使用了由compareTo方法而不是equals方法所施加的等同性测试；而Collection、Set、Map接口的**非有序集合**（如HashSet）的通用约定是按照equals方法来定义（因此，若compareTo方法与equals方法不遵守等同性，可能会带来一些未预想的结果）。
      - 例：**HashSet是通过equals来进行比较的；而TreeSet是通过compareTo来比较的**。
    - 若没有实现Comparable接口，又需要实现排序的需求，可以编写自己的Comparator。
    - 若一个类有多个关键域，选择什么样的顺序来比较这些域非常关键（因为若某个域比较产生了非零的结果，则整个比较操作结束，并会返回结果）。

## 10.4 类和接口

13. **使类和成员的可访问性最小化**
    - 尽可能使每个类或者成员不被外界访问。
    - 私有成员和包级私有成员都是一个类的实现中的部分，一般不会影响它的导出的API；受保护的成员是类的导出的API的一部分，必须永远得到支持（导出的类的受保护成员也代表了该类对于某个实现细节的公开承诺，受保护的成员应该尽量少用）。
    - 如果方法覆盖了超类中的一个方法，子类中的访问级别就不允许低于超类中的访问级别（如：接口中的方法隐含是公有访问级别的，那么其实现类中的所有方法都必须是公有的）。
    - 实例域绝不能是公有的；常量应该设置成公有的静态final域（若final域包含可变对象的引用，这会带来灾难性的后果）；**确保公有静态final域所引用的对象都是不可变的**。
    - 长度为0的数组总是可变的。所以，类具有公有的静态final数组域，或者返回这种域的访问方法，都几乎总是错误。

14. **在公有类中使用访问方法而非公有域**
    - 对于可变的类来说，应该用包含私有域和公有设值方法（setter）的类来代替（因为若公有类暴露了它的数据域，要想在将来改变其内部表示法是不可能的）。
    - 公有类永远都不应该暴露可变的域。

15. **使可变形最小化。**

    - java类库中包含许多不可变的类，如String、基本类型的包装类、BigInteger和BigDecimal。
    - 使类成为不可变类，需要满足以下5条规则：
      - 不要提供任何会修改对象状态的方法。
      - 保证类不会被扩展（如：将类添加final关键字）。
      - 使所有的域都是final的。
      - 使所有的域都成为私有的（防止客户端获得被域引用的可变对象，虽然禁止final域引用可变对象，这里相当于双重保险）。
      - 确保对于任何可变组件的互斥访问。
    - 不可变对象本质上是线程安全的，它们不要求同步。
    - 不可变对象可以被自由地共享，所以不需要也不应该为不可变的类提供clone方法或者拷贝构造器。
    - 不可变类的唯一缺点是：对于每个不同的值都需要一个单独的对象，而这会带来性能问题。
    - 禁止类被子类化的方法：
      - 方法一：在class前添加final关键字
      - 方法二：让类的所有构造器都变成私有的或者包级私有的，并添加公有的静态工厂来替代公有的构造器。

    - 坚决不要为每个get方法编写一个相应的set方法，除非有很好的理由要让类成为可变的类。

16. **复合优先于继承**
    - 包内的继承非常安全；跨越包边界的继承则非常危险。
    - 与方法调用不同，继承打破了封装性（子类依赖于其超类中特定功能的实现细节，若超类随着发行版本变化，子类会遭到破坏）。
    - 若在继承一个类时，仅仅是增加方法而未覆盖方法，你可能会认为这是安全的；但假如超类在版本更新时新增了一个与子类签名相同但返回类型不同的方法，那么子类将无法通过编译。
    - 组合的常见使用是**装饰者模式**。
    - 只有当两个类之间确实存在"is-a"关系时，才应该使用继承（如：Stack不应继承Vector；Properties不应继承Vector，这些都是java类库中设计的不合理的地方）。

17. **要么为继承而设计，并提供文档说明，要么就禁止继承**

    针对可以继承的超类：

    - 该类的文档必须精确地描述覆盖每个方法所带来的影响（应该描述一个给定的方法做了什么工作，而不是描述它是如何做到的）。
    - 对于为了继承而设计的类，唯一的测试方法就是编写子类。
    - 为了允许继承，构造器决不能调用可被覆盖的方法（因为子类中覆盖的方法将会在子类的构造器运行之前就先被调用，若该覆盖版本的方法依赖于子类构造器所执行的任何初始化工作，这将会带来错误）。
    - 无论是clone方法还是readObject方法，都不可以调用可覆盖的方法，不管是以直接还是间接的方式。

18. **接口优于抽象类**
    - 抽象类需要子类继承（extends），而接口需要子类实现（implements）。
    - 由于java只支持单继承，因此抽象类作为类型定义受到了很大的限制。
    - 接口允许我们构造非层次结构的类型框架。
    - 抽象类的一个重要作用就是用来作为**骨架实现**（如集合框架中的AbstractCollection、AbstractSet、AbstractList、AbstractMap），抽象类可以实现（implements）接口为程序员提供一些实现上的帮助。
      - 骨架实现的第一个好处在于，它们为抽象类提供了实现上的帮助，但又不强加"抽象类被用作类型定义时"所特有的严格限制。
      - 骨架实现的第二个好处在于，可以模拟多重继承。
    - 使用抽象类来定义允许多个实现的类型，与使用接口相比有一个明显的优势：抽象类的演变要比接口的演变要容易的多（这是因为抽象类中可以增加方法实现，却不用子类做任何改变）。因此，接口优于抽象类有一个例外，即当演变的容易性比灵活性和功能更为重要的时候。

19. **接口只用于定义类型**
    - 常量接口（可以避免用类名来修饰常量名）：没有任何方法，只包含静态的final域，每个域都导出一个常量。需要指出的是，常量接口是对接口的不良实现，会将实现细节导出到API中。**尽量避免使用常量接口**。
    - 替代常量接口的一个好的办法是使用**不可实例化的工具类**。工具类要求客户端需要用类名来修饰这些常量名，可以通过利用"静态导入"（import static）机制，避免用类名来修饰常量名（如：`import static com.java.science.PhysicalConstants.*;`）。

20. **类层次优于标签类**
    
- 标签类过于冗长，容易出错，并且效率低下。
  
21. **用函数对象表示策略**
    
- 最典型的示例是**策略模式**，如利用Comparator的实现类来实现元素的排序。当一个策略只使用一次时，通常使用匿名类来声明和实例化这个具体策略类；当一个具体的策略是设计用来重复使用时，它的类通常就要被实现为私有的静态成员类，并通过公有的静态final域被导出。
  
22. **优先考虑静态成员类**

    - 嵌套类的四种形式：静态成员类、非静态成员类、匿名类、局部类；后三种统一被称为内部类。
    - 静态成员类：
      - 可以访问外围类的所有成员（包括私有的成员）。
      - 静态成员类是外部类的一个静态成员，与其他静态成员遵守同样的可访问性规则（若静态成员类被声明为私有的，则只能在外部类的内部才能访问）。

    - 非静态成员类：
      - 非静态成员类的每个实例都隐含着与外围类的一个外围实例相关联；而静态成员类的实例可以在它外围类的实例之外独立存在。
      - 每个非静态内部类都将包含一个额外的指向外围对象的引用，保存这份引用需要消耗时间和空间，并且会导致外围实例在符合垃圾回收时却仍然得以保留。

    - 匿名内部类：
      - 第一种常见用法是动态地创建**函数对象**（如：Comparator）。
      - 第二种常见用法是创建**过程对象**（如：Runnable、Thread、TimerTask）。
      - 第三种常见用法是在静态工厂方法的内部。

    - 局部类：

## 10.5 泛型

泛型：声明中具有一个或者多个类型参数的类或接口，就是泛型类或者接口。

23. **请不要在新代码中使用原生态类型**

    - 原生态类型：即不带任何实际类型参数的泛型名称（如：List<E>相对应的原生态类型是List）。
    - 使用泛型的好处：
      - 可以在编译时就检查出错误（**若使用原生态类型，就会失掉泛型在安全性和表述性方面的所有优势**）。
      - 从集合中取出元素时不用进行手工转换了。

    - 无限制的通配符类型：？
    - "不要在新代码中使用原生态类型"，这条规则有两个例外：
      - 在**类文字**中必须使用原生态类型（如：List.class、String[].class、int.class都合法）。
      - 由于泛型信息会在运行时擦除，因此在参数化类型而非无限制通配符类型上使用**instanceof操作符**是非法的。

24. **消除非受检警告**
    - 要尽可能消除每一个非受检的警告。
    - 若你发现自己在长度不止一行的方法或构造器中使用了SuppressWarnings注解，可以将它移到一个局部变量的声明中；且每当使用SuppressWarnings("unchecked")注解时，都要添加一条注释，说明为什么这么做是安全的。

25. **列表优先于数组**
    - 数组与泛型对比：
      - 数组是协变的（如：若Sub是Super的子类型，则Sub[]是Super[]的子类型），而泛型是不可变的（如：List<Type1>既不是List<Type2>的子类型，也不是List<Type2>的超类型）。
      - 数组是具体化的，只有在运行时才知道并检查它们的元素类型约束；泛型只在编译时强化它们的类型信息，并在运行时丢弃其元素类型信息。因此，数组和泛型不能混用（**有一个例外，创建无限制通配符类型的数组是合法的**）。

26. **优先考虑泛型**
    - 重点是如何编写泛型类，可以参考类库中的一些类。

27. **优先考虑泛型方法**
    - 泛型方法调用时会进行类型推导。

28. **利用有限制通配符来提升API的灵活性**
    - E的某种子类型：如`? extends E`（生产者）。
    - E的某种超类：如`? super E`（消费者）。

29. **优先考虑类型安全的异构容器**
    - 









## 10.6 枚举和注解

30. 用enum代替int常量







31. 用实例域代替序数







32. 用EnumSet代替位域









33. 用EnumMap代替序数索引







34. 用接口模拟可伸缩的枚举











35. 注解优先于命名模式











36. 坚持使用Override注解









37. 用标记接口定义类型









## 10.7 方法

38. 检查参数的有效性









39. 必要时进行保护性拷贝









40. 谨慎设计方法签名











41. 慎用重载











42. 慎用可变参数











43. 返回零长度的数组或集合，而不是null







44. 为所有导出的API元素编写文档注释



















## 10.8 通用程序设计







## 10.9 异常









## 10.10 并发













## 10.11 序列化



















> 参考博客文章：[《Effective Java (2th)》]()

# 11 面试点总结

