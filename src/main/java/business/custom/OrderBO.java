package business.custom;


import business.SuperBO;
import dto.OrderDTO;
import dto.OrderDTO2;

import java.util.List;

public interface OrderBO extends SuperBO {

    int getLastOrderId() throws Exception;

    void placeOrder(OrderDTO orderDTO) throws Exception;

    List<OrderDTO2> getOrderInfo() throws Exception;

}
