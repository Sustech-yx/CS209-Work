# Lab3 Answer

### Question 1

对于为什么预期结果和实际结果不同，原因在于HashMap中通过hashCode()方法来判断这个键的值是否在Map中存在。
在Encoding中没有声明hashCode()方法，那么计算hash值使用的就是Object中默认的方法，即这个对象中在虚拟机中内存地址hash值的十六进制表示。
```java
public V get(Object key) {
        Node<K,V> e;
        return (e = getNode(hash(key), key)) == null ? null : e.value;
}
```

在第二次插入 1->a=1 时，两个Encoding对象的hash值实际上是不相同的，致使结果和预期不符。


### Question 2

在 *doSomeOperations* 操作后，虽然改变了一些键中成员的值，但是没有改变键在HashMap中的hash值。也就是说，被修改的键仍然维持修改前的hash值。致使结果和预期不符。
使用HashMap所提供的compute()或是computeIfPresent()方法来修改key或value的成员，同时重新计算key的hashCode。

### Question 3

并不会出现前两个example中所出现的问题，原因是：

1. 用合理的方式计算键的hashCode。
2. 将键的成员声明为final，使之无法被修改。