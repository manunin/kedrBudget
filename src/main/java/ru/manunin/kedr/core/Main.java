package ru.manunin.kedr.core;


import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.slf4j.LoggerFactory;
import ru.manunin.kedr.db.DbConnector;
import ru.manunin.kedr.db.model.*;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


/**
 * Created by Александр on 18.11.2017.
 */
public class Main {

    private static final String C_WORK_BUDGET_XLSX = "c:/work/Budget.xlsx";
    private static FileReader fileReader;
    private static FileInputStream fileInputStream;
    private static Workbook workbook;
    private static SalesList sales = null;
    private static BudgetObjectListTemp<Account> accounts = null;
    private static BudgetObjectListTemp<Customer> customers = null;
    private static BudgetObjectListTemp<Place> places = null;
    private static BudgetObjectListTemp<Group> groups = null;
    private static SalesList salesFromFile = null;
    private static SalesList salesFromDB = null;
    private static SalesList salesToAdd = null;
    private static Scanner scanner = new Scanner(System.in);
    private static Connection connection = null;
    private static int rowInserted = 0;
    private static int rowNotInserted = 0;
    private static int rowInFile = 0;


    public static void main(String[] args) {

        String x;
        String y;

        org.slf4j.Logger logger = LoggerFactory.getLogger("ru.manunin.kedr.core.Main");

        Property.init();

        while (true) {

            System.out.println("Input connection data");
            x = scanner.nextLine();
            y = scanner.nextLine();

            connection = DbConnector.connect(x, y);
            if (connection != null) {
                logger.info("Connection to DB is successful");
                break;
            }

        }

        //TODO Create Factories
        accounts = new BudgetObjectListTemp<Account>("account", Account.class);
        customers = new BudgetObjectListTemp<Customer>("customer", Customer.class);
        places = new BudgetObjectListTemp<Place>("place", Place.class);
        groups = new BudgetObjectListTemp<Group>("groupTable", Group.class);


        salesFromDB = SalesList.salesDbFactory();
        try {
            salesFromFile = SalesList.salesFileFactory(new File(C_WORK_BUDGET_XLSX), accounts, customers, groups, places);
        } catch (Exception e) {
            e.printStackTrace();
        }
        salesToAdd = new SalesList();

        for (Sale saleFile : salesFromFile) {
            if (!salesFromDB.isSaleExists(saleFile)) {
                salesToAdd.add(saleFile);
            }
        }

        long start = System.nanoTime();
        rowInserted = salesToAdd.addSalesToDB();


        //        for (Sale saleFile : salesFromFile) {
//            if (!salesFromDB.isSaleExists(saleFile)) {
//                salesToAdd.add(saleFile);
//
//                saleFile.insert(connection);
//                logger.info("Added the sale: " + saleFile.getDate() + " " + saleFile.getSum()
//                        + " " + accounts.getById(saleFile.getAccountId()).getName()
//                        + " " + places.getById(saleFile.getPlaceId()).getName()
//                        + " " + groups.getById(saleFile.getGroupId()).getName()
//                        + " " + saleFile.getNotes());
//                rowInserted++;
//            } else {
//                rowNotInserted++;
//            }
//        }
        long elapsedTime = System.nanoTime() - start;
        logger.info(String.format("Elapsed time for sales insertion is %s sec.", String.valueOf(TimeUnit.SECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS))));


        logger.info(String.format("Uploading has been performed: row in file - %d, updated row - %d ",
                salesFromFile.size(), rowInserted));

//
//        try {
//            fileInputStream = new FileInputStream("c:/work/Budget.xlsx");
//            workbook = new XSSFWorkbook(fileInputStream);
//        } catch (IOException e) {
//            logger.error("Error of file proccessing " + e.getMessage());
//            System.exit(1);
//        }
//
//        Property.init();
//
//        try {
//            sales = SalesTransformer.DoTransform(workbook, accounts, customers, groups, places);
//            logger.info(String.format("Uploading has been performed: row in file - %d, updated row - %d, not - %d", SalesTransformer.getRowInFile(), SalesTransformer.getRowInserted(), SalesTransformer.getRowNotInserted()));
//        } catch (Exception e) {
//            logger.error("Error of transfarmation " + e.getMessage());
//            System.exit(1);
//        }


//        Test.;

        DbConnector.close();
        logger.info("DB connection is closed");

    }

}
