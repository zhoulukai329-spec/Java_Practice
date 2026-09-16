# Day2: Springboot 入门

## 0. 目录

- [Springboot是什么？](#1-Springboot是什么？)

## 1. Springboot 是什么？
Springboot 的核心目标是让Spring架构能够快速建立并运行，提高生产效率。
```
Springboot
|
自动配置spring
|
自动配置mvc
|
自动配置mybatis
```

类似的，Springboot 的项目结构也和Spring类似：

```
前端 / Postman
      │
      │ HTTP Request
      ↓
┌──────────────┐
│  Controller  │
└──────────────┘
      │
      ↓
┌──────────────┐
│   Service    │
└──────────────┘
      │
      ↓
┌──────────────┐
│    DAO       │
└──────────────┘
      │
      ↓
┌──────────────┐
│   Database   │
└──────────────┘
```
### 1.1 项目入口
每一个springboot项目都会有如下的入口来自动配置spring
```java
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
```
### 1.2 核心语法 Bean
对于一般的Java，
```java
UserService service = new Userservice();
```

而Spring会自动创建、管理、注入UserService，而不用单独封装单独调用，
```java
@Service
public class UserService{

}
```
这样一来，如果Controller想要调用UserService，不需要再new，只需要：
```java
@RestController
public class UserController{
    private final UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }
}
```
Spring 会自动找到 UserService Bean ，然后创建RestController，然后把UserService注入

### 1.3 核心语法Controller

| 注解                       | 作用              |
| ------------------------ | --------------- |
| `@RestController`        | Web Controller  |
| `@Controller`            | MVC Controller  |

Controller 是用来监听 Web 请求并调用 Service 层服务的。

```java
@RestController
@RequestMapping("/users") // 代表统一路径: /users
public class UserController{
    // services
}
```
其中，Controller层里最常见的 REST API 操作如下：
```
GET       查询
POST      新增
PUT       修改
DELETE    删除
REQUEST   请求
```
- a. GET
GET意如其名，就是获取某个资源，类似于sql中的SELECT
例如要查询用户信息，
```java
@RestController
public class UserController{
    @GetMapping("/users/{id}")
    public UserSearch search(@PathVariable int id){
        return userService.searchById(id); // @PathVariable 是url中传来的参数，后接类型和值
    }
}
```
当收到前端发送来的请求和id，后端会自动GET users/id，然后到数据库查询该用户。

- b. POST
POST 意思是创建一个新的资源，类似于sql中的INSERT
例如要加入一个新用户到数据库中，
```java
@RestController
public class UserController{
    @PostMapping("/users")
    public User UserInsert(@RequestBody User user){
        return userService.insertUser(user);
    }
}
```

当前端发送
```
POST /users
Content-Type: application/json

{
    "name": "Tom",
    "age": 20
}
```
@RequestBody会把从前端HTTP中读取的{"name":"Tom","age":20}转换成User user（java对象）

- c. PUT
PUT 是要修改现有的数据，类似于sql中的UPDATE
例如有一个用户要修改密码
```java
@RestController
public class UserController {
    @PutMapping("/users/{id}")
    public User userUpdate(
            @PathVariable int id,
            @RequestBody User user) {
        return userService.updateById(id, user);
    }
}
```
前端发送user_id和用户修改好的密码（在User user这个对象中），例如
```
PUT /users/1
{
    "name":"Tom",
    "id":1
    "password":123abc_
}
```
那么1号用户的用户信息就会根据id更新为上述的样子，并被转回User user这一java对象。

- d. DELETE
意如其名，是从数据库中删去数据。类似于sql中的DELETE
如果有一位用户要注销账号
```java
@RestController
public class UserController{
    @DeleteMapping("/users/{id}")
    public void userDelete(@PathVariable int id){
        userService.deleteById(id);
    }
}
```
当前端发送DELETE /users/1之后，id为1的用户被删除。

- e. Request
RequestMapping是通用映射，可以通过填入参数来实现上述所有4个映射的效果。最常见的用途不是直接代替 @GetMapping，而是放在 Controller 类上，定义公共 URL 前缀。如：
```java
@RestController
@RequestMapping("/users")
public class UserController{
    @GetMapping("/{id}")// 这里相当于 "/users/{id}"
    public User getUser(@PathVariable int id){
        return userService.getById(id);
    } 
}
```

想用Request替代上述映射，只需要在参数中加入method即可：
```java
@RequestMapping(
    value = "/users",
    method = RequestMethod.GET
)
```
即为
```java
@GetMapping("/users")
```

- f. PathVariable
url路径中的参数，可传多种不同类型，前文有所提及
```java
@RestController
public class UserController{
    @GetMapping("/users/{id}")
    public UserSearch search(@PathVariable int id){
        return userService.searchById(id); // @PathVariable 是url中传来的参数，后接类型和值
    }
}
```

- g.RequestParam
这个参数较为特殊，无法用sql语句来类比。它用于读取url中"?"之后的参数。
例如：
```java
@RestController
public class UserController{
    @GetMapping("/users")
    public String getUser(
        @RequestParam("username") String name) {
        return name;
    }
}
```
此时，如果前端发送GET /users?username=Tom，则name = Tom。
这个参数可以用来打日志，如果参数不存在，返回400 Bad Request
如果这个参数可以不传，也可以 ` @RequestParam(required = false) String name`,那么如果没有值就没用输出也没有告警。
如果希望有输出，可以设置default value ，`@RequestParam(required = false) String name`

- h. RequestBody
前文有提过。这是用来把前端传的json转成java中对象的参数。
```java
@RestController
public class UserController{
    @PostMapping("/users")
    public void addUser(@RequestBody User user){//把json转成User user，然后才能insert
        userService.addUser(user); // insert不需要返回
    }
}
```

### 1.4 核心语法Entity
Entity最重要的一点就是一定要跟数据库对齐。无其他语法，普通的java类而已。
假设数据库：
```sql
CREATE TABLE user (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    age INT
);
```
那么Entity就必须是：
```java
public class User {

    private Integer id;

    private String name;

    private Integer age;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
……………………
……………………
……………………
}
```

### 1.5 核心语法Mapper
Mapper层主要和resources里的Mapper/userMapper.xml对齐。Mapper层里的语句必须是.xml里sql语句的子集，只可少，不可以存在没有被定义过的mapper方法。因为mapper本质是为了解决sql在java生态中的硬编码问题，不用在java里写sql语句，只要在xml里定义好，然后在Mapper里直接定义方法名。所以如果没有定义，方法就不存在。
另外，mapper不负责主要逻辑，只是一个接口，负责
```
Java
↕
SQL
↕
Database
```
一般的语法是：
```java
@Mapper
public interface UserMapper {

    List<User> findAll();

    User findById(Long id);

    int insert(User user);
}
```
与此同时，另外一边

### 1.6 核心语法Mapper.xml
Mapper里需要定义好所有需要用到的sql语句和他们作为方法被调用的名称。
所以把上面的Mapper.java对应过来，应该是：
```xml
<mapper namespace="com.example.demo.mapper.UserMapper"> <!--这是Mapper.java的路径-->

    <select id="findAll"
            resultType="com.example.demo.entity.User"><!--id = "findAll"说明在Mapper.java里，这个方法的名字是findAll；resultType是该sql返回的值，必须和Entity中定义过的值名字对齐-->

        SELECT id, name, age
        FROM user <!--sql语句-->

    </select>

    <!--一组select就是一个sql语句-->


    <select id="findById"
        resultType="com.example.demo.entity.User">

    SELECT id, name, age
    FROM user
    WHERE id = #{id}    <!--这个#{id}是占位符，用于传参-->

    </select>


    <insert id="insert"
        parameterType="com.example.demo.entity.User"
        useGeneratedKeys="true"
        keyProperty="id">

    INSERT INTO user(name, age)
    VALUES(#{name}, #{age})

    </insert>
</mapper>
```
可能有敏锐的读者有疑惑：为什么`1.5 Mapper`中，insert这个方法的返回值是int类型，且在xml里也有两行陌生的配置信息。这是一处开发的细节。为了系统的健壮性，在insert的xml模块中，加入了
```xml
    useGeneratedKeys="true" <!--要不要把自增 ID 拿回来-->
    keyProperty="id"  <!--拿回来以后放到 Java 对象的哪个属性里-->
```
并且在sql定义中，也规定了AUTO_INCREMENT，即id是自增主键。
如果xml里不加这两行，MyBatis 不会把数据库生成的 id 自动写回 user.id.
所以insert本身不返回int，int只代表受影响的行数，即插入了多少行。
换言之，直接写void insert (User user);也不会有问题，但xml中一定要定义自增变量和变量填入到哪里。

### 1.7 核心语法Service
Service是整个项目的全部核心业务代码，以接口的形式给Controller调用。
如果业务特别简单，除了Mapper的基本sql语句以外就没有其他的，那可以省略service。

### 1.8 Application.yml
SpringBoot把很多配置都放在`src/main/resources/application.yml`中，这是整个项目的运行配置，数据库、端口、Redis、日志、文件上传、Spring配置、MyBatis配置都写在这里。例如：
```yml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/demo
    username: root
    password: 123456
    driver-class-name: com.mysql.cj.jdbc.Driver

mybatis:
  mapper-locations: classpath:mapper/*.xml

  configuration:
    map-underscore-to-camel-case: true
```

### 1.9 DTO 和 VO
在项目中，非常不推荐直接用 前端→Entity→数据库 的链路读取， 然后用 数据库→Entity→前端 的链路返回。因为有些敏感信息会暴露，因为传的数据中极大可能包含个人账号和密码。
所以DTO和VO最大的作用就是让前端不能直接操作数据库实体Entity，而是只能操作DTO中包含的元素，防止其他不应被修改的元素受到影响。

- a. DTO
DTO用于控制接收的字段，防止前端发送无论什么都能传来后端修改的情况。
假设Entity中的User user如下：
```java
public class UserEntity{
    private Long id;

    private String username;

    private String password;

    private Integer age;

    private LocalDateTime createTime;
}
```

但是系统用户修改信息时，是无法修改id和createTime的。所以前端接口实际上只需要传：
```json
{
    "username": "Tom",
    "password": "123456",
    "age": 20
}
```

所以可以定义DTO
```java
public class UserCreateDTO {

    private String username;

    private String password;

    private Integer age;

}
```
这样就能限制前端，不修改id和createTime，架构更安全更清晰。

- b. VO
VO 用于控制后端的返回，防止后端把敏感信息暴露。
假设数据库里有用户的密码，而且绝对不允许返回密码到前端。那么就应该定义VO：
```java
public class UserVO {

    private Long id;

    private String username;

    private Integer age;

    private LocalDateTime createTime;

}
```

所以，DTO,VO,Entity应该保持以下关系：
```
DTO = 前端 → 后端

Entity = 后端 ↔ 数据库

VO = 后端 → 前端
```

- c. 参数校验
参数校验是用来检查参数是否符合要求的注解。
常见的参数校验注解如下：
| 注解                | 作用                   |
| ----------------- | -------------------- |
| `@NotNull`        | 不能为 `null`           |
| `@NotEmpty`       | 不能为 `null`，也不能为空     |
| `@NotBlank`       | 字符串不能为 null、空字符串、全空格 |
| `@Min(1)`         | 数字最小值                |
| `@Max(100)`       | 数字最大值                |
| `@Positive`       | 必须 > 0               |
| `@PositiveOrZero` | 必须 ≥ 0               |
| `@Negative`       | 必须 < 0               |
| `@Size`           | 字符串/集合长度限制           |
| `@Email`          | Email 格式             |
| `@Pattern`        | 正则表达式                |
| `@Past`           | 必须是过去日期              |
| `@Future`         | 必须是未来日期              |
| `@DecimalMin`     | 小数最小值                |
| `@DecimalMax`     | 小数最大值                |
| `@Digits`         | 限制整数和小数位数            |
| `@AssertTrue`     | 必须为 true             |
| `@AssertFalse`    | 必须为 false            |

例如建立DTO的过程中，
```java
public class createUserDTO{
    @NotBlank(message = "用户名不能为空")
    private String name; // 名字不为空，可以自己加报错信息

    @Min(18)
    private Integer age; // 年龄大于18岁
}
```

那么在Controller调用的时候，就可以用@Valid来检查createUserDTO里的所有变量是否符合注解的校验条件。
```java
@RestController
public class UserController{
    @PostMapping
    public void addUser(@Valid @RequestBody createUserDTO user){
        userService.addUser(user);
    }
}
```

这里的@Valid会校验传来的createUserDTO类型user中的参数是否符合刚才定义DTO中的要求。
另外，@Valid还可以进行嵌套验证。比如：
```java
public class AddressDTO{
    @NotBlank
    private String city;

    @NotBlank
    private String street;
}
public class UserInfo{
    @NotBlank
    private String username;
    
    @Valid
    private AddressDTO address;
}
```
这在多复用的复杂项目里非常常见。

### 1.10 异常值处理
除了刚才提到的在校验注解之后加报错信息以外，还有专门用来写报错日志的注解。
最常用的是以下三个：
```java
@ExceptionHandler //指定这个方法负责处理什么异常
@RestControllerAdvice
@ControllerAdvice
```

- a. ExceptionHandler
指定“这个方法负责处理哪一种异常”。例如：
```java
@ExceptionHandler(RuntimeException.class)
public String runtimeErrorLog(RuntimeException e){
    return "错误原因:" + e.getMessage();
}
```
这里的`@ExceptionHandler(RuntimeException.class)`表示如果出现RuntimeException，就调用runtimeErrorLog方法。
ExceptionHandler通常放在Controller中。
```java
@RestController
public class UserController {

    @GetMapping("/users/{id}")
    public String getUser(@PathVariable Integer id) {

        if (id <= 0) {
            throw new RuntimeException("id不能小于等于0");
        }

        return "success";
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleException(RuntimeException e) {
        return "错误原因：" + e.getMessage();
    }
}
```

但是每一个Controller都有可能报错，如果要在每一个Controller中都写至少一个ExceptionHandler，项目会变得不清晰。所以，我们需要@ControllerAdvice

- b. ControllerAdvice
这能够创建一个报错信息的集合：
```java
@ControllerAdvice
public class GlobalException{
    @ExceptionHandler(RuntimeException.class)
    public String runtimeError(RuntimeException e){
        return "Error:" + e.getMessage();
    }

    @ExceptionHandler(NullPointerException.class)
    public String NullPointerError(NullPointerException e){
        return "Error:" + e.getMessgae();
    }

}
```

- c. RestControllerAdvice
RestControllerAdvice与ControllerAdvice的区别是返回值的区别：
| 注解                      | 主要用途                     |
| ----------------------- | ------------------------ |
| `@ControllerAdvice`     | 全局 Controller 增强         |
| `@RestControllerAdvice` | 全局 Controller 增强 + 返回*响应体* |

也就是说：ControllerAdvice一般只返回String，而RestControllerAdvice会返回响应体json：
```json
{
    "code": 400,
    "message": "用户不存在",
    "data": null
}
```