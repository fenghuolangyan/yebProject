package com.xq.server.secrity.filter;/*
@author  xq 
@date 2021/4/12 - 17:08 
手握梦想,心向旅行的第12天
三尺代码 一世天涯
*/

import com.xq.server.entity.Menu;
import com.xq.server.entity.Role;
import com.xq.server.service.MenuService;
import io.swagger.annotations.ApiModelProperty;
import org.apache.velocity.runtime.directive.Foreach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

@Component
public class customerFilter implements FilterInvocationSecurityMetadataSource {
   @Autowired
   private MenuService menuService;

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        //获取所有权限
        List<Menu> menus = menuService.getMenusWithRole();
        for (Menu menu : menus) {
            if(antPathMatcher.match(menu.getUrl(),requestUrl)){
                //获取该菜单所有角色信息
                String[] strings = menu.getRoles().stream().map(Role::getName).toArray(String[]::new);
                return SecurityConfig.createList(strings);
            }
        }
        //如果该请求没有对应菜单信息  则默认给个角色
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }
}
