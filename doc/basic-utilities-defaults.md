默认值(Defaults)
===
Defaults类提供Java各原生类型的默认值

com.google.common.base.Defaults.defaultValue(Class<T> type)

```java   
boolean.class                         //返回false  
char.class                            //返回'\0'  
byte.class                            //返回(byte)0  
short.class                           //返回(short)0 
int.class                             //返回0  
long.class                            //返回0L  
float.class                           //返回0f  
double.class                          //返回0d  
non-primitive types                   //返回null
```

------
[返回目录](/README.md)
