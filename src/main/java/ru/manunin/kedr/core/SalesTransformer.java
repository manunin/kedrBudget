package ru.manunin.kedr.core;


import org.apache.poi.ss.usermodel.*;
import ru.manunin.kedr.db.model.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Александр on 18.11.2017.
 */
public class SalesTransformer {

    private static final String SALES_PAGES = "Sales_Page";

    public static ArrayList<Sale> tranfer(Workbook workbook, BudgetObjectList<Account> account
            , BudgetObjectList<Customer> customerBudgetObjectList
            , BudgetObjectList<Group> groupBudgetObjectList
            , BudgetObjectList<Place> placeBudgetObjectList) throws Exception {

        ArrayList<Sale> saleList = new ArrayList<Sale>();
        String[] salesSheets = Property.getProperty(SALES_PAGES).split(",");
        HashMap<Integer, String> columnMap = null;
        Account accountTemp = null;
        Customer customerTemp = null;
        Place placeTemp = null;
        Group groupTemp = null;

        for (String page : salesSheets) {
            Sheet sheet = workbook.getSheet(page);
            FormatController formatController = new FormatController();
            if (formatController.isHeaderCorrect(sheet) && formatController.isBodySheetCorrect(sheet)) {
                columnMap = formatController.getColumnMap();
                for (Row row : sheet) {
                    if (row.getRowNum() != sheet.getFirstRowNum()) {
                        Sale sale = new Sale();
                        for (Cell cell : row) {
                            if (columnMap.get(cell.getColumnIndex()) != null) {
                                switch (OrderFieldsEmum.valueOf(columnMap.get(cell.getColumnIndex()))) {
                                    case Date:
                                        sale.setDate(cell.getDateCellValue());
                                        break;
                                    case Customer:
                                        customerBudgetObjectList.addSaleToListElement(cell.getStringCellValue(), sale, Customer.class);
                                        sale.setCustomerId(customerBudgetObjectList.getByName(cell.getStringCellValue()).getUUID());
                                        break;
                                    case Sum:
                                        sale.setSum(cell.getNumericCellValue());
                                        break;
                                    case Account:
                                        account.addSaleToListElement(cell.getStringCellValue(), sale, Account.class);
                                        sale.setAccountId(account.getByName(cell.getStringCellValue()).getUUID());
                                        break;
                                    case Group:
                                        groupBudgetObjectList.addSaleToListElement(cell.getStringCellValue(), sale, Group.class);
                                        sale.setGroupId(groupBudgetObjectList.getByName(cell.getStringCellValue()).getUUID());
                                        break;
                                    case Notes:
                                        sale.setNotes(cell.getStringCellValue());
                                        break;
                                    case Place:
                                        placeBudgetObjectList.addSaleToListElement(cell.getStringCellValue(), sale, Place.class);
                                        sale.setPlaceId(placeBudgetObjectList.getByName(cell.getStringCellValue()).getUUID());
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }
                        saleList.add(sale);

                    }
                }

            }

        }

        return saleList;
    }


}