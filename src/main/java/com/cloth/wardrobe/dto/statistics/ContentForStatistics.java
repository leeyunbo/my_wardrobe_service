package com.cloth.wardrobe.dto.statistics;

import com.cloth.wardrobe.entity.statistics.StatisticsType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContentForStatistics {
    String name;
    StatisticsType type;
    Double percent;
    Long count;
}
