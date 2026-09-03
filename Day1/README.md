# 用户信息管理系统

这是一个用于学习 Spring Boot + Spring MVC + MyBatis 的用户信息管理示例项目。

## 1. 项目结构

```text
Day1
├── pom.xml
├── src
│   └── main
│       ├── java
│       │   └── com/example/demo
│       │       ├── DemoApplication.java
│       │       ├── controller/UserController.java
│       │       ├── service/UserService.java
│       │       ├── mapper/UserMapper.java
│       │       └── entity/User.java
│       └── resources
│           ├── application.yml
│           └── mapper/userMapper.xml
├── TEACHING.md
└── day1.md
```

Java 源码必须放在 `src/main/java`，资源文件必须放在 `src/main/resources`。Maven 只会编译 `src/main/java` 下的源码。

## 2. 依赖安装

项目使用 Maven，根目录下的 `pom.xml` 已经配置好依赖：

- `spring-boot-starter-web`：Spring MVC 和内置 Tomcat
- `mybatis-spring-boot-starter`：Spring Boot 与 MyBatis 集成
- `mysql-connector-j`：MySQL 驱动
- `spring-boot-starter-validation`：参数校验
- `spring-boot-starter-test`：测试

### 2.1 在终端安装并验证

```powershell
cd D:\Java_Practice\Day1
mvn clean compile
```

第一次运行会从 Maven 中央仓库下载依赖，需要联网。看到 `BUILD SUCCESS` 即表示依赖已经安装并编译通过。

### 2.2 本机常见问题：Maven 提示 C:\.m2 不可写

当前环境的 Java 读取到的 `user.home` 是 `C:\`，所以 Maven 默认想把本地仓库建到 `C:\.m2\repository`，会报错：

```text
Could not create local repository at C:\.m2\repository
```

任选一种方式解决：

#### 方式 A：创建用户级 Maven 配置（推荐）

创建目录 `C:\Users\zhou\.m2`，再新建 `C:\Users\zhou\.m2\settings.xml`，内容如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
          https://maven.apache.org/xsd/settings-1.0.0.xsd">
    <localRepository>C:/Users/zhou/.m2/repository</localRepository>
</settings>
```

然后重新打开终端或 IDE。

#### 方式 B：给 Maven 设置 `user.home`

在 PowerShell 中执行：

```powershell
[Environment]::SetEnvironmentVariable("MAVEN_OPTS", "-Duser.home=C:\Users\zhou", "User")
```

然后关闭并重新打开 PowerShell / IDE，让环境变量生效。

#### 方式 C：临时指定本地仓库

```powershell
mvn "-Dmaven.repo.local=C:/Users/zhou/.m2/repository" clean compile
```

这种方式每次运行命令都要带参数，适合临时验证。

## 3. IDE 注解标红怎么解决

注解标红通常不是注解本身写错了，而是 IDE 没有加载到 Spring/MyBatis 的 jar 包。原因是项目结构或 `pom.xml` 配置错误，导致 Maven 没有把依赖导入 IDE。

现在修复后的正确做法：

### IntelliJ IDEA

1. 关闭当前项目，`File -> Open`，选择 `D:\Java_Practice\Day1\pom.xml`，以项目方式打开。
2. 打开右侧 `Maven` 工具窗口，点击 `Reload All Maven Projects`。
3. 检查 `File -> Project Structure -> Project` 的 SDK 是否为 Java 21。
4. 如果 Maven 报 `C:\.m2` 错误，先按第 2.2 节配置 `settings.xml`，再 Reload。
5. 还不行就 `File -> Invalidate Caches... -> Invalidate and Restart`。

### VS Code

1. 安装 `Extension Pack for Java` 和 `Spring Boot Extension Pack`。
2. 打开 `D:\Java_Practice\Day1`。
3. 等待右下角 Java Language Server 导入完成。
4. 如果还标红，按 `Ctrl+Shift+P`，执行 `Java: Clean Java Language Server Workspace`，然后重启。

## 4. 数据库准备

确保本机 MySQL 已启动，并创建数据库和表：

```sql
CREATE DATABASE IF NOT EXISTS demo DEFAULT CHARACTER SET utf8mb4;
USE demo;

CREATE TABLE IF NOT EXISTS `user` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nickname VARCHAR(50) NOT NULL,
    age INT NULL,
    password VARCHAR(100) NOT NULL
);
```

然后检查 `src/main/resources/application.yml` 中的数据库用户名和密码是否与本机一致。

## 5. 启动项目

```powershell
cd D:\Java_Practice\Day1
mvn spring-boot:run
```

启动成功后，服务地址是 `http://localhost:28080`。

## 6. API 列表

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| GET | `/api/users` | 查询所有用户 |
| GET | `/api/users/{id}` | 按 id 查询用户 |
| GET | `/api/users/nickname/{nickname}` | 按昵称查询用户 |
| POST | `/api/users` | 新增用户，JSON body |
| PUT | `/api/users/{id}` | 修改指定 id 的用户 |
| DELETE | `/api/users/{id}` | 按 id 删除用户 |
| DELETE | `/api/users/nickname/{nickname}` | 按昵称删除用户 |

例如新增用户：

```powershell
curl.exe -X POST http://localhost:28080/api/users `
  -H "Content-Type: application/json" `
  -d "{\"nickname\":\"zhangsan\",\"age\":20,\"password\":\"123456\"}"
```
