package com.cloth.wardrobe.config.auth.dto;

import com.cloth.wardrobe.dto.common.Response;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtToken extends Response {
    String token;
}
