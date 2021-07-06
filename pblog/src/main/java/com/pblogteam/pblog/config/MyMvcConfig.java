package com.pblogteam.pblog.config;

import com.pblogteam.pblog.component.RedisSessionInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//扩展SpringMVC的功能
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    @Bean
    public RedisSessionInterceptor getSessionInterceptor() {
        return new RedisSessionInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RedisSessionInterceptor()).addPathPatterns("/**") //拦截任意多层路径下的任意请求
                .excludePathPatterns("/user/signin", "/user/signup", "/druid", "/admin/signin", "/admin");    //放行登录页面
    }

    //所有的实现webMvcConfigurer接口的组件都会一起起作用
    //@Bean //两组件注册到容器
    //public WebMvcConfigurer webMvcConfigurer()
    //{
    //    WebMvcConfigurer configer = new  WebMvcConfigurer()   //匿名内部类
    //    {
    //        //注册拦截器
    //        @Override
    //        public void addInterceptors(InterceptorRegistry registry)
    //        {
    //            //静态资源: *.css, *.js 还需放行 "/static/"
    //            //registry.addInterceptor(new SigninHandlerInterceptor()).addPathPatterns("/**") //拦截任意多层路径下的任意请求
    //            //    .excludePathPatterns("/index.html", "/", "/user/login");    //放行登录页面

    //        }
    //        //后端不需要
    //        //@Override
    //        //public void addViewControllers(ViewControllerRegistry registry)
    //        //{
    //        //    registry.addViewController("/").setViewName("login");
    //        //    registry.addViewController("/index.html").setViewName("login");
    //        //    registry.addViewController("/main.html").setViewName("home");   //登录成功后重定向到home.html
    //        //}
    //    };
    //    return configer;
    //}
}
