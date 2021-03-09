package ru.edjll.cinema.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;
import java.util.List;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Value("${trailer.upload.path}")
    private String trailerUploadPath;

    @Value("${preview.upload.path}")
    private String previewUploadPath;

    @Value("${film.upload.path}")
    private String filmUploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/trailer/**")
                .addResourceLocations("file://" + trailerUploadPath + "/");
        registry.addResourceHandler("/preview/**")
                .addResourceLocations("file://" + previewUploadPath + "/");
        registry.addResourceHandler("/filmVideo/**")
                .addResourceLocations("file://" + filmUploadPath + "/");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        jsonConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
        converters.add(jsonConverter);
    }
}
