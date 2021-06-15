package com.cloth.wardrobe.controller;

import com.cloth.wardrobe.domain.clothes.Wardrobe;
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

    @PostMapping("/api/v1/wardrobes")
    public Long save(@RequestBody WardrobeSaveRequestDto wardrobeSaveRequestDto, HttpSession httpSession) {
        log.info("--------------------------------------save()-----------------------------------");
        return wardrobeService.save(wardrobeSaveRequestDto, httpSession);
    }

    @PutMapping("/api/v1/wardrobes/{id}")
    public Long update(@PathVariable Long id, @RequestBody WardrobeUpdateRequestDto wardrobeUpdateRequestDto) {
        return wardrobeService.update(id, wardrobeUpdateRequestDto);
    }

    @GetMapping("/api/v1/wardrobes/{id}")
    public WardrobeResponseRequestDto findById (@PathVariable Long id) {
        return wardrobeService.findById(id);
    }

    @GetMapping("/api/v1/wardrobes")
    public Page<Wardrobe> findAll(Pageable pageable) {
        return wardrobeService.findAll(pageable);
    }

    @PutMapping("/api/v1/wardrobes/{id}/like_cnt")
    public Long addLikeCnt(@PathVariable Long id) {
        return wardrobeService.addLikeCnt(id);
    }

    @DeleteMapping("/api/v1/wardrobes/{id}/like_cnt")
    public Long delLikeCnt(@PathVariable Long id) {
        return wardrobeService.delLikeCnt(id);
    }

    @GetMapping("/api/v1/wardrobes/{id}/comment")
    public List<CommentResponseRequestDto> getComments(@PathVariable Long id) {
        return wardrobeService.getComments(id);
    }

    @PutMapping("/api/v1/wardrobes/{id}/comment")
    public Long writeComment(@PathVariable Long id, @RequestBody CommentSaveRequestDto commentSaveRequestDto) {
        return wardrobeService.writeComment(id, commentSaveRequestDto);
    }

    @DeleteMapping("/api/v1/wardrobes/{wardrobeId}/comment/{commentId}")
    public Long deleteComment(@PathVariable Long wardrobeId, @PathVariable Long commentId) {
        return wardrobeService.deleteComment(wardrobeId, commentId);
    }
}
