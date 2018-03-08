package ru.manunin.kedr.core;

import jdk.nashorn.internal.ir.LiteralNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static Logger logger = LoggerFactory.getLogger("ru.manunin.kedr.core.Property");


    static void init() {
        String value;

        try {
            bufferedReader = new BufferedReader(new FileReader("settings.properties"));
            value = bufferedReader.readLine();

            while (value != null) {
                propertyMap.put(value.split("=")[0], value.split("=")[1]);
                System.out.println(propertyMap);
                value = bufferedReader.readLine();
            }

        } catch (FileNotFoundException e) {
            logger.error("File not found: " + e.getMessage());
        } catch (IOException e) {
            logger.error("File proccessing error:" + e.getMessage());
        }
    }

    public static String getProperty(String nameProperty) {
        return propertyMap.get(nameProperty);
    }

}
