package business.custom.impl;

import business.custom.CustomerBO;
import dao.DAOFactory;
import dao.DAOTypes;
import dao.custom.CustomerDAO;
import dao.custom.OrderDAO;
import db.JPAUtil;
import dto.CustomerDTO;
import entity.Customer;
import javafx.scene.control.Alert;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {

    private CustomerDAO customerDAO = DAOFactory.getInstance().getDAO(DAOTypes.CUSTOMER);
    private OrderDAO orderDAO = DAOFactory.getInstance().getDAO(DAOTypes.ORDER);

    @Override
    public void saveCustomer(CustomerDTO customer) throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        customerDAO.setEntityManager(entityManager);
        entityManager.getTransaction().begin();
        customerDAO.save(new Customer(customer.getId(), customer.getName(), customer.getAddress()));
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void updateCustomer(CustomerDTO customer) throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        customerDAO.setEntityManager(entityManager);
        entityManager.getTransaction().begin();
        customerDAO.update(new Customer(customer.getId(), customer.getName(), customer.getAddress()));
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void deleteCustomer(String customerId) throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        customerDAO.setEntityManager(entityManager);
        orderDAO.setEntityManager(entityManager);
        entityManager.getTransaction().begin();

        if (orderDAO.existsByCustomerId(customerId)) {
            new Alert(Alert.AlertType.ERROR, "Customer Already exist in an order , Unable to Delete");
            return;
        }
        customerDAO.delete(customerId);
        entityManager.getTransaction().commit();
        entityManager.close();


    }

    @Override
    public List<CustomerDTO> findAllCustomers() throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        customerDAO.setEntityManager(entityManager);
        entityManager.getTransaction().begin();

        List<Customer> alCustomers = customerDAO.findAll();
        List<CustomerDTO> dtos = new ArrayList<>();
        for (Customer customer : alCustomers) {
            dtos.add(new CustomerDTO(customer.getCustomerId(), customer.getName(), customer.getAddress()));
        }
        entityManager.getTransaction().commit();
        entityManager.close();
        return dtos;

    }

    @Override
    public String getLastCustomerId() throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        customerDAO.setEntityManager(entityManager);
        entityManager.getTransaction().begin();

        String lastCustomerId = customerDAO.getLastCustomerId();
        entityManager.getTransaction().commit();
        entityManager.close();
        return lastCustomerId;


    }

    @Override
    public CustomerDTO findCustomer(String customerId) throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        customerDAO.setEntityManager(entityManager);
        entityManager.getTransaction().begin();

        Customer customer = customerDAO.find(customerId);
        entityManager.getTransaction().commit();
        entityManager.close();
        return new CustomerDTO(customer.getCustomerId(), customer.getName(), customer.getAddress());

    }

    @Override
    public List<String> getAllCustomerIDs() throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        customerDAO.setEntityManager(entityManager);
        entityManager.getTransaction().begin();

        List<Customer> customers = customerDAO.findAll();
        List<String> ids = new ArrayList<>();
        for (Customer customer : customers) {
            ids.add(customer.getCustomerId());
        }
        entityManager.getTransaction().commit();
        entityManager.close();
        return ids;
    }
}
