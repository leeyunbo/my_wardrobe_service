package com.cloth.wardrobe.service;

import com.cloth.wardrobe.domain.clothes.Record;
import com.cloth.wardrobe.domain.clothes.Wardrobe;
import com.cloth.wardrobe.dto.records.ResponseForRecord;
import com.cloth.wardrobe.dto.records.ResponseForRecords;
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

@RequiredArgsConstructor
@Service
@Slf4j
public class RecordService {

    private final RecordRepository recordRepository;

    @Transactional
    public ResponseEntity<ResponseForRecords> findAll() {
        List<ResponseForRecord> records = new ArrayList<>();
        for(Record record : recordRepository.findAll()) {
            records.add(new ResponseForRecord(record));
        }

        ResponseForRecords responseForRecords = new ResponseForRecords();
        responseForRecords.set_code(200);
        responseForRecords.set_message("OK");
        responseForRecords.setContent(records);

        return new ResponseEntity<>(responseForRecords, HttpStatus.OK);
    }
}
