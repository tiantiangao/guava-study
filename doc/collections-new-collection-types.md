Guava新增集合类型
===
Guava新增了一些JDK中没有的，但是被广泛使用到的新集合类型

* [Multiset](#multiset)
* [SortedMultiset](#sortedmultiset)
* [MultiMap](#multimap)
* [BiMap](#bimap)
* [Table](#table)
* [ClassToInstanceMap](#classtoinstancemap)
* [RangeSet](#rangeset)

<h3 id="multiset">Multiset</h3>

Multiset和Set的区别就是可以保存多个相同的对象。  
Multiset占据了List和Set之间的一个灰色地带：允许重复，但是不保证顺序。  
常见使用场景：Multiset有一个有用的功能，就是跟踪每种对象的数量，所以你可以用来进行数字统计。  

Multiset接口定义的接口主要有：

* add(E element): 向其中添加单个元素
* add(E element,int occurrences): 向其中添加指定个数的元素
* count(Object element): 返回给定参数元素的个数
* remove(E element): 移除一个元素，其count值 会响应减少
* remove(E element,int occurrences): 移除相应个数的元素
* elementSet(): 将不同的元素放入一个Set中
* entrySet(): 类似与Map.entrySet 返回Set<Multiset.Entry>。包含的Entry支持使用getElement()和getCount()
* setCount(E element ,int count): 设定某一个元素的重复次数
* setCount(E element,int oldCount,int newCount): 将符合原有重复个数的元素修改为新的重复次数
* retainAll(Collection c): 保留出现在给定集合参数的所有的元素
* removeAll(Collectionc): 去除出现给给定集合参数的所有的元素

Guava提供了很多和JDK中的Map对应的Multiset的实现
<table>
<tr>
	<td>Map</td>
	<td>对应的MultiSet</td>
	<td>支持null值</td>
</tr>
<tr>
	<td>HashMap</td>
	<td>HashMultiset</td>
	<td>是</td>
</tr>
<tr>
	<td>TreeMap</td>
	<td>TreeMultiSet</td>
	<td>是</td>
</tr>
<tr>
	<td>LinkedHashMap</td>
	<td>LinkedHashMultiset</td>
	<td>是</td>
</tr>
<tr>
	<td>ConcurrentHashMap</td>
	<td>ConcurrentHashMultiset</td>
	<td>否</td>
</tr>
<tr>
	<td>ImmutableMap</td>
	<td>ImmutableMultiset</td>
	<td>否</td>
</tr>
</table>

<h3 id="sortedmultiset">SortedMultiset</h3>

SortedMultiset是Multiset 接口的变种，它支持高效地获取指定范围的子集。  
比如，你可以用 latencies.subMultiset(0,BoundType.CLOSED, 100, BoundType.OPEN).size()来统计你的站点中延迟在100毫秒以内的访问，然后把这个值和latencies.size()相比，以获取这个延迟水平在总体访问中的比例。

TreeMultiset实现SortedMultiset接口。

<h3 id="multimap">MultiMap</h3>

经常会遇到这种结构 Map<K, List<V>>或Map<K, Set<V>>  
Multimap可以很容易地把一个键映射到多个值。换句话说，Multimap是把键映射到任意多个值的一种方式。

可以用两种方式思考Multimap的概念:  

* "键-单个值映射"的集合:  a->1, a->2, a->4, b->3, c->5
* "键-值集合映射"的映射:  a->[1,2,4], b->3, c->5

一般情况下都会使用ListMultimap或SetMultimap接口，它们分别把键映射到List或Set。  
Multimap.get(key)以集合形式返回键所对应的值视图, 即使没有任何对应的值，也会返回空集合。  
对值视图集合进行的修改最终都会反映到底层的Multimap。  

##### 修改Multimap的方法有:
<table>
<tr>
	<td>方法签名</td>
	<td>描述</td>
	<td>等价于</td>
</tr>
<tr>
	<td>put(K, V)</td>
	<td>添加键到单个值的映射</td>
	<td>multimap.get(key).add(value)</td>
</tr>
<tr>
	<td>putAll(K, Iterable<V>)</td>
	<td>依次添加键到多个值的映射</td>
	<td>Iterables.addAll(multimap.get(key), values)</td>
</tr>
<tr>
	<td>remove(K, V)</td>
	<td>移除键到值的映射；如果有这样的键值并成功移除，返回true。</td>
	<td>multimap.get(key).remove(value)</td>
</tr>
<tr>
	<td>removeAll(K)</td>
	<td>清除键对应的所有值，返回的集合包含所有之前映射到K的值，但修改这个集合就不会影响Multimap了。</td>
	<td>multimap.get(key).clear()</td>
</tr>
<tr>
	<td>replaceValues(K, Iterable<V>)</td>
	<td>清除键对应的所有值，并重新把key关联到Iterable中的每个元素。返回的集合包含所有之前映射到K的值。</td>
	<td>multimap.get(key).clear();   Iterables.addAll(multimap.get(key), values)</td>
</tr>
</table>

##### Multimap不是Map
Multimap<K, V>不是Map<K,Collection<V>>

* Multimap.get(key)总是返回非null、但是可能空的集合。这并不意味着Multimap为相应的键花费内存创建了集合，而只是提供一个集合视图方便你为键增加映射值
* 如果你更喜欢像Map那样，为Multimap中没有的键返回null，请使用asMap()视图获取一个Map<K, Collection<V>>
* 当且仅当有值映射到键时，Multimap.containsKey(key)才会返回true
* Multimap.entries()返回Multimap中所有”键-单个值映射”——包括重复键。如果你想要得到所有”键-值集合映射”，请使用asMap().entrySet()。
* Multimap.size()返回所有”键-单个值映射”的个数，而非不同键的个数。要得到不同键的个数，请改用Multimap.keySet().size()。

<h3 id="bimap">BiMap</h3>

BiMap提供了key和value双向关联的数据结构。

* 可以用inverse()反转BiMap<K, V>的键值映射, 反转的map不是新的map对象，它实现了一种视图关联，这样你对于反转后的map的所有操作都会影响原先的map对象
* 保证值是唯一的，因此 values()返回Set而不是普通的Collection
* 如果你想把键映射到已经存在的值，会抛出IllegalArgumentException异常, 使用BiMap.forcePut(key, value)可强制替换

```java  
BiMap<Integer,String> logfileMap = HashBiMap.create(); 
BiMap<String,Integer> filelogMap = logfileMap.inverse();
```

<h3 id="table">Table</h3>

当我们需要多个索引的数据结构的时候，通常情况下，我们只能用这种丑陋的Map<FirstName, Map<LastName, Person>>来实现。为此Guava提供了一个新的集合类型－Table集合类型，来支持这种数据结构的使用场景。  

##### Table的视图
<table>
<tr>
	<td>视图</td>
	<td>描述</td>
</tr>
<tr>
	<td>rowMap()</td>
	<td>用Map<R, Map<C, V>>表现Table<R, C, V></td>
</tr>
<tr>
	<td>rowKeySet()</td>
	<td>rowKeySet()返回”行”的集合Set<R></td>
</tr>
<tr>
	<td>row(r) </td>
	<td>用Map<C, V>返回给定”行”的所有列，对这个map进行的写操作也将写入Table中。</td>
</tr>
<tr>
	<td>columnMap()</td>
	<td>用Map<C, Map<R, V>>表现Table<R, C, V></td>
</tr>
<tr>
	<td>columnKeySet()</td>
	<td>columnKeySet()返回”列”的集合Set<R></td>
</tr>
<tr>
	<td>column(r) </td>
	<td>用Map<R, V>返回给定”列”的所有行，对这个map进行的写操作也将写入Table中。</td>
</tr>
<tr>
	<td>cellSet()</td>
	<td>用元素类型为Table.Cell<R, C, V>的Set表现Table<R, C, V>。Cell类似于Map.Entry，但它是用行和列两个键区分的。</td>
</tr>
</table>
注: 基于列的访问会比基于的行访问稍微低效点  

Table有如下实现
<table>
<tr>
	<td>实现</td>
	<td>描述</td>
</tr>
<tr>
	<td>HashBasedTable</td>
	<td>本质上用HashMap<R, HashMap<C, V>>实现</td>
</tr>
<tr>
	<td>TreeBasedTable</td>
	<td>本质上用TreeMap<R, TreeMap<C,V>>实现</td>
</tr>
<tr>
	<td>ImmutableTable</td>
	<td>本质上用ImmutableMap<R, ImmutableMap<C, V>>实现；注：ImmutableTable对稀疏或密集的数据集都有优化。</td>
</tr>
<tr>
	<td>ArrayTable</td>
	<td>要求在构造时就指定行和列的大小，本质上由一个二维数组实现，以提升访问速度和密集Table的内存利用率。</td>
</tr>
</table>

<h3 id="classtoinstancemap">ClassToInstanceMap</h3>

ClassToInstanceMap\<B> 相当于 Map<Class<? extends B>, B>, 它的键是类型，而值是符合键所指类型的对象。
ClassToInstanceMap额外声明了两个方法：T getInstance(Class<T>) 和T putInstance(Class<T>, T)，从而避免强制类型转换，同时保证了类型安全。  


<h3 id="rangeset">RangeSet</h3>

RangeSet描述了一组不相连的、非空的区间。当把一个区间添加到可变的RangeSet时，所有相连的区间会被合并，空区间会被忽略。  
例如：  
```java  
RangeSet<Integer> rangeSet = TreeRangeSet.create();  
rangeSet.add(Range.closed(1, 10)); // {[1, 10]}  
rangeSet.add(Range.closedOpen(11, 15)); // 不相连的区间: {[1, 10], [11, 15)} 
rangeSet.add(Range.closedOpen(15, 20)); // 相连的区间; {[1, 10], [11, 20)}
rangeSet.add(Range.openClosed(0, 0)); // 空区间; {[1, 10], [11, 20)}
rangeSet.remove(Range.open(5, 10)); // 分割[1, 10]; {[1, 5], [10, 10], [11, 20)}
```

RangeMap描述了"不相交的、非空的区间"到特定值的映射。和RangeSet不同，RangeMap不会合并相邻的映射，即便相邻的区间映射到相同的值。例如：
```java  
RangeMap<Integer, String> rangeMap = TreeRangeMap.create();
rangeMap.put(Range.closed(1, 10), "foo"); // {[1, 10] => "foo"}
rangeMap.put(Range.open(3, 6), "bar"); // {[1, 3] => "foo", (3, 6) => "bar", [6, 10] => "foo"}
rangeMap.put(Range.open(10, 20), "foo"); // {[1, 3] => "foo", (3, 6) => "bar", [6, 10] => "foo", (10, 20) => "foo"}
rangeMap.remove(Range.closed(5, 11)); // {[1, 3] => "foo", (3, 5) => "bar", (11, 20) => "foo"}
```

------
[返回目录](/README.md)