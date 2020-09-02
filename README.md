# springBoot 2020-08-29

## 01- helloworld

## 02-yaml
  @ConfigurationProperties(prefix = "person")
  作用：
    将配置文件中的配置的每一个属性的值，映射到这个组件中告诉springboot
    将本类中所有的属性和配置文件中能拿个的相关配置进行绑定
    只有这个组件是容器中的组件，才能使用@ConfigurationProperties
    
   ```java
        /* @controller 控制器（注入服务）
        * 用于标注控制层，相当于struts中的action层
        *
        * @service 服务（注入dao）
        * 用于标注服务层，主要用来进行业务的逻辑处理
        *
        * @repository（实现dao访问）
        * 用于标注数据访问层，也可以说用于标注数据访问组件，即DAO组件
        *
        * @component 把普通pojo实例化到spring容器中，相当于配置文件中的 
``` 
数据校验：
```java
    @Validated//数据校验
    public class person {
    
        @Email(message = "邮箱不合法")
        private String name;
        private Integer age;
        private Boolean happy;
        private Date birthday;
        private Map<String,Object> maps;
        private List<Object> lists;
        private Dog dog;
```
要用spring-boot-starter-validation依赖
```xml
       <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-validation</artifactId>
            </dependency>
```

二：多环境的配置，和多个配置文件的优先级
    多个文件的配置优先级：
    1.项目下的config文件
    2.项目的配置文件
    3.resource下的config文件夹
    4.默认的配置文件
 
二点一：多环境配置：
   properties：
       定义多个以application开头的文件，然后在主文件使用spring.profiles.active=test定义选择要使用的配置文件
       
   yaml:
  ```yaml

    #yaml多环境配置,多个环境使用---区分

server:
  port: 8080

spring:
  profiles:
    active: dev
---

server:
  port: 8081
spring:
  profiles: test
---
server:
  port: 8082
spring:
  profiles: dev

```  
                                     
    