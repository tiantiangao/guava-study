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

构建Cache时，可以通过CacheBuilder.maximumSize(long)来指定缓存的容量.   
在缓存容量达到指定容量时(maybe达到之前), 会尝试回收最近没有使用或总体上很少使用的缓存项.  

另外，可以通过CacheBuilder.weight(Weigher), 来指定权重函数, 权重函数将在缓存创建时计算

```java  
LoadingCache<Key, Graph> graphs = CacheBuilder.newBuilder()
        .maximumWeight(100000)
        .weigher(new Weigher<Key, Graph>() {
            public int weigh(Key k, Graph g) {
                return g.vertices().size();
            }
        })
        .build(
            new CacheLoader<Key, Graph>() {
                public Graph load(Key key) { // no checked exception
                    return createExpensiveGraph(key);
                }
            });
```

###### 定时回收(Timed Eviction)

CacheBuilder提供两种定时回收的方式：

* expireAfterAccess(long, TimeUnit): 缓存在给定时间内没有被读/写访问过, 则回收. 回收顺序与基于容量回收的一样
* expireAfterWrite(long, TimeUnit): 缓存在给定时间内没有被写访问(创建/覆盖), 则回收. 

###### 基于引用回收(Reference-based Eviction)

如果使用week references的键/值、soft references的值，则缓存允许被垃圾回收:

* CacheBuilder.weakKeys()
* CacheBuilder.weakValues()
* CacheBuilder.softValues()

###### 显式移除

可以通过以下接口，在任何时间清除缓存

* Cache.invalidate(key): 单个清除 
* Cache.invalidateAll(keys): 批量清除
* Cache.invalidateAll(): 清除所有缓存项

###### 移除监听器

CacheBuilder.removalListener(RemovalListener)  
添加一个监听器，在缓存项被移除时，进行额外操作.  

```java  
RemovalListener<Key, Value> removalListener = new RemovalListener<Key, Value>() {

	// 缓存项被移除时，RemovalListener会获取移除通知[RemovalNotification]  
	// 其中包含移除原因[RemovalCause]、键和值  
    public void onRemoval(RemovalNotification<Key, Value> removal) {
	    removal.getKey(); // 被移除的Key
	    removal.getValue(); // 被移除的Value
	    removal.getCause(); // 被移除的原因: EXPLICIT、REPLACED、COLLECTED、EXPIRED、SIZE
    }
};

return CacheBuilder.newBuilder()
    .expireAfterWrite(2, TimeUnit.MINUTES)
    .removalListener(removalListener)
    .build(loader);
```

用RemovalListeners.asynchronous(RemovalListener, Executor)把监听器装饰为异步操作

###### 缓存清理的时间点

使用CacheBuilder构建的缓存，不会“自动”执行清理和回收工作.  
guava并没有建立独立线程来完成清理工作, 而是在写操作时顺带做少量的维护工作.  
使用者可以建立自己的独立线程, 来主动清理缓存, 只需要调用Cache.cleanUp()就可以了

###### 刷新

LoadingCache.referesh(K)  刷新表示为键加载新值, 可以异步完成  
刷新和回收不一样，刷新时，缓存仍然可以向其他线程返回旧值，而回收时，读取线程必须等待新值加载完成.  
如果刷新失败(抛出异常)，缓存将保留旧值  

CacheLoader.reload(K, V)可以扩展刷新时的行为  
CacheBuilder.refreshAfterWrite(long, TimeUnit)可以为缓存增加自动定时刷新功能  

### 其他特性

###### 统计
CacheBuilder.recordStats()  开启Guava Cache的统计功能。  
Cache.stats()  返回CacheStats对象

CacheStatus提供如下统计信息:  
CacheStats.hitRate()  缓存命中率  
CacheStats.hitCount()  缓存命中数量  
CacheStats.averageLoadPenalty()  加载新值的平均时间，单位为纳秒  
CacheStats.evictionCount()  缓存项被回收的总数，不包括显式清除  
...

###### Map视图

cache.asMap()  提供了缓存的ConcurrentMap形式  

* asMap()包含当前所有加载到缓存的项
* asMap().get(key)实质上等同于cache.getIfPresent(key)，而且不会引起缓存项的加载
* Cache.asMap().get(Object)方法和Cache.asMap().put(K, V)方法会重置相关缓存项的访问时间


------
[返回目录](/README.md)
