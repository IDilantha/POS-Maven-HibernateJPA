package dao.custom.impl;


import dao.CrudDAOImpl;
import dao.custom.OrderDetailDAO;
import entity.OrderDetail;
import entity.OrderDetailPK;
import org.hibernate.query.NativeQuery;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAOImpl extends CrudDAOImpl<OrderDetail,OrderDetailPK> implements OrderDetailDAO {

    @Override
    public boolean existsByItemCode(String itemCode) throws Exception {
        NativeQuery nativeQuery = session.createNativeQuery("SELECT * FROM OrderDetail WHERE itemCode=?");
        nativeQuery.setParameter(1,itemCode);
        return nativeQuery.uniqueResult() != null ;
    }
}
