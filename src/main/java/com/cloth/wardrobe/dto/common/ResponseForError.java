package com.cloth.wardrobe.dto.common;

public class ResponseForError {
    private Integer _code;
    private String _message;

    public ResponseForError(Integer _code, String _message) {
        this._code = _code;
        this._message = _message;
    }
}
