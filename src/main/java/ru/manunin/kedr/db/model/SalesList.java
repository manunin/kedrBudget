package ru.manunin.kedr.db.model;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.manunin.kedr.core.FormatController;
import ru.manunin.kedr.core.OrderFieldsEmum;
import ru.manunin.kedr.core.Property;
import ru.manunin.kedr.db.DbConnector;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class SalesList extends ArrayList<Sale> {

    private final static String TABLE_NAME_SELECT = "sale";
    private static Logger logger = LoggerFactory.getLogger("ru.manunin.kedr.db.model.SalesList");
    private static final String SALES_PAGES = "Sales_Page";
    private final String TABLE_NAME = "sale";
    private final String UUID_COLUMN = "sale_uuid";
    private final String SALE_DATE = "sale_date";
    private final String AMOUND = "amount";
    private final String ACCOUNT_ID = "account_id";
    private final String GROUP_ID = "group_id";
    private final String PLACE_ID = "place_id";
    private final String NOTES = "notes";
    private final String CUSTOMER_ID = "customer_id";





    public static SalesList salesDbFactory() {

        SalesList salesList = new SalesList();
        PreparedStatement ps;
        try {
            String selectSQL = "SELECT * from " + TABLE_NAME_SELECT + ";";
            ps = DbConnector.connection.prepareStatement(selectSQL);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Sale sale = new Sale(rs.getInt(1),
                        UUID.fromString(rs.getString(2)),
                        rs.getDate(3),
                        rs.getDouble(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getInt(7),
                        rs.getString(8),
                        rs.getInt(9));
                salesList.add(sale);
            }
        } catch (SQLException e) {
            logger.error("Error of selecting statement " + e.getMessage());
        }
        return salesList;
    }

    public static SalesList salesFileFactory(File file, BudgetObjectListTemp<Account> accountBudgetObjectList
            , BudgetObjectListTemp<Customer> customerBudgetObjectList
            , BudgetObjectListTemp<Group> groupBudgetObjectList
            , BudgetObjectListTemp<Place> placeBudgetObjectList) throws Exception {

        SalesList salesList = new SalesList();

        Workbook workbook = null;
        String[] salesSheets = Property.getProperty(SALES_PAGES).split(",");
        HashMap<Integer, String> columnMap;
        int rowInFile = 0;

        try {
            workbook = new XSSFWorkbook(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }


        for (String page : salesSheets) {
            Sheet sheet = workbook.getSheet(page);
            FormatController formatController = new FormatController();
            if (formatController.isHeaderCorrect(sheet) && formatController.isBodySheetCorrect(sheet)) {
                columnMap = formatController.getColumnMap();
                for (Row row : sheet) {
                    if (row.getRowNum() != sheet.getFirstRowNum()) {
                        rowInFile++;
                        Sale sale = new Sale();
                        for (Cell cell : row) {
                            if (columnMap.get(cell.getColumnIndex()) != null) {
                                switch (OrderFieldsEmum.valueOf(columnMap.get(cell.getColumnIndex()))) {
                                    case Date:
                                        sale.setDate(cell.getDateCellValue());
                                        break;
                                    case Customer:
                                        customerBudgetObjectList.addSaleToListElement(cell.getStringCellValue(), sale, Customer.class);
                                        sale.setCustomerId(customerBudgetObjectList.getByName(cell.getStringCellValue()).getId());
                                        break;
                                    case Sum:
                                        sale.setSum(cell.getNumericCellValue());
                                        break;
                                    case Account:
                                        accountBudgetObjectList.addSaleToListElement(cell.getStringCellValue(), sale, Account.class);
                                        sale.setAccountId(accountBudgetObjectList.getByName(cell.getStringCellValue()).getId());
                                        break;
                                    case Group:
                                        groupBudgetObjectList.addSaleToListElement(cell.getStringCellValue(), sale, Group.class);
                                        sale.setGroupId(groupBudgetObjectList.getByName(cell.getStringCellValue()).getId());
                                        break;
                                    case Notes:
                                        sale.setNotes(cell.getStringCellValue());
                                        break;
                                    case Place:
                                        placeBudgetObjectList.addSaleToListElement(cell.getStringCellValue(), sale, Place.class);
                                        sale.setPlaceId(placeBudgetObjectList.getByName(cell.getStringCellValue()).getId());
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }
                        salesList.add(sale);

                    }
                }

            }

        }
        return salesList;
    }


    public boolean isSaleExists(Sale saleFromExcel) {

        for (Sale saleFromDb : this) {

            if (saleFromDb.getDate().equals(saleFromExcel.getDate())
                    && saleFromDb.getSum().equals(saleFromExcel.getSum())
                    && saleFromDb.getCustomerId() == saleFromExcel.getCustomerId()
                    && saleFromDb.getGroupId() == saleFromExcel.getGroupId()) {
                return true;
            }

        }
        return false;
    }

    public int addSalesToDB() {
        int insertedRow = 0;
        PreparedStatement stat = null;
        String insertSql = "INSERT INTO " + TABLE_NAME + "(" +
                UUID_COLUMN +
                "," + SALE_DATE +
                "," + AMOUND +
                "," + ACCOUNT_ID +
                "," + GROUP_ID +
                "," + PLACE_ID +
                "," + NOTES +
                "," + CUSTOMER_ID +
                ") values(?,?,?,?,?,?,?,?);";
        try {
            stat = DbConnector.connection.prepareStatement(insertSql);
            for (Sale sale : this) {
                String stringUUID = sale.getSaleUUID().toString();//.replaceAll("-", "");
                stat.setString(1, stringUUID);
                stat.setDate(2, new java.sql.Date(sale.getDate().getTime()));
                stat.setDouble(3, sale.getSum());
                stat.setInt(4, sale.getAccountId());
                stat.setInt(5, sale.getGroupId());
                stat.setInt(6, sale.getPlaceId());
                stat.setString(7, sale.getNotes());
                stat.setInt(8, sale.getCustomerId());
                stat.addBatch();
                insertedRow++;
            }

            int[] recordsAffected = stat.executeBatch();
            DbConnector.connection.commit();
            DbConnector.connection.rollback();
        } catch (SQLException e) {
            logger.error("Error of updating " + e.getMessage());
            try {
                DbConnector.connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            } finally {
                try {
                    stat.close();
                } catch (SQLException e2) {
                    logger.error("Error of closing statement " + e2.getMessage());
                }
            }

        }

    return insertedRow;
    }

}
