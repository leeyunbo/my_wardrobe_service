package com.cloth.wardrobe.controller;

import com.cloth.wardrobe.config.auth.LoginUser;
import com.cloth.wardrobe.config.auth.dto.RequestForMember;
import com.cloth.wardrobe.dto.common.Response;
import com.cloth.wardrobe.dto.member.ResponseForMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {

    @GetMapping
    public ResponseEntity<ResponseForMember> findMemberByToken(@LoginUser RequestForMember requestForMember) {
        ResponseForMember response = new ResponseForMember();

        response.set_code(200);
        response.set_message("OK");
        response.setEmail(requestForMember.getEmail());
        response.setName(requestForMember.getName());
        response.setPicture(requestForMember.getPicture());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
