package com.cloth.wardrobe.service;

import com.cloth.wardrobe.entity.clothes.Record;
import com.cloth.wardrobe.dto.records.ResponseForRecords;
import com.cloth.wardrobe.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class RecordService {

    private final RecordRepository recordRepository;
    private final PaginationService paginationService;

    @Transactional
    public ResponseEntity<ResponseForRecords> findAll(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber-1, pageSize);
        Page<Record> records = recordRepository.findAll(pageRequest);
        return paginationService.convertToPaginatedRecords(records);
    }
}
