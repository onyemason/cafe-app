package com.inn.cafe.wrapper;

import lombok.Data;

@Data
public class ProductWrapper {

    Integer id;

    String name;

    String description;

    Integer price;

    String status;

    Integer categoryId;

    String categoryName;

    public ProductWrapper(){

    }

    public ProductWrapper(Integer id, String name, String description, Integer price, String status, Integer categoryId, String categoryName){
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.status = status;
        this.categoryId = categoryId;
        this.categoryName = categoryName;

    }
}
