package com.cloth.wardrobe.service;

import com.cloth.wardrobe.domain.clothes.Cloth;
import com.cloth.wardrobe.domain.clothes.Record;
import com.cloth.wardrobe.domain.member.Member;
import com.cloth.wardrobe.dto.clothes.ResponseForCloth;
import com.cloth.wardrobe.dto.records.RecordSaveRequestDto;
import com.cloth.wardrobe.exception.BadRequestException;
import com.cloth.wardrobe.repository.ClothRepository;
import com.cloth.wardrobe.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ClothService {

    private final RecordRepository recordRepository;
    private final ClothRepository clothRepository;

    @Transactional
    public ResponseForCloth findById(Long clothId) {
        Cloth cloth = findClothById(clothId);

        return new ResponseForCloth(cloth);
    }

    @Transactional
    public ResponseEntity<?> findAll() {
        List<Cloth> cloths = clothRepository.findAll();
        return new ResponseEntity<>(cloths, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> addRecord(Long clothId, Member member, RecordSaveRequestDto recordSaveRequestDto) {
        Cloth cloth = findClothById(clothId);

        if (!cloth.getMember().getEmail().equals(member.getEmail()))
            throw new BadRequestException("올바르지 않은 접근입니다.");

        recordSaveRequestDto.setMember(member);
        cloth.addRecord(recordSaveRequestDto.toEntity());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> deleteRecord(Long clothId, Long recordId, Member member) {
        Cloth cloth = findClothById(clothId);

        if (!cloth.getMember().getEmail().equals(member.getEmail()))
            throw new BadRequestException("올바르지 않은 접근입니다.");

        Record record = recordRepository.findById(recordId)
                    .orElseThrow(() ->
                            new IllegalArgumentException("해당 기록이 존재하지 않습니다. id=" + recordId));

        cloth.deleteRecord(record);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Cloth findClothById(Long clothId) {
        return clothRepository.findById(clothId)
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 품목이 존재하지 않습니다. id=" + clothId));
    }
}
