package ru.manunin.kedr.core;


import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.manunin.kedr.db.model.*;

import java.util.HashMap;

/**
 * Created by Александр on 18.11.2017.
 */
public class SalesTransformer {

    private static Logger logger = LoggerFactory.getLogger("ru.manunin.kedr.core.SaleTransformr");
    private static final String SALES_PAGES = "Sales_Page";

    private static int rowInserted = 0;
    private static int rowNotInserted = 0;
    private static int rowInFile = 0;

    public static int getRowInFile() {
        return rowInFile;
    }

    public static int getRowInserted() {
        return rowInserted;
    }

    public static int getRowNotInserted() {
        return rowNotInserted;
    }

//    public static SalesList DoTransform(SalesList salesFromFile, SalesList salesFromDB, SalesList salesListToAdd) throws Exception {
//
//        for (Sale saleFile : salesFromFile){
//            if (!salesFromDB.isSaleExists(saleFile)) {
//                salesListToAdd.add(saleFile);
//                logger.info("Added the sale: " + sale.getDate() + " " + sale.getSum()
//                        + " " + accountBudgetObjectList.getById(sale.getAccountId()).getName()
//                        + " " + placeBudgetObjectList.getById(sale.getPlaceId()).getName()
//                        + " " + groupBudgetObjectList.getById(sale.getGroupId()).getName()
//                        + " " + sale.getNotes());
//                rowInserted++;
//            } else {
//                rowNotInserted++;
//            }
//        }
//
//
//
//        SalesList saleList = SalesList.salesDbFactory();
//        String[] salesSheets = Property.getProperty(SALES_PAGES).split(",");
//        HashMap<Integer, String> columnMap;
//
//
//        for (String page : salesSheets) {
//            Sheet sheet = workbook.getSheet(page);
//            FormatController formatController = new FormatController();
//            if (formatController.isHeaderCorrect(sheet) && formatController.isBodySheetCorrect(sheet)) {
//                columnMap = formatController.getColumnMap();
//                for (Row row : sheet) {
//                    if (row.getRowNum() != sheet.getFirstRowNum()) {
//                        rowInFile++;
//                        Sale sale = new Sale();
//                        for (Cell cell : row) {
//                            if (columnMap.get(cell.getColumnIndex()) != null) {
//                                switch (OrderFieldsEmum.valueOf(columnMap.get(cell.getColumnIndex()))) {
//                                    case Date:
//                                        sale.setDate(cell.getDateCellValue());
//                                        break;
//                                    case Customer:
//                                        customerBudgetObjectList.addSaleToListElement(cell.getStringCellValue(), sale, Customer.class);
//                                        sale.setCustomerId(customerBudgetObjectList.getByName(cell.getStringCellValue()).getId());
//                                        break;
//                                    case Sum:
//                                        sale.setSum(cell.getNumericCellValue());
//                                        break;
//                                    case Account:
//                                        accountBudgetObjectList.addSaleToListElement(cell.getStringCellValue(), sale, Account.class);
//                                        sale.setAccountId(accountBudgetObjectList.getByName(cell.getStringCellValue()).getId());
//                                        break;
//                                    case Group:
//                                        groupBudgetObjectList.addSaleToListElement(cell.getStringCellValue(), sale, Group.class);
//                                        sale.setGroupId(groupBudgetObjectList.getByName(cell.getStringCellValue()).getId());
//                                        break;
//                                    case Notes:
//                                        sale.setNotes(cell.getStringCellValue());
//                                        break;
//                                    case Place:
//                                        placeBudgetObjectList.addSaleToListElement(cell.getStringCellValue(), sale, Place.class);
//                                        sale.setPlaceId(placeBudgetObjectList.getByName(cell.getStringCellValue()).getId());
//                                        break;
//                                    default:
//                                        break;
//                                }
//                            }
//                        }
//
//                        if (!saleList.isSaleExists(sale)) {
//                            saleList.addSalesToDB(sale);
//                            logger.info("Added the sale: " + sale.getDate() + " " + sale.getSum()
//                                    + " " + accountBudgetObjectList.getById(sale.getAccountId()).getName()
//                                    + " " + placeBudgetObjectList.getById(sale.getPlaceId()).getName()
//                                    + " " + groupBudgetObjectList.getById(sale.getGroupId()).getName()
//                                    + " " + sale.getNotes());
//                            rowInserted++;
//                        } else {
//                            rowNotInserted++;
//                        }
//
//                    }
//                }
//
//            }
//
//        }
//        return saleList;
//    }


}
