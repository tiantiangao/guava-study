函数式
===

### 注意!注意!注意!

Java中一切皆对象，唯函数不算!  
Java 7以前, Java中只能通过笨拙冗长的匿名类来达到近似函数式编程的效果。  (Java 8引入了Lambda表达式)  

> 过度使用Guava函数式编程会导致冗长、混乱、可读性差而且低效的代码。  
> 如果你想通过函数式风格达成一行代码，致使这行代码长到荒唐，Guava团队会泪流满面。  
> 请务必确保，当使用Guava函数式的时候，用传统的命令式做同样的事情不会更具可读性。  
> 总之，不要盲目使用函数式!!!

比较一下这种场景:  

函数式
```java  
Function<String, Integer> lengthFunction = new Function<String, Integer>() {
    public Integer apply(String string) {
        return string.length();
    }
};
Predicate<String> allCaps = new Predicate<String>() {
    public boolean apply(String string) {
        return CharMatcher.JAVA_UPPER_CASE.matchesAllOf(string);
    }
};
Multiset<Integer> lengths = HashMultiset.create(
     Iterables.transform(Iterables.filter(strings, allCaps), lengthFunction));
```

函数式2
```java  
Multiset<Integer> lengths = HashMultiset.create(
    FluentIterable.from(strings)
        .filter(new Predicate<String>() {
            public boolean apply(String string) {
                return CharMatcher.JAVA_UPPER_CASE.matchesAllOf(string);
            }
        })
        .transform(new Function<String, Integer>() {
            public Integer apply(String string) {
                return string.length();
            }
        }));
```

命令式
```java  
Multiset<Integer> lengths = HashMultiset.create();
for (String string : strings) {
    if (CharMatcher.JAVA_UPPER_CASE.matchesAllOf(string)) {
        lengths.add(string.length());
    }
}
```

### Functions[函数]和Predicates[断言]


------
[返回目录](/README.md)
