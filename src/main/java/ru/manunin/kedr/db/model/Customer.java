package ru.manunin.kedr.db.model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Александр on 25.11.2017.
 */
public class Customer extends BudgetObjectTamplate {

    public Customer(String customerName) {
        // this.groupUUID = UUID.randomUUID();
        super(customerName, "customer", "customer_uuid", "customer_name");
    }

    private ArrayList<Sale> saleArrayList = new ArrayList<Sale>();

    public ArrayList<Sale> getSaleList() {
        return saleArrayList;
    }
}
