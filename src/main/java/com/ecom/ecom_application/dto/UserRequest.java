package com.ecom.ecom_application.dto;

import com.ecom.ecom_application.domain.UserRole;
import lombok.Data;

@Data
public class UserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private AddressDto address;
}
