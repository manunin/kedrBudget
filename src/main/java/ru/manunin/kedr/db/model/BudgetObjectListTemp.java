package ru.manunin.kedr.db.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.manunin.kedr.db.DbConnector;

import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class BudgetObjectListTemp<T extends BudgetObjectTamplate> extends ArrayList<T> {

    private Logger logger = LoggerFactory.getLogger("ru.manunin.kedr.db.model.BudgetObjectListTemp");

    public BudgetObjectListTemp(String tableName, Class<T> cls) {
        super();
        PreparedStatement ps;
        try {
            String selectSQL = "SELECT * from " + tableName + ";";
            ps = DbConnector.connection.prepareStatement(selectSQL);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                T temp = cls.getDeclaredConstructor(Integer.class, UUID.class, String.class).newInstance(rs.getInt(1),
                        UUID.fromString(rs.getString(2)), rs.getString(3));
                this.add(temp);
            }
        } catch (SQLException e) {
            logger.error("Error of selecting statement " + e.getMessage());
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage());
        } catch (InstantiationException e) {
            logger.error(e.getMessage());
        } catch (NoSuchMethodException e) {
            logger.error(e.getMessage());
        } catch (InvocationTargetException e) {
            logger.error(e.getMessage());
        }

    }

    protected T getByName(String name) {
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

    protected void addSaleToListElement(String elementName, Sale sale, Class<T> cls) throws Exception {
        if (this.getByName(elementName) == null) {
            T temp = cls.getDeclaredConstructor(String.class).newInstance(elementName);
            temp.getSaleList().add(sale);
            this.add(temp);
        } else {
            getByName(elementName).getSaleList().add(sale);
        }
    }


}
