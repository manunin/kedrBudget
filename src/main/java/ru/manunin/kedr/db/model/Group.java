package ru.manunin.kedr.db.model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Александр on 25.11.2017.
 */
public class Group extends BudgetObjectTamplate {


    public static final String GROUP_TABLE = "groupTable";
    public static final String GROUP_UUID = "group_uuid";
    public static final String GROUP_NAME = "group_name";

    public Group(String groupName) {
       // this.groupUUID = UUID.randomUUID();
        super(groupName, GROUP_TABLE, GROUP_UUID, GROUP_NAME);
    }

    public Group(Integer id, UUID uuid, String name){
        super(id, uuid, name);
    }





}
