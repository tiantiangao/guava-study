集合扩展工具类
===
有时候你需要实现自己的集合扩展。也许你想要在元素被添加到列表时增加特定的行为，或者你想实现一个Iterable，其底层实际上是遍历数据库查询的结果集。Guava提供了若干工具方法，以便让类似的工作变得更简单。


#### Forwarding Decorators
针对所有类型的集合接口，Guava都提供了Forwarding抽象类以简化*装饰者模式*的使用。  
Forwarding抽象类定义了一个抽象方法：delegate()，你可以覆盖这个方法来返回被装饰对象。所有其他方法都会直接委托给delegate()。  
通过创建ForwardingXXX的子类并实现delegate()方法，可以选择性地覆盖子类的方法来增加装饰功能，而不需要自己委托每个方法。  
此外，很多集合方法都对应一个”标准方法[standardxxx]“实现，可以用来恢复被装饰对象的默认行为，比如standardAdd  

示例:  
```java  
class AddLoggingList<E> extends ForwardingList<E> {  
  final List<E> delegate; // backing list  
  @Override  
  protected List<E> delegate() {  
    return delegate;  
  }  
  @Override  
  public void add(int index, E elem) {  
    log(index, elem);  
    super.add(index, elem);  
  }  
  @Override  
  public boolean add(E elem) {  
    return standardAdd(elem); // implements in terms of add(int, E)  
  }  
  @Override  
  public boolean addAll(Collection<? extends E> c) {  
    return standardAddAll(c); // implements in terms of add  
  }  
}  

```

目前提供了Forwarding包装类的接口有:
ForwardingCollection、ForwardingList、ForwardingSet、ForwardingSortedSet、ForwardingMap、ForwardingSortedMap、ForwardingConcurrentMap、ForwardingMapEntry、ForwardingQueue、ForwardingIterator、ForwardingListIterator、ForwardingMultiset、ForwardingMultimap、ForwardingListMultimap、ForwardingSetMultimap

#### PeekingIterator

##### AbstractIterator

------
[返回目录](/README.md)
