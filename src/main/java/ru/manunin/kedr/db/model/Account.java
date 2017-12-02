package ru.manunin.kedr.db.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Александр on 25.11.2017.
 */
public class Account implements BudgetObject {


    private String accountName;
    private UUID accountId;

    public UUID getUUID() {
        return accountId;
    }

    public Account() {
        this.accountId = UUID.randomUUID();
    }

    private ArrayList<Sale> saleList = new ArrayList<Sale>();

    public ArrayList<Sale> getSaleList() {
        return saleList;
    }

    public void setSaleList(ArrayList<Sale> saleList) {
        this.saleList = saleList;
    }

    public void setName(String accountName) {
        this.accountName = accountName;
    }

    public String getName() {
        return accountName;
    }
}
