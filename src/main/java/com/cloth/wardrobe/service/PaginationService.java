package com.cloth.wardrobe.service;

import com.cloth.wardrobe.dto.community.ResponseForComments;
import com.cloth.wardrobe.dto.community.element.ContentForComment;
import com.cloth.wardrobe.entity.clothes.Cloth;
import com.cloth.wardrobe.entity.clothes.Record;
import com.cloth.wardrobe.entity.clothes.Wardrobe;
import com.cloth.wardrobe.dto.clothes.ResponseForClothes;
import com.cloth.wardrobe.dto.clothes.ResponseForWardrobes;
import com.cloth.wardrobe.dto.clothes.element.ContentForCloth;
import com.cloth.wardrobe.dto.clothes.element.ContentForWardrobe;
import com.cloth.wardrobe.dto.records.ResponseForRecords;
import com.cloth.wardrobe.dto.records.element.ContentForRecord;
import com.cloth.wardrobe.entity.community.Comment;
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

    protected ResponseEntity<ResponseForRecords> convertToPaginatedRecords(Page<Record> paginatedRecords) {
        List<ContentForRecord> records = new ArrayList<>();
        ResponseForRecords responseForRecords = new ResponseForRecords();

        for(Record record : paginatedRecords.getContent()) {
            records.add(new ContentForRecord(record));
        }

        responseForRecords.set_code(200);
        responseForRecords.set_message("OK");
        responseForRecords.setContents(records);
        responseForRecords.setTotalPages(paginatedRecords.getTotalPages());
        responseForRecords.setPageNumber(paginatedRecords.getNumber());
        responseForRecords.setSize(paginatedRecords.getSize());
        responseForRecords.setNumberOfElements(records.size());
        responseForRecords.setTotalElements(paginatedRecords.getTotalElements());
        responseForRecords.setIsLast(paginatedRecords.isLast());
        responseForRecords.setIsFirst(paginatedRecords.isFirst());

        return new ResponseEntity<>(responseForRecords, HttpStatus.OK);
    }

    protected ResponseEntity<ResponseForComments> convertToPaginatedComments(Page<Comment> paginatedComments) {
        List<ContentForComment> comments = new ArrayList<>();
        ResponseForComments responseForComments = new ResponseForComments();

        for(Comment comment : paginatedComments.getContent()) {
            comments.add(new ContentForComment(comment));
        }

        responseForComments.set_code(200);
        responseForComments.set_message("OK");
        responseForComments.setContents(comments);
        responseForComments.setTotalPages(paginatedComments.getTotalPages());
        responseForComments.setPageNumber(paginatedComments.getNumber());
        responseForComments.setSize(paginatedComments.getSize());
        responseForComments.setNumberOfElements(comments.size());
        responseForComments.setTotalElements(paginatedComments.getTotalElements());
        responseForComments.setIsLast(paginatedComments.isLast());
        responseForComments.setIsFirst(paginatedComments.isFirst());

        return new ResponseEntity<>(responseForComments, HttpStatus.OK);
    }


}
