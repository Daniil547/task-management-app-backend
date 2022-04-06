package io.github.daniil547.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.time.ZonedDateTime;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("io.github.daniil547"))
                .build()
                // a workaround for springfox not supporting java 8 date/time classes
                .directModelSubstitute(ZonedDateTime.class, java.util.Date.class);
    }
}
