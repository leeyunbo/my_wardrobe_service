package com.cloth.wardrobe.dto.common;

import lombok.Data;

@Data
public class ResponseForError {
    private Integer _code;
    private String _message;
}
