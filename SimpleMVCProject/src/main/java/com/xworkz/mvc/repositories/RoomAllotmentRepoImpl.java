package com.xworkz.mvc.repositories;

import com.xworkz.mvc.dto.RoomAllotmentDTO;
import com.xworkz.mvc.util.EMFUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

public class RoomAllotmentRepoImpl implements RoomAllotmentRepo{

    EntityManagerFactory entityManagerFactory = EMFUtil.getEntityManagerFactory();
    @Override
    public void saveAllDept(List<RoomAllotmentDTO> roomAllotmentDTOS) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            for (RoomAllotmentDTO roomAllotmentDTO : roomAllotmentDTOS) {
                entityManager.persist(roomAllotmentDTO);
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
