package com.inn.cafe.rest;

import com.inn.cafe.POJO.Product;
import com.inn.cafe.service.ProductService;
import com.inn.cafe.wrapper.ProductWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping(path = "/product")
public class ProductRest {
    private final ProductService productService;

    public ProductRest(ProductService productService) {
        this.productService = productService;
    }

//    @PostMapping(path="/add")
//    ResponseEntity<String> addNewProduct(@RequestBody Map<String,  String> requestMap);

    @PreAuthorize("hasAnyRole('admin')")
    @GetMapping(path ="/get")
    ResponseEntity<?> getAllProduct(){
        List<Product> products = productService.getAllProduct();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

}
