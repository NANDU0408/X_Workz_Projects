package com.xworkz.springproject.model.repository;

import com.xworkz.springproject.dto.dept.DeptAdminDTO;
import com.xworkz.springproject.dto.dept.EmployeeRegisterDTO;
import com.xworkz.springproject.dto.dept.WaterDeptDTO;
import com.xworkz.springproject.dto.requestDto.HistoryDTO;
import com.xworkz.springproject.dto.requestDto.RequestToDeptAndStatusOfComplaintDto;
import com.xworkz.springproject.dto.user.RaiseComplaintDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class DeptRepoImpl implements DeptRepo{

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int PASSWORD_LENGTH = 16;

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


//            employeeRegisterDTO.setPassword(encodedPassword);
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
    public boolean updateEmpPassword(EmployeeRegisterDTO employeeRegisterDTO) {
        System.out.println("Repo update by password "+employeeRegisterDTO);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            EmployeeRegisterDTO employeeRegisterDTO1 = entityManager.find(EmployeeRegisterDTO.class,employeeRegisterDTO.getId());
            employeeRegisterDTO1.setPassword(employeeRegisterDTO.getPassword());
            employeeRegisterDTO1.setUpdatedBy(employeeRegisterDTO.getFirstName()+" "+employeeRegisterDTO.getLastName());
            employeeRegisterDTO1.setUpdatedDate(LocalDateTime.now());
            employeeRegisterDTO1.setCount(employeeRegisterDTO.getCount()+1);


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

    @Override
    public List<HistoryDTO> findComplaintHistoryByComplaintId(HistoryDTO historyDTO) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<HistoryDTO> query = entityManager.createQuery(
                "SELECT r FROM HistoryDTO r WHERE r.complaintId = :complaintId", HistoryDTO.class);
        query.setParameter("complaintId", historyDTO.getComplaintId());
        System.out.println("Running findComplaintHistoryByComplaintId in ComplaintRepoImpl" +query);

        return query.getResultList();
    }

    @Override
    public List<WaterDeptDTO> getAllDepartments() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<WaterDeptDTO> query = entityManager.createQuery("SELECT d FROM WaterDeptDTO d", WaterDeptDTO.class);
            return query.getResultList();
        } finally {
            entityManager.close();
}
}

    @Override
    @Transactional
    public Optional<EmployeeRegisterDTO> mergeEmp(EmployeeRegisterDTO employeeRegisterDTO) {
        System.out.println("merging the dto "+employeeRegisterDTO);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
//            signUpDTO.setPassword(generateRandomPassword());
            employeeRegisterDTO.setCreatedBy(employeeRegisterDTO.getFirstName() + " " + employeeRegisterDTO.getLastName());
            employeeRegisterDTO.setCreatedDate(LocalDateTime.now());
            employeeRegisterDTO.setUpdatedBy(employeeRegisterDTO.getFirstName() + " " + employeeRegisterDTO.getLastName());
            employeeRegisterDTO.setUpdatedDate(LocalDateTime.now());
            entityManager.merge(employeeRegisterDTO);
            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
//            e.printStackTrace();
            System.out.println("Exception while saving data: " + e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
            return Optional.empty();
        } finally {
            entityManager.close();
        }
        return Optional.of(employeeRegisterDTO);
    }

    @Override
    @Transactional
    public Optional<DeptAdminDTO> mergeDeptAdmin(DeptAdminDTO deptAdminDTO) {
        System.out.println("merging the dto "+deptAdminDTO);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
//            signUpDTO.setPassword(generateRandomPassword());
            deptAdminDTO.setCreatedBy(deptAdminDTO.getFirstName() + " " + deptAdminDTO.getLastName());
            deptAdminDTO.setCreatedDate(LocalDateTime.now());
            deptAdminDTO.setUpdatedBy(deptAdminDTO.getFirstName() + " " + deptAdminDTO.getLastName());
            deptAdminDTO.setUpdatedDate(LocalDateTime.now());
            entityManager.merge(deptAdminDTO);
            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
//            e.printStackTrace();
            System.out.println("Exception while saving data: " + e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
            return Optional.empty();
        } finally {
            entityManager.close();
        }
        return Optional.of(deptAdminDTO);
    }

    @Override
    public String findByDeptName(int departmentId) {
        System.out.println("find by name in dep repo");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try{
            Query query = entityManager.createQuery(
                    "SELECT d.deptName FROM WaterDeptDTO d WHERE d.deptId = :departmentId");
            query.setParameter("departmentId", departmentId);
            return (String) query.getSingleResult();
        }catch (Exception e){
            e.printStackTrace();
        }

        return "-";
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
