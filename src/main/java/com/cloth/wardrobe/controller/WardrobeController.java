package com.cloth.wardrobe.controller;

import com.cloth.wardrobe.dto.clothes.CommentSaveRequestDto;
import com.cloth.wardrobe.dto.clothes.WardrobeResponseRequestDto;
import com.cloth.wardrobe.dto.clothes.WardrobeUpdateRequestDto;
import com.cloth.wardrobe.dto.clothes.WardrobeSaveRequestDto;
import com.cloth.wardrobe.service.WardrobeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class WardrobeController {

    private final WardrobeService wardrobeService;

    @PostMapping("/api/v1/wardrobe")
    public Long save(@RequestBody WardrobeSaveRequestDto wardrobeSaveRequestDto) {
        return wardrobeService.save(wardrobeSaveRequestDto);
    }

    @PutMapping("/api/v1/wardrobe/{id}")
    public Long update(@PathVariable Long id, @RequestBody WardrobeUpdateRequestDto wardrobeUpdateRequestDto) {
        return wardrobeService.update(id, wardrobeUpdateRequestDto);
    }

    @GetMapping("/api/v1/wardrobe/{id}")
    public WardrobeResponseRequestDto findById (@PathVariable Long id) {
        return wardrobeService.findById(id);
    }

    @PutMapping("/api/v1/wardrobe/{id}/likeCnt")
    public Long addLikeCnt(@PathVariable Long id) {
        return wardrobeService.addLikeCnt(id);
    }

    @PutMapping("/api/v1/wardrobe/{id}/comment")
    public Long wrtieComment(@PathVariable Long id, @RequestBody CommentSaveRequestDto commentSaveRequestDto) {
        return wardrobeService.writeComment(id, commentSaveRequestDto);
    }
}
