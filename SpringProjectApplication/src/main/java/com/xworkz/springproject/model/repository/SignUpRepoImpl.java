package com.xworkz.springproject.model.repository;

import com.xworkz.springproject.dto.SignUpDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
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
    @Transactional
    public Optional<SignUpDTO> save(SignUpDTO signUpDTO) {
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
            return Optional.empty();
        } finally {
            entityManager.close();
        }
        return Optional.of(signUpDTO);
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

    @Override
    public Optional<SignUpDTO> findByEmailAddress(String emailAddress) {
        System.out.println("find by email "+emailAddress);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT s FROM SignUpDTO s WHERE s.emailAddress = :emailAddress");
            query.setParameter("emailAddress", emailAddress);
            SignUpDTO signUpDTO = (SignUpDTO) query.getSingleResult();
            System.out.println(signUpDTO);
//            entityManager.detach(signUpDTO);
            return Optional.ofNullable(signUpDTO);
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public boolean updatePassword(SignUpDTO signUpDTO) {
        System.out.println("Repo update by password "+signUpDTO);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            SignUpDTO signUpDTO1 = entityManager.find(SignUpDTO.class,signUpDTO.getId());
            signUpDTO1.setUpdatedPassword(signUpDTO.getPassword());
            signUpDTO1.setUpdatedBy(signUpDTO.getFirstName()+" "+signUpDTO.getLastName());
            signUpDTO1.setUpdatedDate(LocalDateTime.now());
            signUpDTO1.setCount(signUpDTO.getCount()+1);


            entityManager.getTransaction().commit();
            return true;
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            System.out.println("Exception while saving data: " + e);

        } finally {
            entityManager.close();
        }

        return false;
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