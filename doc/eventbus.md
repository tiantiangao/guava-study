事件总线EventBus
===

### 背景

JDK中通过Observer接口和Observable类实现观察者模式, Observer对象是观察者，Observable对象是被观察者.  

实现一个简单的观察者模式有以下几步:

1. 创建被观察者, 继承自java.util.Observable类
2. 创建观察者, 实现java.util.Observer接口
3. 在观察者中实现void update(java.util.Observable observable, java.lang.Object o)方法
4. 在被观察者对象上添加观察者Observable.addObserver(observer)
5. 当被观察事件发生时，执行以下代码  
6. setChanged(); // 内部标志，注明数据发生了变化
7. notifyObservers(); // 调用观察者对象列表中所有的Observer的update()方法, 通知它们数据变化了

这种方式是通过发布者和订阅者之间的显式注册实现的.  
guava的EventBus就是为了取代这种显示注册方式，使组件间有更好的解耦.  
EventBus不适用于进程间通信。

### 示例

消息封装类: 任意的Java对象均可  
```java  
public class LogEvent {

	private String log;
	
	// setter、getter
}
```

消息接收类: 任意的Java对象均可, 只需要在接收方法上添加注解@Subscribe即可  
```java  
public class LogEventListener {

	@Subscribe
	public void listen(LogEvent log) {
		// handle log
	}

}
```

消息发布  
```java  
// 事件总线
EventBus eventBus = new EventBus();
// 事件监听者
LogEventListener logEventListener = new LogEventListener();
// 注册监听
eventBus.register(logEventListener)
// 发布消息
eventBus.post(new LogEvent("测试"));
```

### 解析

EventBus中主要包括以下几个角色:  

* 事件: 可以向事件总线发布的消息
* 监听者: 提供一个处理方法, 通过参数声明希望接受和处理事件对象，实现自己的处理逻辑
* 事件总线: 可以理解为消息传输的渠道，所有在当前事件总线上注册了的监听者都会收到来自于当前事件总线、与监听者所声明的期望类型一致(支持继承关系)的消息

同步发布事件: EventBus.post(Object)  
异步发布事件: AsyncEventBus.post(Object)  

guava并未将EventBus设计为单例, 所以可以根据实际情况使用

### DeadEvent

EventBus会将所有发布后，没有监听者处理的事件包装为DeadEvent

### 单例使用

在简单情况下，可以将EventBus声明为全局唯一的单例, 并可以通过Spring完成自动注册, 这样将进一步简单使用




------
[返回目录](/README.md)