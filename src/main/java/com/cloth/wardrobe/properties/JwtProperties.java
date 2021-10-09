package com.cloth.wardrobe.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    String tokenIssuer;
    String tokenName;
    String secretKey;
    String expireTime;
}
