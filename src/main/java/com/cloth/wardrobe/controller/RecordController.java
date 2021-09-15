package com.cloth.wardrobe.controller;

import com.cloth.wardrobe.dto.records.ResponseForRecords;
import com.cloth.wardrobe.service.RecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    @GetMapping("api/v1/records")
    public ResponseEntity<ResponseForRecords> findAll(@RequestParam(name = "page") int pageNumber , @RequestParam(name = "page_size") int pageSize) {
        return recordService.findAll(pageNumber, pageSize);
    }
}
