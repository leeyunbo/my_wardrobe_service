package com.cloth.wardrobe.controller;

import com.cloth.wardrobe.config.auth.CustomOAuth2MemberService;
import com.cloth.wardrobe.config.auth.LoginUser;
import com.cloth.wardrobe.config.auth.dto.SessionMember;
import com.cloth.wardrobe.entity.community.PostType;
import com.cloth.wardrobe.dto.clothes.RequestForClothSave;
import com.cloth.wardrobe.dto.clothes.ResponseForClothes;
import com.cloth.wardrobe.dto.community.RequestForCommentSave;
import com.cloth.wardrobe.dto.clothes.RequestForWardrobeUpdate;
import com.cloth.wardrobe.dto.clothes.RequestForWardrobeSave;
import com.cloth.wardrobe.service.PostService;
import com.cloth.wardrobe.service.WardrobeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/v1/wardrobes")
@RequiredArgsConstructor
public class WardrobeController {

    private final WardrobeService wardrobeService;
    private final CustomOAuth2MemberService customOAuth2MemberService;

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestPart(value="wardrobeSaveRequestDto") RequestForWardrobeSave requestForWardrobeSave,
                                  @RequestPart(value="file") MultipartFile file,
                                  @LoginUser SessionMember sessionMember) throws IOException {
        return wardrobeService.save(
                requestForWardrobeSave,
                customOAuth2MemberService.getMemberBySession(sessionMember),
                file);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody RequestForWardrobeUpdate wardrobeUpdateRequestDto) {
        return wardrobeService.update(
                id,
                wardrobeUpdateRequestDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById (@PathVariable Long id) {
        return wardrobeService.findById(id);
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(name="page_number") int pageNumber, @RequestParam(name="page_size") int pageSize) {
        return wardrobeService.findAll(
                pageNumber,
                pageSize);
    }

    @GetMapping("/{id}/clothes")
    public ResponseEntity<ResponseForClothes> getClothByWardrobeId(@RequestParam(name="page_number") int pageNumber, @RequestParam(name="page_size") int pageSize, @PathVariable Long id) {
        return wardrobeService.findClothesByWardrobeId(
                pageNumber,
                pageSize,
                id);
    }

    @PostMapping("/{id}/clothes")
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

    @DeleteMapping("/{wardrobeId}/clothes/{clothId}")
    public ResponseEntity<?> deleteCloth(@PathVariable Long wardrobeId, @PathVariable Long clothId, @LoginUser SessionMember sessionMember) {
        return wardrobeService.deleteCloth(
                wardrobeId,
                clothId,
                customOAuth2MemberService.getMemberBySession(sessionMember));
    }
}
