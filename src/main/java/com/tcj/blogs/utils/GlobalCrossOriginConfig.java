//package com.tcj.blogs.utils;
//
//import lombok.AllArgsConstructor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//@AllArgsConstructor
//public class GlobalCrossOriginConfig implements WebMvcConfigurer {
//
//    private final ReadApplicationProperties readConfig;
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
////                .allowCredentials(true)
//                .allowedOrigins("*")
//                .allowedMethods(new String[]{"GET", "POST", "PUT", "DELETE"})
//                .maxAge(readConfig.getMaxAge())
//                .allowedHeaders("*")
//                .exposedHeaders("*");
//    }
//}
