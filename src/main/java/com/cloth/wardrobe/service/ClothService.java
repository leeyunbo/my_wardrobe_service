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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> findAll(Pageable pageable) {
        try {
            Page<Cloth> cloths = clothRepository.findAll(pageable);
            return new ResponseEntity<>(cloths, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<?> addRecord(Long clothId, Member member, RecordSaveRequestDto recordSaveRequestDto) {
        try {
            Cloth cloth = findClothById(clothId);

            if (!cloth.getMember().getEmail().equals(member.getEmail())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            recordSaveRequestDto.setMember(member);
            cloth.addRecord(recordSaveRequestDto.toEntity());
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> deleteRecord(Long clothId, Long recordId, Member member) {
        try {
            Cloth cloth = findClothById(clothId);

            if (!cloth.getMember().getEmail().equals(member.getEmail())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            Record record = recordRepository.findById(recordId)
                    .orElseThrow(() ->
                            new IllegalArgumentException("해당 기록이 존재하지 않습니다. id=" + recordId));

            cloth.deleteRecord(record);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Cloth findClothById(Long clothId) {
        return clothRepository.findById(clothId)
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 품목이 존재하지 않습니다. id=" + clothId));
    }
}
