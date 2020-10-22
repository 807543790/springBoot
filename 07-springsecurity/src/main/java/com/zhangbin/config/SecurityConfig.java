package com.zhangbin.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 认认真真敲代码，开开心心每一天
 *
 * @Date 2020/10/22-16:30
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    //一：授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    //链式编程
        http.authorizeRequests()
                //首页所有人可以访问
                .antMatchers("/").permitAll()
                //请求授权的规则
                .antMatchers("/level1/**").hasAnyRole("vip1")
                .antMatchers("/level2/**").hasAnyRole("vip2")
                .antMatchers("/level3/**").hasAnyRole("vip3");

//        没有权限就会默认跳到登录页面
        http.formLogin();
    }

//    二：认证

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //内存认证inMemoryAuthentication
        auth.inMemoryAuthentication()

                //passwordEncoder密码编译
                .passwordEncoder(new BCryptPasswordEncoder())

                //创建用户zhangbin,密码为123456的用户，可访问vip2
                .withUser("zhangbin").password(new BCryptPasswordEncoder().encode("123456")).roles("vip2")
                .and()
                //创建用户root,密码为123456的用户，可访问，vip1，vip2，vip3
                .withUser("root").password(new BCryptPasswordEncoder().encode("123456")).roles("vip1","vip2","vip3");
    }
}
