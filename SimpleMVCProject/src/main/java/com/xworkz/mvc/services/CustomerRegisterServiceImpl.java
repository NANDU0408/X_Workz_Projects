package com.xworkz.mvc.services;

import com.xworkz.mvc.dto.customer.CustomerDTO;
import com.xworkz.mvc.dto.customer.CustomerLoginDTO;
import com.xworkz.mvc.repositories.customer.CustomerRegistrationRepo;
import com.xworkz.mvc.repositories.customer.CustomerRegistrationRepoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomerRegisterServiceImpl implements CustomerRegisterService{
    private static final Logger log = LoggerFactory.getLogger(CustomerRegisterServiceImpl.class);

    @Override
    public void registerService(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.error(" Customer data is being processed.....");

        CustomerDTO customerDTO = getCustomerDTOFromRequest(request);
        CustomerLoginDTO customerLoginDTO = getCustomerLoginDTOFromRequest(request);

        CustomerRegistrationRepo customerRegistrationRepo=new CustomerRegistrationRepoImpl();
        customerRegistrationRepo.customerRegister(customerDTO, customerLoginDTO);

        response.getWriter().println("Customer data saved successfully");
    }

    private CustomerDTO getCustomerDTOFromRequest(HttpServletRequest request){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCNAME(request.getParameter("name"));
        customerDTO.setAGE(Integer.valueOf(request.getParameter("age")));
        customerDTO.setGender(request.getParameter("gender"));
        customerDTO.setDOB(LocalDate.parse(request.getParameter("dob"), DateTimeFormatter.ofPattern("dd-MMM-yyyy")));
        customerDTO.setAddress(request.getParameter("address"));
        customerDTO.setPhoneNo(Long.valueOf(request.getParameter("phoneNo")));
        customerDTO.setIDProof(request.getParameter("IDType"));
        customerDTO.setIDProofNo(Long.valueOf(request.getParameter("IDProofNo")));
        customerDTO.setEmergencyContact(Long.valueOf(request.getParameter("emergencyNo")));

        return customerDTO;
    }

    private CustomerLoginDTO getCustomerLoginDTOFromRequest(HttpServletRequest request){
        CustomerLoginDTO customerLoginDTO = new CustomerLoginDTO();
        customerLoginDTO.setCustomerName(request.getParameter("username"));
        customerLoginDTO.setPassword(request.getParameter("password"));

        return customerLoginDTO;
    }
}
