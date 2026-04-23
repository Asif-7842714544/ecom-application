package com.ecom.ecom_application.controller;

import com.ecom.ecom_application.dto.ProductRequest;
import com.ecom.ecom_application.dto.ProductResponse;
import com.ecom.ecom_application.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest){
        return new ResponseEntity<ProductResponse>(productService.createProduct(productRequest),HttpStatus.CREATED);

    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long productId,@RequestBody ProductRequest productRequest){
        return new ResponseEntity<ProductResponse>(productService.updateProduct(productId,productRequest),HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts(){
        return new ResponseEntity<List<ProductResponse>>(productService.fetchAllProducts(),HttpStatus.OK);

    }
}
