package ru.manunin.kedr.core;


import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.slf4j.LoggerFactory;
import ru.manunin.kedr.db.DbConnector;
import ru.manunin.kedr.db.model.*;


import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;


/**
 * Created by Александр on 18.11.2017.
 */
public class Main {

    public static FileReader fileReader;
    private static FileInputStream fileInputStream;
    private static Workbook workbook;
    private static SalesList<Sale> sales = null;
    private static BudgetObjectListTemp<Account> accounts = null;
    private static BudgetObjectListTemp<Customer> customers = null;
    private static BudgetObjectListTemp<Place> places = null;
    private static BudgetObjectListTemp<Group> groups = null;
    private static Scanner scanner = new Scanner(System.in);
    private static Connection connection = null;


    public static void main(String[] args) {

        String x;
        String y;

        org.slf4j.Logger logger = LoggerFactory.getLogger("ru.manunin.kedr.core.Main");


        while (true) {

            System.out.println("Input connection data");
            x = scanner.nextLine();
            y = scanner.nextLine();

            connection = DbConnector.connect(x, y);
            if (connection != null) {
                logger.info("Connection to DB is successful");
                break;
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        accounts = new BudgetObjectListTemp<Account>("account", Account.class);
        customers = new BudgetObjectListTemp<Customer>("customer", Customer.class);
        places = new BudgetObjectListTemp<Place>("place", Place.class);
        groups = new BudgetObjectListTemp<Group>("groupTable", Group.class);


        try {
            fileInputStream = new FileInputStream("c:/work/Budget.xlsx");
            workbook = new XSSFWorkbook(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Property.init();

        try {
            sales = SalesTransformer.tranfer(workbook, accounts, customers, groups, places);
        } catch (Exception e) {
            e.printStackTrace();
        }

        DbConnector.close();

    }

}
