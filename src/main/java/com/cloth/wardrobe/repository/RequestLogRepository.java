package com.cloth.wardrobe.repository;

import com.cloth.wardrobe.entity.common.RequestLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestLogRepository extends JpaRepository<RequestLog, String> {

}
