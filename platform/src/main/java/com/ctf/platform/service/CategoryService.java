package com.ctf.platform.service;

import com.ctf.platform.dto.CategoryDTO;
import com.ctf.platform.entity.Category;
import com.ctf.platform.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
private CategoryRepository categoryRepository;

public List<CategoryDTO> getAllCategories(){
    return categoryRepository.findAll().stream().map(CategoryDTO::new).toList();
}


}
