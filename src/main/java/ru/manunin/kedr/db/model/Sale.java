package ru.manunin.kedr.db.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Александр on 18.11.2017.
 */
public class Sale {

    private final String TABLE_NAME = "sale";
    private final String UUID_COLUMN = "sale_uuid";
    private final String SALE_DATE = "sale_date";
    private final String AMOUND = "amount";
    private final String ACCOUNT_ID = "account_id";
    private final String GROUP_ID = "group_id";
    private final String PLACE_ID = "place_id";
    private final String NOTES = "notes";
    private final String CUSTOMER_ID = "customer_id";

    private int id;
    private Date date;
    private Double sum;
    private String notes;
    private UUID saleUUID;
    private int accountId;
    private int customerId;

    private Logger logger = LoggerFactory.getLogger("ru.manunin.kedr.db.model.Sale");

    public int getAccountId() {
        return accountId;
    }


    protected Sale() {
        this.saleUUID = UUID.randomUUID();
    }


    protected Sale(int id, UUID saleUUID, Date date, Double sum, int accountId, int groupId, int placeId, String notes, int customerId) {
        this.id = id;
        this.date = date;
        this.sum = sum;
        this.notes = notes;
        this.saleUUID = saleUUID;
        this.accountId = accountId;
        this.customerId = customerId;
        this.groupId = groupId;
        this.placeId = placeId;
    }

    protected void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    protected int getCustomerId() {
        return customerId;
    }

    protected void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getGroupId() {
        return groupId;
    }

    protected void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getPlaceId() {
        return placeId;
    }

    protected void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    private int groupId;
    private int placeId;

    public UUID getSaleUUID() {
        return saleUUID;
    }


    public Date getDate() {
        return date;
    }

    protected void setDate(Date date) {
        this.date = date;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public String getNotes() {
        return notes;
    }

    protected void setNotes(String notes) {
        this.notes = notes;
    }

    public void insert(Connection connection) {

        PreparedStatement stat = null;
        String stringUUID = saleUUID.toString();//.replaceAll("-", "");
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
            stat = connection.prepareStatement(insertSql);
            stat.setString(1, stringUUID);
            stat.setDate(2, new java.sql.Date(this.date.getTime()));
            stat.setDouble(3, this.sum);
            stat.setInt(4, this.accountId);
            stat.setInt(5, this.groupId);
            stat.setInt(6, this.placeId);
            stat.setString(7, this.notes);
            stat.setInt(8, this.customerId);
            stat.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error of updating " + e.getMessage());
        } finally {
            try {
                stat.close();
            } catch (SQLException e) {
                logger.error("Error of closing statement " + e.getMessage());
            }
        }

    }

}
