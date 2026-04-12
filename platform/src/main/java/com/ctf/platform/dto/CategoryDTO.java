package com.ctf.platform.dto;

import com.ctf.platform.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryDTO {
    Long id;
    String name;

    public CategoryDTO(Category category){
        this.id = category.getId();
        this.name = category.getName();
    }
}
