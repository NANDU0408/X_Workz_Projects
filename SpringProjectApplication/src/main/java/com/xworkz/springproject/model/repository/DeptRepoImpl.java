package com.xworkz.springproject.model.repository;

import com.xworkz.springproject.dto.dept.DeptAdminDTO;
import com.xworkz.springproject.dto.dept.EmployeeRegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class DeptRepoImpl implements DeptRepo{

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public Optional<DeptAdminDTO> findByDeptAdminEmailAddress(String emailAddress) {
        System.out.println("find by email "+emailAddress);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT a FROM DeptAdminDTO a WHERE a.emailAddress = :emailAddress");
            query.setParameter("emailAddress", emailAddress);
            DeptAdminDTO deptAdminDTO = (DeptAdminDTO) query.getSingleResult();
            return Optional.ofNullable(deptAdminDTO);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public Optional<EmployeeRegisterDTO> saveEmp(EmployeeRegisterDTO employeeRegisterDTO) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            employeeRegisterDTO.setCreatedBy(employeeRegisterDTO.getFirstName() + " " + employeeRegisterDTO.getLastName());
            employeeRegisterDTO.setCreatedDate(LocalDateTime.now());
            employeeRegisterDTO.setUpdatedBy(employeeRegisterDTO.getFirstName() + " " + employeeRegisterDTO.getLastName());
            employeeRegisterDTO.setUpdatedDate(LocalDateTime.now());
            entityManager.persist(employeeRegisterDTO);
            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            System.out.println("Exception while saving data: " + e);
            return Optional.empty();
        } finally {
            entityManager.close();
        }
        return Optional.of(employeeRegisterDTO);
    }

    @Override
    public boolean checkEmailExists(String emailAddress) {
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
    public boolean checkPhoneNumberExists(String mobileNumber) {
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
}
