package com.cloth.wardrobe.dto.statistics;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContentForStatistics {
    String name;
    String type;
    int percent;
    int count;
}
