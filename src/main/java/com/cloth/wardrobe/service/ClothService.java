package com.cloth.wardrobe.service;

import com.cloth.wardrobe.domain.clothes.Cloth;
import com.cloth.wardrobe.domain.clothes.Record;
import com.cloth.wardrobe.domain.clothes.Wardrobe;
import com.cloth.wardrobe.domain.member.Member;
import com.cloth.wardrobe.domain.common.Image;
import com.cloth.wardrobe.dto.clothes.ResponseForCloth;
import com.cloth.wardrobe.dto.clothes.ResponseForClothes;
import com.cloth.wardrobe.dto.clothes.element.ContentForCloth;
import com.cloth.wardrobe.dto.common.Response;
import com.cloth.wardrobe.dto.records.RequestForRecordSave;
import com.cloth.wardrobe.dto.records.ResponseForRecords;
import com.cloth.wardrobe.dto.records.element.ContentForRecord;
import com.cloth.wardrobe.exception.BadRequestException;
import com.cloth.wardrobe.exception.DoNotFoundContentException;
import com.cloth.wardrobe.exception.WrongAccessException;
import com.cloth.wardrobe.repository.ClothRepository;
import com.cloth.wardrobe.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class ClothService {

    private final RecordRepository recordRepository;
    private final ClothRepository clothRepository;
    private final PaginationService paginationService;

    @Transactional
    public ResponseEntity<ResponseForCloth> findById(Long clothId) {
        Cloth cloth = clothRepository.findById(clothId)
                .orElseThrow(() -> new BadRequestException("해당 품목이 존재하지 않습니다. id=" + clothId));

        ResponseForCloth responseForCloth = new ResponseForCloth(cloth);
        responseForCloth.set_code(200);
        responseForCloth.set_message("OK");

        return new ResponseEntity<>(responseForCloth, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<ResponseForClothes> findAll(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber-1, pageSize);
        Page<Cloth> paginatedClothes = clothRepository.findAll(pageRequest);

        return paginationService.convertToPaginatedClothes(paginatedClothes);
    }

    @Transactional
    public ResponseEntity<ResponseForRecords> findRecordsByClothId(Long id) {
        Cloth cloth = clothRepository.findById(id)
                .orElseThrow(() -> new DoNotFoundContentException("해당 아이템이 존재하지 않습니다. id=" + id));

        ResponseForRecords responseForRecords = new ResponseForRecords();
        List<ContentForRecord> contents =
                cloth.getRecords()
                        .stream()
                        .map(ContentForRecord::new)
                        .collect(Collectors.toList());

        responseForRecords.setContent(contents);
        responseForRecords.set_code(200);
        responseForRecords.set_message("OK");

        return new ResponseEntity<>(responseForRecords, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Response> addRecord(Long clothId, Member member, RequestForRecordSave requestForRecordSave, MultipartFile file) {
        Cloth cloth = clothRepository.findById(clothId)
                .orElseThrow(() -> new DoNotFoundContentException("해당 품목이 존재하지 않습니다. id=" + clothId));

        if (cloth.getMember().getEmail().equals(member.getEmail())) {
            try {
                Image image = new Image().fileUpload(file, member.getEmail());
                requestForRecordSave.setImage(image);
                requestForRecordSave.setMember(member);
                cloth.addRecord(requestForRecordSave.toEntity());

                Response response = new Response();
                response.set_code(200);
                response.set_message("OK");

                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (IOException e) {
                throw new BadRequestException("올바르지 않은 요청입니다.");
            }
        }
        else {
            throw new WrongAccessException("올바르지 않은 접근입니다.");
        }
    }

    @Transactional
    public ResponseEntity<Response> deleteRecord(Long clothId, Long recordId, Member member) {
        Cloth cloth = clothRepository.findById(clothId)
                .orElseThrow(() -> new DoNotFoundContentException("해당 품목이 존재하지 않습니다."));

        if (cloth.getMember().getEmail().equals(member.getEmail())) {

            Record record = recordRepository.findById(recordId)
                    .orElseThrow(() -> new DoNotFoundContentException("해당 기록이 존재하지 않습니다."));

            cloth.deleteRecord(record);

            Response response = new Response();
            response.set_code(200);
            response.set_message("OK");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            throw new BadRequestException("올바르지 않은 접근입니다.");
        }
    }
}
