Guava新增集合类型
===
Guava新增了一些JDK中没有的，但是被广泛使用到的新集合类型

* [MultiSet](#multiset)
* [MultiMap](#multimap)
* [BiMap](#bimap)
* [Table](#table)
* [ClassToInstanceMap](#classtoinstancemap)
* [RangeSet](#rangeset)

<h4 id="MultiSet">MultiSet</h4>
MultiSet和Set的区别就是可以保存多个相同的对象。  
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

<h4 id="MultiMap">MultiMap</h4>

<h4 id="BiMap">BiMap</h4>

<h4 id="Table">Table</h4>

<h4 id="ClassToInstanceMap">ClassToInstanceMap</h4>

<h4 id="RangeSet">RangeSet</h4>



------
[返回目录](/README.md)