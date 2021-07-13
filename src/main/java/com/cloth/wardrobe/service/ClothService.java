package com.cloth.wardrobe.service;

import com.cloth.wardrobe.domain.clothes.Cloth;
import com.cloth.wardrobe.dto.clothes.ClothGetResponseDto;
import com.cloth.wardrobe.repository.ClothRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class ClothService {

    private final ClothRepository clothRepository;

    @Transactional
    public ClothGetResponseDto findById(Long clothId) {
        Cloth cloth =
                clothRepository.findById(clothId)
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 멤버가 존재하지 않습니다. id=" + clothId));

        return new ClothGetResponseDto(cloth);
    }

    @Transactional
    public Page<Cloth> findAll(Pageable pageable) {
        return clothRepository.findAll(pageable);
    }
}
