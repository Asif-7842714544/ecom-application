package com.ecom.ecom_application.reporsitory;

import com.ecom.ecom_application.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product,Long> {
}
