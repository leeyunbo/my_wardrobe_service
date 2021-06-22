package com.cloth.wardrobe.controller;

import com.cloth.wardrobe.config.auth.CustomOAuth2MemberService;
import com.cloth.wardrobe.config.auth.dto.SessionMember;
import com.cloth.wardrobe.domain.clothes.Wardrobe;
import com.cloth.wardrobe.dto.clothes.WardrobeGetRequestDto;
import com.cloth.wardrobe.dto.community.CommentResponseRequestDto;
import com.cloth.wardrobe.dto.community.CommentSaveRequestDto;
import com.cloth.wardrobe.dto.clothes.WardrobeResponseRequestDto;
import com.cloth.wardrobe.dto.clothes.WardrobeUpdateRequestDto;
import com.cloth.wardrobe.dto.clothes.WardrobeSaveRequestDto;
import com.cloth.wardrobe.service.WardrobeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class WardrobeController {

    private final WardrobeService wardrobeService;
    private final CustomOAuth2MemberService customOAuth2MemberService;

    @PostMapping("/api/v1/wardrobes")
    public Long save(@RequestBody WardrobeSaveRequestDto wardrobeSaveRequestDto, HttpSession httpSession) {
        log.info("--------------------------------------save()-----------------------------------");
        SessionMember sessionMember = (SessionMember) httpSession.getAttribute("user");
        return wardrobeService.save(wardrobeSaveRequestDto, customOAuth2MemberService.getIdBySession(sessionMember));
    }

    @PutMapping("/api/v1/wardrobes/{id}")
    public Long update(@PathVariable Long id, @RequestBody WardrobeUpdateRequestDto wardrobeUpdateRequestDto) {
        return wardrobeService.update(id, wardrobeUpdateRequestDto);
    }

    @GetMapping("/api/v1/wardrobes/{id}")
    public WardrobeGetRequestDto findById (@PathVariable Long id) {
        return wardrobeService.findById(id);
    }

    @GetMapping("/api/v1/wardrobes")
    public Page<Wardrobe> findAll(Pageable pageable) {
        return wardrobeService.findAll(pageable);
    }

    @PutMapping("/api/v1/wardrobes/{id}/like_cnt")
    public Long addLikeCnt(@PathVariable Long id, HttpSession httpSession) {
        SessionMember sessionMember = (SessionMember) httpSession.getAttribute("user");
        return wardrobeService.addLikeCnt(id, customOAuth2MemberService.getIdBySession(sessionMember));
    }

    @DeleteMapping("/api/v1/wardrobes/{id}/like_cnt")
    public Long delLikeCnt(@PathVariable Long id, HttpSession httpSession) {
        SessionMember sessionMember = (SessionMember) httpSession.getAttribute("user");
        return wardrobeService.delLikeCnt(id, customOAuth2MemberService.getIdBySession(sessionMember));
    }

    @PutMapping("/api/v1/wardrobes/{id}/comment")
    public Long writeComment(@PathVariable Long id, @RequestBody CommentSaveRequestDto commentSaveRequestDto, HttpSession httpSession) {
        SessionMember sessionMember = (SessionMember) httpSession.getAttribute("user");
        return wardrobeService.writeComment(id, customOAuth2MemberService.getIdBySession(sessionMember), commentSaveRequestDto);
    }

    @DeleteMapping("/api/v1/wardrobes/{wardrobeId}/comment/{commentId}")
    public Long deleteComment(@PathVariable Long wardrobeId, @PathVariable Long commentId) {
        return wardrobeService.deleteComment(wardrobeId, commentId);
    }
}
