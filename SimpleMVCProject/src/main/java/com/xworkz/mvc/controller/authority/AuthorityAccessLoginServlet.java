package com.xworkz.mvc.controller.authority;

import com.xworkz.mvc.dto.authority.AuthorityAccessLoginDTO;
import com.xworkz.mvc.repositories.authority.AuthorityAccessLoginRepoImpl;
import com.xworkz.mvc.repositories.authority.AuthorityAccessLoginRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet( urlPatterns = {"/aeg"}, loadOnStartup = 1)
public class AuthorityAccessLoginServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(AuthorityAccessLoginServlet.class);

    public AuthorityAccessLoginServlet(){
        System.out.println("Running AuthorityAccessLoginServlet");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("doPost method invoked");
        logger.info("login process is initiated....");
        try {
            AuthorityAccessLoginRepo authorityAccessLoginRepo= new AuthorityAccessLoginRepoImpl();
            String authorityName = req.getParameter("authorityName");
            System.out.println("authorityName: " + authorityName);
            String password = req.getParameter("password");

            AuthorityAccessLoginDTO authorityAccessLoginDTO = new AuthorityAccessLoginDTO(4, authorityName,password);
            List<AuthorityAccessLoginDTO> authorityAccessLoginList = new ArrayList<>();
            authorityAccessLoginList.add(authorityAccessLoginDTO);

            authorityAccessLoginRepo.saveAllDept(authorityAccessLoginList);

            resp.getWriter().println("Authority Login data saved successfully");
        }  catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().println("An error occurred");
        }

    }
}
