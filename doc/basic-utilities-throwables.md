简化异常处理(Throwables)
===
Guava提供了一个异常处理工具类, 可以简单地捕获和重新抛出多个异常

#### 常用方法

```java
// 把throwable包装成RuntimeException，用该方法保证异常传递，抛出一个RuntimeException异常  
RuntimeException propagate(Throwable); 

// 当且仅当它是一个X的实例时，传递throwable  
void propagateIfInstanceOf(Throwable, Class<X extends Exception>) throws X;

// 当且仅当它是一个RuntimeException和Error时，传递throwable  
void propagateIfPossible(Throwable); 

// 当且仅当它是一个RuntimeException和Error时，或者是一个X的实例时，传递throwable  
void propagateIfPossible(Throwable, Class<X extends Throwable>) throws X;
```

#### 异常链处理
```java  
Throwable getRootCause(Throwable)  
List<Throwable> getCausalChain(Throwable) 
String getStackTraceAsString(Throwable)  
```

------
[返回目录](/README.md)
