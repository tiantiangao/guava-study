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

##### 创建Immutable集合的方法
```java
ImmutableSet.copyOf(set);           // 使用copyOf方法
ImmutableSet.of("a", "b", "c");     // 使用of方法
ImmutableMap.of("a", 1, "b", 2);    // 使用of方法
ImmutableSet.<Color>builder()       // 使用builder 
            .add(new Color(0, 255, 255))
            .add(new Color(0, 191, 255))
            .build();
```

##### 智能的copyOf方法  
一般来说，ImmutableXXX.copyOf(ImmutableCollection)会避免线性复杂度的拷贝操作:

* 这个操作有可能就利用了被封装数据结构的常数复杂度的操作。但例如ImmutableSet.copyOf(list)不能在常数复杂度下实现。
* 这样不会导致内存泄漏－例如，你有个ImmutableList<String> imInfolist，然后你显式操作ImmutableList.copyOf(imInfolist.subList(0, 10))。这样的操作可以避免意外持有不再需要的在hugeList里元素的reference。
* 它不会改变集合的语意－像ImmutableSet.copyOf(myImmutableSortedSet)这样的显式拷贝操作，因为在ImmutableSet里的hashCode()和equals()的含义和基于comparator的ImmutableSortedSet是不同的。  

这些特性有助于最优化防御性编程的性能开销

##### asList方法
所有的immutable集合都以asList()的形式提供了ImmutableList视图(view).  
比如，你把数据放在ImmutableSortedSet，你就可以调用sortedSet.asList().get(k)来取得第k个元素的集合。  
返回的ImmutableList常常是个常数复杂度的视图，而不是一个真的拷贝。  

##### guava中的不可变集合

<table>
<tbody>
<tr>
	<td>可变类型集合</td>
	<td>来源</td>
	<td>Guava中的不可变集合</td>
</tr>
<tr>
	<td>Collection </td>
	<td>JDK</td>
	<td>ImmutableCollection</td>
</tr>
<tr>
	<td>List</td>
	<td>JDK</td>
	<td>ImmutableList</td>
</tr>
<tr>
	<td>Set</td>
	<td>JDK</td>
	<td>ImmutableSet</td>
</tr>
<tr>
	<td>SortedSet/NavigableSet</td>
	<td>JDK</td>
	<td>ImmutableSortedSet</td>
</tr>
<tr>
	<td>Map</td>
	<td>JDK</td>
	<td>ImmutableMap</td>
</tr>
<tr>
	<td>SortedMap</td>
	<td>JDK</td>
	<td>ImmutableSortedMap</td>
</tr>
<tr>
	<td>Multiset</td>
	<td>Guava</td>
	<td>ImmutableMultiset</td>
</tr>
<tr>
	<td>SortedMultiset</td>
	<td>Guava</td>
	<td>ImmutableSortedMultiset</td>
</tr>
<tr>
	<td>Multimap</td>
	<td>Guava</td>
	<td>ImmutableMultimap</td>
</tr>
<tr>
	<td>ListMultimap</td>
	<td>Guava</td>
	<td>ImmutableListMultimap</td>
</tr>
<tr>
	<td>SetMultimap</td>
	<td>Guava</td>
	<td>ImmutableSetMultimap</td>
</tr>
<tr>
	<td>BiMap</td>
	<td>Guava</td>
	<td>ImmutableBiMap</td>
</tr>
<tr>
	<td>ClassToInstanceMap</td>
	<td>Guava</td>
	<td>ImmutableClassToInstanceMap</td>
</tr>
<tr>
	<td>Table</td>
	<td>Guava</td>
	<td>ImmutableTable</td>
</tr>
</tbody>
</table>

------
[返回目录](/README.md)
