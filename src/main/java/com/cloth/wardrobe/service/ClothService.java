package com.cloth.wardrobe.service;

import com.cloth.wardrobe.domain.clothes.Cloth;
import com.cloth.wardrobe.domain.clothes.Record;
import com.cloth.wardrobe.domain.member.Member;
import com.cloth.wardrobe.dto.clothes.ResponseForCloth;
import com.cloth.wardrobe.dto.clothes.ResponseForClothes;
import com.cloth.wardrobe.dto.clothes.element.ContentForCloth;
import com.cloth.wardrobe.dto.common.Response;
import com.cloth.wardrobe.dto.records.RequestForRecordSave;
import com.cloth.wardrobe.exception.BadRequestException;
import com.cloth.wardrobe.repository.ClothRepository;
import com.cloth.wardrobe.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ClothService {

    private final RecordRepository recordRepository;
    private final ClothRepository clothRepository;

    @Transactional
    public ResponseEntity<?> findById(Long clothId) {
        Cloth cloth = clothRepository.findById(clothId).orElseThrow(() -> new IllegalArgumentException("해당 품목이 존재하지 않습니다. id=" + clothId));
        ResponseForCloth responseForCloth = new ResponseForCloth(cloth);
        responseForCloth.set_code(200);
        responseForCloth.set_message("OK");

        return new ResponseEntity<>(responseForCloth, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> findAll() {
        List<ContentForCloth> clothes = new ArrayList<>();

        for(Cloth cloth : clothRepository.findAll()) {
            clothes.add(new ContentForCloth(cloth.getId(), cloth.getImage(), cloth.getBuyingDate(), cloth.getClothBrand()));
        }

        ResponseForClothes responseForClothes = new ResponseForClothes();
        responseForClothes.set_code(200);
        responseForClothes.set_message("OK");
        responseForClothes.setContents(clothes);

        return new ResponseEntity<>(responseForClothes, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> addRecord(Long clothId, Member member, RequestForRecordSave recordSaveRequestDto) {
        Cloth cloth = clothRepository.findById(clothId)
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 품목이 존재하지 않습니다. id=" + clothId));

        if (!cloth.getMember().getEmail().equals(member.getEmail())) throw new BadRequestException("올바르지 않은 접근입니다.");

        recordSaveRequestDto.setMember(member);
        cloth.addRecord(recordSaveRequestDto.toEntity());

        Response response = new Response();
        response.set_code(200);
        response.set_message("OK");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> deleteRecord(Long clothId, Long recordId, Member member) {
        Cloth cloth = clothRepository.findById(clothId)
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 품목이 존재하지 않습니다. id=" + clothId));

        if (!cloth.getMember().getEmail().equals(member.getEmail())) throw new BadRequestException("올바르지 않은 접근입니다.");

        Record record = recordRepository.findById(recordId)
                    .orElseThrow(() ->
                            new IllegalArgumentException("해당 기록이 존재하지 않습니다. id=" + recordId));

        cloth.deleteRecord(record);

        Response response = new Response();
        response.set_code(200);
        response.set_message("OK");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
