package com.cloth.wardrobe.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "wardrobe")
public class AuthorizationProperties {
    private String auth_header;
    private String auth_value;
}
