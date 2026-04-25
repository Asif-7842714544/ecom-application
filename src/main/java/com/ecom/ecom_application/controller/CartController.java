package com.ecom.ecom_application.controller;

import com.ecom.ecom_application.dto.CartItemRequest;
import com.ecom.ecom_application.model.CartItem;
import com.ecom.ecom_application.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;


    @PostMapping
    public ResponseEntity<String> addToCart(
            @RequestHeader("X-user-id") String userId,
            @RequestBody CartItemRequest request
    ) {
        if (!cartService.addToCart(userId, request)) {
            return ResponseEntity.badRequest().body("Failed to add item to cart. Please check the product ID and quantity.");
        }
        return ResponseEntity.accepted().body("Item added to cart successfully.");
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<String> removeFromCart(
            @RequestHeader("X-user-id") String userId, @PathVariable Long productId) {
        boolean deleted = cartService.deleteItemfromCart(userId, productId);
        return deleted ? ResponseEntity.ok("Item removed from cart successfully.") :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found in cart or failed to remove.");
    }

    @GetMapping("/items")
    public ResponseEntity<List<CartItem>> getCartItems(@RequestHeader("X-user-id") String userId) {
        return ResponseEntity.ok(cartService.getCartItems(userId));
    }

}

