package com.xworkz.mvc.repositories.authority;

import com.xworkz.mvc.dto.authority.AuthorityAccessDetailsDTO;

import java.util.List;

public interface AuthorityAccessDetailsRepo {

    public void saveAllDept(List<AuthorityAccessDetailsDTO> authorityAccessDetailsDTOS);
}
