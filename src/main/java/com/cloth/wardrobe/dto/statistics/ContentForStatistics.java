package com.cloth.wardrobe.dto.statistics;

import com.cloth.wardrobe.domain.statistics.StatisticsType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContentForStatistics {
    String name;
    StatisticsType type;
    double percent;
    int count;
}
