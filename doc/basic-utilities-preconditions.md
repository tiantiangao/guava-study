优雅的参数检查(Preconditions)
===
#### 问题
对外接口方法参数过多时，需对参数进行必要的检查，将预期之外的请求快速驳回。

```java  
public boolean someMethod(int arg1, String arg2, String arg3, Object arg4){  
	if( arg1>0 && isNotEmpty(arg2) && isNotEmpty(arg3) && arg4!=null){  
		return false; // maybe需要将具体的错误类型告诉调用方  
	}  
	...  
}  

```

#### 方案
com.google.common.base.Preconditions

```java  
// 检查boolean是否为真  
// 失败时抛出 IllegalArgumentException  
Preconditions.checkArgument(boolean expression, String errMsg, Object... errMsgArgs)

// 检查value是否为null  
// 失败时抛出 NullPointerException  
Preconditions.checkNotNull(T reference, String errMsg, Object... errMsgArgs)

// 检查对象的一些状态, 不依赖方法参数(相比checkArgument, 在某些情况下更有语义...)  
// 失败时抛出 IllegalStateException  
Preconditions.checkState(boolean expression, String errMsg, Object... errMsgArgs)

// 检查index是否在合法范围[0, size)(不包含size)  
// 失败时抛出 IndexOutOfBoundsException  
Preconditions.checkElementIndex(int index, int size, String desc)

// 检查位置是否在合法范围[0, size](包含size)
// 失败时抛出 IndexOutOfBoundsException  
Preconditions.checkPositionIndex(int index, int size, String desc)

// 检查[start, end)是一个长度为size的集合合法的子集范围
// 失败时抛出 IndexOutOfBoundsException  
Preconditions.checkPositionIndexs(int start, int index, int size)

```

建议通过静态方式引入com.google.common.base.Preconditions.*

------
[返回目录](/README.md)
