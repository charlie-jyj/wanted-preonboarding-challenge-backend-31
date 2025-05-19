package com.wanted.cqrs.service;

import com.wanted.cqrs.model.dto.CategoryDto;
import com.wanted.cqrs.model.entity.Category;
import com.wanted.cqrs.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDto> getAllCategories(Integer level) {
        List<Category> categories;

        if (level != null) {
            categories = categoryRepository.findByLevel(level);
            return categories.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        } else {
            categories = categoryRepository.findAllWithChildren();
            return categories.stream()
                    .map(this::convertToDtoWithChildren)
                    .collect(Collectors.toList());
        }
    }

    private CategoryDto convertToDto(Category category) {
        return new CategoryDto(); // 빌더 패턴 사용할 지?
    }

    private CategoryDto convertToDtoWithChildren(Category category) {
        CategoryDto dto = convertToDto(category);

        if (!category.getChildren().isEmpty()) {
            List<CategoryDto> childrenDto = category.getChildren().stream()
                    .map(this::convertToDtoWithChildren)
                    .collect(Collectors.toList());
            dto.setChildren(childrenDto);
        }

        return dto;
    }
}