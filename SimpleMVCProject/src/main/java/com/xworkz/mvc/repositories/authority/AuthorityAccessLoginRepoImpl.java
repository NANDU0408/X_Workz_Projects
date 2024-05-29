package com.xworkz.mvc.repositories.authority;

import com.xworkz.mvc.dto.authority.AuthorityAccessLoginDTO;
import com.xworkz.mvc.util.EMFUtil;

import javax.persistence.*;
import java.util.List;

public class AuthorityAccessLoginRepoImpl implements AuthorityAccessLoginRepo {

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

    @Override
    public AuthorityAccessLoginDTO findByAuthorityName(String authorityName) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        AuthorityAccessLoginDTO authorityAccessLoginDTO = null;
        try {
            Query query = entityManager.createQuery("SELECT c FROM AuthorityAccessLoginDTO c WHERE c.authorityName = :authorityName");
            query.setParameter("authorityName", authorityName);
            authorityAccessLoginDTO = (AuthorityAccessLoginDTO) query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("No customer found with name: " + authorityName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return authorityAccessLoginDTO;
    }
}
