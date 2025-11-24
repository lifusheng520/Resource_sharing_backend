package com.sharing.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.properties.DruidStatProperties;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.util.Utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 李福生
 * @date 2022-2-25
 * @time 下午 12:45
 */

@Configuration
public class MyDruidConfig {

    //   从配置文件中获取Durid监控的账号密码
    @Value("${spring.druid.monitor.login-username}")
    private String loginUsername;

    @Value("${spring.druid.monitor.login-password}")
    private String loginPassword;

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource getDruidDataSource() {
        return new DruidDataSource();
    }

    //    后台监控 web.xml,ServletRegistrationBean
    //    因为Springboot内置了Servlet容器，所有没有web.xml， 替代方法：ServletRegistrationBean
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");

        //        后台需要有人登陆，账号密码配置
        HashMap<String, String> initParameters = new HashMap<>();

        //        增加配置
        initParameters.put("loginUsername", loginUsername);   //登陆key为固定值（loginUsername， loginPassword）
        initParameters.put("loginPassword", loginPassword);

//        允许谁可以访问(localhost:本地访问)
//        initParameters.put("allow", "");

//        禁止谁能访问
//        initParameters.put("username", "192.168.12.56");

        bean.setInitParameters(initParameters); //设置初始化参数

        return bean;
    }

    //    filter过滤器
    @Bean
    public FilterRegistrationBean webStartFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();

        bean.setFilter(new WebStatFilter());

//        设置可以过滤的请求
        Map<String, String> initParameters = new HashMap<>();

//        配置不进行统计的项
        initParameters.put("exclusions", "*.js,*.css,/druid/*");

        bean.setInitParameters(initParameters);

        return bean;
    }

    // 关闭Druid 监控中的广告
    @Bean
    public FilterRegistrationBean removeDruidAdFilterRegistrationBean(DruidStatProperties properties) {
        // 获取web监控页面中的配置参数
        DruidStatProperties.StatViewServlet config = properties.getStatViewServlet();

        String url;

        // 提取common.js的配置路径
        if (config.getUrlPattern() != null)
            url = config.getUrlPattern();
        else
            url = "/druid/*";

        String cj = url.replaceAll("\\*", "js/common.js");

        // 广告文件的位置
        String file = "support/http/resources/js/common.js";

        //创建filter对象处理过滤
        Filter filter = new Filter() {
            @Override
            public void init(FilterConfig filterConfig) throws ServletException {
            }

            @Override
            public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
                // 过滤链
                chain.doFilter(request, response);

                // 重置缓冲区，响应头不会被重置
                response.resetBuffer();

                // 获取common.js文件
                String text = Utils.readFromResource(file);

                // 正则替换banner, 除去底部的广告信息
                text = text.replaceAll("<a.*?banner\"></a><br/>", "");
                text = text.replaceAll("powered.*?shrek.wang</a>", "");

                // 将结果写入文件中
                response.getWriter().write(text);
            }

            @Override
            public void destroy() {
            }
        };

        FilterRegistrationBean bean = new FilterRegistrationBean();
        // 设置过滤器
        bean.setFilter(filter);
        bean.addUrlPatterns(cj);
        return bean;
    }

}
