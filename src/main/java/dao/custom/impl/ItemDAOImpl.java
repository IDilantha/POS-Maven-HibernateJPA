package dao.custom.impl;


import dao.CrudDAOImpl;
import dao.custom.ItemDAO;
import entity.Item;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl extends CrudDAOImpl<Item,String> implements ItemDAO {

    @Override
    public String getLastItemCode() throws Exception {
        return (String) entityManager.createNativeQuery("SELECT code FROM Item ORDER BY code DESC LIMIT 1").getSingleResult();
    }

}
