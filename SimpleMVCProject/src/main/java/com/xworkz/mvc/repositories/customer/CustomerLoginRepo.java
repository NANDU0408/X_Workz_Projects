package com.xworkz.mvc.repositories.customer;

import com.xworkz.mvc.dto.customer.CustomerDTO;
import com.xworkz.mvc.dto.customer.CustomerLoginDTO;

import java.util.List;

public interface CustomerLoginRepo {
    public void saveAllDept(CustomerLoginDTO customerLoginDTO);

    public CustomerLoginDTO findByUserName(String customerName);
}
