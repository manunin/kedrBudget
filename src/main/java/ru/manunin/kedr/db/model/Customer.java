package ru.manunin.kedr.db.model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Александр on 25.11.2017.
 */
public class Customer implements BudgetObject {

    private String customerName;
    private UUID customerUUID;

    public UUID getUUID() {
        return customerUUID;
    }

    public void insert(Connection connection) {

    }

    private ArrayList<Sale> saleArrayList = new ArrayList<Sale>();

    public Customer() {
        this.customerUUID = UUID.randomUUID();
    }

    public String getName() {
        return customerName;
    }

    public ArrayList<Sale> getSaleList() {
        return saleArrayList;
    }


    public void setName(String customerName) {
        this.customerName = customerName;
    }


}
