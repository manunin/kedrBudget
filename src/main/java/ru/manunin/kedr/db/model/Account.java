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


    private static final String ACCOUNT = "account";
    private static final String ACCOUNT_UUID = "account_uuid";
    private static final String ACCOUNT_NAME = "account_name";

    public Account(String accountName) {
        super(accountName, ACCOUNT, ACCOUNT_UUID, ACCOUNT_NAME);
    }

    public Account(Integer id, UUID uuid, String name){
        super(id, uuid, name);
    }


}
