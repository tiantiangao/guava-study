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


------
[返回目录](/README.md)