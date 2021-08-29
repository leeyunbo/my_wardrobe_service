package com.cloth.wardrobe.dto.statistics;

import com.cloth.wardrobe.dto.common.Response;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class ResponseForStatistics extends Response {
    List<ContentForStatistics> content;
}
