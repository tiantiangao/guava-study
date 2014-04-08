缓存
===

### 什么是缓存
缓存你懂的，memcached用过没？ehcache用过没？内存Map总该用过吧...

当计算或检索一个值的代价很高，并且对同样的输入需要不止一次获取值的时候，就应当考虑使用缓存。这下懂了吧

换句话说，缓存就是以空间换时间

### 问题
内存Map会一直保存所有添加的元素, 直到显示地移除, 所以会一直占用内存  
而Guava Cache为了限制内存使用，通常都设定为自动回收元素。  

由于存放于内存中，Guava Cache不适合存放过大的数据，数据量较大时，可以尝试使用 Memcached 等

### 主要流程
get-if-absent-compute  
如果有缓存则返回；否则运算、缓存、然后返回

### 缓存加载
当缓存不存在时，guava提供了多种方式来加载数据: CacheLoader、Callable、显示插入.

###### CacheLoader

LoadingCache是一种基于CacheLoader的缓存实现. 

```java  
LoadingCache<Key, Graph> graphs = CacheBuilder.newBuilder()  
        .maximumSize(1000)
        .expireAfterWrite(10, TimeUnit.MINUTES)
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

###### Callable

所有类型的Guava Cache, 不管有没有自动加载功能, 都支持get(K, Callable<V>)方法。  
get(K, Callable<V>)方法尝试返回缓存中对应的值; 如果值不存在，则使用Callable运算，并把结果加入缓存中。

```java  
Cache<Key, Graph> cache = CacheBuilder.newBuilder()
        .maximumSize(1000)
        .build(); // 看，木有CacheLoader
...
try {
    cache.get(key, new Callable<Key, Graph>() {
        @Override
        public Value call() throws AnyException {
            // 缓存不存在，就会调用call()方法计算, 并把结果加入缓存
            return doThingsTheHardWay(key);
        }
    });
} catch (ExecutionException e) {
    throw new OtherException(e.getCause());
}
```
这种方式简便地实现了"get-if-absent-compute"模式

###### 显式插入

使用cache.put(key, value)方法可以直接向缓存中插入值, 该方法会直接覆盖掉给定键之前映射的值. 


### 缓存回收
由于guava缓存是将数据存放于内存中，所以确定一定以及肯定没有足够的内存存放所有的数据  
guava提供了三种基本的缓存回收方式: 基于容量回收、定时回收和基于引用回收。

###### 基于容量回收(Size-based Eviction)

###### 定时回收(Timed Eviction)

###### 基于引用回收(Reference-based Eviction)




### 其他




------
[返回目录](/README.md)
