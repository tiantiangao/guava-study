使用和避免null
===


null本身不是对象，也不是Objcet的实例

#### 问题:
null代表不确定的对象, 是一个很模糊的概念, 容易产生二义性

Map.get(key)若返回value值为null，其代表的含义可能是该键指向的value值是null，亦或者该键在map中并不存在

#### 优点:
从内存消耗和效率方面，null更加廉价

#### 优化: Optional
com.google.common.base.Optional

```java
Optional<T> possbile = Optional.formNullable(T); //将一个T的实例转换为Optional对象(T可以为空)
boolean present = possible.isPresent();          //若Optional包含的T实例不为null，则返回true；若T实例为null，返回false
T t = possible.get();                            //返回Optional包含的T实例，该T实例必须不为空；否则，抛出一个IllegalStateException异常
```

构建一个Optional对象

```java
Optional.of(T);                   //将一个T的实例转换为Optional对象(T不可以为空)
Optional.absent();                //获得一个Optional对象，其内部包含了空值
Optional.fromNullable(T);         //将一个T的实例转换为Optional对象，T的实例可以不为空，也可以为空  
                                  //Optional.fromNullable(null) 等同于 Optional.absent()  
```

Optional实例方法

```java
boolean isPresent();              //若Optional包含的T实例不为null, 返回true; 否则, 返回false
T get();                          //若Optional包含的T实例不为null, 返回T; 否则, 抛出IllegalStateException
T or(T);                          //若Optional包含的T实例不为null, 返回T; 否则, 返回参数输入的T实例
T orNull();                       //若Optional包含的T实例不为null, 返回T; 否则, 返回null
```

------
[返回目录](/README.md)