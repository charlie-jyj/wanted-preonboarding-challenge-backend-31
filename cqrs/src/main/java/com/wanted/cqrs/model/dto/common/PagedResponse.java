package com.wanted.cqrs.model.dto.common;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 페이지네이션 정보를 포함하는 응답 데이터 래퍼 클래스
 *
 * @param <T> 페이지네이션된 아이템의 타입
 */
@Getter
@Builder
public class PagedResponse<T> {

    private final List<T> items;
    private final PaginationInfo pagination;

    /**
     * Spring Data의 Page 객체로부터 PagedResponse 객체 생성
     *
     * @param page Spring Data의 Page 객체
     * @return 페이지네이션 정보가 포함된 응답 객체
     */
    public static <T> PagedResponse<T> from(Page<T> page) {
        PaginationInfo paginationInfo = PaginationInfo.builder()
                .totalItems(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .currentPage(page.getNumber() + 1) // Spring Data JPA는 0부터 시작하므로 1 추가
                .perPage(page.getSize())
                .build();

        return PagedResponse.<T>builder()
                .items(page.getContent())
                .pagination(paginationInfo)
                .build();
    }

    /**
     * 페이지네이션 정보
     */
    @Getter
    @Builder
    public static class PaginationInfo {
        private final long totalItems;
        private final int totalPages;
        private final int currentPage;
        private final int perPage;
    }
}
