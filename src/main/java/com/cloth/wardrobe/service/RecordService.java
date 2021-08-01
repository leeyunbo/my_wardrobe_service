package com.cloth.wardrobe.service;

import com.cloth.wardrobe.domain.clothes.Record;
import com.cloth.wardrobe.domain.clothes.Wardrobe;
import com.cloth.wardrobe.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class RecordService {

    private final RecordRepository recordRepository;

    @Transactional
    public ResponseEntity<?> findAll(Pageable pageable) {
        try {
            Page<Record> records = recordRepository.findAll(pageable);
            return new ResponseEntity<>(records, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
