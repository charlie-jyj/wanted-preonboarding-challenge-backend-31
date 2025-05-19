package com.wanted.cqrs.repository;

import com.wanted.cqrs.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/*
* JpaRepository 구현체가 생성되는 과정

- 동적 프록시 생성: Spring Data JPA는 애플리케이션 시작 시 @Repository 어노테이션이 붙은 인터페이스나 JpaRepository를 상속한 인터페이스를 찾아 자동으로 구현체를 생성합니다.
구현체 주입: Spring이 구현체를 생성하고 의존성 주입을 통해 서비스 계층 등에 주입합니다.

이러한 과정은 Spring Data JPA가 제공하는 핵심 기능 중 하나입니다. 이것이 가능한 이유는:
-> JpaRepository가 이미 SimpleJpaRepository라는 기본 구현체를 가지고 있음
Spring의 AOP와 프록시 메커니즘을 사용해 인터페이스에 정의된 메서드에 맞는 쿼리 생성
*
* */
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByParentIsNull();

    List<Category> findByLevel(Integer level);

    // 복잡한 쿼리나 조인, 최적화가 필요한 경우 사용하는 @Query 어노테이션
    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.children WHERE c.parent IS NULL")
    List<Category> findAllWithChildren();
}