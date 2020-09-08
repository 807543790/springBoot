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
##03-web
   一：先导入依赖，将HTML文件放入templates文件夹
   ```xml
        <dependency>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-thymeleaf</artifactId>
                </dependency>
```         
   二：拓展springmvc,想自定义一些拦截器或者其他，只要实现WebMvcConfigurer接口
 ```java
    //扩展springmvc
    //    如果你想div一些定制化的功能,只要写这个组件,然后把它交给springboot,它会帮我们自动装配
    @Configuration
    public class MyMvcConfig implements WebMvcConfigurer {
    
    //    二：ViewResolver实现了视图解析器接口的类，我们将自定义的视图解析器加载到bean中
        @Bean
        public ViewResolver myViewResolver(){
            return new MyViewResolver();
        }
    
        //一：自定义一个自己的视图解析器MyViewResolver
        public static class MyViewResolver implements ViewResolver{
            @Override
            public View resolveViewName(String s, Locale locale) throws Exception {
                return null;
            }
        }
    
    }
``` 

##04-员工管理系统
1. 
    一：先导入依赖，将HTML文件放入templates文件夹
   ```xml
        <dependency>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-thymeleaf</artifactId>
                </dependency>
    ```         
   二：拓展springmvc,想自定义一些拦截器或者其他，只要实现WebMvcConfigurer接口
 ```java
    //扩展springmvc
    //    如果你想div一些定制化的功能,只要写这个组件,然后把它交给springboot,它会帮我们自动装配
    @Configuration
    public class MyMvcConfig implements WebMvcConfigurer {
    
    //    二：ViewResolver实现了视图解析器接口的类，我们将自定义的视图解析器加载到bean中
        @Bean
        public ViewResolver myViewResolver(){
            return new MyViewResolver();
        }
    
        //一：自定义一个自己的视图解析器MyViewResolver
        public static class MyViewResolver implements ViewResolver{
            @Override
            public View resolveViewName(String s, Locale locale) throws Exception {
                return null;
            }
        }
    
    }
``` 

2.前期的准备工作
    一：静态资源导入
```xml
    <!--导入<html xmlns:th="http://www.thymeleaf.org">依赖后，
        使用@{}引入css文件 
        -->
    <link th:href="@{css/bootstrap.min.css}" rel="stylesheet">
    
```   
 
3.国际化
    一：先定义i18n文件夹，创建中英文文件：
        ·login_en_US.properties
        ·login_zh_CN.properties
        ·login.properties
        在文件中定义号中英互译的文字
    二：在application.properties中绑定文件位置
         #我们配置的真实位置
         spring.messages.basename=i18n.login
    三：在页面的<a>标签中定义好跳转时携带的参数    
    ```html
        <a class="btn btn-sm" th:href="@{/index.html(lang='zh_CN')}">中文</a>
        <a class="btn btn-sm" th:href="@{/index.html(lang='en_US')}">English</a>
        <!--注意点-->
        <!--th:href="@{/index.html(lang='en_US')}"相当于/index.html?lang='en_US'-->
    ```    
    四：自定义MyLocaleResolver  
```java
    package com.zhangbin.config;
    
    import org.springframework.web.servlet.LocaleResolver;
    import org.thymeleaf.util.StringUtils;
    
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;
    import java.util.Locale;
    
    /**
     * 认认真真敲代码，开开心心每一天
     *
     * @Date 2020/9/6-13:00
     */
    public class MylocaleResolver implements LocaleResolver {
        //解析请求
        @Override
        public Locale resolveLocale(HttpServletRequest request) {
            //获取请求中的语言参数
            String language = request.getParameter("lang");
            //如果没有设置的，就执行默认的
            Locale locale = Locale.getDefault();
            //如果请求参数不为空，携带了国际化参数
            if(!StringUtils.isEmpty(language)){
                //zh_CN
                String[] split = language.split("_");
                //国家，地区
                locale  = new Locale(split[0], split[1]);
    
            }
    
            return locale;
        }
    
        @Override
        public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {
    
        }
    }

```    
   五：在MyMvcConfig注册bean
```java    
        @Bean
        public LocaleResolver localeResolver(){
            return new MylocaleResolver();
        }
```   
4.登录功能实现
    一：在form表单中添加请求地址，注意URL使用@取值，注意最前方也要/符号
```html
<form class="form-signin" th:action="@{/user/login}" method="post">
```    
   二：登录页面的controller:loginController,注意重定向单词：redirect
