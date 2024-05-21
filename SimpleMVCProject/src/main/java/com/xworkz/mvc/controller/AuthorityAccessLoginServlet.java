package com.xworkz.mvc.controller;

import com.xworkz.mvc.dto.AuthorityAccessLoginDTO;
import com.xworkz.mvc.repositories.AuthorityAccessLoginRepoImpl;
import com.xworkz.mvc.repositories.AuthorityAccessLoginRepo;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet( urlPatterns = {"/aeg"}, loadOnStartup = 1)
public class AuthorityAccessLoginServlet extends HttpServlet {

    public AuthorityAccessLoginServlet(){
        System.out.println("Running AuthorityAccessLoginServlet");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("doPost method invoked");
        try {
            AuthorityAccessLoginRepo authorityAccessLoginRepo= new AuthorityAccessLoginRepoImpl();
            String authorityName = req.getParameter("authorityName");
            System.out.println("authorityName: " + authorityName);
            String password = req.getParameter("password");

            AuthorityAccessLoginDTO authorityAccessLoginDTO = new AuthorityAccessLoginDTO(0, authorityName,password);
            List<AuthorityAccessLoginDTO> authorityAccessLoginList = new ArrayList<>();
//            authorityAccessLoginList.add(authorityAccessLoginDTO);

//            authorityAccessLoginRepo.saveAllDept(authorityAccessLoginList);

            resp.getWriter().println("Authority Login data saved successfully");
        }  catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().println("An error occurred");
        }

    }
}
