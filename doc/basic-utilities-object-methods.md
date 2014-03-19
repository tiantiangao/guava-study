常用的对象方法(Objects)
===
Objects提供了Java对象的equals、hashCode、toString等方法

#### equals
覆写equals方法时, 减少了null判断和分支处理  

```java  
Objects.equal("a", "a"); // returns true
Objects.equal(null, "a"); // returns false
Objects.equal("a", null); // returns false
Objects.equal(null, null); // returns true
```

#### hashCode
更方便地完成多个属性的hash

```java  
Objects.hashCode(Object...)
Objects.hashCode(field1, field2, ..., fieldn)
```

#### toString
对象的toString方法更多是为了更好的可读性, ToStringHelper可以通过链式更方便地将对象的各属性都加入

```java   
Objects.toStringHelper(Persion.class)
       .add("name", this.name)
       .add("age", this.age)
       .toString();
```

> Intellij Idea中可以安装*Guava equals, hashCode and toString generator*插件来快速生成这三个方法


#### compare/compareTo
compareTo是java.lang.Comparable<T>接口中的方法  

guava提供了所有原始类型的对比工具
```java   
Ints.compare(int a, int b)  
Longs.compare(long a, long b)
Shorts.compare(short a, short b)
Doubles.compare(double a, double b)
Floats.compare(float a, floab b)
Booleans.compare(boolean a, boolean b)
Chars.compare(char a, char b)
```

同时，guava还提供了链式对比的工具ComparisonChain
```java   
ComparisonChain.start()
         .compare(this.aString, that.aString)
         .compare(this.anInt, that.anInt)
         .compare(this.anEnum, that.anEnum, Ordering.natural().nullsLast())
         .result();
```
ComparisonChain是一个lazy的比较过程， 当比较结果为0的时候， 即相等的时候， 会继续比较下去， 出现非0的情况， 就会忽略后面的比较

------
[返回目录](/README.md)
