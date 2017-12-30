package ru.manunin.kedr.db.model;

import com.sun.scenario.effect.impl.prism.PrImage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Александр on 25.11.2017.
 */
public class Account extends BudgetObjectTamplate {



    private ArrayList<Sale> saleList = new ArrayList<Sale>();
    public Account(String accountName) {
        super(accountName, "account", "account_uuid", "account_name");
    }

    public ArrayList<Sale> getSaleList() {
        return saleList;
    }

//    public void setSaleList(ArrayList<Sale> saleList) {
//        this.saleList = saleList;
//    }

}
