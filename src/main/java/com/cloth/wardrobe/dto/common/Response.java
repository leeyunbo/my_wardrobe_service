package com.cloth.wardrobe.dto.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {
    private Integer _code;
    private String _message;
    private Integer page;
    private Integer pageSize;
    private Integer totalElements;
    private Boolean isLast;
    private Boolean isFirst;
}
