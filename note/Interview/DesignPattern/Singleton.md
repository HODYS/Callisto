# 单例模式(Singleton Pattern)
直接看这个:
[如何正确地写出单例模式](http://wuchong.me/blog/2014/08/28/how-to-correctly-write-singleton-pattern/)

大概总结一下以上博文的内容：
单例模式：确保一个类只有一个实例。

实现方式
+ 懒汉式（线程不安全）
懒加载（lazy initialization），线程不安全
```java
public class Singleton {
    private static Singleton instance;
    private Singleton() {}

    public static Singleton getInstance() {
        if(instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
```
+ 懒汉式（线程安全）
解决了线程安全问题，但是效率太低了，因为只有在第一次创建单例实例对象的时候才存在线程安全问题。
```java
public class Singleton {
    private static Singleton instance;
    private Singleton() {}

    public static synchronized Singleton getInstance() {
        if(instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
```

+ 懒汉式（双重检验锁）
双重检验锁模式（double checked locking pattern），是一种使用同步块加锁的方法。程序员称其为双重检查锁，因为会有两次检查 instance == null，一次是在同步块外，一次是在同步块内。为什么在同步块内还要再检验一次？因为可能会有多个线程一起进入同步块外的 if，如果在同步块内不进行二次检验的话就会生成多个实例了。
```java
public class Singleton {
    private static Singleton instance;
    private Singleton {}

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                } 
            }
        }
        return instance;
    }
}
```
虽然看起来完美，实际上仍然存在问题，因为创建新实例并赋值给instance的过程并不是原子的。
事实上在 JVM 中这句话大概做了下面 3 件事情。

1. 给 instance 分配内存
2. 调用 Singleton 的构造函数来初始化成员变量
3. 将instance对象指向分配的内存空间（执行完这步 instance 就为非 null 了）
但是在 JVM 的即时编译器中存在指令重排序的优化。也就是说上面的第二步和第三步的顺序是不能保证的，最终的执行顺序可能是 1-2-3 也可能是 1-3-2。如果是后者，则在 3 执行完毕、2 未执行之前，被线程二抢占了，这时 instance 已经是非 null 了（但却没有初始化），所以线程二会直接返回 instance，然后使用，然后顺理成章地报错。

+ 懒汉式（双重检验锁 + volatile）
将instance声明为volatile，利用volatile禁止指令重排序优化的特性，禁止2、3之间重排序（这是艺术里面说的，需要jdk5以上版本）。
```java
public class Singleton {
    private static volatile Singleton instance;
    private Singleton {}

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                } 
            }
        }
        return instance;
    }
}
```
这里稍微延申一下volatile的问题。volatile的两层语义，一是可见性，对volatile变量修改值，volatile关键字会强制要求立即写入主存，使其他线程的工作内存中的值无效，其他线程必须去主存中读取新值。二是禁止指令重排序，具体来说是，禁止volatile读之后的操作重排序到volatile读之前，禁止volatile写之前的操作重排序到volatile之后，volatile读和v写之间不会发生重排序。具体到内存屏障层面的实现可以见[java并发编程(二)-volatile写操作前为什么不加LoadStore屏障](https://blog.csdn.net/ly262173911/article/details/106063924)，或者见Multi-thread那一篇。

+ 饿汉式
类加载的时候就进行初始化，非懒加载，当Singleton实例创建需要一定配置与参数的时候，这种方式不可行。
```java
public class Singleton {
    private static final Singleton instance = new Singleton();

    private Singleton() {}

    public static Singleton getInstance() {
        return instance;
    }
}
```

+ 静态内部类
这种方式既实现了懒加载（静态内部类不会在外部类加载的时候进行加载，只有在调用Singleton.getInstance() 时才会初始化），同时利用类中静态变量的唯一性，保证了instance的唯一性。优点是懒加载而且效率比synchronized更高。
```java
public class Singleton {
    private static class SingletonHolder {
        private static final Singleton INSTANCE = new Singleton();
    }
    private Singleton() {}

    public static Singleton getInstance() {
        return SingleHolder.INSTANCE
    }
}
```

+ 枚举Enum
创建枚举默认就是线程安全的，所以不需要担心double checked locking，而且还能防止反序列化导致重新创建新的对象。
```java
public enum Singleton {
    INSTANCE;
} 
```


+ 单例模式在JDK8源码中的使用

当然JDK源码中使用了大量的设计模式，那哪些地方使用了单例设计模式呢？

Runtime类部分源码如下
```java
//饿汉式单例设计模式
public class Runtime {
    private static Runtime currentRuntime = new Runtime();
 
    public static Runtime getRuntime() {
        return currentRuntime;
    }
 
    private Runtime() {
    }
 
    //省略很多行
}
```