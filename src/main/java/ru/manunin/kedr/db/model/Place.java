package ru.manunin.kedr.db.model;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Александр on 25.11.2017.
 */
public class Place implements BudgetObject {

    private String name;
    private UUID placeId;

    public Place() {
        this.placeId = UUID.randomUUID();
    }

    public UUID getUUID() {
        return placeId;
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
