package com.cloth.wardrobe.service;

import com.cloth.wardrobe.dto.clothes.WardrobeSaveRequestDto;
import com.cloth.wardrobe.repository.WardrobeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class WardrobeService {

    private final WardrobeRepository wardrobeRepository;

    @Transactional
    public Long save(WardrobeSaveRequestDto requestDto) {
        return wardrobeRepository.save(requestDto.toEntity()).getId();
    }

}
