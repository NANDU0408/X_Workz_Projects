package com.xworkz.mvc.controller;

import com.xworkz.mvc.dto.AuthorityAccessLoginDTO;
import com.xworkz.mvc.dto.CustomerLoginDTO;
import com.xworkz.mvc.repositories.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet( urlPatterns = {"/caeg"}, loadOnStartup = 1)
public class CustomerAccessLoginServlet extends HttpServlet {

    public CustomerAccessLoginServlet(){
        System.out.println("Running CustomerAccessLoginServlet");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("doPost method invoked");
        try {
            CustomerLoginRepo customerLoginRepo= new CustomerLoginRepoImpl();
            String customerName = req.getParameter("customerName");
            System.out.println("customerName: " + customerName);
            String password = req.getParameter("password");

            CustomerLoginDTO customerLoginDTO = new CustomerLoginDTO(0, customerName,password);
            List<CustomerLoginDTO> customerLoginDTOList = new ArrayList<>();
            customerLoginDTOList.add(customerLoginDTO);

            customerLoginRepo.saveAllDept(customerLoginDTOList);

            resp.getWriter().println("Customer Login data saved successfully");
        }  catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().println("An error occurred");
        }
}
}
