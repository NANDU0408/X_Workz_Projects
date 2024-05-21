package com.xworkz.mvc.repositories;

import com.xworkz.mvc.dto.AuthorityAccessLoginDTO;

import java.util.List;

public interface AuthorityAccessLoginRepo {
    public void saveAllDept(List<AuthorityAccessLoginDTO> authorityAccessLoginDTOS);
}
