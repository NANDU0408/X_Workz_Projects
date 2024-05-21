package com.xworkz.mvc.repositories;

import com.xworkz.mvc.dto.CustomerLoginDTO;

import java.util.List;

public interface CustomerLoginRepo {
    public void saveAllDept(List<CustomerLoginDTO> customerLoginDTOS);

    public CustomerLoginDTO findByUserName(String customerName);
}
