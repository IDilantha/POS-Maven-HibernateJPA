package dao.custom;

import dao.SuperDAO;
import entity.CustomEntity;

import java.util.List;

public interface QueryDAO extends SuperDAO {



    /**
     *
     * @param query customerId, customerName, orderId, orderDate
     * @return
     * @throws Exception
     */
    List<CustomEntity> getOrdersInfo() throws Exception;

}
