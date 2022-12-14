package com.zzx.config;

import com.zzx.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @program: SGBlog
 * @Description
 * @Author: 那个小楠瓜
 * @create: 2022-02-24 17:02
 **/
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http
               //关闭csrf
               .csrf().disable()
               //不通过 Session 获取 SecurityContext
               .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
               .and()
               .authorizeRequests()
               //对于登录接口 允许匿名访问
               .antMatchers("/login").anonymous()
               //必须通过认证才能访问的接口
               .antMatchers("/logout").authenticated()
               .antMatchers("/user/userInfo").authenticated()
               //除上面以外的所有请求全部不需要认证即可访问
               .anyRequest().permitAll();
       //配置异常处理器
       http.exceptionHandling()
               .accessDeniedHandler(accessDeniedHandler)
               .authenticationEntryPoint(authenticationEntryPoint);
       //关闭默认注销功能
       http.logout().disable();
       //把jwtAuthenticationTokenFilter添加到SpringSecurity的过滤器链中
       http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
       //允许跨域
       http.cors();
    }
}
