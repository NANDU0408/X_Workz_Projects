package com.xworkz.mvc.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMFUtil {

    static EntityManagerFactory entityManagerFactory;

    public static EntityManagerFactory getEntityManagerFactory(){
        return entityManagerFactory;
    }

    static{
        entityManagerFactory= Persistence.createEntityManagerFactory("lodging_hotel_pu");
    }
}
