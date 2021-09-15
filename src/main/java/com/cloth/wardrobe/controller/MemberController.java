package com.cloth.wardrobe.controller;

import com.cloth.wardrobe.config.auth.CustomOAuth2MemberService;
import com.cloth.wardrobe.config.auth.LoginUser;
import com.cloth.wardrobe.config.auth.dto.SessionMember;
import com.cloth.wardrobe.entity.member.Member;
import com.cloth.wardrobe.dto.clothes.ResponseForWardrobe;
import com.cloth.wardrobe.service.WardrobeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final WardrobeService wardrobeService;
    private final CustomOAuth2MemberService customOAuth2MemberService;

    @GetMapping("/wardrobe")
    public ResponseEntity<ResponseForWardrobe> findWardrobeByMember(@LoginUser SessionMember sessionMember) {
        Member member = customOAuth2MemberService.getMemberBySession(sessionMember);
        return wardrobeService.findByMember(member);
    }
}
