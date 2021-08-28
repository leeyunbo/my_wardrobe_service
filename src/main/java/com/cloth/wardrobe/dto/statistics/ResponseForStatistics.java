package com.cloth.wardrobe.dto.statistics;

import com.cloth.wardrobe.dto.common.Response;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ResponseForStatistics extends Response {
    List<ContentForStatistics> content;
}
