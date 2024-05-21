package com.xworkz.mvc.repositories;

import com.xworkz.mvc.dto.AuthorityAccessDetailsDTO;

import java.util.List;

public interface AuthorityAccessDetailsRepo {

    public void saveAllDept(List<AuthorityAccessDetailsDTO> authorityAccessDetailsDTOS);
}
