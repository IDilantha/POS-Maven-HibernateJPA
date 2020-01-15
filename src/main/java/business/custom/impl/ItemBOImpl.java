package business.custom.impl;

import business.custom.ItemBO;
import business.exception.AlreadyExistsInOrderException;
import dao.DAOFactory;
import dao.DAOTypes;
import dao.custom.ItemDAO;
import dao.custom.OrderDetailDAO;
import db.HibernateUtil;
import dto.ItemDTO;
import entity.Item;
import javafx.scene.control.Alert;
import org.hibernate.Session;


import java.util.ArrayList;
import java.util.List;

public class ItemBOImpl implements ItemBO {

    private OrderDetailDAO orderDetailDAO = DAOFactory.getInstance().getDAO(DAOTypes.ORDER_DETAIL);
    private ItemDAO itemDAO = DAOFactory.getInstance().getDAO(DAOTypes.ITEM);

    @Override
    public void saveItem(ItemDTO item) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            itemDAO.setSession(session);
            session.beginTransaction();

            itemDAO.save(new Item(item.getCode(),
                    item.getDescription(), item.getUnitPrice(), item.getQtyOnHand()));
            session.getTransaction().commit();
        }
    }

    @Override
    public void updateItem(ItemDTO item) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            itemDAO.setSession(session);
            itemDAO.update(new Item(item.getCode(),
                    item.getDescription(), item.getUnitPrice(), item.getQtyOnHand()));
            session.getTransaction().commit();
        }
    }

    @Override
    public void deleteItem(String itemCode) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            itemDAO.setSession(session);
            orderDetailDAO.setSession(session);
            session.beginTransaction();
            if (orderDetailDAO.existsByItemCode(itemCode)) {
                new Alert(Alert.AlertType.ERROR,"Item Already exist in an order , Unable to Delete");
                return;
            }
            itemDAO.delete(itemCode);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<ItemDTO> findAllItems() throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            itemDAO.setSession(session);
            session.beginTransaction();

            List<Item> allItems = itemDAO.findAll();
            List<ItemDTO> dtos = new ArrayList<>();
            for (Item item : allItems) {
                dtos.add(new ItemDTO(item.getCode(),
                        item.getDescription(),
                        item.getQtyOnHand(),
                        item.getUnitPrice()));
            }
            session.getTransaction().commit();
            return dtos;
        }
    }

    @Override
    public String getLastItemCode() throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            itemDAO.setSession(session);
            session.beginTransaction();
            String lastItemCode = itemDAO.getLastItemCode();
            session.getTransaction().commit();
            return lastItemCode;
        }
    }

    @Override
    public ItemDTO findItem(String itemCode) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            itemDAO.setSession(session);
            session.beginTransaction();

            Item item = itemDAO.find(itemCode);
            session.getTransaction().commit();
            return new ItemDTO(item.getCode(),item.getDescription(),item.getQtyOnHand(),item.getUnitPrice());
        }
    }

    @Override
    public List<String> getAllItemCodes() throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            itemDAO.setSession(session);
            session.beginTransaction();

            List<Item> allItems = itemDAO.findAll();
            List<String> codes = new ArrayList<>();
            for (Item item : allItems) {
                codes.add(item.getCode());
            }
            session.getTransaction().commit();
            return codes;
        }
    }
}
