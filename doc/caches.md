缓存
===

#### 什么是缓存
缓存你懂的，memcached用过没？ehcache用过没？内存Map总该用过吧...

当计算或检索一个值的代价很高，并且对同样的输入需要不止一次获取值的时候，就应当考虑使用缓存。这下懂了吧

换句话说，缓存就是以空间换时间

#### 问题
内存Map会一直保存所有添加的元素, 直到显示地移除, 所以会一直占用内存  
而Guava Cache为了限制内存使用，通常都设定为自动回收元素。  

由于存放于内存中，Guava Cache不适合存放过大的数据，数据量较大时，可以尝试使用 Memcached 等

#### 主要流程
get-if-absent-compute
尝试获取缓存，如果不存在，则计算

#### 缓存加载
当缓存不存在时，guava提供了多种方式来加载数据

##### CacheLoader

LoadingCache是一种基于CacheLoader的缓存实现. 

```java  
LoadingCache<Key, Graph> graphs = CacheBuilder.newBuilder()  
        .maximumSize(1000)
        .build(
            new CacheLoader<Key, Graph>() {
                public Graph load(Key key) throws AnyException {
                    return createExpensiveGraph(key);
                }
            });

...
try {
    return graphs.get(key);
} catch (ExecutionException e) {
    throw new OtherException(e.getCause());
}
```
使用LoadingCache.get(K)方法可以获取缓存中对应的值，如果没有缓存，则会使用CacheLoader原子地加载新值.


##### Callable

##### 显式插入



#### 缓存回收




#### 其他




------
[返回目录](/README.md)
