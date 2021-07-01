package com.cloth.wardrobe.repository;

import com.cloth.wardrobe.domain.clothes.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Long> {
}
