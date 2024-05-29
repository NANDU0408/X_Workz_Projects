package com.xworkz.mvc.repositories.roomDetails;

import com.xworkz.mvc.dto.roomDetails.AllotmentDTO;
import com.xworkz.mvc.dto.roomDetails.RoomAllotmentDTO;
import com.xworkz.mvc.util.EMFUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;


public class AllotmentRepoImpl implements AllotmentRepo {

    @Override
    public void save(AllotmentDTO allotmentDTO) {
        EntityManager entityManager = EMFUtil.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            entityManager.persist(allotmentDTO);
            entityTransaction.commit();
        } catch (Exception e) {
            System.out.println("Exception While Inserting Data: " + e.getMessage());
            entityTransaction.rollback();
        } finally {
            entityManager.close();
        }
    }
}
