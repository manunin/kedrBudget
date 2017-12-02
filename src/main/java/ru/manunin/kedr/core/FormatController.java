package ru.manunin.kedr.core;

import org.apache.poi.ss.usermodel.*;
import ru.manunin.kedr.db.model.Sale;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


/**
 * Created by Александр on 18.11.2017.
 */
public class FormatController {

    private HashMap<Integer, String> columnMap = new HashMap<Integer, String>();

    public HashMap<Integer, String> getColumnMap() {
        return columnMap;
    }

//    public static HashMap<Integer, String> getColumnsMap(Sheet sheet) {
//
//        String[] controlColumns = Property.getProperty("Sales_Columns").split(",");
//        ArrayList<String> controlList = (ArrayList<String>) Arrays.asList(controlColumns);
//        // HashMap<Integer, String> columnMap = null;
//
//
//        for (Row row : sheet) {
//            if (row.getRowNum() == 1) {
//                for (Cell cell : row) {
//                    if (cell.getStringCellValue() != null) {
//                        if (controlList.contains(cell.getStringCellValue())) {
//                            columnMap.put(cell.getColumnIndex(), cell.getStringCellValue());
//                        }
//                    }
//                }
//                if (columnMap.size() == controlList.size()) {
//                    return columnMap;
//                } else {
//                    return null;
//                }
//            }
//        }
//
//        return columnMap;
//
//    }

    public boolean isHeaderCorrect(Sheet sheet) {

        String[] controlColumns = Property.getProperty("Sales_Columns").split(",");
        ArrayList<String> controlList = new ArrayList<String>(Arrays.asList(controlColumns));
        //HashMap<Integer, String> columnMap = null;

        for (Cell cell : sheet.getRow(sheet.getFirstRowNum())) {
            if (cell.getStringCellValue() != null) {
                //System.out.println(cell.getStringCellValue());
                if (controlList.contains(cell.getStringCellValue()) && !columnMap.containsValue(cell.getStringCellValue())) {
                    columnMap.put(cell.getColumnIndex(), cell.getStringCellValue());
                }
            }
        }

        if (columnMap.size() == controlList.size()) {
            return true;
        } else {
            System.out.println("Format of the sheet " + sheet.getSheetName() + " isn't correct");
            return false;
        }

    }

    public boolean isBodySheetCorrect(Sheet salesSheet) {

        int errorCount = 0;
        for (Row row : salesSheet) {
            if (row.getRowNum() != salesSheet.getFirstRowNum()) {
                for (Cell cell : row) {
                    if (columnMap.get(cell.getColumnIndex()) != null) {
                        Sale sale = new Sale();
                        switch (OrderFieldsEmum.valueOf(columnMap.get(cell.getColumnIndex()))) {
                            case Date:
                                if (DateUtil.isCellDateFormatted(cell)) {
                                    sale.setDate(cell.getDateCellValue());
                                } else {
                                    errorCount++;
                                    System.out.println("Format of the cell isn't correct: " + salesSheet.getSheetName() + " Row: " + row.getRowNum() + " Column: " + cell.getColumnIndex());
                                }
                                break;
                            case Customer:
                                if (cell.getCellTypeEnum() != CellType.STRING) {
                                    errorCount++;
                                    System.out.println("Format of the cell isn't correct: " + salesSheet.getSheetName() + " Row: " + row.getRowNum() + " Column: " + cell.getColumnIndex());
                                }
                                break;
                            case Sum:
                                if (cell.getCellTypeEnum() != CellType.NUMERIC && cell.getCellTypeEnum() != CellType.FORMULA) {
                                    errorCount++;
                                    System.out.println("Format of the cell isn't correct: " + salesSheet.getSheetName() + " Row: " + row.getRowNum() + " Column: " + cell.getColumnIndex());
                                }
                                break;
                            case Account:
                                if (cell.getCellTypeEnum() != CellType.STRING && cell.getCellTypeEnum() != CellType.FORMULA) {
                                    errorCount++;
                                    System.out.println("Format of the cell isn't correct: " + salesSheet.getSheetName() + " Row: " + row.getRowNum() + " Column: " + cell.getColumnIndex());
                                }
                                break;
                            case Group:
                                if (cell.getCellTypeEnum() != CellType.STRING && cell.getCellTypeEnum() != CellType.BLANK) {
                                    errorCount++;
                                    System.out.println("Format of the cell isn't correct: " + salesSheet.getSheetName() + " Row: " + row.getRowNum() + " Column: " + cell.getColumnIndex());
                                }
                                break;
                            case Notes:
                                if (cell.getCellTypeEnum() != CellType.STRING && cell.getCellTypeEnum() != CellType.BLANK) {
                                    errorCount++;
                                    System.out.println("Format of the cell isn't correct: " + salesSheet.getSheetName() + " Row: " + row.getRowNum() + " Column: " + cell.getColumnIndex());
                                }
                                break;
                            case Place:
                                if (cell.getCellTypeEnum() != CellType.STRING && cell.getCellTypeEnum() != CellType.FORMULA) {
                                    errorCount++;
                                    System.out.println("Format of the cell isn't correct: " + salesSheet.getSheetName() + " Row: " + row.getRowNum() + " Column: " + cell.getColumnIndex());
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


}


