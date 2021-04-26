## LearnFromOthers - Java
1. 用stream
2.  Map的Merge
它将新的值赋值到 key （如果不存在）或更新给定的key 值对应的 value。详见这一篇[Java 8 中 Map 骚操作之 merge() 的用法](https://zhuanlan.zhihu.com/p/86255471)
```java
 default V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction)
```
3. map是有pubIfAbsent等系列方法的
总之对java8的运用可以再加强一下