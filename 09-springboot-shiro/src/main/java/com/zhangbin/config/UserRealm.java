package com.zhangbin.config;

import com.zhangbin.pojo.User;
import com.zhangbin.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 认认真真敲代码，开开心心每一天
 *
 * @Date 2020/10/28-12:35
 */

//自定义的userRealm,继承 AuthorizingRealm即可
public class UserRealm extends AuthorizingRealm {
    @Autowired
    UserService service;

//    授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了授权=doGetAuthorizationInfo");
        return null;
    }
//    认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了认证 doGetAuthenticationInfo");


        UsernamePasswordToken userToken = (UsernamePasswordToken) token;

        //连接正式数据库
        User user = service.queryUserByName(userToken.getUsername());
        if(user == null){ //没有这个人
            return null;
        }

        System.out.println(service.queryUserByName("房贷"));
        return new SimpleAuthenticationInfo("",user.getPwd(),"");
    }
}
