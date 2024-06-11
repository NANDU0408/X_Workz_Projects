package com.xworkz.springproject.util;

import javax.persistence.EntityManager;
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

    public static EntityManager getEntityManager(){
        return entityManagerFactory.createEntityManager();
    }
}
