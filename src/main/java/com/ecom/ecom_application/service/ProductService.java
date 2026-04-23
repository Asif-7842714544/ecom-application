package com.ecom.ecom_application.service;

import com.ecom.ecom_application.domain.Product;
import com.ecom.ecom_application.dto.ProductRequest;
import com.ecom.ecom_application.dto.ProductResponse;
import com.ecom.ecom_application.reporsitory.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepo productRepo;


    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = productRepo.save(maptoProduct(productRequest));

        return mapToProductResponse(product);
    }

    public ProductResponse updateProduct(Long productId, ProductRequest productRequest) {

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        maptoProduct(product, productRequest);

        Product updatedProduct = productRepo.save(product);

        return mapToProductResponse(updatedProduct);
    }

    private void maptoProduct(Product product, ProductRequest req) {

        if (req.getName() != null) {
            product.setName(req.getName());
        }

        if (req.getDescription() != null) {
            product.setDescription(req.getDescription());
        }

        if (req.getCategory() != null) {
            product.setCategory(req.getCategory());
        }

        if (req.getPrice() != null) {
            product.setPrice(req.getPrice());
        }

        if (req.getStockQuantity() != null) {
            product.setStockQuantity(req.getStockQuantity());
        }

        if (req.getImageUrl() != null) {
            product.setImageUrl(req.getImageUrl());
        }
    }

    public Product maptoProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setDescription(productRequest.getDescription());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setCategory(productRequest.getCategory());
        product.setImageUrl(productRequest.getImageUrl());
        return product;
    }

    public ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .stockQuantity(product.getStockQuantity())
                .category(product.getCategory())
                .imageUrl(product.getImageUrl())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }


    public List<ProductResponse> fetchAllProducts() {
        return productRepo.findAll()
                .stream()
                .map(this::mapToProductResponse)
                .toList();
    }
}
