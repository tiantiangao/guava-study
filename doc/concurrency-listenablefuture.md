并发编程之ListenableFuture
===

### 背景
在并发编程方面，JDK提供了Future, 但是使用起来不是很方便，guava提供了ListenableFuture以简化并发的编写.  
ListenableFuture继承自Future.  

### 接口

```java  
interface: ListenableFuture   
method: addListener(Runnable, Executor)  
```

传统的Future: 通过异步的方式计算返回结果，Future是运行中的多线程的一个引用句柄.  
ListenableFuture: 允许注册回调方法, 在运算(多线程执行)完成的时候，使用指定的Executor执行指定的Runnable.  

### 添加回调

guava提供了以下几种方式添加回调  

* ListenableFuture接口上的addLister(Runnbale, Executor)  
* Futures.addCallback(ListenableFuture<V>, FutureCallback<? super V>, Executor)  
* Futures.addCallback(ListenableFuture<V>, FutureCallback<? super V>) // 这种情况默认使用MoreExecutors.sameThreadExecutor()线程池

FutureCallback采用轻量级的设计, 只需要实现以下两个方法  

* onSuccess(V) // 在Future成功的时候执行
* onFailure(Throwable) // 在Future失败的时候执行



------
[返回目录](/README.md)