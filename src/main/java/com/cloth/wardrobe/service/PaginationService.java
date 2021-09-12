package com.cloth.wardrobe.service;

import com.cloth.wardrobe.domain.clothes.Cloth;
import com.cloth.wardrobe.domain.clothes.Wardrobe;
import com.cloth.wardrobe.dto.clothes.ResponseForClothes;
import com.cloth.wardrobe.dto.clothes.ResponseForWardrobes;
import com.cloth.wardrobe.dto.clothes.element.ContentForCloth;
import com.cloth.wardrobe.dto.clothes.element.ContentForWardrobe;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaginationService {

    protected ResponseEntity<ResponseForWardrobes> convertToPaginatedWardrobes(Page<Wardrobe> paginatedWardrobes) {
        List<ContentForWardrobe> wardrobes = new ArrayList<>();
        ResponseForWardrobes responseForWardrobes = new ResponseForWardrobes();

        for(Wardrobe wardrobe : paginatedWardrobes.getContent()) {
            wardrobes.add(new ContentForWardrobe(
                    wardrobe.getId(),
                    wardrobe.getName(),
                    wardrobe.getClothes().size(),
                    wardrobe.getLikeCnt(),
                    wardrobe.getMember().getName())
            );
        }

        responseForWardrobes.set_code(200);
        responseForWardrobes.set_message("OK");
        responseForWardrobes.setContents(wardrobes);
        responseForWardrobes.setTotalPages(paginatedWardrobes.getTotalPages());
        responseForWardrobes.setPageNumber(paginatedWardrobes.getNumber());
        responseForWardrobes.setSize(paginatedWardrobes.getSize());
        responseForWardrobes.setNumberOfElements(wardrobes.size());
        responseForWardrobes.setTotalElements(paginatedWardrobes.getTotalElements());
        responseForWardrobes.setIsLast(paginatedWardrobes.isLast());
        responseForWardrobes.setIsFirst(paginatedWardrobes.isFirst());

        return new ResponseEntity<>(responseForWardrobes, HttpStatus.OK);
    }

    protected ResponseEntity<ResponseForClothes> convertToPaginatedClothes(Page<Cloth> paginatedClothes) {
        List<ContentForCloth> clothes = new ArrayList<>();
        ResponseForClothes responseForClothes = new ResponseForClothes();

        for(Cloth cloth : paginatedClothes.getContent()) {
            clothes.add(new ContentForCloth(cloth));
        }

        responseForClothes.set_code(200);
        responseForClothes.set_message("OK");
        responseForClothes.setContents(clothes);
        responseForClothes.setTotalPages(paginatedClothes.getTotalPages());
        responseForClothes.setPageNumber(paginatedClothes.getNumber());
        responseForClothes.setSize(paginatedClothes.getSize());
        responseForClothes.setNumberOfElements(clothes.size());
        responseForClothes.setTotalElements(paginatedClothes.getTotalElements());
        responseForClothes.setIsLast(paginatedClothes.isLast());
        responseForClothes.setIsFirst(paginatedClothes.isFirst());

        return new ResponseEntity<>(responseForClothes, HttpStatus.OK);
    }


}
