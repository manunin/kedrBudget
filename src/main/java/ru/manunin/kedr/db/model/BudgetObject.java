package ru.manunin.kedr.db.model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Александр on 25.11.2017.
 */
public interface BudgetObject {

    String getName();

    ArrayList<Sale> getSaleList();

    void setName(String name);

    UUID getUUID();

    int getID();

//    int insert(Connection connection);

}
