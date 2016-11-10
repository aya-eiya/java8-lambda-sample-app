package jp.eiya.aya.web.config;


import jp.eiya.aya.web.util.rest.APIResponse;
import jp.eiya.aya.web.util.rest.APIResponse2HttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

@Configuration
@EnableAspectJAutoProxy
@EnableSpringHttpSession
public class MvcConfig {

    @Bean
    public HttpMessageConverter<APIResponse<?>> apiResponse2HttpMessageConverter(){
        return new APIResponse2HttpMessageConverter();
    }

    @Bean
    public MapSessionRepository sessionRepository(){
        return new MapSessionRepository();
    }
}