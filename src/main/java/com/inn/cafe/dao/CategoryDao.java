package com.inn.cafe.dao;

import com.inn.cafe.POJO.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CategoryDao extends JpaRepository<Category, Integer> {
    @Query(value = "SELECT * from category c where c.name=:name", nativeQuery = true )
    Category getCategoryByProductName(@Param("name") String name);
    @Transactional
    @Modifying
    @Query(value =  "UPDATE category set name=:name where id=:id", nativeQuery = true)
    void updateCategory(@Param("name") String name, @Param("id") Integer id);




}
