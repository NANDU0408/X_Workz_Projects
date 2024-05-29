package com.xworkz.bean;

import com.xworkz.dto.MatrimonyDTO;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@RequestMapping("/")
public class MatrimonyBean {



    public MatrimonyBean(){
        System.out.println("Running MatrimonyBean");
    }

    @PostMapping("/go")
    public String setMatrimony(MatrimonyDTO matrimonyDTO, Model model) {
        System.out.println("Created matrimony successfully");
        System.out.println("The Response: " + matrimonyDTO);
        model.addAttribute("msg", "Hi,"+matrimonyDTO.getName()+ " ,welcome to INS Matrimony");
        return "matrimony.jsp";
    }
}
