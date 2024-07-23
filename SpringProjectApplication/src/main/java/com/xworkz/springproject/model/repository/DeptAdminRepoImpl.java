package com.xworkz.springproject.model.repository;

import com.xworkz.springproject.dto.dept.DeptAdminDTO;
import com.xworkz.springproject.dto.dept.EmployeeRegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class DeptAdminRepoImpl implements DeptAdminRepo{

        @Autowired
        private EntityManagerFactory entityManagerFactory;

        @Autowired
        private PasswordEncoder passwordEncoder;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int PASSWORD_LENGTH = 16;

    @Override
    public boolean checkDeptAdminEmailExists(String emailAddress) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT COUNT(s) FROM EmployeeRegisterDTO s WHERE s.emailAddress = :emailAddress");
            query.setParameter("emailAddress", emailAddress);
            Long count = (Long) query.getSingleResult();
            return count > 0;
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            entityManager.close();
        }
        return false;
    }

    @Override
    public boolean checkDeptAdminPhoneNumberExists(String mobileNumber) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT COUNT(s) FROM EmployeeRegisterDTO s WHERE s.mobileNumber = :mobileNumber");
            query.setParameter("mobileNumber", mobileNumber);
            Long count = (Long) query.getSingleResult();
            return count > 0;
        } finally {
            entityManager.close();
        }
    }

    @Override
    @Transactional
    public Optional<DeptAdminDTO> saveDeptAdmin(DeptAdminDTO deptAdminDTO) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();


//            employeeRegisterDTO.setPassword(encodedPassword);
            deptAdminDTO.setCreatedBy(deptAdminDTO.getFirstName() + " " + deptAdminDTO.getLastName());
            deptAdminDTO.setCreatedDate(LocalDateTime.now());
            deptAdminDTO.setUpdatedBy(deptAdminDTO.getFirstName() + " " + deptAdminDTO.getLastName());
            deptAdminDTO.setUpdatedDate(LocalDateTime.now());
            entityManager.persist(deptAdminDTO);
            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            System.out.println("Exception while saving data: " + e);
            return Optional.empty();
        } finally {
            entityManager.close();
        }
        return Optional.of(deptAdminDTO);
    }

    @Override
    public Optional<DeptAdminDTO> findByDeptAdminEmailAddress(String emailAddress) {
        System.out.println("find by email "+emailAddress);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT s FROM DeptAdminDTO s WHERE s.emailAddress = :emailAddress");
            query.setParameter("emailAddress", emailAddress);
            DeptAdminDTO deptAdminDTO = (DeptAdminDTO) query.getSingleResult();
            System.out.println(deptAdminDTO);
            return Optional.ofNullable(deptAdminDTO);
        } catch (NoResultException e) {
            e.printStackTrace();
            return Optional.empty();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public boolean updateDeptAdminPassword(DeptAdminDTO deptAdminDTO) {
        System.out.println("Repo update by password "+deptAdminDTO);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            DeptAdminDTO deptAdminDTO1 = entityManager.find(DeptAdminDTO.class,deptAdminDTO.getId());
            deptAdminDTO1.setPassword(deptAdminDTO.getPassword());
            deptAdminDTO1.setUpdatedBy(deptAdminDTO.getFirstName()+" "+deptAdminDTO.getLastName());
            deptAdminDTO1.setUpdatedDate(LocalDateTime.now());
            deptAdminDTO1.setCount(deptAdminDTO.getCount()+1);


            entityManager.getTransaction().commit();
            return true;
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            System.out.println("Exception while saving data: " + e);

        } catch (Exception e)

        {
            e.printStackTrace();
        }
        finally {
            entityManager.close();
        }

        return false;
    }

    public String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            password.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }

        return password.toString();
    }
}
