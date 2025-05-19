package com.wanted.cqrs.model.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

/**
 * API 응답의 공통 포맷을 정의하는 클래스
 * 모든 API 응답은 이 클래스 형태로 반환됩니다.
 *
 * [참고]
 * @JsonInclude의 역할
 * @JsonInclude 어노테이션은 객체를 JSON으로 변환할 때 특정 필드를 포함할지 말지를 결정하는 데 사용됩니다. 주로 null 값이나 빈 컬렉션 등의 특정 값을 JSON 출력에서 제외하고 싶을 때 유용합니다.
 * 파라미터 값들
 * @JsonInclude는 JsonInclude.Include 열거형 값을 파라미터로 받습니다. 주요 값들은 다음과 같습니다:
 *
 * Include.ALWAYS - 항상 필드를 포함 (기본값)
 * Include.NON_NULL - null이 아닌 값만 포함
 * Include.NON_EMPTY - 비어있지 않은 값만 포함 (null, 빈 문자열, 빈 컬렉션, 빈 배열 등을 제외)
 * Include.NON_DEFAULT - 기본값이 아닌 값만 포함 (0, false 등의 기본값을 제외)
 * Include.NON_ABSENT - 존재하는 값만 포함 (Optional.empty() 등을 제외)
 * Include.CUSTOM - 사용자 정의 필터를 사용하여 포함 여부 결정
 * Include.USE_DEFAULTS - 전역 설정을 사용
 *
 * @Data
 * @JsonInclude(JsonInclude.Include.NON_NULL)  // 클래스 레벨에서 적용
 * public class User {
 *     private String name;
 *
 *     @JsonInclude(JsonInclude.Include.NON_EMPTY)  // 필드 레벨에서 적용
 *     private String email;
 *
 *     private Integer age;
 * }
 *
 * @param <T> 응답 데이터의 타입
 */
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) // null 값은 JSON 응답에서 제외
public class ApiResponse<T> {

    private final boolean success;
    private final T data;
    private final String message;
    private final ErrorResponse error;

    /**
     * 성공 응답 생성
     *
     * @param data 응답 데이터
     * @param message 응답 메시지
     * @return 성공 응답 객체
     */
    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .success(true)
                .data(data)
                .message(message)
                .build();
    }
    
    /**
     * 성공 응답 생성 (메시지 기본값 사용)
     *
     * @param data 응답 데이터
     * @return 성공 응답 객체
     */
    public static <T> ApiResponse<T> success(T data) {
        return success(data, "요청이 성공적으로 처리되었습니다.");
    }

    /**
     * 실패 응답 생성
     *
     * @param error 에러 정보
     * @return 실패 응답 객체
     */
    public static <T> ApiResponse<T> error(ErrorResponse error) {
        return ApiResponse.<T>builder()
                .success(false)
                .error(error)
                .build();
    }

}
