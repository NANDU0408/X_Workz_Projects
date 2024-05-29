package com.xworkz.bean;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@RequestMapping("/")
public class WisdomInit {
    public WisdomInit(){
        System.out.println("Running WisdomInit");
    }

    @PostMapping("/wisdom")
    public String Mapping(){
        System.out.println("Returning Method");
        return "welcome.jsp";
    }
}
