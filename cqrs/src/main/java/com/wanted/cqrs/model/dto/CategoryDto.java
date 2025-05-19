package com.wanted.cqrs.model.dto;

/*
*
*DTO와 Entity를 분리하는 이유

- 관심사 분리
Entity: 데이터베이스 테이블 구조를 표현하고 영속성 관리에 중점
DTO: 클라이언트와 서버 간의 데이터 전송에 중점

- 의존성 감소
클라이언트 요구사항이 변경되더라도 도메인 모델(Entity)에 영향을 주지 않음
API 스펙과 내부 데이터 모델을 독립적으로 발전시킬 수 있음


- 데이터 노출 제어
Entity는 종종 민감한 데이터를 포함하지만, DTO는 클라이언트에게 필요한 데이터만 포함
Entity의 특정 필드만 포함하거나 여러 Entity의 데이터를 조합한 DTO를 생성 가능


- 유연한 API 설계
동일한 Entity에 대해 다양한 DTO를 통해 다양한 뷰 제공 가능
예: 상품 요약 정보용 DTO, 상품 상세 정보용 DTO 등


- 효율적인 데이터 전송
필요한 데이터만 포함하여 네트워크 트래픽 감소
불필요한 데이터를 제외하여 직렬화/역직렬화 성능 향상


- 순환 참조 방지
Entity 간의 양방향 관계로 인한 JSON 직렬화 시 순환 참조 문제 해결
*
* */

import jdk.jshell.Snippet;

import java.util.List;

public class CategoryDto {
    public static Snippet builder() {
        return null;
    }

    public void setChildren(List<CategoryDto> childrenDto) {
    }
}
