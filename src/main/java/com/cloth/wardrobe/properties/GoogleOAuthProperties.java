package com.cloth.wardrobe.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.security.oauth2.client.registration.google")
public class GoogleOAuthProperties {
    String client_id;
    String client_secret;
    String scope;
    String oauth_base_url;
}
