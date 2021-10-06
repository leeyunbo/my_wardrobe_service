package com.cloth.wardrobe.dto.member;

import com.cloth.wardrobe.dto.common.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseForMember extends Response {
    private String name;
    private String email;
    private String picture;
}
