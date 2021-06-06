package com.cloth.wardrobe.controller;

import com.cloth.wardrobe.dto.clothes.WardrobeSaveRequestDto;
import com.cloth.wardrobe.service.WardrobeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class WardrobeController {

    private final WardrobeService wardrobeService;

    @PostMapping("/api/v1/wardrobe/{memberId}")
    public Long save(@RequestBody WardrobeSaveRequestDto requestDto) {
        return wardrobeService.save(requestDto);
    }




}
