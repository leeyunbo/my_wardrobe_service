package com.cloth.wardrobe.service;

import com.cloth.wardrobe.domain.clothes.Cloth;
import com.cloth.wardrobe.repository.ClothRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ClothService {

    private final ClothRepository clothRepository;

    @Transactional
    public Cloth findById(Long clothId) {
        return clothRepository.findById(clothId)
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 멤버가 존재하지 않습니다. id=" + clothId));
    }

    @Transactional
    public List<Cloth> findAll() {
        return clothRepository.findAll();
    }
}
