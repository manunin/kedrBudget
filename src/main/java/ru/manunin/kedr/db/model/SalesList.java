package ru.manunin.kedr.db.model;

import ru.manunin.kedr.db.DbConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class SalesList<T extends Sale> extends ArrayList<Sale> {

    private final String TABLE_NAME = "sale";

    public SalesList() {
        super();
        PreparedStatement ps;
        try {
            String selectSQL = "SELECT * from " + TABLE_NAME + ";";
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
                this.add(sale);
            }
        } catch (SQLException e) {
            System.err.println("Error of closing statement " + e.getMessage());
            System.err.println("Error code " + e.getErrorCode());
        }
    }

    public boolean isSaleExists(Sale saleFromExcel) {

        for (Sale saleFromDb : this) {
//            System.out.println(saleFromDb.getDate() + " " + saleFromDb.getSum() + " " + saleFromDb.getCustomerId() + "" + saleFromDb.getGroupId());
//            System.out.println(saleFromExcel.getDate() + " " + saleFromExcel.getSum() + " " + saleFromExcel.getCustomerId() + "" + saleFromExcel.getGroupId());
            // TODO: 13.01.2018 Realize the way to compare of two sales
            if (saleFromDb.getDate().equals(saleFromExcel.getDate())
                    && saleFromDb.getSum().equals(saleFromExcel.getSum())
                    && saleFromDb.getCustomerId() == saleFromExcel.getCustomerId()
                    && saleFromDb.getGroupId() == saleFromExcel.getGroupId()) {
                return true;
            }
        }
        return false;
    }

}
