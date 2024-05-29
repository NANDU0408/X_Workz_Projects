package com.xworkz.bean;

import com.xworkz.dto.ContactDTO;
import com.xworkz.dto.MatrimonyDTO;
import com.xworkz.dto.ResortBookingDTO;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@RequestMapping("/")
public class resortBookingBean {
    public resortBookingBean(){
        System.out.println("Running resortBookingBean");
    }

    @PostMapping("/resortBooking")
    public String setResortForm(ResortBookingDTO resortBookingDTO, Model model) {
        System.out.println("Resort Booked successfully");
        System.out.println("The Response: " + resortBookingDTO);
        model.addAttribute("msg", "Hi,"+resortBookingDTO.getName()+ " ,welcome to " +resortBookingDTO.getResort());
        return "resortBookingResult.jsp";
    }
}
