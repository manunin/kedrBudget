package ru.manunin.kedr.db.model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Александр on 25.11.2017.
 */
public class Place extends BudgetObjectTamplate {

    private static final String PLACE = "place";
    private static final String PLACE_UUID = "place_uuid";
    private static final String PLACE_NAME = "place_name";

    public Place(String placeName) {
        // this.groupUUID = UUID.randomUUID();
        super(placeName, PLACE, PLACE_UUID, PLACE_NAME);
    }

    public Place(Integer id, UUID uuid, String name){
        super(id, uuid, name);
    }


}
