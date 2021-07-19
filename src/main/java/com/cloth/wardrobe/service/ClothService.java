package com.cloth.wardrobe.service;

import com.cloth.wardrobe.domain.clothes.Cloth;
import com.cloth.wardrobe.domain.clothes.Record;
import com.cloth.wardrobe.domain.member.Member;
import com.cloth.wardrobe.dto.clothes.ClothGetResponseDto;
import com.cloth.wardrobe.dto.records.RecordSaveRequestDto;
import com.cloth.wardrobe.repository.ClothRepository;
import com.cloth.wardrobe.repository.RecordRepository;
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

    private final RecordRepository recordRepository;
    private final ClothRepository clothRepository;

    @Transactional
    public ClothGetResponseDto findById(Long clothId) {
        Cloth cloth = findClothById(clothId);

        return new ClothGetResponseDto(cloth);
    }

    @Transactional
    public Page<Cloth> findAll(Pageable pageable) {
        return clothRepository.findAll(pageable);
    }

    @Transactional
    public Long addRecord(Long clothId, Member member, RecordSaveRequestDto recordSaveRequestDto) {
        Cloth cloth = findClothById(clothId);

        recordSaveRequestDto.setMember(member);
        cloth.addRecord(recordSaveRequestDto.toEntity());

        return clothId;
    }

    @Transactional
    public Long deleteRecord(Long clothId, Long recordId, Member member) {
        Cloth cloth = findClothById(clothId);

        Record record = recordRepository.findById(recordId)
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 기록이 존재하지 않습니다. id=" + recordId));

        cloth.deleteRecord(record);

        return clothId;
    }

    private Cloth findClothById(Long clothId) {
        return clothRepository.findById(clothId)
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 품목이 존재하지 않습니다. id=" + clothId));
    }
}
