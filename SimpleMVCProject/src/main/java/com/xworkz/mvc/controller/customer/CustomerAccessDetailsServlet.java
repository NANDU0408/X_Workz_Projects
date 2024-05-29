package com.xworkz.mvc.controller.customer;

import com.xworkz.mvc.services.CustomerRegisterService;
import com.xworkz.mvc.services.CustomerRegisterServiceImpl;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebServlet( urlPatterns = {"/reg"}, loadOnStartup = 1)
public class CustomerAccessDetailsServlet extends HttpServlet {


    public CustomerAccessDetailsServlet() {
        log.info("Running The Hotel Form in Servlet");
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException {
        log.info("Customer doPost method invoked");
        CustomerRegisterService customerRegisterService = new CustomerRegisterServiceImpl();

        try {
            customerRegisterService.registerService(req, resp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        log.info("Customer registered successfully.....");
    }
}