强大的集合工具类
===
Guava提供了很多类似java.util.Collections的静态工具类

Guava中工具类与集合的对应关系如下:
<table>
<tr>
	<td>集合接口</td>
	<td>来自于JDK/Guava</td>
	<td>对应的Guava工具类</td>
</tr>
<tr>
	<td>Collection</td>
	<td>JDK</td>
	<td>Collections2</td>
</tr>
<tr>
	<td>List</td>
	<td>JDK</td>
	<td>Lists</td>
</tr>
<tr>
	<td>Set</td>
	<td>JDK</td>
	<td>Sets</td>
</tr>
<tr>
	<td>SortedSet</td>
	<td>JDK</td>
	<td>Sets</td>
</tr>
<tr>
	<td>Map</td>
	<td>JDK</td>
	<td>Maps</td>
</tr>
<tr>
	<td>SortedMap</td>
	<td>JDK</td>
	<td>Maps</td>
</tr>
<tr>
	<td>Queue</td>
	<td>JDK</td>
	<td>Queues</td>
</tr>
<tr>
	<td>Multiset</td>
	<td>Guava</td>
	<td>Multisets</td>
</tr>
<tr>
	<td>Multimap</td>
	<td>Guava</td>
	<td>Multimaps</td>
</tr>
<tr>
	<td>BiMap</td>
	<td>Guava</td>
	<td>Maps</td>
</tr>
<tr>
	<td>Table</td>
	<td>Guava</td>
	<td>Tables</td>
</tr>
</table>


