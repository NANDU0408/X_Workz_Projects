package com.xworkz.springproject.model.repository;

import com.xworkz.springproject.dto.dept.WaterDeptDTO;
import com.xworkz.springproject.dto.requestDto.HistoryDTO;
import com.xworkz.springproject.dto.requestDto.RequestToDeptAndStatusOfComplaintDto;
import com.xworkz.springproject.dto.user.RaiseComplaintDTO;
import com.xworkz.springproject.dto.user.SignUpDTO;
import com.xworkz.springproject.model.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class ComplaintRepoImpl implements ComplaintRepo{

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private SignUpService signUpService;

    @Override
    @Transactional
    public Optional<RaiseComplaintDTO> saveComplaint(RaiseComplaintDTO raiseComplaintDTO, SignUpDTO signUpDTO) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        System.out.println("Running saveComplaint in ComplaintRepoImpl: " + raiseComplaintDTO);
        System.out.println(signUpDTO);
        try {
            entityManager.getTransaction().begin();

            // Fetch the user details of the logged-in user using their email address
            Query userQuery = entityManager.createQuery("SELECT s FROM SignUpDTO s WHERE s.emailAddress = :emailAddress");
            userQuery.setParameter("emailAddress", signUpDTO.getEmailAddress());
            SignUpDTO currentUser = (SignUpDTO) userQuery.getSingleResult();

            System.out.println("Current user "+currentUser);

            raiseComplaintDTO.setCreatedBy(signUpDTO.getFirstName() + " " + signUpDTO.getLastName());
            raiseComplaintDTO.setUpdatedBy(signUpDTO.getFirstName() + " " + signUpDTO.getLastName());
            // Set audit details
            raiseComplaintDTO.setCreatedDate(LocalDateTime.now());
            raiseComplaintDTO.setUpdatedDate(LocalDateTime.now());

            // Set the user ID
            raiseComplaintDTO.setUserId(currentUser.getId()); // Assuming getId() returns the user's ID

            entityManager.persist(raiseComplaintDTO);
            entityManager.getTransaction().commit();

            return Optional.of(raiseComplaintDTO);

        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return Optional.empty();
    }

    @Override
    public List<RaiseComplaintDTO> findAllComplaints(int userId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            System.out.println("Running findAllComplaints in ComplaintRepoImpl");
            Query query = entityManager.createQuery("SELECT r FROM RaiseComplaintDTO r WHERE r.userId = :userId ORDER BY r.updatedDate DESC");
            query.setParameter("userId",userId);
            System.out.println(query);
            List<RaiseComplaintDTO> raiseComplaintDTOS = query.getResultList();
            System.out.println(raiseComplaintDTOS);
            return raiseComplaintDTOS;
        }
        catch (Exception e){
            System.out.println("Error found while fetching the data");
            e.printStackTrace();
        }
        finally {
            entityManager.close();
        }

        return Collections.emptyList();
    }

    @Override
    public List<RaiseComplaintDTO> findAllComplaintsForAdmin() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            System.out.println("Running findAllComplaints in ComplaintRepoImpl");
            Query query = entityManager.createQuery("SELECT r FROM RaiseComplaintDTO r ORDER BY r.updatedDate DESC");
