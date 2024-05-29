package com.xworkz.mvc.controller.authority;

import com.xworkz.mvc.dto.authority.AuthorityAccessLoginDTO;
import com.xworkz.mvc.repositories.authority.AuthorityAccessLoginRepo;
import com.xworkz.mvc.repositories.authority.AuthorityAccessLoginRepoImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/authorityLogin"}, loadOnStartup = 1)
public class AuthorityLoginFetchingServlet extends HttpServlet {

    public AuthorityLoginFetchingServlet() {
        System.out.println("Running AuthorityLoginFetchingServlet");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("doPost method invoked");
        try {
            AuthorityAccessLoginRepo authorityAccessLoginRepo = new AuthorityAccessLoginRepoImpl();
            String authorityName = req.getParameter("authorityName");
            String password = req.getParameter("password");

            System.out.println("authorityName: " + authorityName);

            // Check if the authority exists
            AuthorityAccessLoginDTO existingAuthority = authorityAccessLoginRepo.findByAuthorityName(authorityName);
            if (existingAuthority != null) {
                // Validate the password
                if (existingAuthority.getPassword().equalsIgnoreCase(password)) {
                    resp.getWriter().println("Authority Login successful");
                } else {
                    resp.getWriter().println("Invalid password");
                }
            } else {
                resp.getWriter().println("Invalid UserName");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().println("An error occurred");
        }
    }
}