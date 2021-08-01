package com.cloth.wardrobe.controller;

import com.cloth.wardrobe.config.auth.CustomOAuth2MemberService;
import com.cloth.wardrobe.config.auth.LoginUser;
import com.cloth.wardrobe.config.auth.dto.SessionMember;
import com.cloth.wardrobe.domain.community.PostType;
import com.cloth.wardrobe.dto.records.RecordSaveRequestDto;
import com.cloth.wardrobe.service.ClothService;
import com.cloth.wardrobe.service.CommunityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ClothController {

    private final ClothService clothService;
    private final CustomOAuth2MemberService customOAuth2MemberService;
    private final CommunityService communityService;

    @PostMapping("/api/v1/clothes")
    public ResponseEntity<?> findAll(Pageable pageable) {
        return clothService.findAll(pageable);
    }

    @PutMapping("/api/v1/clothes/{id}/like_cnt")
    public ResponseEntity<?> changeLikeCnt(@PathVariable Long id, @LoginUser SessionMember sessionMember) {
        return communityService.changeLikeCnt(id,
                customOAuth2MemberService.getMemberBySession(sessionMember),
                PostType.Cloth);
    }

    @PostMapping("/api/v1/clothes/{clothId}/records")
    public ResponseEntity<?> addRecordOfCloth(@PathVariable Long clothId, @LoginUser SessionMember sessionMember, @RequestBody RecordSaveRequestDto recordSaveRequestDto) {
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

