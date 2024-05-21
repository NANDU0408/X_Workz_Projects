package com.xworkz.mvc.repositories;

import com.xworkz.mvc.dto.CustomerDTO;

import java.util.List;

public interface CustomerRepo {

    public void saveAllDept(List<CustomerDTO> customerDTOS);
}
