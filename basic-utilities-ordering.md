犀利的比较器(Ordering)
===
Ordering是Guava类库提供的一个犀利强大的比较器工具，Guava的Ordering和JDK Comparator相比功能更强。它非常容易扩展，可以轻松构造复杂的comparator，然后用在容器的比较、排序等操作中。

#### 常用静态方法

```java  
Ordering.natural();        // 使用Comparable类型的自然顺序， 例如：整数从小到大，字符串是按字典顺序;  
Ordering.usingToString();  // 使用toString()返回的字符串按字典顺序进行排序；
Ordering.from(Comparator); // 将Comparator转换为Ordering
new Ordering<T>(){         // 或者直接构建一个Ordering对象，并实现compare方法
	public int compare(T left, T right){}
}
```


#### 实例方法(支持链式)
com.google.common.collect.Ordering


|| 方法 || 描述 ||
|| reverse() || 返回与当前Ordering相反的排序 ||
|| nullsFirst() || 返回一个将null放在non-null元素之前的Ordering，其他的和原始的Ordering一样 ||

------
[返回目录](README.md)
