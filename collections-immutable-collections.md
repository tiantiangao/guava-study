不可变集合(Immutable collections)
===
不可变集合是不可被修改的, 集合的数据项是在创建的时候提供, 并且在整个生命周期中都不可改变.

Immutable对象有以下的优点:  

* 对不可靠的客户代码库来说，它使用安全，可以在未受信任的类库中安全的使用这些对象
* 线程安全的：immutable对象在多线程下安全，没有竞态条件
* 不需要支持可变性, 可以尽量节省空间和时间的开销. 所有的不可变集合实现都比可变集合更加有效的利用内存 (analysis)
* 可以被使用为一个常量，并且期望在未来也是保持不变的

Immutable对象是一个很好的防御编程(defensive programming)的技术实践

#### 问题
JDK自带的Collections.unmodifiableXXX实现的不是真正的不可变集合，当原始集合修改后，不可变集合也发生变化。

```java  
	List<String> lists = Lists.newArrayList("aa", "bb", "cc");

	List<String> unmodifiedLists = Collections.unmodifiableList(lists);
	assertEquals(3, unmodifiedLists.size());

	lists.add("dd");
	assertEquals(4, unmodifiedLists.size());
```  
  
JDK自带的Collections.unmodifiableXXX实现的不可变集合存在问题:  

* 它不安全: 如果有对象reference原始的被封装的集合类，这些方法返回的集合也就不是正真的不可改变
* 效率低: 因为它返回的数据结构本质仍旧是原来的集合类，所以它的操作开销，包括并发下修改检查，hash table里的额外数据空间都和原来的集合是一样的。


#### 方案
com.google.common.collect.ImmutableXXX

```java

```

------
[返回目录](README.md)
