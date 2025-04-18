package com.inn.cafe.serviceImpl;

import com.inn.cafe.JWT.JwtAuthenticationFilter;
import com.inn.cafe.POJO.Category;
import com.inn.cafe.POJO.User;
import com.inn.cafe.constants.CafeConstants;
import com.inn.cafe.dao.CategoryDao;
import com.inn.cafe.dto.CategoryDto;
import com.inn.cafe.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    CategoryDao categoryDao;

    public CategoryServiceImpl(CategoryDao categoryDao, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.categoryDao = categoryDao;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Override
    public String addNewCategory(CategoryDto categoryDto) {
        if (jwtAuthenticationFilter.isAdmin()) {
            Category category = categoryDao.getCategoryByProductName(categoryDto.getName());
            if(category !=null){
                return "Category Already Exist";
            }
            Category newCategory = new Category();
            newCategory.setName(categoryDto.getName());
            categoryDao.save(newCategory);
            return "Successfully Added";

        } else {
            String message = "User Not Exists";
            return message;

        }

    }

    @Override
    public List<Category> getAllCategory() {
        return categoryDao.findAll();
    }

    @Override
    public Category updateCategory(CategoryDto categoryDto, Integer id) {
                if (categoryDao.existsById(id)) {
                    categoryDao.updateCategory(categoryDto.getName(), categoryDto.getId());
                    Optional<Category> cat = categoryDao.findById(id);
                    if(cat.isPresent()){
                        return cat.get();
                    }

                }
                return null;
    }
}



