package com.inn.cafe.rest;
import com.inn.cafe.POJO.Category;
import com.inn.cafe.dto.CategoryDto;
import com.inn.cafe.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping(path="/category")
public class  CategoryRest {

    CategoryService categoryService;

    public CategoryRest(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping(path = "/add")
    ResponseEntity<String> addNewCategory(@RequestBody(required = true) CategoryDto categoryDto ){
        String response = categoryService.addNewCategory(categoryDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping(path = "/get")
    ResponseEntity<List<Category>> getAllCategory(){
        List<Category> getCategory = categoryService.getAllCategory();
        return new ResponseEntity<>(getCategory, HttpStatus.OK);
    }
     @PostMapping(path = "/update/{id}")
    ResponseEntity<Category> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable Integer id){
    Category updateCategory  =  categoryService.updateCategory(categoryDto, id);
      return new ResponseEntity<>(updateCategory, HttpStatus.OK);
     }
}