//            query.setParameter("userId",userId);
            System.out.println(query);
            List<RaiseComplaintDTO> raiseComplaintDTOS = query.getResultList();
            System.out.println(raiseComplaintDTOS);
            return raiseComplaintDTOS;
        }
        catch (Exception e){
            System.out.println("Error found while fetching the data");
            e.printStackTrace();
        }
        finally {
            entityManager.close();
        }

        return Collections.emptyList();
    }

    @Override
    public Optional<RaiseComplaintDTO> findComplaintById(int complaintId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            System.out.println("Running findComplaintById in ComplaintRepoImpl");
            RaiseComplaintDTO complaint = entityManager.find(RaiseComplaintDTO.class, complaintId);
            System.out.println(complaint);
            return Optional.ofNullable(complaint);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<RaiseComplaintDTO> findByUserIdAndStatus(int userId, String status) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<RaiseComplaintDTO> query = entityManager.createQuery(
                    "SELECT r FROM RaiseComplaintDTO r WHERE r.userId = :userId AND r.status = :status",
                    RaiseComplaintDTO.class);
            query.setParameter("userId", userId);
            query.setParameter("status", status);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<RaiseComplaintDTO> findByUserStatusForAdmin(String status) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<RaiseComplaintDTO> query = entityManager.createQuery(
                    "SELECT r FROM RaiseComplaintDTO r WHERE r.status = :status",
                    RaiseComplaintDTO.class);
//            query.setParameter("userId", userId);
            query.setParameter("status", status);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<RaiseComplaintDTO> findById(int complaintId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            System.out.println("Running findById in ComplaintRepoImpl");
            RaiseComplaintDTO complaint = entityManager.find(RaiseComplaintDTO.class, complaintId);
            System.out.println(complaint);
            return Optional.ofNullable(complaint);
        } finally {
            entityManager.close();
        }
    }

    @Override
    @Transactional
    public Optional<RaiseComplaintDTO> mergeDescription(RaiseComplaintDTO raiseComplaintDTO) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            // Update description using JPQL query
            int updatedEntities = entityManager.createQuery(
                            "UPDATE RaiseComplaintDTO c SET c.description = :description WHERE c.complaintId = :complaintId")
                    .setParameter("description", raiseComplaintDTO.getDescription())
                    .setParameter("complaintId", raiseComplaintDTO.getComplaintId())
                    .executeUpdate();

            entityManager.getTransaction().commit();

            if (updatedEntities > 0) {
                return Optional.of(raiseComplaintDTO);
            } else {
                return Optional.empty();
            }
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            System.out.println("Exception while updating description: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<RaiseComplaintDTO> searchComplaintsByType(String keyword) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String queryStr = "SELECT r FROM RaiseComplaintDTO r " +
                "WHERE r.complaintType LIKE CONCAT('%', :keyword, '%')";

        TypedQuery<RaiseComplaintDTO> query = entityManager.createQuery(queryStr, RaiseComplaintDTO.class);
        query.setParameter("keyword", keyword);

        return query.getResultList();
    }

    @Override
    public List<RaiseComplaintDTO> searchComplaintsByCity(String keyword) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String queryStr = "SELECT r FROM RaiseComplaintDTO r " +
                "WHERE r.city LIKE CONCAT('%', :keyword, '%')";

        TypedQuery<RaiseComplaintDTO> query = entityManager.createQuery(queryStr, RaiseComplaintDTO.class);
        query.setParameter("keyword", keyword);

        return query.getResultList();
}

    @Override
    public List<RaiseComplaintDTO> searchComplaintsByUpdatedDate(String keyword) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String queryStr = "SELECT r FROM RaiseComplaintDTO r " +
                "WHERE r.updatedDate LIKE CONCAT('%', :keyword, '%')";

        TypedQuery<RaiseComplaintDTO> query = entityManager.createQuery(queryStr, RaiseComplaintDTO.class);
        query.setParameter("keyword", keyword);

        return query.getResultList();
    }



    @Override
    public List<RaiseComplaintDTO> searchComplaintsByTypeForAdmin(String complaintType) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String queryStr = "SELECT r FROM RaiseComplaintDTO r " +
                "WHERE r.complaintType = :complaintType";

        TypedQuery<RaiseComplaintDTO> query = entityManager.createQuery(queryStr, RaiseComplaintDTO.class);
        query.setParameter("complaintType", complaintType);

        return query.getResultList();
    }

    @Override
    public List<RaiseComplaintDTO> searchComplaintsByCityForAdmin(String city) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String queryStr = "SELECT r FROM RaiseComplaintDTO r " +
                "WHERE r.city = :city";

        TypedQuery<RaiseComplaintDTO> query = entityManager.createQuery(queryStr, RaiseComplaintDTO.class);

        query.setParameter("city", city);

        List<RaiseComplaintDTO> complaintDTOS = query.getResultList();
        System.out.println("Query executed for area: " +city+ ", Result size: " +complaintDTOS.size());

        return query.getResultList();
    }

    @Override
    public List<RaiseComplaintDTO> searchComplaintsBycomplaintTypeAndCityAndComplaintStatusForAdmin(String complaintType, String city, String complaintStatus) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String queryStr = "SELECT r FROM RaiseComplaintDTO r " +
                "WHERE r.complaintType = :complaintType AND r.city = :city AND r.complaintStatus = :complaintStatus";

        TypedQuery<RaiseComplaintDTO> query = entityManager.createQuery(queryStr, RaiseComplaintDTO.class);
        query.setParameter("complaintType", complaintType);
        query.setParameter("city", city);
        query.setParameter("complaintStatus", complaintStatus);

        return query.getResultList();
    }

    @Override
    public List<RaiseComplaintDTO> searchComplaintsBycomplaintTypeAndComplaintStatusForAdmin(String complaintType,String complaintStatus) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String queryStr = "SELECT r FROM RaiseComplaintDTO r " +
                "WHERE r.complaintType = :complaintType AND r.complaintStatus = :complaintStatus";

        TypedQuery<RaiseComplaintDTO> query = entityManager.createQuery(queryStr, RaiseComplaintDTO.class);
        query.setParameter("complaintType", complaintType);
        query.setParameter("complaintStatus", complaintStatus);

        return query.getResultList();
    }

    @Override
    public List<RaiseComplaintDTO> searchComplaintsCityAndComplaintStatusForAdmin(String city, String complaintStatus) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String queryStr = "SELECT r FROM RaiseComplaintDTO r " +
                "WHERE r.city = :city AND r.complaintStatus = :complaintStatus";

        TypedQuery<RaiseComplaintDTO> query = entityManager.createQuery(queryStr, RaiseComplaintDTO.class);
        query.setParameter("city", city);
        query.setParameter("complaintStatus", complaintStatus);

        return query.getResultList();
    }

    @Override
    public List<RaiseComplaintDTO> searchComplaintsBycomplaintTypeAndCityForAdmin(String complaintType, String city) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String queryStr = "SELECT r FROM RaiseComplaintDTO r " +
                "WHERE r.complaintType = :complaintType AND r.city = :city";

        TypedQuery<RaiseComplaintDTO> query = entityManager.createQuery(queryStr, RaiseComplaintDTO.class);
        query.setParameter("complaintType", complaintType);
        query.setParameter("city", city);

        return query.getResultList();
    }



    @Override
    public List<RaiseComplaintDTO> searchComplaintsBycomplaintTypeOrcityForAdmin(String complaintType, String city, String complaintStatus) {
        System.out.println("or complaint");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String queryStr = "SELECT r FROM RaiseComplaintDTO r " +
                "WHERE r.complaintType = :complaintType OR r.city = :city OR r.complaintStatus = :complaintStatus";

        TypedQuery<RaiseComplaintDTO> query = entityManager.createQuery(queryStr, RaiseComplaintDTO.class);
        System.out.println("Running searchComplaintsBycomplaintTypeOrcityForAdmin in ComplaintRepoImpl" +query);
        query.setParameter("complaintType", complaintType);
        query.setParameter("city", city);
        query.setParameter("complaintStatus", complaintStatus);
        List<RaiseComplaintDTO> list = query.getResultList();

        System.out.println(list);

        return list;
    }

    @Override
    public List<RaiseComplaintDTO> findAllComplaints() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<RaiseComplaintDTO> query = entityManager.createQuery(
                    "SELECT r FROM RaiseComplaintDTO r",
                    RaiseComplaintDTO.class);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<WaterDeptDTO> getdeptIdAnddeptName() {
        System.out.println("Dept Id and Dept Name");

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<WaterDeptDTO> list=null;


        try {
            String queryStr = "SELECT r FROM WaterDeptDTO r";
            System.out.println(queryStr);
            TypedQuery<WaterDeptDTO> query = entityManager.createQuery(queryStr, WaterDeptDTO.class);
            list = query.getResultList();
            System.out.println(list);
        } finally {
            entityManager.close();
        }

        return list;
    }

    @Override
    public boolean savedeptIdAnddeptName(int complaintId,int deptId,String complaintStatus) {
        System.out.println("Dept Id and Dept Name");

        EntityManager entityManager = entityManagerFactory.createEntityManager();


        try {
            entityManager.getTransaction().begin();
            String queryStr = "UPDATE RaiseComplaintDTO r SET r.deptAssign = :deptId, r.complaintStatus = :complaintStatus WHERE r.complaintId = :complaintId";
            System.out.println(queryStr);
            Query query = entityManager.createQuery(queryStr);
            query.setParameter("deptId",String.valueOf(deptId));
            query.setParameter("complaintStatus",complaintStatus);
            query.setParameter("complaintId",complaintId);
            query.executeUpdate();

            entityManager.getTransaction().commit();
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            entityManager.getTransaction();
        }
        finally {
            entityManager.close();
        }

        return false;
    }

    @Override
    @Transactional
    public Optional<RaiseComplaintDTO> saveHistory(HistoryDTO historyDTO, RaiseComplaintDTO raiseComplaintDTO, RequestToDeptAndStatusOfComplaintDto requestToDeptAndStatusOfComplaintDto) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();

//            historyDTO.setUserId(raiseComplaintDTO.getUserId());
            historyDTO.setComplaintId(raiseComplaintDTO.getComplaintId());
            historyDTO.setDepartmentId(requestToDeptAndStatusOfComplaintDto.getDepartmentId());
            historyDTO.setComplaintStatus(raiseComplaintDTO.getComplaintStatus());

            // Persisting HistoryDTO
            entityManager.persist(historyDTO);

            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            System.out.println("Exception while saving data: " + e);
            return Optional.empty();
        } finally {
            entityManager.close();
        }
        return Optional.of(raiseComplaintDTO);
    }


    @Override
    public List<HistoryDTO> findComplaintHistoryByComplaintId(HistoryDTO historyDTO) {
        System.out.println("History  in repo "+historyDTO);
        try{
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            TypedQuery<HistoryDTO> query = entityManager.createQuery(
                    "SELECT r FROM HistoryDTO r WHERE r.complaintId = :complaintId", HistoryDTO.class);
            query.setParameter("complaintId", historyDTO.getComplaintId());
            return query.getResultList();
        }catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
}
