package com.xworkz.mvc.controller.roomDetails;

import com.xworkz.mvc.dto.authority.AuthorityAccessDetailsDTO;
import com.xworkz.mvc.dto.roomDetails.RoomDetailsDTO;
import com.xworkz.mvc.repositories.authority.AuthorityAccessDetailsRepo;
import com.xworkz.mvc.repositories.authority.AuthorityAccessDetailsRepoImpl;
import com.xworkz.mvc.repositories.roomDetails.RoomDetailsRepo;
import com.xworkz.mvc.repositories.roomDetails.RoomDetailsRepoImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RoomDetailsServlet extends HttpServlet {

    public RoomDetailsServlet(){
        System.out.println("Running RoomDetailsServlet");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("doPost method invoked");
        try {
            RoomDetailsRepo roomDetailsRepo= new RoomDetailsRepoImpl();

            long roomNo = Long.parseLong(req.getParameter("RoomNo"));
            String floorNo = req.getParameter("floorno");
            double roomType = Double.parseDouble(req.getParameter("RoomType"));
            float rent = Float.parseFloat(req.getParameter("Rent"));
            RoomAllotment roomAllotment = RoomAllotment.valueOf(req.getParameter("alloted").toUpperCase());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dob = dateFormat.parse(dobStr);

            RoomDetailsDTO roomDetailsDTO = new RoomDetailsDTO(0, name, age, gender, dob, address, phoneNo, idProof, idProofNo, emergencyContact);
            List<RoomDetailsDTO> roomDetailsDTOS = new ArrayList<>();
//            authorityAccessDetailsList.add(authorityAccessDetailsDTO);

//            authorityAccessDetailsRepo.saveAllDept(authorityAccessDetailsList);

            resp.getWriter().println("Authority Access Details data saved successfully");
        } catch (ParseException e) {
            e.printStackTrace();
            resp.getWriter().println("Error parsing date");
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().println("An error occurred");
        }

    }
}
