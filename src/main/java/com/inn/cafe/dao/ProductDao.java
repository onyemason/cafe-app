package com.inn.cafe.dao;

import com.inn.cafe.POJO.Product;
import com.inn.cafe.wrapper.ProductWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductDao extends JpaRepository<Product, Integer> {

    @Query(value = "SELECT p.id, p.name, p.description, p.status, p.category_fk, p.price FROM product p ORDER BY p.id DESC", nativeQuery = true)
    List<Product> getAllProduct();

}
