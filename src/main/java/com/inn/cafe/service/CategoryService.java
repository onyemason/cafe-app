package com.inn.cafe.service;

import com.inn.cafe.POJO.Category;
import com.inn.cafe.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    String addNewCategory(CategoryDto categoryDto);

    List<Category> getAllCategory();

   Category updateCategory(CategoryDto categoryDto, Integer id);

}
