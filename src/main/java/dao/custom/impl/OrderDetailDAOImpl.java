package dao.custom.impl;


import dao.CrudDAOImpl;
import dao.custom.OrderDetailDAO;
import entity.OrderDetail;
import entity.OrderDetailPK;
import org.hibernate.query.NativeQuery;

import javax.persistence.Query;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAOImpl extends CrudDAOImpl<OrderDetail,OrderDetailPK> implements OrderDetailDAO {

    @Override
    public boolean existsByItemCode(String itemCode) throws Exception {
        Query nativeQuery = entityManager.createNativeQuery("SELECT * FROM OrderDetail WHERE itemCode=?");
        nativeQuery.setParameter(1,itemCode);
        return nativeQuery.getSingleResult() != null ;
    }
}
