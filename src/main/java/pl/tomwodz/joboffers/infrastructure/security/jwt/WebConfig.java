package pl.tomwodz.joboffers.infrastructure.security.jwt;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping( "/**" )
                .allowedOrigins("http://localhost:4200", "http://www.devjava.pl:9009")
                .allowedMethods( "GET", "POST", "DELETE", "PUT")
                .allowedHeaders( "*" )
                .allowCredentials( true )
                .exposedHeaders( "Authorization" )
                .maxAge( 3600 );
    }
}
