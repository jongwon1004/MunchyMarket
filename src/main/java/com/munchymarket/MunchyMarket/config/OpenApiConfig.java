package com.munchymarket.MunchyMarket.config;

import io.swagger.v3.oas.models.info.Info; // import 잘못쓰는거 조심
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI(@Value("${springdoc.version}") String springdocVersion) {

        SecurityScheme apiKey = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");


        Info info = new Info()
                .title("MunchyMarket API-DOCS")
                .version(springdocVersion)
                .description("MunchyMarket - EC2 サーバーデプロイのためのAPIドキュメントです。");

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("Bearer Token");

        return new OpenAPI()
                .components(new Components().addSecuritySchemes("Bearer Token", apiKey))
                .info(info)
                .addSecurityItem(securityRequirement);

    }
}