package com.ctf.platform.controller;


import com.ctf.platform.dto.CategoryDTO;
import com.ctf.platform.entity.Category;
import com.ctf.platform.repository.CategoryRepository;
import com.ctf.platform.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping()
    public ResponseEntity<List<CategoryDTO>> getCategory(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
}
