package com.xworkz.mvc.controller.roomDetails;

import com.xworkz.mvc.dto.roomDetails.AllotmentDTO;;
import com.xworkz.mvc.repositories.roomDetails.AllotmentRepo;
import com.xworkz.mvc.repositories.roomDetails.AllotmentRepoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(urlPatterns = {"/allotmentServlet"}, loadOnStartup = 1)
public class AllotmentServlet extends HttpServlet {

    public AllotmentServlet() {
        System.out.println("Running AllotmentServlet");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doPost method invoked");
        try {
            AllotmentRepo allotmentRepo = new AllotmentRepoImpl();

            int roomNo = Integer.parseInt(req.getParameter("RoomNo"));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            Date entryDate = dateFormat.parse(req.getParameter("Entry_date"));
            Date vacateDate = dateFormat.parse(req.getParameter("vacate_date"));
            double amountToPay = Double.parseDouble(req.getParameter("Amount_to_pay"));
            double payedAmount = Double.parseDouble(req.getParameter("Payed_Amount"));

            AllotmentDTO allotmentDTO = new AllotmentDTO(0, roomNo, entryDate, vacateDate, amountToPay, payedAmount);

            allotmentRepo.save(allotmentDTO);

            resp.getWriter().println("Allotment details submitted successfully");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            resp.getWriter().println("Invalid number format: " + e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
            resp.getWriter().println("Invalid date format: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().println("An error occurred while processing the allotment details: " + e.getMessage());
        }
    }
}

//nandishIIAP*12345