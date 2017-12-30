package ru.manunin.kedr.core;


import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.manunin.kedr.db.DbConnector;
import ru.manunin.kedr.db.model.*;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * Created by Александр on 18.11.2017.
 */
public class Main {

    public static FileReader fileReader;
    private static FileInputStream fileInputStream;
    private static Workbook workbook;
    private static ArrayList<Sale> sales = null;
    private static BudgetObjectListTemp<Account> accounts = new BudgetObjectListTemp<Account>();
    private static BudgetObjectList<Customer> customers = new BudgetObjectList<Customer>();
    private static BudgetObjectList<Place> places = new BudgetObjectList<Place>();
    private static BudgetObjectListTemp<Group> groups = new BudgetObjectListTemp<Group>();
    private static Scanner scanner = new Scanner(System.in);
    private static Connection connection = null;

    public static void main(String[] args) {

        String x;
        String y;


        while (true) {

            System.out.println("Input connection data");
            x = scanner.nextLine();
            y = scanner.nextLine();

            connection = DbConnector.connect(x, y);
            if (connection != null) break;

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


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

        //print all sales
        if (sales.size() > 0) {
            for (Sale sale : sales) {
                System.out.println("Продажа " + sale.getDate() + '\t' + customers.getById(sale.getCustomerId()).getName() + '\t' + sale.getSum());
            }
        } else {

            System.out.println("Errors've been detected  ");
            System.out.println("Test");

        }
//
        for (Sale sale : sales) {
            sale.insert(DbConnector.connection);
        }

        //print sales by group

        for (Account account : accounts) {

            System.out.println("Счет:" + '\t' + account.getName());

            for (Sale sale : account.getSaleList()) {

                System.out.println("\t\t" + "Продажа " + sale.getDate() + '\t' + customers.getById(sale.getCustomerId()).getName() + '\t' + sale.getSum());

            }

        }

        DbConnector.close();

    }

}
