package app;

import com.fasterxml.classmate.TypeResolver;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.Server;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.ModelRendering;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.TagsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.util.Collections;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static springfox.documentation.schema.AlternateTypeRules.newRule;

@EnableSwagger2
@Configuration
@RequiredArgsConstructor
public class SwaggerDocConfig {

    public static final String SYSTEM_ERROR_CODE = "500";
    public static final String SYSTEM_ERROR_DESCRIPTION = "Системная ошибка";
    private final TypeResolver typeResolver;

    @Bean
    Docket integrationApi() {
        Contact contact = new Contact("Лесин Сергей", "", "sa-lesin@rsb.ru");
        ApiInfo apiInfo = new ApiInfo("ПО Hunter", "", "1.0-SNAPSHOT", "",
                contact, "", "", Collections.emptyList());
        return new Docket(DocumentationType.OAS_30)
                .groupName("AFC Integration")
                .apiInfo(apiInfo)
                .servers(new Server("", "http://uwr-test2.rs.ru:81/", "", emptyList(), emptyList()))
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .apis(RequestHandlerSelectors.withClassAnnotation(IntegrationApi.class))
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .directModelSubstitute(LocalDate.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .alternateTypeRules(
                        newRule(typeResolver.resolve(DeferredResult.class,
                                        typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
                                typeResolver.resolve(WildcardType.class)))
                .useDefaultResponseMessages(false)
                .globalResponses(HttpMethod.GET, singletonList(new ResponseBuilder()
                                .code(SYSTEM_ERROR_CODE)
                                .description(SYSTEM_ERROR_DESCRIPTION)
                                .build()
                        )
                )
                .globalResponses(HttpMethod.POST, singletonList(new ResponseBuilder()
                                .code(SYSTEM_ERROR_CODE)
                                .description(SYSTEM_ERROR_DESCRIPTION)
                                .build()
                        )
                )
                .globalResponses(HttpMethod.PUT, singletonList(new ResponseBuilder()
                                .code(SYSTEM_ERROR_CODE)
                                .description(SYSTEM_ERROR_DESCRIPTION)
                                .build()
                        )
                )
                .enableUrlTemplating(true);
    }

    @Bean
    Docket frontApi() {
        Contact contact = new Contact("Лесин Сергей", "", "sa-lesin@rsb.ru");
        ApiInfo apiInfo = new ApiInfo("ПО Hunter", "", "1.0-SNAPSHOT", "",
                contact, "", "", Collections.emptyList());
        return new Docket(DocumentationType.OAS_30)
                .groupName("Front API")
                .apiInfo(apiInfo)
                .servers(new Server("", "http://uwr-test2.rs.ru:81/", "", emptyList(), emptyList()))
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .apis(RequestHandlerSelectors.withClassAnnotation(FrontApi.class))
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .directModelSubstitute(LocalDate.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .alternateTypeRules(
                        newRule(typeResolver.resolve(DeferredResult.class,
                                        typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
                                typeResolver.resolve(WildcardType.class)))
                .useDefaultResponseMessages(false)
                .globalResponses(HttpMethod.GET, singletonList(new ResponseBuilder()
                                .code(SYSTEM_ERROR_CODE)
                                .description(SYSTEM_ERROR_DESCRIPTION)
                                .build()
                        )
                )
                .enableUrlTemplating(true)
                .globalRequestParameters(
                        singletonList(new RequestParameterBuilder()
                                .name("SESSION")
                                .description("Сессионная кука")
                                .in(ParameterType.COOKIE)
                                .required(true)
                                .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
                                .build()))
//                .additionalModels(typeResolver.resolve(AdditionalModel.class))
                ;
    }

    @Bean
    UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
                .deepLinking(true)
                .displayOperationId(false)
                .defaultModelsExpandDepth(1)
                .defaultModelExpandDepth(1)
                .defaultModelRendering(ModelRendering.MODEL)
                .displayRequestDuration(false)
                .docExpansion(DocExpansion.NONE)
                .filter(false)
                .maxDisplayedTags(null)
                .operationsSorter(OperationsSorter.ALPHA)
                .showExtensions(false)
                .showCommonExtensions(false)
                .tagsSorter(TagsSorter.ALPHA)
                .operationsSorter(OperationsSorter.METHOD)
                .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
                .validatorUrl(null)
                .build();
    }
}
