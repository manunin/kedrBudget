package ru.manunin.kedr.db.model;

import java.awt.peer.ChoicePeer;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Александр on 25.11.2017.
 */
public class Customer implements BudgetObject {

    private String customerName;
    private UUID customerId;

    public UUID getUUID() {
        return customerId;
    }

    private ArrayList<Sale> saleArrayList = new ArrayList<Sale>();

    public Customer() {
        this.customerId = UUID.randomUUID();
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
