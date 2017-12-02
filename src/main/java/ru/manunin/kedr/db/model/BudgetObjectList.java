package ru.manunin.kedr.db.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Александр on 25.11.2017.
 */

public class BudgetObjectList<T extends BudgetObject> extends ArrayList<T> {

    //private ArrayList<T> list;


    public BudgetObjectList() {
        super();
    }

    public T getByName(String name) {
        for (T element : this) {
            if (element.getName() == name)
                return element;
        }
        return null;
    }

    public T getById(UUID uuid) {
        for (T element : this) {
            if (element.getUUID() == uuid)
                return element;
        }
        return null;
    }

    public void addSaleToListElement(String elementName, Sale sale, Class<T> cls) throws Exception {
        if (this.getByName(elementName) == null) {
            T temp = cls.newInstance();
            temp.setName(elementName);
            temp.getSaleList().add(sale);
            this.add(temp);
        } else {
            getByName(elementName).getSaleList().add(sale);
        }
    }

}
