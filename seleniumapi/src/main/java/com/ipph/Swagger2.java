//package com.dipub;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//@Configuration
//@EnableSwagger2
///**
// * http://blog.didispace.com/springbootswagger2/
// * Spring Boot中使用Swagger2构建强大的RESTful API文档
// * 启动Spring Boot程序，访问：http://localhost:8080/swagger-ui.html
// * @author jiahh 2017年2月13日
// *
// */
//public class Swagger2 {
//
//    @Bean
//    public Docket createRestApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.dipub.web"))
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//        		//.title("Spring Boot中使用Swagger2构建RESTful APIs")
//                .title("DI公众版 RESTful APIs")
//                //.description("更多Spring Boot相关文章请关注：http://blog.didispace.com/")
//                //.termsOfServiceUrl("http://blog.didispace.com/")
//                .contact("菲玛")
//                .version("1.0")
//                .build();
//    }
//
//}