package com.xworkz.mvc.repositories.customer;

import com.xworkz.mvc.dto.customer.CustomerLoginDTO;
import com.xworkz.mvc.util.EMFUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.List;

public class CustomerLoginRepoImpl implements CustomerLoginRepo {

    private static final Logger log = LoggerFactory.getLogger(CustomerLoginRepoImpl.class);
    EntityManagerFactory entityManagerFactory = EMFUtil.getEntityManagerFactory();
    @Override
    public void saveAllDept(CustomerLoginDTO customerLoginDTO) {
        log.info("Storing the customer login credentials data in database.....");

        EntityManager entityManager = EMFUtil.getEntityManager();

        try {
            entityManager.getTransaction().begin();

            entityManager.persist(customerLoginDTO);

            entityManager.getTransaction().commit();

            log.info("Storing the customer login credentials in database is successful.....");
        } catch (Exception e) {
            log.error("Exception while saving data in customer login credentials....." +e);
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }



    @Override
    public CustomerLoginDTO findByUserName(String customerName) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CustomerLoginDTO customerLoginDTO = null;
        try {
            Query query = entityManager.createQuery("SELECT c FROM CustomerLoginDTO c WHERE c.customerName = :customerName");
            query.setParameter("customerName", customerName);
            customerLoginDTO = (CustomerLoginDTO) query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("No customer found with name: " + customerName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return customerLoginDTO;
    }
    }
