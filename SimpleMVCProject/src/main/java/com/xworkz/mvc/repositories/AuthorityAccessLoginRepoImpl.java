package com.xworkz.mvc.repositories;

import com.xworkz.mvc.dto.AuthorityAccessLoginDTO;
import com.xworkz.mvc.util.EMFUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

public class AuthorityAccessLoginRepoImpl implements AuthorityAccessLoginRepo{

    EntityManagerFactory entityManagerFactory = EMFUtil.getEntityManagerFactory();
    @Override
    public void saveAllDept(List<AuthorityAccessLoginDTO> authorityAccessLoginDTOS) {
        System.out.println("access login details saving....");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            for (AuthorityAccessLoginDTO authorityAccessLoginDTO : authorityAccessLoginDTOS) {
                entityManager.persist(authorityAccessLoginDTO);
            }
            entityTransaction.commit();
            System.out.println("access login details saved successfully");
        } catch (Exception e) {
            System.out.println("Exception While Inserting Data!!!!"+e);
            entityTransaction.rollback();
        } finally {
            entityManager.close();
        }
    }
}
