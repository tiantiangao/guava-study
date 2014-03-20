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

<h3 id="sets">Sets</h3>

<h3 id="maps">Maps</h3>

<h3 id="multisets">Multisets</h3>

<h3 id="multimaps">Multimaps</h3>

<h3 id="tables">Tables</h3>


------
[返回目录](/README.md)