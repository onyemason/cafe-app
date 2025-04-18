package com.inn.cafe.serviceImpl;

import com.inn.cafe.JWT.JwtAuthenticationFilter;
import com.inn.cafe.POJO.Category;
import com.inn.cafe.POJO.User;
import com.inn.cafe.constants.CafeConstants;
import com.inn.cafe.dao.CategoryDao;
import com.inn.cafe.dto.CategoryDto;
import com.inn.cafe.service.CategoryService;
import com.inn.cafe.utils.CafeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

//
//    @Override
//    public ResponseEntity<List<Category>> getAllCategory(String filterValue) {
//        try{
//            if (!Strings.isBlank(filterValue) && filterValue.equalsIgnoreCase("true")){
//                log.info("Inside if");
//                return new ResponseEntity<List<Category>>(categoryDao.getAllCategory(), HttpStatus.OK);
//            }
//            return new ResponseEntity<>(categoryDao.findAll(),HttpStatus.OK);
//
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//        return new ResponseEntity<List<Category>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    @Override
//    public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
//        try{
//           if(jwtAuthenticationFilter.isAdmin()) {
//               if(validateCategoryMap(requestMap,true)){
//                Optional optional = categoryDao.findById(Integer.parseInt(requestMap.get("id")));
//                if(!optional.isEmpty()){
//                    categoryDao.save(getCategoryFromMap(requestMap,true));
//                    return CafeUtils.getResponseEntity("Category Updated Successfully",HttpStatus.OK);
//
//                }else{
//                   return CafeUtils.getResponseEntity("Category id does not exist", HttpStatus.OK);
//                }
//               }
//               return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
//           }
//           else {
//               return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
//           }
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
//    }

