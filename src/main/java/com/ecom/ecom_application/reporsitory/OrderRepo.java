package com.ecom.ecom_application.reporsitory;

import com.ecom.ecom_application.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order,Long> {
}
