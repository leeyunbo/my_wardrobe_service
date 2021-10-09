package com.cloth.wardrobe.controller;

import com.cloth.wardrobe.config.auth.LoginUser;
import com.cloth.wardrobe.config.auth.dto.RequestForMember;
import com.cloth.wardrobe.dto.common.Response;
import com.cloth.wardrobe.dto.common.ResponseMessage;
import com.cloth.wardrobe.dto.member.ResponseForMember;
import com.cloth.wardrobe.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<ResponseForMember> findMemberByToken(@LoginUser RequestForMember requestForMember) {
        ResponseForMember response = new ResponseForMember();
        response.set_code(200);
        response.set_message(ResponseMessage.OK);
        response.setEmail(requestForMember.getEmail());
        response.setName(requestForMember.getName());
        response.setPicture(requestForMember.getPicture());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<Response> updateMember(@LoginUser RequestForMember requestForMember) {
        return memberService.updateMemberFromIdToken(requestForMember);
    }
}
