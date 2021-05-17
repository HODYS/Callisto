# HashMap

## HashMap的底层实现
java7和java8在实现HashMap上有所区别，当然java8的效率要更好一些，主要是java8的HashMap在java7的基础上增加了红黑树这种数据结构，使得在桶里面查找数据的复杂度从O(n)降到O(logn)，当然还有一些其他的优化，比如resize的优化等。

而HashMap则更加关注hash的计算效率问题。在取模计算时，如果模数是2的幂，那么我们可以直接使用位运算来得到结果，效率要大大高于做除法。HashMap为了加快hash的速度，将哈希表的大小固定为了2的幂。
## 1.7版本

## 1.8版本

## 两者对比
+ 底层数据结构
1.7是数组+链表，1.8则是数组+链表+红黑树结构（当链表长度大于8，转为红黑树）
+ 数据插入
1.7中新增节点采用头插法，1.8中新增节点采用尾插法。这也是为什么1.8不容易出现环型链表的原因。1.7在多线程环境容易出现环形链表。
+ 初始化方式
JDK1.8中resize()方法在表为空时，创建表；在表不为空时，扩容；而JDK1.7中resize()方法负责扩容，inflateTable()负责创建表。
+ hash值的计算
定义了hash方法，使用key的hashcode进行一系列运算。
1.8简化了的hash值的计算，只有2次扰动处理（1次位运算1次异或）
1.7有9次扰动（4次位运算+5次亦或）
```java
//1.8
static final int hash(Object key) { // 计算key的hash值
    int h;
    // 1.先拿到key的hashCode值; 2.将hashCode的高16位参与运算
    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    
}
```
```java
//1.7
final int hash(Object k) {
        int h = hashSeed;
        if (0 != h && k instanceof String) {//这里针对String优化了Hash函数，是否使用新的Hash函数和Hash因子有关  
            return sun.misc.Hashing.stringHash32((String) k);
        }

        h ^= k.hashCode();

        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }
```

+ 扩容
1.7扩容的时候，hash需要重新按照原来的方式计算，表的length扩大到两倍。1.8在扩容的时候，经过rehash之后，元素的位置要么是在原位置，要么是在原位置再移动2次幂的位置。因此，我们在扩充HashMap的时候，不需要像JDK1.7的实现那样重新计算hash，只需要看看原来的hash值新增的那个bit是1还是0就好了，是0的话索引没变，是1的话索引变成“原索引+oldCap”。这里的具体过程可见[Java HashMap中在resize()时候的rehash,即再哈希法的理解](https://blog.csdn.net/sybnfkn040601/article/details/73194559)

## Hashmap与HashTable的区别
详细见[源码分析hashmap与hashtable的区别](https://blog.csdn.net/weixin_47294072/article/details/107550919)
最主要的区别其实是，前者不是线程安全，后者对每个方式都使用syncrhonized关键字实现了线程安全， 但是全局只有一把锁，在线程竞争比较激烈的情况下效率太低了。因为当一个线程访问hashtable的同步方法时，其他线程再次尝试访问的时候，会进入阻塞或者轮询状态，比如当线程1使用put进行元素添加的时候，线程2不但不能使用put来添加元素，而且不能使用get获取元素。

## 为什么HashMap线程不安全



## Hashcode的一点小知识
hashCode就是对象的散列码，是根据对象的某些信息推导出的一个整数值，默认情况下表示是对象的存储地址。通过散列码，可以提高检索的效率，主要用于在散列存储结构中快速确定对象的存储地址，如Hashtable、hashMap中。

hashCode() 定义在 JDK 的 Object 类中，这就意味 着 Java 中的任何类都包含有 hashCode() 函数。另外需要注意的是： Object 的 hashcode ⽅法 是本地⽅法，也就是⽤ c 语⾔或 c++ 实现的，该⽅法通常⽤来将对象的 内存地址 转换为整数之 后返回。

equals()与hashCode()的联系：
Java的超类Object类已经定义了equals()和hashCode()方法，在Obeject类中，equals()比较的是两个对象的内存地址是否相等，而hashCode()返回的是对象的内存地址。所以hashCode主要是用于查找使用的，而equals()是用于比较两个对象是否相等的。但有时候我们根据特定的需求，可能要重写这两个方法，在重写这两个方法的时候，主要注意保持一下几个特性：

（1）如果两个对象的equals()结果为true，那么这两个对象的hashCode一定相同；

（2）两个对象的hashCode()结果相同，并不能代表两个对象的equals()一定为true，只能够说明这两个对象在一个散列存储结构中。

（3）如果对象的equals()被重写，那么对象的hashCode()也要重写。
为什么？如果equals重写了，但不重写hashcode，可能会出现equals为true但是hashcode不相等的问题，比如在hashset里面会数据不唯一。因为没有重写hashcode，使用的是object类中的hashcode方法，比较的是对象的内存地址。详见[Java面试宝典之：hashCode与equals](https://blog.csdn.net/weixin_44259720/article/details/95599407)，里面还提到了hashset内存泄露的问题hhh。

为什么两个对象有相同的 hashcode 值，它们也不⼀定是相等的？
因为可能有哈希冲突（所谓碰撞（冲突）也就是指的是不同的对象得到相同 的 hashCode ）

我们刚刚也提到了 HashSet ,如果 HashSet 在对⽐的时候，同样的 hashcode 有多个对象，它会 使⽤ equals() 来判断是否真的相同。也就是说 hashcode 只是⽤来缩⼩查找成本。

## 一些问题
** 如果已知所需容量，如何设置hashmap的初始capacity？
initialCapacity = desiredCapacity / loadFactor + 1
见[java中hashmap容量的初始化](https://www.cnblogs.com/yanggb/p/11613070.html)
