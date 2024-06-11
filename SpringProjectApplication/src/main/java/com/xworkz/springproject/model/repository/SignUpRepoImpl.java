package com.xworkz.springproject.model.repository;

import com.xworkz.springproject.dto.SignUpDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class SignUpRepoImpl implements SignUpRepo {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int PASSWORD_LENGTH = 16;

    @Override
    public Optional<SignUpDTO> save(SignUpDTO signUpDTO) {
        System.out.println("Running save in SignUpRepoImpl");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            signUpDTO.setPassword(generateRandomPassword());
            signUpDTO.setCreatedBy(signUpDTO.getFirstName() + " " + signUpDTO.getLastName());
            signUpDTO.setCreatedDate(LocalDateTime.now());
            signUpDTO.setUpdatedBy(signUpDTO.getFirstName() + " " + signUpDTO.getLastName());
            signUpDTO.setUpdatedDate(LocalDateTime.now());
            entityManager.persist(signUpDTO);
            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            System.out.println("Exception while saving data: " + e);
        } finally {
            entityManager.close();
        }
        return Optional.ofNullable(signUpDTO);
    }

    @Override
    public boolean checkEmailExists(String emailAddress) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT COUNT(s) FROM SignUpDTO s WHERE s.emailAddress = :emailAddress");
            query.setParameter("emailAddress", emailAddress);
            Long count = (Long) query.getSingleResult();
            return count > 0;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public boolean checkPhoneNumberExists(String mobileNumber) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT COUNT(s) FROM SignUpDTO s WHERE s.mobileNumber = :mobileNumber");
            query.setParameter("mobileNumber", mobileNumber);
            Long count = (Long) query.getSingleResult();
            return count > 0;
        } finally {
            entityManager.close();
        }
    }

    private String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            password.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return password.toString();
    }
}