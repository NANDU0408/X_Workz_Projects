package com.xworkz.springproject.model.repository;

import com.xworkz.springproject.dto.admin.AdminDTO;
import com.xworkz.springproject.dto.user.ImageDownloadDTO;
import com.xworkz.springproject.dto.user.SignUpDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
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
    @Transactional
    public Optional<SignUpDTO> merge(SignUpDTO signUpDTO) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            signUpDTO.setPassword(generateRandomPassword());
            signUpDTO.setCreatedBy(signUpDTO.getFirstName() + " " + signUpDTO.getLastName());
            signUpDTO.setCreatedDate(LocalDateTime.now());
            signUpDTO.setUpdatedBy(signUpDTO.getFirstName() + " " + signUpDTO.getLastName());
            signUpDTO.setUpdatedDate(LocalDateTime.now());
            entityManager.merge(signUpDTO);
            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
//            e.printStackTrace();
            System.out.println("Exception while saving data: " + e.getMessage());
            System.out.println(e.getCause());
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
    public Optional<AdminDTO> findByAdminEmailAddress(String emailAddress) {
        System.out.println("find by email "+emailAddress);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT a FROM AdminDTO a WHERE a.emailAddress = :emailAddress");
            query.setParameter("emailAddress", emailAddress);
            AdminDTO adminDTO = (AdminDTO) query.getSingleResult();
            return Optional.ofNullable(adminDTO);
        } catch (Exception e) {
            return Optional.empty();
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

    @Override
    public List<SignUpDTO> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createQuery("SELECT s FROM SignUpDTO s");
        return query.getResultList();
    }

    @Override
    @Transactional
    public SignUpDTO updateProfile(SignUpDTO signUpDTO) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        System.out.println("Running updateProfile in repo user");
        try {
            entityManager.getTransaction().begin();

            // Find the existing entity by email address
//            SignUpDTO existingDTO = findByEmailAddress(signUpDTO.getEmailAddress())
//                    .orElseThrow(() -> new EntityNotFoundException("User with email address " + signUpDTO.getEmailAddress() + " not found"));

            // Update the fields you want to modify
//            existingDTO.setFirstName(signUpDTO.getFirstName());
//            existingDTO.setLastName(signUpDTO.getLastName());
//            existingDTO.setMobileNumber(signUpDTO.getMobileNumber());

            // Merge the updated entity
            SignUpDTO updatedDTO = entityManager.merge(signUpDTO);

            entityManager.getTransaction().commit();
            System.out.println(updatedDTO);
            return updatedDTO;
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            System.out.println("Exception while updating data: " + e);
            return null;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    @Transactional
    public void saveImageDetails(ImageDownloadDTO imageDownloadDTO) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        System.out.println("Running saveImageDetails in SignRepo"+imageDownloadDTO);
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(imageDownloadDTO);
            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            System.out.println("Exception while saving image details: " + e);
        } finally {
            entityManager.close();
        }
    }

    @Transactional
    public Optional<ImageDownloadDTO> save(ImageDownloadDTO imageDownloadDTO) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(imageDownloadDTO);
            entityManager.getTransaction().commit();
            return Optional.of(imageDownloadDTO);
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            System.out.println("Exception while saving image data: " + e);
            return Optional.empty();
        } finally {
            entityManager.close();
        }
    }

    @Transactional
    public Optional<ImageDownloadDTO> mergeImage(ImageDownloadDTO imageDownloadDTO) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            // Merge the entity
            ImageDownloadDTO mergedEntity = entityManager.merge(imageDownloadDTO);

            entityManager.getTransaction().commit();
            return Optional.of(mergedEntity);
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            System.out.println("Exception while saving image data: " + e);
            return Optional.empty();
        } finally {
            entityManager.close();
        }
    }

    public Optional<List<ImageDownloadDTO>> findImage(int userId) {
        System.out.println("find by userData" +userId);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT a FROM ImageDownloadDTO a WHERE a.userId = :userId  AND a.status = 'ACTIVE'");
            query.setParameter("userId", userId);
            List<ImageDownloadDTO> imageDownloadDTOList = (List<ImageDownloadDTO>) query.getResultList();
            System.out.println(imageDownloadDTOList);
            return Optional.ofNullable(imageDownloadDTOList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public List<ImageDownloadDTO> findByUserIdAndStatus(int userId, String status) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        System.out.println("Running findByUserIdAndStatus in repo");
        try {
            Query query = entityManager.createQuery("SELECT i FROM ImageDownloadDTO i WHERE i.userId = :userId AND i.status = :status");
            query.setParameter("userId", userId);
            query.setParameter("status", status);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
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