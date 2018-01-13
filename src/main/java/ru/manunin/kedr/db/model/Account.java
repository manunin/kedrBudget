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


    public Account(String accountName) {
        super(accountName, "account", "account_uuid", "account_name");
    }

    public Account(Integer id, UUID uuid, String name){
        super(id, uuid, name);
    }


}
