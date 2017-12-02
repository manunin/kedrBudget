package ru.manunin.kedr.core;

import jdk.nashorn.internal.ir.LiteralNode;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Handler;

/**
 * Created by Александр on 18.11.2017.
 */
public class Property {

    private static BufferedReader bufferedReader;
    private static HashMap<String, String> propertyMap = new HashMap<String, String>();

    public static void init() {
        String value = null;

        try {
            bufferedReader = new BufferedReader(new FileReader("settings.properties"));
            value = bufferedReader.readLine();

            while (value != null) {
                propertyMap.put(value.split("=")[0], value.split("=")[1]);
                System.out.println(propertyMap);
                value = bufferedReader.readLine();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String nameProperty) {
        return propertyMap.get(nameProperty);
    }

}
