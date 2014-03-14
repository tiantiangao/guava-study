优雅的参数检查(Preconditions)
===
#### 问题
对外接口方法参数过多时，需对参数进行必要的检查，将预期之外的请求快速驳回

```java
public boolean someMethod(int arg1, String arg2, String arg3, Object arg4){
	if( arg1>0 && isNotEmpty(arg2) && isNotEmpty(arg3) && arg4!=null){
		return false;
	}
	...
}
```

#### 方案
com.google.common.base.Preconditions

```java
// 检查boolean是否为真
// throw IllegalArgumentException
Preconditions.checkArgument(boolean expression, String errMsg, Object... errMsgArgs)

// 检查value是否为null
// throw NullPointerException
Preconditions.checkNotNull(T reference, String errMsg, Object... errMsgArgs)

// 检查对象的一些状态, 不依赖方法参数
// throw IllegalStateException
Preconditions.checkState(boolean expression, String errMsg, Object... errMsgArgs)

```

可以考虑通过静态方式引入com.google.common.base.Preconditions.*

------
[返回目录](README.md)