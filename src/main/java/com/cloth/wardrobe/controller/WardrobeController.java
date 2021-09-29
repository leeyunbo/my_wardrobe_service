package com.cloth.wardrobe.controller;

import com.cloth.wardrobe.dto.common.Response;
import com.cloth.wardrobe.dto.clothes.RequestForClothSave;
import com.cloth.wardrobe.dto.clothes.ResponseForClothes;
import com.cloth.wardrobe.dto.wardrobe.RequestForWardrobeUpdate;
import com.cloth.wardrobe.dto.wardrobe.RequestForWardrobeSave;
import com.cloth.wardrobe.service.WardrobeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/v1/wardrobes")
@RequiredArgsConstructor
public class WardrobeController {

    private final WardrobeService wardrobeService;

    @PostMapping
    public ResponseEntity<?> save(@RequestPart(value="data") RequestForWardrobeSave requestForWardrobeSave,
                                  @RequestPart(value="file") MultipartFile file) throws IOException {
        return wardrobeService.save(requestForWardrobeSave, file);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody RequestForWardrobeUpdate wardrobeUpdateRequestDto) {
        return wardrobeService.update(id, wardrobeUpdateRequestDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById (@PathVariable Long id) {
        return wardrobeService.findById(id);
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(name="page_number") int pageNumber, @RequestParam(name="page_size") int pageSize) {
        return wardrobeService.findAll(pageNumber, pageSize);
    }

    @GetMapping("/{id}/clothes")
    public ResponseEntity<ResponseForClothes> getClothByWardrobeId(@RequestParam(name="page_number") int pageNumber, @RequestParam(name="page_size") int pageSize, @PathVariable Long id) {
        return wardrobeService.findClothesByWardrobeId(pageNumber, pageSize, id);
    }

    @PostMapping("/{id}/cloth")
    public ResponseEntity<Response> addCloth(@PathVariable Long id,
                                             @RequestPart(value="data") RequestForClothSave requestForClothSave,
                                             @RequestPart(value="file") MultipartFile file) {
        return wardrobeService.addCloth(id, requestForClothSave,file);
    }

    @DeleteMapping("/{wardrobe_id}/clothes/{cloth_id}")
    public ResponseEntity<?> deleteCloth(@PathVariable(name = "wardrobe_id") Long wardrobeId, @PathVariable(name = "cloth_id") Long clothId, @RequestBody RequestForWardrobeUpdate requestForWardrobeUpdate) {
        return wardrobeService.deleteCloth(wardrobeId, clothId, requestForWardrobeUpdate);
    }
}
