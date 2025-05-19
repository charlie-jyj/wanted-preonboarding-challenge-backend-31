package com.wanted.cqrs.repository;

import com.wanted.cqrs.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByStatus(String status, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:keyword% OR p.shortDescription LIKE %:keyword% OR p.fullDescription LIKE %:keyword%")
    Page<Product> search(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT p FROM Product p JOIN p.categories pc WHERE pc.category.id = :categoryId")
    Page<Product> findByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);
}