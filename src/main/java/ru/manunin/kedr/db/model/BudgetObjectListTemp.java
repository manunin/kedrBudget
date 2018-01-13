package ru.manunin.kedr.db.model;

import ru.manunin.kedr.db.DbConnector;

import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class BudgetObjectListTemp<T extends BudgetObjectTamplate> extends ArrayList<T> {


    public BudgetObjectListTemp(String tableName, Class<T> cls) {
        super();
        PreparedStatement ps;
        try {
            String selectSQL = "SELECT * from " + tableName + ";";
            ps = DbConnector.connection.prepareStatement(selectSQL);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                T temp = cls.getDeclaredConstructor(Integer.class, UUID.class, String.class).newInstance(rs.getInt(1), UUID.fromString(rs.getString(2)), rs.getString(3));
                this.add(temp);
            }
        } catch (SQLException e) {
            System.err.println("Error of closing statement " + e.getMessage());
            System.err.println("Error code " + e.getErrorCode());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    public T getByName(String name) {
        for (T element : this) {
            if (element.getName().equals(name))
                return element;
        }
        return null;
    }

    public T getById(int id) {
        for (T element : this) {
            if (element.getId() == id)
                return element;
        }
        return null;
    }

    public void addSaleToListElement(String elementName, Sale sale, Class<T> cls) throws Exception {
        if (this.getByName(elementName) == null) {
            T temp = cls.getDeclaredConstructor(String.class).newInstance(elementName);
            temp.getSaleList().add(sale);
            this.add(temp);
        } else {
            getByName(elementName).getSaleList().add(sale);
        }
    }


}
