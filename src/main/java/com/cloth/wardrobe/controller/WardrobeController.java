package com.cloth.wardrobe.controller;

import com.cloth.wardrobe.config.auth.CustomOAuth2MemberService;
import com.cloth.wardrobe.config.auth.LoginUser;
import com.cloth.wardrobe.config.auth.dto.SessionMember;
import com.cloth.wardrobe.domain.community.PostType;
import com.cloth.wardrobe.dto.clothes.RequestForClothSave;
import com.cloth.wardrobe.dto.community.CommentSaveRequestDto;
import com.cloth.wardrobe.dto.clothes.RequestForWardrobeUpdate;
import com.cloth.wardrobe.dto.clothes.RequestForWardrobeSave;
import com.cloth.wardrobe.service.CommunityService;
import com.cloth.wardrobe.service.WardrobeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class WardrobeController {

    private final CommunityService communityService;
    private final WardrobeService wardrobeService;
    private final CustomOAuth2MemberService customOAuth2MemberService;

    @PostMapping("/api/v1/wardrobes")
    public ResponseEntity<?> save(@Valid @RequestPart(value="wardrobeSaveRequestDto") RequestForWardrobeSave requestForWardrobeSave,
                                  @RequestPart(value="file") MultipartFile file,
                                  @LoginUser SessionMember sessionMember) throws IOException {
        return wardrobeService.save(requestForWardrobeSave, customOAuth2MemberService.getMemberBySession(sessionMember), file);
    }

    @PutMapping("/api/v1/wardrobes/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody RequestForWardrobeUpdate wardrobeUpdateRequestDto) {
        return wardrobeService.update(id, wardrobeUpdateRequestDto);
    }

    @GetMapping("/api/v1/wardrobes/{id}")
    public ResponseEntity<?> findById (@PathVariable Long id) {
        return wardrobeService.findById(id);
    }

    @GetMapping("/api/v1/wardrobes")
    public ResponseEntity<?> findAll() {
        return wardrobeService.findAll();
    }

    @PutMapping("/api/v1/wardrobes/{id}/like_cnt")
    public ResponseEntity<?> addLikeCnt(@PathVariable Long id, @LoginUser SessionMember sessionMember) {
        return communityService.changeLikeCnt(id,
                customOAuth2MemberService.getMemberBySession(sessionMember),
                PostType.Wardrobe);
    }

    @PostMapping("/api/v1/wardrobes/{id}/comment")
    public ResponseEntity<?> writeComment(@PathVariable Long id, @RequestBody CommentSaveRequestDto commentSaveRequestDto, @LoginUser SessionMember sessionMember) {
        return wardrobeService.writeComment(
                id,
                customOAuth2MemberService.getMemberBySession(sessionMember).getId(),
                commentSaveRequestDto);
    }

    @DeleteMapping("/api/v1/wardrobes/{wardrobeId}/comment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long wardrobeId, @PathVariable Long commentId, @LoginUser SessionMember sessionMember) {
        return wardrobeService.deleteComment(
                wardrobeId,
                commentId,
                customOAuth2MemberService.getMemberBySession(sessionMember));
    }

    @PostMapping("/api/v1/wardrobes/{id}/clothes")
    public ResponseEntity<?> addCloth(@PathVariable Long id,
                                      @RequestPart(value="clothSaveRequestDto") RequestForClothSave requestForClothSave,
                                      @RequestPart(value="file") MultipartFile file,
                                      @LoginUser SessionMember sessionMember) {
        return wardrobeService.addCloth(
                id,
                requestForClothSave,
                customOAuth2MemberService.getMemberBySession(sessionMember)
                ,file);
    }

    @DeleteMapping("/api/v1/wardrobes/{wardrobeId}/clothes/{clothId}")
    public ResponseEntity<?> deleteCloth(@PathVariable Long wardrobeId, @PathVariable Long clothId, @LoginUser SessionMember sessionMember) {
        return wardrobeService.deleteCloth(
                wardrobeId,
                clothId,
                customOAuth2MemberService.getMemberBySession(sessionMember));
    }
}