```java
    package com.zhangbin.controller;
    
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.util.StringUtils;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RequestParam;
    
    /**
     * 认认真真敲代码，开开心心每一天
     *
     * @Date 2020/9/6-13:32
     */
    //登录页面controller
    @Controller
    public class loginController {
    
        @RequestMapping("/user/login")
        public String login(@RequestParam("username") String username,
                            @RequestParam("password")String password,
                            Model model){
            if(!StringUtils.isEmpty(username) && "123456".equals(password)){
                System.out.println("成功");
                return "redirect:/main.html";
            }else {
                model.addAttribute("msg","用户名或者密码错误");
                System.out.println("失败");
                return "index";
            }
    
        }
    }

```   
   三：使用重定向需要在MyMvcConfig配置
```java
    //视图重定向跳转
    registry.addViewController("/main.html").setViewName("dashboard");
```
   四：添加用户名密码错误提醒
       注意th:if="${not #strings.isEmpty(msg)}"表示的是：如果msg不为空则显示标签数据
```html
    <!--如果msg为空显示提示-->
    			<p style="color:#ff1500;" th:text="${msg}" th:if="${not #strings.isEmpty(msg)}"></p>
```  
##5：拦截器判断登录是否可以进入主页面
   一：创建自定义拦截器LoginHandlerInterceptor实现HandlerInterceptor接口，重写preHandle方法
```java
    package com.zhangbin.config;
    
    import org.springframework.web.servlet.HandlerInterceptor;
    
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;
    
    /**
     * 认认真真敲代码，开开心心每一天
     *
     * @Date 2020/9/6-15:00
     */
    //拦截器
    public class LoginHandlerInterceptor implements HandlerInterceptor {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            //判断是否登录成功，成功session不为空
            Object loginUser = request.getSession().getAttribute("loginUser");
            if(loginUser == null){
                request.setAttribute("msg","没有权限，请登录");
    //            登录成功请求转发，携带request和response
                request.getRequestDispatcher("/index.html").forward(request,response);
            }
                return true;
    
        }
    }

```   
   二：在登录页面添加登录成功后给session传入值功能
```java
       session.setAttribute("loginUser",username);
```   
   三：重写拦截器方法，传入自定义拦截器
```java
    
    //重写拦截器，传入自定义的拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandlerInterceptor())
//                拦截的请求
                .addPathPatterns("/**")
//                不拦截的请求
                .excludePathPatterns("/index.html","/","/user/login","/static/**","/templates/*","/i18n/*","/css/*","/js/**","/img/**");
    }
```   
##6：员工列表展示
```html
<!DOCTYPE html>
<!-- saved from url=(0052)http://getbootstrap.com/docs/4.0/examples/dashboard/ -->

		<!--头部导航栏-->
		<div th:insert="~{commons/commons::topber}"></div>

		<div class="container-fluid">
			<div class="row">
				<!--侧边导航栏 (active='list.html')传递参数给组件 -->
				<div th:insert="~{commons/commons::sidebar(active='list.html')}"></div>

				<main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
					<!--添加按钮-->
					<h2><a class="btn btn-sm btn-success" th:href="@{/emp}">添加员工</a></h2>

					<div class="table-responsive">
						<table class="table table-striped table-sm">
							<thead>
								<tr>
									<th>id</th>
									<th>lsatName</th>
									<th>email</th>
									<th>gender</th>
									<th>department</th>
									<th>birth</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody th:each="emp:${EmpList}">
								<td th:text="${emp.getId()}"></td>
								<td th:text="${emp.getLastName()}"></td>
								<td th:text="${emp.getEmail()}"></td>
								<td th:text="${emp.getGender() == 0? '女':'男'}"></td>
								<td th:text="${emp.getDepartment().getDepartment()}"></td>
								<td th:text="${ #dates.format(emp.getBirth(),'yyyy-MM-dd HH:mm:ss')}"></td>
								<td>
									<button class="btn btn-sm btn-primary">编辑</button>
									<button class="btn btn-sm btn-danger">删除</button>
								</td>
							</tbody>
						</table>
					</div>
				</main>
			</div>
		</div>

</html>
```
   定义模板：
         1,在模板标签中添加: th:fragment="sidebar"
         2,在页面中使用：
                <!--侧边导航栏  -->commons/commons文件夹目录，sidebar模板定义好的参数
         		<div th:insert="~{commons/commons::sidebar(active='list.html')}"></div>
    
    