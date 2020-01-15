package dao.custom.impl;


import dao.custom.QueryDAO;
import entity.CustomEntity;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;

import javax.persistence.EntityManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class QueryDAOImpl implements QueryDAO {

    protected EntityManager entityManager;

    @Override
    public List<CustomEntity> getOrdersInfo() throws Exception {
        List resultList = entityManager.createNativeQuery("SELECT O.id as orderId, C.customerId as customerId, C.name as customerName, O.date as orderDate, SUM(OD.qty * OD.unitPrice) AS orderTotal  FROM Customer C INNER JOIN `Order` O ON C.customerId=O.customerId INNER JOIN  OrderDetail OD on O.id = OD.orderId GROUP BY O.id ").getResultList();
       // Query<CustomEntity> query = nativeQuery.setResultTransformer(Transformers.aliasToBean(CustomEntity.class));
        //List<CustomEntity> list = query.list();
        return null;
    }

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
