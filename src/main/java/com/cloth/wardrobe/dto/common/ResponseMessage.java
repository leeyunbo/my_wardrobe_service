package com.cloth.wardrobe.dto.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseMessage {
    INVALID_PARAMETER("invalid_parameter"),
    INVALID_AUTHENTICATION("invalid_authentication"),
    UNAUTHORIZED("unauthorized"),
    INTERNAL_SERVER_ERROR("internal_server_error"),
    EXPIRED_AUTHENTICATION("expired_authentication"),
    MISSING_PARAMETER("mssing_parameter"),
    INVALID_CONTENT_TYPE("invalid_content_type");

    private final String key;
}
