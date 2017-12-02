package ru.manunin.kedr.db.model;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Александр on 25.11.2017.
 */
public interface BudgetObject {

    String getName();

    public ArrayList<Sale> getSaleList();

    public void setName(String name);

    public UUID getUUID();

}
