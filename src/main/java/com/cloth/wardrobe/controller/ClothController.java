package com.cloth.wardrobe.controller;

import com.cloth.wardrobe.config.auth.CustomOAuth2MemberService;
import com.cloth.wardrobe.config.auth.LoginUser;
import com.cloth.wardrobe.config.auth.dto.SessionMember;
import com.cloth.wardrobe.domain.community.PostType;
import com.cloth.wardrobe.dto.records.RequestForRecordSave;
import com.cloth.wardrobe.service.ClothService;
import com.cloth.wardrobe.service.CommunityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ClothController {

    private final ClothService clothService;
    private final CustomOAuth2MemberService customOAuth2MemberService;
    private final CommunityService communityService;

    @GetMapping("/api/v1/clothes/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return clothService.findById(id);
    }
    @GetMapping("/api/v1/clothes")
    public ResponseEntity<?> findAll() {
        return clothService.findAll();
    }

    @PutMapping("/api/v1/clothes/{id}/like_cnt")
    public ResponseEntity<?> changeLikeCnt(@PathVariable Long id, @LoginUser SessionMember sessionMember) {
        return communityService.changeLikeCnt(id,
                customOAuth2MemberService.getMemberBySession(sessionMember),
                PostType.Cloth);
    }

    @PostMapping("/api/v1/clothes/{clothId}/records")
    public ResponseEntity<?> addRecordOfCloth(@PathVariable Long clothId, @LoginUser SessionMember sessionMember, @RequestBody RequestForRecordSave recordSaveRequestDto) {
        return clothService.addRecord(
                clothId,
                customOAuth2MemberService.getMemberBySession(sessionMember),
                recordSaveRequestDto);
    }

    @DeleteMapping("/api/v1/clothes/{clothId}/records/{recordId}")
    public ResponseEntity<?> deleteRecordOfCloth(@PathVariable Long clothId, @PathVariable Long recordId, @LoginUser SessionMember sessionMember) {
        return clothService.deleteRecord(
                clothId,
                recordId,
                customOAuth2MemberService.getMemberBySession(sessionMember));
    }
}

