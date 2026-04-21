package com.ecom.ecom_application.reporsitory;

import com.ecom.ecom_application.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {
}
