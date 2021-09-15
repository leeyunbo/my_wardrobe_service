package com.cloth.wardrobe.controller;

import com.cloth.wardrobe.config.auth.CustomOAuth2MemberService;
import com.cloth.wardrobe.config.auth.LoginUser;
import com.cloth.wardrobe.config.auth.dto.SessionMember;
import com.cloth.wardrobe.entity.community.PostType;
import com.cloth.wardrobe.dto.records.RequestForRecordSave;
import com.cloth.wardrobe.dto.records.ResponseForRecords;
import com.cloth.wardrobe.service.ClothService;
import com.cloth.wardrobe.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ClothController {

    private final ClothService clothService;
    private final CustomOAuth2MemberService customOAuth2MemberService;
    private final PostService communityService;

    @GetMapping("/api/v1/clothes/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return clothService.findById(id);
    }

    @GetMapping("/api/v1/clothes")
    public ResponseEntity<?> findAll(@RequestParam(name="page_number") int pageNumber, @RequestParam(name="page_size") int pageSize) {
        return clothService.findAll(pageNumber, pageSize);
    }

    @GetMapping("/api/v1/clothes/{id}/records")
    public ResponseEntity<ResponseForRecords> findRecordsByClothId(@RequestParam(name="page_number") int pageNumber, @RequestParam(name="page_size") int pageSize, @PathVariable Long id) {
        return clothService.findRecordsByClothId(pageNumber, pageSize, id);
    }

    @PutMapping("/api/v1/clothes/{id}/like_cnt")
    public ResponseEntity<?> changeLikeCnt(@PathVariable Long id, @LoginUser SessionMember sessionMember) {
        return communityService.changeLikeCnt(id,
                customOAuth2MemberService.getMemberBySession(sessionMember));
    }

    @PostMapping("/api/v1/clothes/{clothId}/records")
    public ResponseEntity<?> addRecordOfCloth(@PathVariable Long clothId,
                                              @LoginUser SessionMember sessionMember,
                                              @RequestPart RequestForRecordSave requestForRecordSave,
                                              @RequestPart(value="file") MultipartFile file ) {
        return clothService.addRecord(
                clothId,
                customOAuth2MemberService.getMemberBySession(sessionMember),
                requestForRecordSave,
                file);
    }

    @DeleteMapping("/api/v1/clothes/{clothId}/records/{recordId}")
    public ResponseEntity<?> deleteRecordOfCloth(@PathVariable Long clothId, @PathVariable Long recordId, @LoginUser SessionMember sessionMember) {
        return clothService.deleteRecord(
                clothId,
                recordId,
                customOAuth2MemberService.getMemberBySession(sessionMember));
    }
}

