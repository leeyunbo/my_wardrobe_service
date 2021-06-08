package com.cloth.wardrobe.controller;

import com.cloth.wardrobe.domain.clothes.WardrobeResponseRequestDto;
import com.cloth.wardrobe.domain.clothes.WardrobeUpdateRequestDto;
import com.cloth.wardrobe.dto.clothes.WardrobeSaveRequestDto;
import com.cloth.wardrobe.service.WardrobeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class WardrobeController {

    private final WardrobeService wardrobeService;

    @PostMapping("/api/v1/wardrobe")
    public Long save(@RequestBody WardrobeSaveRequestDto requestDto) {
        return wardrobeService.save(requestDto);
    }

    @PutMapping("/api/v1/wardrobe/{id}")
    public Long update(@PathVariable Long id, @RequestBody WardrobeUpdateRequestDto wardrobeUpdateRequestDto) {
        return wardrobeService.update(id, wardrobeUpdateRequestDto);
    }

    @GetMapping("/api/v1/wardrobe/{id}")
    public WardrobeResponseRequestDto findById (@PathVariable Long id) {
        return wardrobeService.findById(id);
    }




}
