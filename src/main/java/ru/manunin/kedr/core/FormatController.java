package ru.manunin.kedr.core;

import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.manunin.kedr.db.model.Sale;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


/**
 * Created by Александр on 18.11.2017.
 */
public class FormatController {

    private Logger logger = LoggerFactory.getLogger("ru.manunin.kedr.core.FormatController");

    private HashMap<Integer, String> columnMap = new HashMap<Integer, String>();

    public HashMap<Integer, String> getColumnMap() {
        return columnMap;
    }


    public boolean isHeaderCorrect(Sheet sheet) {

        String[] controlColumns = Property.getProperty("Sales_Columns").split(",");
        ArrayList<String> controlList = new ArrayList<String>(Arrays.asList(controlColumns));

        for (Cell cell : sheet.getRow(sheet.getFirstRowNum())) {
            if (cell.getStringCellValue() != null) {

                if (controlList.contains(cell.getStringCellValue()) && !columnMap.containsValue(cell.getStringCellValue())) {
                    columnMap.put(cell.getColumnIndex(), cell.getStringCellValue());
                }
            }
        }

        if (columnMap.size() == controlList.size()) {
            return true;
        } else {
//            System.out.println("Format of the sheet " + sheet.getSheetName() + " isn't correct");
            logger.warn("Format of the sheet " + sheet.getSheetName() + " isn't correct");
            return false;
        }

    }

    public boolean isBodySheetCorrect(Sheet salesSheet) {

        int errorCount = 0;
        for (Row row : salesSheet) {
            if (row.getRowNum() != salesSheet.getFirstRowNum()) {
                for (Cell cell : row) {
                    if (columnMap.get(cell.getColumnIndex()) != null) {
//                        Sale sale = new Sale();
                        switch (OrderFieldsEmum.valueOf(columnMap.get(cell.getColumnIndex()))) {
                            case Date:
                                if (!DateUtil.isCellDateFormatted(cell)) {
//                                    sale.setDate(cell.getDateCellValue());
//                                } else {
                                    errorCount++;
//                                    System.out.println("Format of the cell isn't correct: " + salesSheet.getSheetName() + " Row: " + row.getRowNum() + " Column: " + cell.getColumnIndex());
                                    printError(salesSheet,row,cell);
                                }
                                break;
                            case Customer:
                                if (cell.getCellTypeEnum() != CellType.STRING) {
                                    errorCount++;
//                                    System.out.println("Format of the cell isn't correct: " + salesSheet.getSheetName() + " Row: " + row.getRowNum() + " Column: " + cell.getColumnIndex());
                                    printError(salesSheet,row,cell);
                                }
                                break;
                            case Sum:
                                if (cell.getCellTypeEnum() != CellType.NUMERIC && cell.getCellTypeEnum() != CellType.FORMULA) {
                                    errorCount++;
//                                    System.out.println("Format of the cell isn't correct: " + salesSheet.getSheetName() + " Row: " + row.getRowNum() + " Column: " + cell.getColumnIndex());
                                    printError(salesSheet,row,cell);
                                }
                                break;
                            case Account:
                                if (cell.getCellTypeEnum() != CellType.STRING && cell.getCellTypeEnum() != CellType.FORMULA) {
                                    errorCount++;
//                                    System.out.println("Format of the cell isn't correct: " + salesSheet.getSheetName() + " Row: " + row.getRowNum() + " Column: " + cell.getColumnIndex());
                                    printError(salesSheet,row,cell);
                                }
                                break;
                            case Group:
                                if (cell.getCellTypeEnum() != CellType.STRING && cell.getCellTypeEnum() != CellType.BLANK) {
                                    errorCount++;
//                                    System.out.println("Format of the cell isn't correct: " + salesSheet.getSheetName() + " Row: " + row.getRowNum() + " Column: " + cell.getColumnIndex());
                                    printError(salesSheet,row,cell);
                                }
                                break;
                            case Notes:
                                if (cell.getCellTypeEnum() != CellType.STRING && cell.getCellTypeEnum() != CellType.BLANK) {
                                    errorCount++;
//                                    System.out.println("Format of the cell isn't correct: " + salesSheet.getSheetName() + " Row: " + row.getRowNum() + " Column: " + cell.getColumnIndex());
                                    printError(salesSheet,row,cell);
                                }
                                break;
                            case Place:
                                if (cell.getCellTypeEnum() != CellType.STRING && cell.getCellTypeEnum() != CellType.FORMULA) {
                                    errorCount++;
//                                    System.out.println("Format of the cell isn't correct: " + salesSheet.getSheetName() + " Row: " + row.getRowNum() + " Column: " + cell.getColumnIndex());
                                    printError(salesSheet,row,cell);
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }

            }
        }

        if (errorCount > 0) {
            return false;
        } else {
            return true;
        }

    }

    private void printError(Sheet sheet, Row row, Cell cell){
        logger.warn("Format of the cell isn't correct: " + sheet.getSheetName() + " Row: " + row.getRowNum() + " Column: " + cell.getColumnIndex());
    }
}


