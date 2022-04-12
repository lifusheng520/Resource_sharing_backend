package com.sharing.config;

import com.sharing.config.handler.*;
import com.sharing.serviceImpl.MyUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author 李福生
 * @date 2022-3-5
 * @time 下午 12:29
 */
@Configuration
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsServiceImpl userDetailsService;

    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;

    @Autowired
    private MyAuthenticationEntryPoint myAuthenticationEntryPoint;

    @Autowired
    private MyAuthenticationFailHandler myAuthenticationFailHandler;

    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private MyInvalidSessionStrategyHandler myInvalidSessionStrategyHandler;

    @Autowired
    private MyLogoutHandler myLogoutHandler;

    @Autowired
    private MyLogoutSuccessHandler myLogoutSuccessHandler;

    @Autowired
    private MySessionInformationExpiredStrategyHandler mySessionInformationExpiredStrategyHandler;

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService).passwordEncoder(this.passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    /**
     * 跨域过滤器
     *
     * @return
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 配置前端服务器前端URL端口
        corsConfiguration.addAllowedOrigin("http://localhost:8086");
//        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        // 允许请求携带cookies
        corsConfiguration.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers("/druid/**", "/index/**", "/rank/**", "/comment/**", "/focus/**").permitAll()
                // 允许所有的首页请求
                .antMatchers("/user/icon/**").permitAll()
                // 允许所有认证请求
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/file/**").permitAll()
                .antMatchers("/user/authEmail/**").permitAll()
                .antMatchers("/user/updatePass").permitAll()
                .antMatchers("/resource/**").permitAll()
                .antMatchers("/user/**").permitAll()
//                .antMatchers("/user/**").hasAnyRole("admin", "teacher", "student", "user")
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                // 退出登录的处理端口
                .logout().logoutUrl("/auth/logout")
                // 设置退出登录处理器
                .addLogoutHandler(this.myLogoutHandler)
                // 退出登录成功处理器
                .logoutSuccessHandler(this.myLogoutSuccessHandler)
                // 退出登录成功后，删除session会话
                .deleteCookies("JSESSIONID")
                .and()
                // 登录设置
                .formLogin()
                // 配置登录认证处理端口
                .loginProcessingUrl("/auth/login")
                // 设置表单参数名
                .usernameParameter("username")
                .passwordParameter("password")
                // 登录成功处理器
                .successHandler(this.myAuthenticationSuccessHandler)
                // 登录失败处理器
                .failureHandler(this.myAuthenticationFailHandler)
                .and()
                // 记住我
                .rememberMe()
                // 即登录页面的记住登录按钮的参数名
                .rememberMeParameter("remember-me")
//                 会话过期时间   （单位：秒）
                .tokenValiditySeconds(1800)
                .and()
                // 未登录设置(登录认证入口)
                .exceptionHandling().authenticationEntryPoint(this.myAuthenticationEntryPoint)
                .and()
                // 没有权限设置
                .exceptionHandling().accessDeniedHandler(this.myAccessDeniedHandler)
                .and()
                .sessionManagement()
                // 设置session失效过期处理器
                .invalidSessionStrategy(this.myInvalidSessionStrategyHandler)
                // 设置只允许一个用户登录
                .maximumSessions(1)
                // 禁止其他地方登录账号   （false：另一个人登录时当前用户被挤下线   true：当前账号已登录，后面用户不能再登录该账号）
                .maxSessionsPreventsLogin(false)
                // 用户被挤下线时的处理类
                .expiredSessionStrategy(this.mySessionInformationExpiredStrategyHandler);
    }
}
