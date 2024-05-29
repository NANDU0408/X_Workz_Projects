package com.xworkz.mvc.repositories.customer;

import com.xworkz.mvc.dto.customer.CustomerDTO;
import com.xworkz.mvc.dto.customer.CustomerLoginDTO;
import com.xworkz.mvc.util.EMFUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class CustomerRegistrationRepoImpl implements CustomerRegistrationRepo{
    private static final Logger log = LoggerFactory.getLogger(CustomerRegistrationRepoImpl.class);

    @Override
    public void customerRegister(CustomerDTO customerDTO, CustomerLoginDTO customerLoginDTO) {
        EntityManager entityManager = EMFUtil.getEntityManager();
        log.info("Data storing process initiated.....");

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(customerDTO);
            CustomerDTO customerDTO1 = findCustomer(customerDTO, entityManager);
            customerLoginDTO.setCustomerId(customerDTO1.getCustomer_id());

            entityManager.getTransaction().commit();
            log.info("Storing the customer data in database is successful");
        }catch (Exception e){
            log.error("Exception while storing data in customer....." +e);
            entityManager.getTransaction().rollback();
        }finally {
            entityManager.close();
        }
    }

    private CustomerDTO findCustomer(CustomerDTO customerDTO, EntityManager entityManager) throws Exception{
        CustomerDTO returnedCustomer = null;

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CustomerDTO> criteriaQuery = criteriaBuilder.createQuery(CustomerDTO.class);
        Root<CustomerDTO> root = criteriaQuery.from(CustomerDTO.class);

        CriteriaQuery<CustomerDTO> query = criteriaQuery.select(root).
                where(criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("name"),customerDTO.getCNAME()),
                        criteriaBuilder.equal(root.get("age"),customerDTO.getAGE()),
                        criteriaBuilder.equal(root.get("gender"),customerDTO.getGender()),
                        criteriaBuilder.equal(root.get("dob"),customerDTO.getDOB()),
                        criteriaBuilder.equal(root.get("address"),customerDTO.getAddress()),
                        criteriaBuilder.equal(root.get("phoneNo"),customerDTO.getPhoneNo()),
                        criteriaBuilder.equal(root.get("idProof"),customerDTO.getIDProof()),
                        criteriaBuilder.equal(root.get("idProofNo"),customerDTO.getIDProofNo()),
                        criteriaBuilder.equal(root.get("emergencyContact"),customerDTO.getEmergencyContact())
                ));

        Query query1 = entityManager.createQuery(query);
        returnedCustomer = (CustomerDTO) query1.getResultList();

        return returnedCustomer;
    }
}