* [静态工厂方法](#static-constructor)
* [Iterables](#iterables)
* [Lists](#lists)
* [Sets](#sets)
* [Maps](#maps)
* [Multisets](#multisets)
* [Multimaps](#multimaps)
* [Tables](#tables)

<h3 id="static-constructor">Static Constructor</h3>

JDK7之前构造一个集合
> List<TypeThatsTooLongForItsOwnGood> list = new ArrayList<TypeThatsTooLongForItsOwnGood>();

Guava提供了能够推断泛型的静态工厂方法
> List<TypeThatsTooLongForItsOwnGood> list = Lists.newArrayList();  
> List<String> theseElements = Lists.newArrayList("alpha", "beta", "gamma"); //可以直接初始化的静态构造方法  
> List<Type> exactly100 = Lists.newArrayListWithCapacity(100); //更具可读性的工厂方法  
> List<Type> approx100 = Lists.newArrayListWithExpectedSize(100); //更具可读性的工厂方法  
> Set<String> set = Sets.newHashSet();  
> Set<Type> approx100Set = Sets.newHashSetWithExpectedSize(100);  

<h3 id="iterables">Iterables</h3>

相比于Collection, Guava更偏向于提供Iterable类型, 原因就不写了，网上可以找到  
大部分的方法都在Iterators和FluentIterable中, 后者提供了很多链式操作  

Iterators常用方法
<table>
<tr>
	<td>方法</td>
	<td>描述</td>
	<td>参考</td>
</tr>
<tr>
	<td>concat(Iterable<Iterable>)</td>
	<td>串联多个iterables的懒加载视图</td>
	<td>concat(Iterable...)</td>
</tr>
<tr>
	<td>frequency(Iterable, Object)</td>
	<td>返回对象在iterable中出现的次数</td>
	<td>Collections.frequency(Collection, Object)</td>
</tr>
<tr>
	<td>partition(Iterable, int)</td>
	<td>把iterable按指定大小分割，得到的子集都不能进行修改操作</td>
	<td>Lists.partition(List, int);  paddedPartition(Iterable, int)</td>
</tr>
<tr>
	<td>getFirst(Iterable, T default)</td>
	<td>返回iterable的第一个元素，若iterable为空则返回默认值</td>
	<td>Iterable.iterator().next();  FluentIterable.first()</td>
</tr>
<tr>
	<td>getLast(Iterable)</td>
	<td>返回iterable的最后一个元素，若iterable为空则抛出NoSuchElementException</td>
	<td>getLast(Iterable, T default);  FluentIterable.last()</td>
</tr>
<tr>
	<td>elementsEqual(Iterable, Iterable)</td>
	<td>如果两个iterable中的所有元素相等且顺序一致，返回true</td>
	<td>List.equals(Object)</td>
</tr>
<tr>
	<td>unmodifiableIterable(Iterable)</td>
	<td>返回iterable的不可变视图</td>
	<td>Collections.unmodifiableCollection(Collection)</td>
</tr>
<tr>
	<td>limit(Iterable, int)</td>
	<td>返回一个尽可能达到指定个数的iterable</td>
	<td>FluentIterable.limit(int)</td>
</tr>
<tr>
	<td>getOnlyElement(Iterable)</td>
	<td>获取iterable中唯一的元素，如果iterable为空或有多个元素，则失败</td>
	<td>getOnlyElement(Iterable, T default)</td>
</tr>
</table>

Iterators中也有很多和Collections相似的工具方法，比如addAll、removeAll、retainAll、contains、size、isEmpty等

<h3 id="lists">Lists</h3>

```java  
// 静态工厂方法  
Lists.newArrayList();  
Lists.newArrayList(1, 2, 3);  
Lists.newArrayList(Sets.newHashSet(1, 2, 3));  
Lists.newArrayListWithCapacity(10);  
Lists.newArrayListWithExpectedSize(10);

Lists.newLinkedList();
Lists.newLinkedList(Sets.newHashSet(1, 2, 3));

// 其他工具方法 
Lists.partition(Lists.newArrayList(1, 2, 3, 4, 5), 2);
Lists.reverse(Lists.newArrayList(1, 2, 3, 4, 5));
```

<h3 id="sets">Sets</h3>

```java  
// 静态工厂方法  
Sets.newHashSet();  
Sets.newHashSet(1, 2, 3);  
Sets.newHashSetWithExpectedSize(10);  
Sets.newHashSet(Lists.newArrayList(1, 2, 3));  

Sets.newLinkedHashSet();  
Sets.newLinkedHashSetWithExpectedSize(10);  
Sets.newLinkedHashSet(Lists.newArrayList(1, 2, 3));  

Sets.newTreeSet();  
Sets.newTreeSet(Lists.newArrayList(1, 2, 3));  
Sets.newTreeSet(Ordering.natural());  

// 集合运算(返回SetView)  
Sets.union(Sets.newHashSet(1, 2, 3), Sets.newHashSet(4, 5, 6)); // 取并集[1,2,3,4,5,6]  
Sets.intersection(Sets.newHashSet(1, 2, 3), Sets.newHashSet(3, 4, 5)); // 取交集[3]  
Sets.difference(Sets.newHashSet(1, 2, 3), Sets.newHashSet(3, 4, 5)); // 只在set1, 不在set2[1,2]  
Sets.symmetricDifference(Sets.newHashSet(1, 2, 3), Sets.newHashSet(3, 4, 5)); // 交集取反[1,2,4,5]  

// 其他工具方法  
Sets.cartesianProduct(Lists.newArrayList(Sets.newHashSet(1, 2), Sets.newHashSet(3, 4))); // 返回所有集合的笛卡尔积  
Sets.powerSet(Sets.newHashSet(1, 2, 3)); // 返回给定集合的所有子集
```

<h3 id="maps">Maps</h3>

Maps除了类似Lists、Sets一样提供基本的静态工厂方法外，还提供了很多其他有意思的方法  

#### uniqueIndex

场景：有一组对象，它们在某个属性上分别有独一无二的值，而我们希望能够按照这个属性值查找对象

> Maps.uniqueIndex(Iterable,Function)
> 这个方法返回一个Map，键为Function返回的属性值，值为Iterable中相应的元素，因此我们可以反复用这个Map进行查找操作。

示例:
```java  
ImmutableMap<Integer, String> stringsByIndex = Maps.uniqueIndex(strings, new Function<String, Integer> () {
    public Integer apply(String string) {
        return string.length();
    }
});
  
```
如果索引值不是独一无二的，请参见下面的Multimaps.index方法。

#### difference

Maps.difference(Map, Map)用来比较两个Map以获取所有不同点, 该方法返回MapDifference对象

```java  
Map<String, Integer> left = ImmutableMap.of("a", 1, "b", 2, "c", 3);
Map<String, Integer> right = ImmutableMap.of("b", 2, "c", 4, "d", 5);
MapDifference<String, Integer> diff = Maps.difference(left, right);

diff.entriesInCommon(); // {"b" => 2}, 两个Map中都有的映射项，包括键与值
diff.entriesDiffering(); // {"c" => (3, 4)}, 键相同但是值不同的映射项。  
                         // 返回的Map的值类型为MapDifference.ValueDifference，以表示左右两个不同的值
diff.entriesOnlyOnLeft(); // {"a" => 1}, 键只存在于左边Map的映射项
diff.entriesOnlyOnRight(); // {"d" => 5}, 键只存在于右边Map的映射项

```


<h3 id="multisets">Multisets</h3>

<h3 id="multimaps">Multimaps</h3>

<h3 id="tables">Tables</h3>


------
[返回目录](/README.md)