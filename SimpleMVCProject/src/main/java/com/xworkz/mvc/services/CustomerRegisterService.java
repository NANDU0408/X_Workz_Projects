package com.xworkz.mvc.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CustomerRegisterService {

    public void registerService(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
