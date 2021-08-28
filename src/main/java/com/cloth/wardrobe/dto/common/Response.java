package com.cloth.wardrobe.dto.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {
    @Setter private Integer _code;
    @Setter private String _message;
}
