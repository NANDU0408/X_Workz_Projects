package com.xworkz.mvc.repositories.customer;

import com.xworkz.mvc.dto.customer.CustomerDTO;
import com.xworkz.mvc.dto.customer.CustomerLoginDTO;

public interface CustomerRegistrationRepo {

    public void customerRegister(CustomerDTO customerDTO, CustomerLoginDTO customerLoginDTO);
}
