package com.eliasfb.bgn;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AdditionalResourceWebConfiguration implements WebMvcConfigurer {
  @Override
  public void addResourceHandlers(final ResourceHandlerRegistry registry) {
    registry
        .addResourceHandler("/images/**", "/rules/**")
        .addResourceLocations("file:images/", "file:rules/");
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/rules/**").allowedOrigins("http://localhost:4201");
  }
}
