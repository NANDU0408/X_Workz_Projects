package com.xworkz.mvc.repositories;

import com.xworkz.mvc.dto.AuthorityAccessDetailsDTO;
import com.xworkz.mvc.util.EMFUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

public class AuthorityAccessDetailsRepoImpl implements AuthorityAccessDetailsRepo{

    EntityManagerFactory entityManagerFactory = EMFUtil.getEntityManagerFactory();
    @Override
    public void saveAllDept(List<AuthorityAccessDetailsDTO> authorityAccessDetailsDTOS) {
        System.out.println("authorize detains is saving...");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            for (AuthorityAccessDetailsDTO authorityAccessDetailsDTO : authorityAccessDetailsDTOS) {
                entityManager.persist(authorityAccessDetailsDTO);
            }
            entityTransaction.commit();

            System.out.println("authorize detains is seved successfully...");
        } catch (Exception e) {
            System.out.println("Exception While Inserting Data!!!!"+e);
            entityTransaction.rollback();
        } finally {
            entityManager.close();
        }
    }
}
