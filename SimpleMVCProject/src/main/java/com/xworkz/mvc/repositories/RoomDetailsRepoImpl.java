package com.xworkz.mvc.repositories;

import com.xworkz.mvc.dto.RoomDetailsDTO;
import com.xworkz.mvc.util.EMFUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

public class RoomDetailsRepoImpl implements RoomDetailsRepo{

    EntityManagerFactory entityManagerFactory = EMFUtil.getEntityManagerFactory();
    @Override
    public void saveAllDept(List<RoomDetailsDTO> roomDetailsDTOS) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            for (RoomDetailsDTO roomDetailsDTO : roomDetailsDTOS) {
                entityManager.persist(roomDetailsDTO);
            }
            entityTransaction.commit();
        } catch (Exception e) {
            System.out.println("Exception While Inserting Data!!!!");
            entityTransaction.rollback();
        } finally {
            entityManager.close();
        }
    }
}
