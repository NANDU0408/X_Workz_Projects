package com.xworkz.bean;

import com.xworkz.dto.ContactDTO;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@RequestMapping("/")
public class ContactBean {
    public ContactBean(){
        System.out.println("Running ContactBean");
    }

    @PostMapping("/contact")
    public String setContact(ContactDTO contactDTO, Model model){
        System.out.println("Created contact successfully");
        System.out.println("The Response, " +contactDTO);
        model.addAttribute("msg","Thankyou, " +contactDTO.getName()+ " for contacting");
        return "contact.jsp";
    }
}
