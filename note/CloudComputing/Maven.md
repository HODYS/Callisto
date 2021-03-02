# Maven使用指南
直接看这个吧[Maven in 5 minutes](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html)

+ 测试具体类
mvn test -Dtest=[ClassName]
运行测试类中指定的方法：(这个需要maven-surefire-plugin:2.7.3以上版本才能支持)

+ 测试具体方法
mvn test -Dtest=[ClassName]#[MethodName]
//[MethodName]为要运行的方法名，支持*通配符，范例：
mvn test -Dtest=MyClassTest#test1
mvn test -Dtest=MyClassTest#tes