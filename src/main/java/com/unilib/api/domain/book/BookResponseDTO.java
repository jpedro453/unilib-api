package com.unilib.api.domain.book;

import com.unilib.api.domain.category.Category;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

public record BookResponseDTO(UUID id,
                              String title,
                              Boolean available,
                              String description,
                              String image,
                              String pdf,
                              Boolean hasEbook,
                              Set<Category> categories,
                              Date createdAt,
                              Date updatedAt
                              ) {
}