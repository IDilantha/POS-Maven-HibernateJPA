package dao.custom;

import dao.CrudDAO;
import entity.OrderDetail;
import entity.OrderDetailPK;

public interface OrderDetailDAO extends CrudDAO<OrderDetail, OrderDetailPK> {

    boolean existsByItemCode(String itemCode) throws Exception;

}
