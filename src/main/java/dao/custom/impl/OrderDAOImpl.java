package dao.custom.impl;

import dao.CrudDAOImpl;
import dao.custom.OrderDAO;
import entity.Order;
import org.hibernate.query.NativeQuery;

import javax.persistence.Query;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl extends CrudDAOImpl<Order,Integer> implements OrderDAO {

    @Override
    public int getLastOrderId() throws Exception {
        Object o = entityManager.createNativeQuery("SELECT id FROM `Order` ORDER BY id DESC LIMIT 1").getSingleResult();
        return ( o == null ) ? 0 : (int)o;
    }

    @Override
    public boolean existsByCustomerId(String customerId) throws Exception {
        Query nativeQuery = entityManager.createNativeQuery("SELECT * FROM `Order` WHERE customerId=?");
        nativeQuery.setParameter(1,customerId);
        return nativeQuery.getSingleResult() != null;
    }

}
