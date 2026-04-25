package com.ecom.ecom_application.service;

import com.ecom.ecom_application.dto.OrderItemDto;
import com.ecom.ecom_application.dto.OrderResponse;
import com.ecom.ecom_application.dto.OrderStatus;
import com.ecom.ecom_application.model.CartItem;
import com.ecom.ecom_application.model.Order;
import com.ecom.ecom_application.model.OrderItem;
import com.ecom.ecom_application.model.User;
import com.ecom.ecom_application.reporsitory.OrderRepo;
import com.ecom.ecom_application.reporsitory.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepo orderRepo;
    private final CartService cartService;
    private final UserService userService;
    private final UserRepo userRepo;

    public OrderResponse createOrder(String userId) {
        //validate for cart Item
        List<CartItem> cartItems = cartService.getCartItems(userId);

        //validatev for user
        User user = userRepo.findById(Long.valueOf(userId))
                .orElseThrow(() -> new RuntimeException("User not found with userId " + userId));
        //calculate total price
        BigDecimal totalPrice = cartItems.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal::add)
                .orElseThrow(() -> new RuntimeException("Cart is empty. Cannot create order."));
        //create order
        Order order = new Order();
        order.setUser(user);
        order.setTotalAmount(totalPrice);
        order.setOrderStatus(OrderStatus.CONFIRMED);
        List<OrderItem> orderItems = cartItems.stream().map(item -> new OrderItem(
                null,
                item.getProduct(),
                item.getQuantity(),
                item.getPrice(),
                order
        )).toList();
        order.setItems(orderItems);
        Order savedOrder = orderRepo.save(order);
        //clear cart
        cartService.clearCart(userId);

        return Optional.of(maptoOrderResponse(savedOrder))
                .orElseThrow(() -> new RuntimeException("Failed to create order."));
    }

    private OrderResponse maptoOrderResponse(Order savedOrder) {
        return new OrderResponse(
                savedOrder.getOrderId(),
                savedOrder.getTotalAmount(),
                savedOrder.getOrderStatus(),
                savedOrder.getItems().stream().map(item -> new OrderItemDto(
                        item.getId(),
                        item.getProduct().getProductId(),
                        item.getQuantity(),
                        item.getPrice(),
                        item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())
                        ))).toList(),
                savedOrder.getCreatedAt()
        );
    }
}
