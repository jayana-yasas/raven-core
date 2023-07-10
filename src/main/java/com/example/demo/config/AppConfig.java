package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

//  @Bean
//  public SessionFactory sessionFactory(DataSource dataSource) throws Exception {
//    LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
//    sessionFactoryBean.setDataSource(dataSource);
//    sessionFactoryBean.setPackagesToScan("com.example.demo.entity");
//    sessionFactoryBean.afterPropertiesSet();
//    return sessionFactoryBean.getObject();
//  }
}
