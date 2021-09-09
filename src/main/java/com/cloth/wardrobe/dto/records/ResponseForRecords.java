package com.cloth.wardrobe.dto.records;

import com.cloth.wardrobe.dto.common.Response;
import com.cloth.wardrobe.dto.records.element.ContentForRecord;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class ResponseForRecords extends Response {
    List<ContentForRecord> content;
}
