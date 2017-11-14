package io.github.wonyoungpark;

import org.h2.server.web.WebServlet;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by wyPark on 2016. 6. 13..
 */
@EnableAutoConfiguration
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {


    // <mvc:default-servlet-handler>와 동일한 기능
    // WebMvcConfigurerAdapter class 를 오버라이딩해서 정적자원처리가 디폴트 서블릿으로 위임되게 한다.
    // TODO http://bestheroz.blog.me/220299004488 사이트를 통해 <mvc:default-servlet-handler> 학습하기.
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    // H2 Database 콘솔 접근 URL 설정
    @Bean
    public ServletRegistrationBean h2servletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new WebServlet());
        registration.addUrlMappings("/h2console/*");
        return registration;
    }

    // UTF-8 Filter 설정
    @Order(Ordered.HIGHEST_PRECEDENCE) // Bean정의 우선순위를 지정함. (=가장 먼저 적용되게 함)
    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return characterEncodingFilter;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("/common/main");
    }
}
