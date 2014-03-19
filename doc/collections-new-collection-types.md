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

一般来说，Multimap接口应该用第一种方式看待，但asMap()视图返回Map<K, Collection<V>>，让你可以按另一种方式看待Multimap。  
重要的是，不会有任何键映射到空集合：一个键要么至少到一个值，要么根本就不在Multimap中。  
一般情况下都使用ListMultimap或SetMultimap接口，它们分别把键映射到List或Set。  
Multimap.get(key)以集合形式返回键所对应的值视图, 即使没有任何对应的值，也会返回空集合。对值视图集合进行的修改最终都会反映到底层的Multimap。  

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

<h3 id="table">Table</h3>

<h3 id="classtoinstancemap">ClassToInstanceMap</h3>

<h3 id="rangeset">RangeSet</h3>



------
[返回目录](/README.md)