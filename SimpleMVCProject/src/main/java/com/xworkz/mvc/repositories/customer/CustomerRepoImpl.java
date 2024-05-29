package com.xworkz.mvc.repositories.customer;

import com.xworkz.mvc.dto.customer.CustomerDTO;
import com.xworkz.mvc.util.EMFUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

public class CustomerRepoImpl implements CustomerRepo {

    private static final Logger log = LoggerFactory.getLogger(CustomerRepoImpl.class);

    @Override
    public CustomerDTO saveAllDept(CustomerDTO customerDTO) {
        log.info("Customer data persisting process is initiated.....");
        EntityManager entityManager = EMFUtil.getEntityManager();

        try {
            entityManager.getTransaction().begin();

            entityManager.persist(customerDTO);

            entityManager.getTransaction().commit();

            log.info("Storing the customer data is successful");

        } catch (Exception e) {
            log.error("Exception While Inserting Data!!!!");
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return customerDTO;
    }
}
