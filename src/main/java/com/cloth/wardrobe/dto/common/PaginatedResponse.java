package com.cloth.wardrobe.dto.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginatedResponse {
    private Integer _code;
    private String _message;
    private Integer totalPages; // 총 페이지 수
    private Integer pageNumber; // 현재 페이지
    private Integer numberOfElements; // 현재 페이지의 아이템 개수
    private Integer size; // 한 페이지의 최대 아이템 수
    private Long totalElements; // 총 아이템 수
    private Boolean isLast; // 마지막 페이지 여부
    private Boolean isFirst; // 첫 페이지 여부

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber + 1;
    }
}
