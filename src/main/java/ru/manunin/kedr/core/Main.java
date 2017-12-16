package ru.manunin.kedr.core;


import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.manunin.kedr.db.DbConnector;
import ru.manunin.kedr.db.model.*;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Александр on 18.11.2017.
 */
public class Main {

    public static FileReader fileReader;
    private static FileInputStream fileInputStream;
    private static Workbook workbook;
    private static ArrayList<Sale> sales = null;
    private static BudgetObjectList<Account> accounts = new BudgetObjectList<Account>();
    private static BudgetObjectList<Customer> customers = new BudgetObjectList<Customer>();
    private static BudgetObjectList<Place> places = new BudgetObjectList<Place>();
    private static BudgetObjectList<Group> groups = new BudgetObjectList<Group>();

    public static void main(String[] args) {

        DbConnector.connect("root", "EfktO5rspTgILWKfDE9w");
        DbConnector.close();

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

        //print sales by group

        for (Group group : groups) {

            System.out.println("Группа:" + '\t' + group.getName());

            for (Sale sale : group.getSaleList()) {

                System.out.println("\t\t" + "Продажа " + sale.getDate() + '\t' + customers.getById(sale.getCustomerId()).getName() + '\t' + sale.getSum());

            }

        }

    }

}
