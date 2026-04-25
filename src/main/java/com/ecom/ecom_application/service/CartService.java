package com.ecom.ecom_application.service;

import com.ecom.ecom_application.dto.CartItemRequest;
import com.ecom.ecom_application.dto.UserResponse;
import com.ecom.ecom_application.model.CartItem;
import com.ecom.ecom_application.model.Product;
import com.ecom.ecom_application.model.User;
import com.ecom.ecom_application.reporsitory.CartRepo;
import com.ecom.ecom_application.reporsitory.ProductRepo;
import com.ecom.ecom_application.reporsitory.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepo cartRepo;
    private final ProductRepo productRepo;
    private final UserRepo userRepo;

    public boolean addToCart(String userId, CartItemRequest request) {
        // Retrieve the product
        Product product = productRepo.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Check if sufficient stock is available
        if (product.getStockQuantity() < request.getQuantity()) {
            throw new RuntimeException("Insufficient stock for the product");
        }

        // Retrieve the user
        User user = userRepo.findById(Long.valueOf(userId))
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if the item already exists in the cart
        CartItem existingCartItem = cartRepo.findByUserAndProduct(user, product);

        if (existingCartItem != null) {
            // Update the quantity and total price for the existing cart item
            int newQuantity = existingCartItem.getQuantity() + request.getQuantity();
            existingCartItem.setQuantity(newQuantity);
            existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(newQuantity)));
            cartRepo.save(existingCartItem);
        } else {
            // Create a new cart item
            CartItem cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            cartRepo.save(cartItem);
        }
        return true;
    }

    public boolean deleteItemfromCart(String userId, Long productId) {

        User user = userRepo.findById(Long.valueOf(userId))
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem cartItem = cartRepo.findByUserAndProduct(user, product);
        if (cartItem != null) {
            cartRepo.delete(cartItem);
            return true;
        }
        return false;
    }

    public List<CartItem> getCartItems(String userId) {
        User user = userRepo.findById(Long.valueOf(userId))
                .orElseThrow(() -> new RuntimeException("User not found"));
        return cartRepo.findByUser(user);
    }

    public void clearCart(String userId) {
        userRepo.findById(Long.valueOf(userId)).ifPresent(user -> {
            List<CartItem> cartItems = cartRepo.findByUser(user);
            cartRepo.deleteAll(cartItems);
        });
    }
}
