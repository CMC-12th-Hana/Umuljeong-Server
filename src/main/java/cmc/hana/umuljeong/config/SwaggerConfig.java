package cmc.hana.umuljeong.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("v1-definition")
                .packagesToScan("cmc.hana.umuljeong.web.controller")
                .build();
    }

    @Bean
    public OpenAPI eroomAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Field Mate API")
                                .description("필드메이트 API 명세서")
                                .version("1.0.0")
                );
    }
}
