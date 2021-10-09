package com.cloth.wardrobe.dto.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ResponseMessage {
    public static final String INVALID_PARAMETER = "invalid_parameter";
    public static final String INVALID_GOOGLE_ID_TOKEN = "invalid_google_id_token";
    public static final String DO_NOT_FOUND_GOOGLE_ID_TOKEN = "do_not_found_google_id_token";
    public static final String DO_NOT_FOUND_JWT_TOKEN = "do_not_found_jwt_token";
    public static final String INVALID_AUTHENTICATION = "invalid_authentication";
    public static final String UNAUTHORIZED = "unauthorized";
    public static final String INTERNAL_SERVER_ERROR = "internal_server_error";
    public static final String EXPIRED_AUTHENTICATION = "expired_authentication";
    public static final String MISSING_PARAMETER = "missing_parameter";
    public static final String INVALID_CONTENT_TYPE = "invalid_content_type";
    public static final String OK = "OK";
}
