package ru.manunin.kedr.db.model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Александр on 25.11.2017.
 */
public class Group extends BudgetObjectTamplate {


    public Group(String groupName) {
       // this.groupUUID = UUID.randomUUID();
        super(groupName, "groupTable", "group_uuid", "group_name");
    }

    public Group(Integer id, UUID uuid, String name){
        super(id, uuid, name);
    }

    private ArrayList<Sale> saleArrayList = new ArrayList<Sale>();

    public ArrayList<Sale> getSaleList() {
        return saleArrayList;
    }

}
