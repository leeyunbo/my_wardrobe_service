package com.cloth.wardrobe.service;

import com.cloth.wardrobe.domain.clothes.Record;
import com.cloth.wardrobe.domain.clothes.Wardrobe;
import com.cloth.wardrobe.dto.records.ResponseForRecord;
import com.cloth.wardrobe.dto.records.ResponseForRecords;
import com.cloth.wardrobe.dto.records.element.ContentForRecord;
import com.cloth.wardrobe.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class RecordService {

    private final RecordRepository recordRepository;

    @Transactional
    public ResponseEntity<ResponseForRecords> findAll() {
        List<Record> records = recordRepository.findAll();
        List<ContentForRecord> contents =
                records.stream()
                        .map(ContentForRecord::new)
                        .collect(Collectors.toList());

        ResponseForRecords responseForRecords = new ResponseForRecords();
        responseForRecords.set_code(200);
        responseForRecords.set_message("OK");
        responseForRecords.setContent(contents);

        return new ResponseEntity<>(responseForRecords, HttpStatus.OK);
    }
}
