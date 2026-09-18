# Spring Cloud 入门
## 0. 目录

## 1. 为什么需要Spring Cloud？
Spring Cloud 本身是一个针对分布式系统常见模式的工具集合，包括服务发现、路由、服务间调用、负载均衡、熔断等。当项目很复杂的时候，可能会有很多个服务，每一个都是独立的SpringBoot，此时，springcloud可以辅助管理每一个服务之间的联系。

SpringCloud中的常用组件是Eureka / Feign / Gateway，下文逐一介绍。

## 2. SpringCloud 应用
### 2.1 Eureka
Eureka可以理解为是Spring的信息交流中心，或者可以比喻成上世纪八九十年代的电话接线员、现代的通讯录