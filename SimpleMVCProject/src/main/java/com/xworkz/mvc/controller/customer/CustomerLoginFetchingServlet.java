package com.xworkz.mvc.controller.customer;

import com.xworkz.mvc.dto.customer.CustomerLoginDTO;
import com.xworkz.mvc.repositories.customer.CustomerLoginRepo;
import com.xworkz.mvc.repositories.customer.CustomerLoginRepoImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet( urlPatterns = {"/customerLogin"}, loadOnStartup = 1)
public class CustomerLoginFetchingServlet extends HttpServlet {

    public CustomerLoginFetchingServlet(){
        System.out.println("Running CustomerLoginFetchingServlet");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("doPost method invoked");
        try {
            CustomerLoginRepo customerLoginRepo = new CustomerLoginRepoImpl();
            String customerName = req.getParameter("customerName");
            String password = req.getParameter("password");

            System.out.println("customerName: " + customerName);

            // Check if the customer exists
            CustomerLoginDTO existingCustomer = customerLoginRepo.findByUserName(customerName);
            if (existingCustomer != null) {
                // Validate the password
                if (existingCustomer.getPassword().equalsIgnoreCase(password)) {
                    resp.getWriter().println("<script>window.location.href='bookingForm.jsp';</script>");
                } else {
                    resp.getWriter().println("<script>window.location.href='invalidCustomerPassword.jsp';</script>");;
                }
            }else {
                resp.getWriter().println("<script>window.location.href='invalidCustomer.jsp';</script>");;
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().println("An error occurred");
        }
    }

}
