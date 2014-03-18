犀利的比较器(Ordering)
===
Ordering是Guava类库提供的一个犀利强大的比较器工具，Guava的Ordering和JDK Comparator相比功能更强。它非常容易扩展，可以轻松构造复杂的comparator，然后用在容器的比较、排序等操作中。

#### 常用静态方法

```java  

Ordering.natural();                  // 使用Comparable类型的自然顺序， 例如：整数从小到大，字符串是按字典顺序;  
Ordering.usingToString();            // 使用toString()返回的字符串按字典顺序进行排序；
Ordering.from(Comparator);           // 将Comparator转换为Ordering
new Ordering<T>(){                   // 或者直接构建一个Ordering对象，并实现compare方法
	public int compare(T left, T right){}
}
```


#### 实例方法(支持链式)
com.google.common.collect.Ordering

```java   
reverse();                            //返回与当前Ordering相反的排序   
nullsFirst();                         //返回一个将null放在non-null元素之前的Ordering，其他的和原始的Ordering一样  
nullsLast();                          //返回一个将null放在non-null元素之后的Ordering，其他的和原始的Ordering一样  
compound(Comparator);                 //返回一个使用Comparator的Ordering，Comparator作为第二排序元素  
lexicographical();                    //返回一个按照字典元素迭代的Ordering  
onResultOf(Function);                 //将function应用在各个元素上之后, 在使用原始ordering进行排序  
greatestOf(Iterable iterable, int k); //返回指定的前k个可迭代的最大的元素，按照当前Ordering从最大到最小的顺序  
leastOf(Iterable iterable, int k);    //返回指定的前k个可迭代的最小的元素，按照当前Ordering从最小到最大的顺序  
isOrdered(Iterable);                  //是否有序(前面的元素可以大于或等于后面的元素)，Iterable不能少于2个元素
isStrictlyOrdered(Iterable);          //是否严格有序(前面的元素必须大于后面的元素)，Iterable不能少于两个元素  
sortedCopy(Iterable);                 //返回指定的元素作为一个列表的排序副本

```

------
[返回目录](/README.md)
