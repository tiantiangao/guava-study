并发编程之ListenableFuture
===

### 背景
在并发编程方面，JDK提供了Future, 但是使用起来不是很方便，guava提供了ListenableFuture以简化并发的编写.  
ListenableFuture继承自Future.  

### 接口

```java  
interface ListenableFuture<V> extends Future<V>   
void addListener(Runnable listener, Executor executor)  
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

### 创建ListenableFuture

传统JDK中创建Future的方式:

```java  
Executors.newFixedThreadPool(10).submit(Callable);    
```

guava中创建ListenableFuture的方式:  

```java  
MoreExecutors.listeningDecorator(ExecutorService).submit(Callable);  
```

完整的ListenableFuture使用示例:

```java  
// 创建ListeningExecutorService
ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));

// 添加执行操作
ListenableFuture explosion = service.submit(new Callable() {
  public Explosion call() {
    return pushBigRedButton();
  }
});

// 添加回调
Futures.addCallback(explosion, new FutureCallback() {
  // 操作执行完成后，执行onSuccess
  public void onSuccess(Explosion explosion) {
    walkAwayFrom(explosion);
  }
  public void onFailure(Throwable thrown) {
    battleArchNemesis(); // escaped the explosion!
  }
});
```

当然，还有其他方式来创建，比如:

```java  
// 类似JDK的FutureTask模式
ListenableFutureTask.create(Callable<V>);

// 将其他API提供的Future转换为ListenableFuture
JdkFutureAdapters.listenInPoolThread(Future);  
```

### 使用

guava还提供了一些支持链式操作的API

```java  
Futures.transform(ListenableFuture<A>, AsyncFunction<A, B>, Executor);  
Futures.transform(ListenableFuture<A>, Function<A, B>, Executor);  
Futures.allAsList(Iterable<ListenableFuture<V>>);  
Futures.successfulAsList(Iterable<ListenableFuture<V>>);  
```


------
[返回目录](/README.md)