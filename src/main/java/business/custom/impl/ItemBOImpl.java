package business.custom.impl;

import business.custom.ItemBO;
import dao.DAOFactory;
import dao.DAOTypes;
import dao.custom.ItemDAO;
import dao.custom.OrderDetailDAO;
import db.JPAUtil;
import dto.ItemDTO;
import entity.Item;
import javafx.scene.control.Alert;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class ItemBOImpl implements ItemBO {

    private OrderDetailDAO orderDetailDAO = DAOFactory.getInstance().getDAO(DAOTypes.ORDER_DETAIL);
    private ItemDAO itemDAO = DAOFactory.getInstance().getDAO(DAOTypes.ITEM);

    @Override
    public void saveItem(ItemDTO item) throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        itemDAO.setEntityManager(entityManager);
        entityManager.getTransaction().begin();

        itemDAO.save(new Item(item.getCode(),
                item.getDescription(), item.getUnitPrice(), item.getQtyOnHand()));
        entityManager.getTransaction().commit();
        entityManager.close();

    }

    @Override
    public void updateItem(ItemDTO item) throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        itemDAO.setEntityManager(entityManager);
        itemDAO.update(new Item(item.getCode(),
                item.getDescription(), item.getUnitPrice(), item.getQtyOnHand()));
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void deleteItem(String itemCode) throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        itemDAO.setEntityManager(entityManager);
        orderDetailDAO.setEntityManager(entityManager);
        entityManager.getTransaction().begin();
        if (orderDetailDAO.existsByItemCode(itemCode)) {
            new Alert(Alert.AlertType.ERROR, "Item Already exist in an order , Unable to Delete");
            return;
        }
        itemDAO.delete(itemCode);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public List<ItemDTO> findAllItems() throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        itemDAO.setEntityManager(entityManager);
        entityManager.getTransaction().begin();

        List<Item> allItems = itemDAO.findAll();
        List<ItemDTO> dtos = new ArrayList<>();
        for (Item item : allItems) {
            dtos.add(new ItemDTO(item.getCode(),
                    item.getDescription(),
                    item.getQtyOnHand(),
                    item.getUnitPrice()));
        }
        entityManager.getTransaction().commit();
        entityManager.close();
        return dtos;

    }

    @Override
    public String getLastItemCode() throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        itemDAO.setEntityManager(entityManager);
        entityManager.getTransaction().begin();
        String lastItemCode = itemDAO.getLastItemCode();
        entityManager.getTransaction().commit();
        entityManager.close();
        return lastItemCode;
    }

    @Override
    public ItemDTO findItem(String itemCode) throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        itemDAO.setEntityManager(entityManager);
        entityManager.getTransaction().begin();

        Item item = itemDAO.find(itemCode);
        entityManager.getTransaction().commit();
        entityManager.close();
        return new ItemDTO(item.getCode(), item.getDescription(), item.getQtyOnHand(), item.getUnitPrice());
    }

    @Override
    public List<String> getAllItemCodes() throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        itemDAO.setEntityManager(entityManager);
        entityManager.getTransaction().begin();

        List<Item> allItems = itemDAO.findAll();
        List<String> codes = new ArrayList<>();
        for (Item item : allItems) {
            codes.add(item.getCode());
        }
        entityManager.getTransaction().commit();
        entityManager.close();
        return codes;
    }
}
