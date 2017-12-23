package ru.manunin.kedr.db.model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Александр on 25.11.2017.
 */
public class Place implements BudgetObject {

    private String name;
    private UUID placeUUID;

    public Place() {
        this.placeUUID = UUID.randomUUID();
    }

    public UUID getUUID() {
        return placeUUID;
    }

    public int getID() {
        return 0;
    }

    private ArrayList<Sale> saleArrayList = new ArrayList<Sale>();

    public ArrayList<Sale> getSaleList() {
        return saleArrayList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
