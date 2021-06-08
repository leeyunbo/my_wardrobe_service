package com.cloth.wardrobe.service;

import com.cloth.wardrobe.domain.clothes.Wardrobe;
import com.cloth.wardrobe.domain.clothes.WardrobeResponseRequestDto;
import com.cloth.wardrobe.domain.clothes.WardrobeUpdateRequestDto;
import com.cloth.wardrobe.dto.clothes.WardrobeSaveRequestDto;
import com.cloth.wardrobe.domain.clothes.WardrobeRepository;
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

    @Transactional
    public Long update(Long id, WardrobeUpdateRequestDto requestDto) {
        Wardrobe wardrobe = wardrobeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        wardrobe.update(requestDto.getImage(), requestDto.getName(), requestDto.isPublic());

        return id;
    }


    @Transactional
    public WardrobeResponseRequestDto findById(Long id) {
        Wardrobe wardrobe = wardrobeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new WardrobeResponseRequestDto(wardrobe);
    }

    @Transactional
    public Long addLikeCnt(Long id) {
        Wardrobe wardrobe = wardrobeRepository.findById(id)
                .orElseThrow(() ->
                    new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return id;
    }

}
