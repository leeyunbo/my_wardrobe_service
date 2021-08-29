package com.cloth.wardrobe.repository;

import com.cloth.wardrobe.domain.clothes.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {

    @Query("SELECT w FROM Record w WHERE w.cloth IS NOT NULL ORDER BY w.likeCnt ASC")
    List<Record> findAll();
}
