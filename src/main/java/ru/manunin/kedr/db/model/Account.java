package ru.manunin.kedr.db.model;

import com.sun.scenario.effect.impl.prism.PrImage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Александр on 25.11.2017.
 */
public class Account implements BudgetObject {

    private static final String TABLE_NAME = "account";
    private static final String UUID_COLUMN = "account_uuid";
    private static final String NAME_COLUMN = "account_name";

    private String accountName;
    private UUID accountUUID;
    private ArrayList<Sale> saleList = new ArrayList<Sale>();

    public UUID getUUID() {
        return accountUUID;
    }

    public Account() {
        this.accountUUID = UUID.randomUUID();
    }


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

    public void insert(Connection connection) {

        PreparedStatement stat = null;
        String stringUUID = accountUUID.toString().replaceAll("-","");
        String insertSql = "INSERT INTO " + TABLE_NAME + "(" +
                UUID_COLUMN +
                "," + NAME_COLUMN +
                ") values(?, ?);";

        try {
            stat = connection.prepareStatement(insertSql);
            stat.setString(1, stringUUID);
            stat.setString(2, accountName);
            stat.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error of updating " + e.getMessage());
            System.err.println("Error code " + e.getErrorCode());
        } finally {
            try {
                stat.close();
            } catch (SQLException e) {
                System.err.println("Error of closing statement " + e.getMessage());
                System.err.println("Error code " + e.getErrorCode());
            }
        }

    }


}
