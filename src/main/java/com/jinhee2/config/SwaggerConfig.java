package com.jinhee2.config;


import com.fasterxml.classmate.TypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.client.LinkDiscoverer;
import org.springframework.hateoas.client.LinkDiscoverers;
import org.springframework.hateoas.mediatype.collectionjson.CollectionJsonLinkDiscoverer;
import org.springframework.http.ResponseEntity;
import org.springframework.plugin.core.SimplePluginRegistry;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.singletonList;
import static springfox.documentation.schema.AlternateTypeRules.newRule;

@Configuration
@EnableSwagger2
@ComponentScan(basePackages = "com.jinhee2")
public class SwaggerConfig {

    @Autowired
    private TypeResolver typeResolver;

    @Bean
    public Docket api(){
        List<ResponseMessage> list = new ArrayList<>();
        list.add(
                new ResponseMessageBuilder()
                        .code(400)
                        .message("축하합니다 ^^ 400 에러입니다.")
                        .responseModel(new ModelRef("Error"))
                        .build());
        list.add(
                new ResponseMessageBuilder()
                        .code(500)
                        .message("축하합니다 ^^ 500 에러입니다.")
                        .responseModel(new ModelRef("Error"))
                        .build());

        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/api/**"))
                .build()
                .directModelSubstitute(LocalDate.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .alternateTypeRules(
                    newRule(typeResolver.resolve(DeferredResult.class,
                        typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
                        typeResolver.resolve(WildcardType.class)
                    ))
                .useDefaultResponseMessages(false)
//                .ignoredParameterTypes(
//                    Pageable.class,
//                    PagedResourcesAssembler.class,
//                    AuthenticationPrincipal.class
//                );
                .securitySchemes(singletonList(securityScheme()))
                .securityContexts(singletonList(securityContext()))
                .apiInfo(getApiInfo())

                .globalResponseMessage(RequestMethod.GET, list);
    }



    private ApiInfo getApiInfo() {
        return new ApiInfo(
//            "jinhee generation Application",
                "스웨거 테스트 프로젝트 적용중",
                "이부분은 부가적인설명입니다.",
                "API 버전부분",
                "Terms of service",
                null,
                "라이센스 부분",
                "http://localhost:8080",
                Collections.emptyList()
        );
    }

    @Bean
    public SecurityConfiguration securityConfiguration() {
        return SecurityConfigurationBuilder.builder()
                .clientId("강진희")
                .clientSecret("강진희바보")
                // ? 승인 이후 주는 권한 ?
                .scopeSeparator("password refresh_token client_credentials")
                // auth_code를 tokenURL에 요청하며, base64 인코딩을 통해 client 시크릿, 아이디를 넘긴다.
                .useBasicAuthenticationWithAccessCodeGrant(true)
                .build();
    }

    private SecurityScheme securityScheme() {
        GrantType grantType = new ResourceOwnerPasswordCredentialsGrant("/oauth/token");

        // scope & token URL 내보냄
        return new OAuthBuilder().name("spring_oauth")
                .grantTypes(singletonList(grantType))
                .scopes(Arrays.asList(scopes()))
                .build();
    }

    // 권한을 적용하기위한 custom request 메소드 설정
    private springfox.documentation.spi.service.contexts.SecurityContext securityContext() {
        return springfox.documentation.spi.service.contexts.SecurityContext.builder()
                .securityReferences(
                        // scope array와 reference값을 설정
                        singletonList(new SecurityReference("spring_oauth", scopes()))
                )
                // 특정 패스경로
                .forPaths(PathSelectors.ant("/api/**"))
                .build();
    }

    private AuthorizationScope[] scopes() {
        return new AuthorizationScope[]{
                new AuthorizationScope("read", "for read operations"),
                new AuthorizationScope("write", "for write operations")};
    }

    @Bean
    public LinkDiscoverers discoverers() {
        List<LinkDiscoverer> plugins = new ArrayList<>();
        plugins.add(new CollectionJsonLinkDiscoverer());
        return new LinkDiscoverers(SimplePluginRegistry.create(plugins));
    };
}

