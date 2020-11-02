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























> 参考文章博客：[JDK里的设计模式](https://stackoverflow.com/questions/1673841/examples-of-gof-design-patterns-in-javas-core-libraries)、[HeadFirst设计模式]()

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











# 3 IO/BIO/NIO



















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











# 6 MySQL调优

















# 7 微服务













# 8 算法解题









# 9 JVM













> 参考博客文章：[周志明-深入理解Java虚拟机]()

# 10 Netty







# 11 面试点总结

