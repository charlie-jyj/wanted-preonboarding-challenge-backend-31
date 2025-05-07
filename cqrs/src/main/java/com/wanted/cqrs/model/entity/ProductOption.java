package com.wanted.cqrs.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product_options")
@Getter
@Setter
public class ProductOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_group_id")
    private ProductOptionGroup optionGroup;

    private String name;

    @Column(name = "additional_price")
    private BigDecimal additionalPrice;

    private String sku;

    private Integer stock;

    @Column(name = "display_order")
    private Integer displayOrder;

    @OneToMany(mappedBy = "option", cascade = CascadeType.ALL)
    private List<ProductImage> images = new ArrayList<>();
}