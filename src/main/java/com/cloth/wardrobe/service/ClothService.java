package com.cloth.wardrobe.service;

import com.cloth.wardrobe.dto.records.RequestForRecordDelete;
import com.cloth.wardrobe.entity.clothes.Cloth;
import com.cloth.wardrobe.entity.clothes.Record;
import com.cloth.wardrobe.entity.member.Member;
import com.cloth.wardrobe.entity.common.Image;
import com.cloth.wardrobe.dto.clothes.ResponseForCloth;
import com.cloth.wardrobe.dto.clothes.ResponseForClothes;
import com.cloth.wardrobe.dto.common.Response;
import com.cloth.wardrobe.dto.records.RequestForRecordSave;
import com.cloth.wardrobe.dto.records.ResponseForRecords;
import com.cloth.wardrobe.entity.member.MemberRepository;
import com.cloth.wardrobe.exception.BadRequestException;
import com.cloth.wardrobe.exception.DoNotFoundContentException;
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

@RequiredArgsConstructor
@Service
@Slf4j
public class ClothService {

    private final RecordRepository recordRepository;
    private final ClothRepository clothRepository;
    private final MemberRepository memberRepository;
    private final PaginationService paginationService;
    private final CheckService checkService;

    @Transactional
    public ResponseEntity<ResponseForCloth> findById(Long clothId) {
        Cloth cloth = clothRepository.findById(clothId)
                .orElseThrow(() -> new BadRequestException("잘못된 요청입니다."));

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
    public ResponseEntity<ResponseForRecords> findRecordsByClothId(int pageNumber, int pageSize, Long id) {
        PageRequest pageRequest = PageRequest.of(pageNumber-1, pageSize);
        Page<Record> records = recordRepository.findRecordByClothId(pageRequest, id);

        return paginationService.convertToPaginatedRecords(records);
    }

    @Transactional
    public ResponseEntity<Response> addRecord(Long clothId, RequestForRecordSave requestForRecordSave, MultipartFile file) {
        Cloth cloth = clothRepository.findById(clothId)
                .orElseThrow(() -> new BadRequestException("잘못된 요청입니다."));
        Member member = memberRepository.findByEmail(requestForRecordSave.getEmail())
                .orElseThrow(() -> new BadRequestException("잘못된 요청입니다."));

        checkService.confirmRightApproach(member.getEmail(), cloth.getMember().getEmail());

        try {
            Image image = new Image().fileUpload(file, member.getEmail());
            cloth.addRecord(requestForRecordSave.toEntity(member, image));

            Response response = new Response();
            response.set_code(200);
            response.set_message("OK");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            throw new BadRequestException("잘못된 요청입니다.");
        }
    }

    @Transactional
    public ResponseEntity<Response> deleteRecord(Long clothId, Long recordId, RequestForRecordDelete requestForRecordDelete) {
        Cloth cloth = clothRepository.findById(clothId)
                .orElseThrow(() -> new BadRequestException("잘못된 요청입니다."));
        Member member = memberRepository.findByEmail(requestForRecordDelete.getEmail())
                .orElseThrow(() -> new BadRequestException("잘못된 요청입니다."));

        checkService.confirmRightApproach(member.getEmail(), cloth.getMember().getEmail());

        Record record = recordRepository.findById(recordId)
                .orElseThrow(() -> new BadRequestException("잘못된 요청입니다."));

        cloth.deleteRecord(record);

        Response response = new Response();
        response.set_code(200);
        response.set_message("OK");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
