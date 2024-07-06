package com.xworkz.springproject.model.repository;

import com.xworkz.springproject.dto.dept.DeptAdminDTO;
import com.xworkz.springproject.dto.dept.EmployeeRegisterDTO;
import com.xworkz.springproject.dto.requestDto.HistoryDTO;
import com.xworkz.springproject.dto.requestDto.RequestToDeptAndStatusOfComplaintDto;
import com.xworkz.springproject.dto.user.RaiseComplaintDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
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

    @Override
    public Optional<EmployeeRegisterDTO> findByEmpEmailAddress(String emailAddress) {
        System.out.println("find by email "+emailAddress);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT s FROM EmployeeRegisterDTO s WHERE s.emailAddress = :emailAddress");
            query.setParameter("emailAddress", emailAddress);
            EmployeeRegisterDTO employeeRegisterDTO = (EmployeeRegisterDTO) query.getSingleResult();
            System.out.println(employeeRegisterDTO);
            return Optional.ofNullable(employeeRegisterDTO);
        } catch (NoResultException e) {
            e.printStackTrace();
            return Optional.empty();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<List<EmployeeRegisterDTO>> findEmployeeByDeptId(int deptId) {

        System.out.println("find employee by deptId "+deptId);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT s FROM EmployeeRegisterDTO s WHERE s.dept_id = :dept_id");
            query.setParameter("dept_id", deptId);

            List<EmployeeRegisterDTO> employeeRegisterDTOList = query.getResultList();
            return Optional.ofNullable(employeeRegisterDTOList);
        } catch (NoResultException e) {
            e.printStackTrace();
//            return Optional.empty();
        } finally {
            entityManager.close();
        }


        return Optional.empty();
    }

    @Override
    public List<EmployeeRegisterDTO> getdeptIdAnddeptNameForDept() {
        System.out.println("Dept Id and Dept Name For Dept Admin");

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<EmployeeRegisterDTO> list=null;


        try {
            String queryStr = "SELECT r FROM EmployeeRegisterDTO r";
            System.out.println(queryStr);
            TypedQuery<EmployeeRegisterDTO> query = entityManager.createQuery(queryStr, EmployeeRegisterDTO.class);
            list = query.getResultList();
            System.out.println(list);
        } finally {
            entityManager.close();
        }

        return list;
    }

    @Override
    public List<RaiseComplaintDTO> findAllComplaintsForDeptAdmin() {
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
    public boolean savedeptIdAnddeptNameForDeptAdmin(int complaintId, int deptId, String complaintStatus, String assignEmployee) {
        System.out.println("Dept Id and Dept Name");

        EntityManager entityManager = entityManagerFactory.createEntityManager();


        try {
            entityManager.getTransaction().begin();
            String queryStr = "UPDATE RaiseComplaintDTO r SET r.assignEmployee = :assignEmployee, r.complaintStatus = :complaintStatus WHERE r.complaintId = :complaintId";
            System.out.println(queryStr);
            Query query = entityManager.createQuery(queryStr);
            query.setParameter("assignEmployee",assignEmployee);
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
    public boolean savedeptIdAnddeptNameForDeptHistory(int complaintId, int deptId, String complaintStatus, String assignEmployee) {
        System.out.println("Dept Id and Dept Name");

        EntityManager entityManager = entityManagerFactory.createEntityManager();


        try {
            entityManager.getTransaction().begin();
            String queryStr = "UPDATE RaiseComplaintDTO r SET r.deptId = :deptId,r.assignEmployee = :assignEmployee, r.complaintStatus = :complaintStatus WHERE r.complaintId = :complaintId";
            System.out.println(queryStr);
            Query query = entityManager.createQuery(queryStr);
            query.setParameter("assignEmployee",assignEmployee);
            query.setParameter("deptId",deptId);
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
    public Optional<RaiseComplaintDTO> saveHistoryForDept(HistoryDTO historyDTO, RaiseComplaintDTO raiseComplaintDTO, RequestToDeptAndStatusOfComplaintDto requestToDeptAndStatusOfComplaintDto) {
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
    public List<RaiseComplaintDTO> searchComplaintsBycomplaintTypeAndcityForDept(String complaintType, String city) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String queryStr = "SELECT r FROM RaiseComplaintDTO r " +
                "WHERE r.complaintType = :complaintType AND r.city = :city";

        TypedQuery<RaiseComplaintDTO> query = entityManager.createQuery(queryStr, RaiseComplaintDTO.class);
        query.setParameter("complaintType", complaintType);
        query.setParameter("city", city);

        return query.getResultList();
    }

    @Override
    public List<RaiseComplaintDTO> searchComplaintsBycomplaintTypeOrcityForDept(String complaintType, String city, String complaintStatus) {
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
}
