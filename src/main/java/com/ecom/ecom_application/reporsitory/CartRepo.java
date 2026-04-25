package com.ecom.ecom_application.reporsitory;

import com.ecom.ecom_application.model.CartItem;
import com.ecom.ecom_application.model.Product;
import com.ecom.ecom_application.model.User;
import com.ecom.ecom_application.service.CartService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepo extends JpaRepository<CartItem,Long> {
    CartItem findByUserAndProduct(User user, Product product);

    List<CartItem> findByUser(User user);
}
