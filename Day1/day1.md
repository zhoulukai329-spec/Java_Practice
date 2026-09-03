# Spring Framework Study Day 1 (2026/09/03)
## 0. 目录

  - [为什么我们需要Spring](#1-为什么我们需要spring)
  - [mybatis框架介绍](#2-mybatis框架介绍)
  - [Spring MVC框架介绍](#3-spring-mvc框架介绍)
  - [Controller + Service + Dao/Mapper 三层架构](#4-controller--service--daomapper-三层架构)
## 1. 为什么我们需要Spring
### a. 如果没有Spring，传统Java开发中会遇到的问题

对于最原始的Java，如果我们要写Java Web, 我们需要自己去封装网址的基本功能。 例如：
```java
private class UserLoginService{
    private UserRepository userRepository;
    public UserLoginService(){
        this.userRepository = new UserRepository();
    }
    public boolean Login(String username, String password){
        User user = userRepository.findByUsername(username);
        if(user == null){
            return false;
        }
        return user.getPassword().equals(password);
    }
}
```

这样看似是封装完好的登陆系统，但如果项目的结构变得更加复杂，我们每一次调用都需要手动创造对象`new UserService()`、`new UserRepository()`，这就会导致开发的效率变得低下。

除此之外，项目中对象之间的依赖关系也会变得非常复杂，导致代码难以维护和扩展。例如：
```
公司门户网站
|
登录
|
研发云/工时填报/容器库……
```

要开发这样一个*功能复杂的系统*，如果公司因为权限问题，在研发云/工时填报/容器库这一层再设登录验证，那么我们就需要在每一个模块中都去写登录验证的代码，调用相同的方法，这样会导致代码大量重复，对象之间的依赖关系也非常复杂。不仅在开发时拉低效率，在维护时也牵一发动全身，代码*耦合度非常高*。

而Spring框架的出现就是为了解决这些问题。Spring框架通过*控制反转（Inversion of Control）*、*依赖注入（Dependency Injection）*和*面向切面编程（Aspect-Oriented Programming）*等技术，帮助开发者更好地管理对象之间的依赖关系，减少代码重复，提高开发效率和代码可维护性。

### b. Spring框架介绍
- 控制反转（IoC）
本质上控制反转就是Spring框架*自动帮助你创建对象*，而不是每一次都手动去创建对象。Spring框架通过配置文件或者注解的方式，来告诉Spring容器如何创建和管理对象，从而实现对象的自动化管理。例如：
对于刚才的`UserLoginService`类，如果我们没有Spring：
```java
UserRepository userRepository = new UserRepository();
UserLoginService userLoginService = new UserLoginService(userRepository);
```
但是如果我们有了Spring框架，我们只需要在配置文件中声明`UserRepository`和`UserLoginService`，Spring就会自动帮我们创建对象，并注入依赖：
```java
@Service
public class UserLoginService {

    private final UserRepository repository;

    public UserLoginService(UserRepository repository) {
        this.repository = repository;
    }
}
```
```java
@Repository
public class UserRepository {
}
```
`@Service`和`@Repository`注解告诉Spring框架，这两个类是需要被管理的Bean，Spring会自动创建它们的实例，并在需要的时候注入到其他类中。

- 依赖注入（DI）
依赖注入是实现反转控制的一种方式，它允许我们将对象的*依赖关系*从代码中解耦出来，通过配置文件或者注解的方式来注入依赖。这样，我们就不需要在代码中手动创建对象，而是由Spring框架来管理对象的生命周期和依赖关系。依然拿登陆系统距离：
```java
private class UserLoginService{
    private UserRepository userRepository;
    public UserLoginService(String username, String password){
        this.userRepository = new UserRepository();
        User user = userRepository.findByUsername(username);
        if(user == null){
            return false;
        }
        return user.getPassword().equals(password);
    }
}
UserRepository userRepo = new UserRepository();
UserLoginService userLoginService = new UserLoginService(userRepo);
…………
UserRepository userRepo2 = new UserRepository();
UserLoginService userLoginService2 = new UserLoginService(userRepo2);
…………
```
在这个系统中，`UserLoginService`依赖于`UserRepository`类。如果我们没有使用Spring框架，我们需要在`UserLoginService`的构造函数中手动创建`UserRepository`对象，这样会导致代码耦合度高、重复度高，难以维护和扩展。

而如果我们使用Spring框架，我们可以通过依赖注入的方式，将`UserRepository`对象注入到`UserLoginService`中，而不需要在代码中手动创建对象。
```java
public class UserLoginService {

    private final UserRepository repository;

    public UserLoginService(UserRepository repository) {
        this.repository = repository;
    }
}
```
- 面向切面编程（AOP）
这是一个使业务代码更干净的优化思想。面向切面编程（AOP）是一种编程范式，它允许我们将*横切关注点*（比如日志、安全性等在多个模块中都大概率会出现的功能）从业务逻辑中分离出来，从而使业务代码更加干净和可维护。

### c. Spring-Boot框架和Spring框架的关系
Spring-Boot是基于Spring框架的一个子项目，主要简化了基于Spring的开发过程，提供了一种快速开发的方式，通过自动配置、内嵌服务器和约定优于配置等特性，使得开发者可以更快地构建和部署Spring应用程序。

### 本阶段总结
Spring/Spring-Boot框架的出现，通过配置、注解的方式，自动化管理*类*、*对象之间的依赖*、以及*监测日志的生成*，极大地提高了开发效率和维护性，是聚焦于Java应用层开发的主要框架。

## 2. mybatis 框架介绍

上文提到，spring框架提高了Java在应用层开发的效率和维护性，但在*数据库层开发*中，Java仍然存在一些问题。传统的Java数据库开发中，我们需要手动编写SQL语句，并将结果集映射到Java对象中，这样会导致代码重复、难以维护和扩展。

而mybatis框架聚焦于解决Java在数据库层开发中遇到的问题，通过*SQL映射*的方式，将SQL语句和Java对象进行映射，从而简化了数据库操作，提高了开发效率和代码可维护性。

### a. 如果没有mybatis，传统Java数据库开发中会遇到的问题
传统的Java JDBC开发中，我们采用手动编写SQL语句+硬编码的方式来操作数据库，这样会导致代码重复、难以维护和扩展。例如：
```java
try{
    Connection connection = DriverManager.getConnection(<url>);//通过驱动管理类获取数据库链接

    String sql = "SELECT * FROM users WHERE username = ?"; //定义sql语句

    preparedStatement = connection.prepareStatement(sql);//预处理操作对象
    preparedStatement.setString(1, "zhang san");//设置参数，第一个参数为sql语句中参数的序号（从1开始），第二个参数为设置的参数值
    resultSet =  preparedStatement.executeQuery();//开始查询数据库并创建结果集对象

    while(resultSet.next()){
        System.out.println(resultSet.getInt("id") + ", " + resultSet.getString("username"));//通过结果集对象获取数据
    }
}catch (SQLException e) {
    e.printStackTrace();
}finally{
    …………//释放资源
}
```
这样的操作有以下严重的问题：
- 1.数据库连接问题：使用时就创建，不使用立即释放，对数据库进行频繁连接开启和关闭，造成数据库资源浪费，影响数据库性能
- 2.将sql语句硬编码到java代码中，如果sql语句修改，或是数据库技术栈更新，需要重新编译java代码，不利于系统维护
- 3.向preparedStatement中设置参数，对占位符号位置和设置参数值，硬编码在java代码中，不利于系统维护
- 4.从resultSet中遍历结果集数据时，存在硬编码，将获取表的字段进行硬编码，不利于系统维护

### b. mybatis帮我们做了什么
mybatis框架解决的上述的四个问题：
- 1.数据库连接问题：mybatis框架提供了连接池技术，避免了频繁的数据库连接开启和关闭
- 2.Java硬编码问题：将sql语句配置在xml配置文件中，即使sql变化，不需要对java代码进行重新编译
- 3.向preparedStatement中设置参数然后硬编码问题：将sql语句及占位符号和参数全部配置在xml中
- 4.从resultSet中遍历结果集数据时，存在硬编码：mybatis框架提供了结果映射的方式，将结果集映射到Java对象中

例如：
先在xml配置文件里定义所有需要的sql语句：
```xml
<select id="findByUsername" parameterType="String" resultType="User">
    SELECT * FROM users WHERE username = #{username}
</select>
```
其中，#{username}是mybatis框架提供的占位符号，表示将参数username的值传入到sql语句中。
然后在java代码中调用：

```java
@Mapper
public interface UserRepository {
    User findByUsername(String username);
}
```
这是，username被传入sql语句中，数据库开始执行`select * from users where username = 'zhang san'`，并将结果集映射到User对象中。

## 3. Spring MVC框架介绍
Spring MVC是Spring框架的一个子模块，在java项目中主要用于处理Web请求和响应。它将应用程序分为三个主要部分：模型（Model）、视图（View）和控制器（Controller），从而实现了关注点分离，提高了代码的可维护性和可扩展性。

其中，控制器（Controller）负责处理用户请求，调用业务逻辑层（Service）进行处理，并将结果返回给前端视图层（View）。模型（Model）用于封装数据，视图（View）用于展示数据。

Spring MVC的常见注解：
  - @Controller：用于标注控制器类，表示该类是一个Spring MVC的控制器。
  - @RequestMapping：用于映射请求路径和处理方法，支持GET、POST等请求方式。
  - @GetMapping、@PostMapping：分别用于处理GET和POST请求，是@RequestMapping的简化版。
  - @PathVariable：用于获取请求路径中的变量值。
  - @RequestParam：用于获取请求参数的值。
  - @ResponseBody：用于将方法的返回值直接作为响应体返回给客户端，常用于返回JSON数据。

## 4. Controller + Service + Dao/Mapper 三层架构
三层架构是基于spring + mvc + mybatis的开发模式。Controller层接收Web请求，不写复杂的业务逻辑算法；收到请求后，调用Service层进行业务逻辑处理；Service层调用Dao/Mapper层进行数据库操作，Dao/Mapper层负责与数据库进行交互，执行SQL语句并返回结果。

整个流程由spring框架进行管理，mvc 和 mybatis 各司其职，处理访问需求、业务逻辑，和数据库操作。

一般的完整后端项目结构如下：
```
src
└── main
    ├── resources
    │   ├── application.yml
    │   └── mapper
    │       └── UserMapper.xml
    └── java
        └── com.example.demo
            │
            ├── controller
            │   └── UserController.java
            │
            ├── service
            │   ├── UserService.java
            │   └── UserServiceImpl.java
            │
            ├── mapper
            │   └── UserMapper.java
            │
            ├── entity
            │   └── User.java
            │
            └── DemoApplication.java
```
下面基于这个项目结构，搭建一个http://10.45.59.194:28080/framework_dynconfig-1.1.0/login.html 的登录系统（账号/密码：11/1）

