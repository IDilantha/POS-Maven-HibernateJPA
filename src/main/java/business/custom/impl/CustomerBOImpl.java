package business.custom.impl;

import business.custom.CustomerBO;
import business.exception.AlreadyExistsInOrderException;
import dao.DAOFactory;
import dao.DAOTypes;
import dao.custom.CustomerDAO;
import dao.custom.OrderDAO;
import db.HibernateUtil;
import dto.CustomerDTO;
import entity.Customer;
import javafx.scene.control.Alert;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {

    private CustomerDAO customerDAO = DAOFactory.getInstance().getDAO(DAOTypes.CUSTOMER);
    private OrderDAO orderDAO = DAOFactory.getInstance().getDAO(DAOTypes.ORDER);

    @Override
    public void saveCustomer(CustomerDTO customer) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            customerDAO.setSession(session);
            session.beginTransaction();
            customerDAO.save(new Customer(customer.getId(), customer.getName(), customer.getAddress()));
            session.getTransaction().commit();
        }

    }

    @Override
    public void updateCustomer(CustomerDTO customer) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            customerDAO.setSession(session);
            session.beginTransaction();
            customerDAO.update(new Customer(customer.getId(), customer.getName(), customer.getAddress()));
            session.getTransaction().commit();
        }
    }

    @Override
    public void deleteCustomer(String customerId) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            customerDAO.setSession(session);
            orderDAO.setSession(session);
            session.beginTransaction();

            if (orderDAO.existsByCustomerId(customerId)) {
                new Alert(Alert.AlertType.ERROR,"Customer Already exist in an order , Unable to Delete");
                return;
            }
            customerDAO.delete(customerId);
            session.getTransaction().commit();
        }

    }

    @Override
    public List<CustomerDTO> findAllCustomers() throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            customerDAO.setSession(session);
            session.beginTransaction();

            List<Customer> alCustomers = customerDAO.findAll();
            List<CustomerDTO> dtos = new ArrayList<>();
            for (Customer customer : alCustomers) {
                dtos.add(new CustomerDTO(customer.getCustomerId(), customer.getName(), customer.getAddress()));
            }
            session.getTransaction().commit();
            return dtos;
        }
    }

    @Override
    public String getLastCustomerId() throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            customerDAO.setSession(session);
            session.beginTransaction();

            String lastCustomerId = customerDAO.getLastCustomerId();
            session.getTransaction().commit();
            return lastCustomerId;
        }
    }

    @Override
    public CustomerDTO findCustomer(String customerId) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            customerDAO.setSession(session);
            session.beginTransaction();

            Customer customer = customerDAO.find(customerId);
            session.getTransaction().commit();
            return new CustomerDTO(customer.getCustomerId(), customer.getName(), customer.getAddress());
        }
    }

    @Override
    public List<String> getAllCustomerIDs() throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            customerDAO.setSession(session);
            session.beginTransaction();

            List<Customer> customers = customerDAO.findAll();
            List<String> ids = new ArrayList<>();
            for (Customer customer : customers) {
                ids.add(customer.getCustomerId());
            }
            session.getTransaction().commit();
            return ids;
        }
    }
}
