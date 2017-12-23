package ru.manunin.kedr.db.model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Александр on 25.11.2017.
 */
public class Group implements BudgetObject {

    private String name;
    private Date date;
    private UUID groupUUID;

    public UUID getUUID() {
        return groupUUID;
    }

    public int getID() {
        return 0;
    }


    public Group() {
        this.groupUUID = UUID.randomUUID();
    }

    private ArrayList<Sale> saleArrayList = new ArrayList<Sale>();

    public ArrayList<Sale> getSaleList() {
        return saleArrayList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
