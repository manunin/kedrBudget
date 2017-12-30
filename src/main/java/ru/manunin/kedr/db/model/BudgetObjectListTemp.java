package ru.manunin.kedr.db.model;

import java.util.ArrayList;
import java.util.UUID;

public class BudgetObjectListTemp<T extends BudgetObjectTamplate> extends ArrayList<T> {

    public BudgetObjectListTemp() {
        super();
    }

    public T getByName(String name) {
        for (T element : this) {
            if (element.getName().equals(name))
                return element;
        }
        return null;
    }

    public T getById(int id) {
        for (T element : this) {
            if (element.getId() == id)
                return element;
        }
        return null;
    }

    public void addSaleToListElement(String elementName, Sale sale, Class<T> cls) throws Exception {
        if (this.getByName(elementName) == null) {
            T temp = cls.getDeclaredConstructor(String.class).newInstance(elementName);
            temp.getSaleList().add(sale);
            this.add(temp);
        } else {
            getByName(elementName).getSaleList().add(sale);
        }
    }

}
