package app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.Ordered;

@Slf4j
@EnableAutoConfiguration()
@ComponentScan({"app"})
public class WebProjectApplication {

    public static void main(String[] args) {
        log.info("Lets get started...");
        SpringApplication.run(WebProjectApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean<RequestIdFilter> requestIdFilterFilterRegistrationBean() {
        RequestIdFilter filter = new RequestIdFilter();
        FilterRegistrationBean<RequestIdFilter> bean = new FilterRegistrationBean<>(filter);
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE + 1000);
        bean.addUrlPatterns(AppPath.API_PATH + "/*");
        return bean;
    }

}
