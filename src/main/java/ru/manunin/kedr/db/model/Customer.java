package ru.manunin.kedr.db.model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Александр on 25.11.2017.
 */
public class Customer extends BudgetObjectTamplate {

    private static final String CUSTOMER = "customer";
    private static final String CUSTOMER_UUID = "customer_uuid";
    private static final String CUSTOMER_NAME = "customer_name";

    public Customer(String customerName) {
        // this.groupUUID = UUID.randomUUID();
        super(customerName, CUSTOMER, CUSTOMER_UUID, CUSTOMER_NAME);
    }

    public Customer(Integer id, UUID uuid, String name){
        super(id, uuid, name);
    }

}
