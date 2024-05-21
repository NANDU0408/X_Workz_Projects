package com.xworkz.mvc.repositories;

import com.xworkz.mvc.dto.CustomerLoginDTO;
import com.xworkz.mvc.util.EMFUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

public class CustomerLoginRepoImpl implements CustomerLoginRepo{

    EntityManagerFactory entityManagerFactory = EMFUtil.getEntityManagerFactory();
    @Override
    public void saveAllDept(List<CustomerLoginDTO> customerLoginDTOS) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            for (CustomerLoginDTO customerLoginDTO : customerLoginDTOS) {
                entityManager.persist(customerLoginDTO);
            }
            entityTransaction.commit();
        } catch (Exception e) {
            System.out.println("Exception While Inserting Data!!!!");
            entityTransaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public CustomerLoginDTO findByUserName(String customerName) {
        return null;
    }
}
