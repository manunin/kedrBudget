package ru.manunin.kedr.db.model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Александр on 25.11.2017.
 */
public class Place extends BudgetObjectTamplate {

    public Place(String placeName) {
        // this.groupUUID = UUID.randomUUID();
        super(placeName, "place", "place_uuid", "place_name");
    }

    public Place(Integer id, UUID uuid, String name){
        super(id, uuid, name);
    }


}
