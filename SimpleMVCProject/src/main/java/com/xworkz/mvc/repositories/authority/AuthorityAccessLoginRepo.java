package com.xworkz.mvc.repositories.authority;

import com.xworkz.mvc.dto.authority.AuthorityAccessLoginDTO;

import java.util.List;

public interface AuthorityAccessLoginRepo {
    public void saveAllDept(List<AuthorityAccessLoginDTO> authorityAccessLoginDTOS);

    public AuthorityAccessLoginDTO findByAuthorityName(String authorityName);
}
