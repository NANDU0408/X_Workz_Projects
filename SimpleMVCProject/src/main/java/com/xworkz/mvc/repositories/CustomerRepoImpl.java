package com.xworkz.mvc.repositories;

import com.xworkz.mvc.dto.CustomerDTO;
import com.xworkz.mvc.util.EMFUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

public class CustomerRepoImpl implements CustomerRepo{

    @Override
    public void saveAllDept(List<CustomerDTO> customerDTOS) {
        EntityManagerFactory entityManagerFactory = EMFUtil.getEntityManagerFactory();

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            for (CustomerDTO customerDTO : customerDTOS) {
                entityManager.persist(customerDTO);
            }
            entityTransaction.commit();
        } catch (Exception e) {
            System.err.println("Exception While Inserting Data!!!!");
            entityTransaction.rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }
}
