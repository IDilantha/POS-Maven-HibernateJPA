package dao.custom;

import dao.CrudDAO;
import entity.Item;

public interface ItemDAO extends CrudDAO<Item, String> {

    String getLastItemCode() throws Exception;
}
