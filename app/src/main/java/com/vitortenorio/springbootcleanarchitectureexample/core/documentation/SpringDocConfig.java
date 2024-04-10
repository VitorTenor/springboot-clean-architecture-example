package com.vitortenorio.springbootcleanarchitectureexample.core.documentation;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SpringDocConfig {
    @Value("${document.api.version}")
    private String version;
    @Value("${document.api.description}")
    private String description;
    @Value("${document.api.title}")
    private String title;
    @Value("${document.api.contact.name}")
    private String contactName;
    @Value("${document.api.contact.email}")
    private String contactEmail;

    @Bean
    public OpenAPI openAPI() {
        var contact = new Contact()
                .name(contactName)
                .email(contactEmail);

        return new OpenAPI()
                .info(new Info()
                        .title(title)
                        .version(version)
                        .description(description)
                        .contact(contact)
                );
    }
}
