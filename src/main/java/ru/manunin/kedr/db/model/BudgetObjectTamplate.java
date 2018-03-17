package ru.manunin.kedr.db.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.manunin.kedr.db.DbConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

abstract public class BudgetObjectTamplate {

    private final String TABLE_NAME;
    private final String UUID_COLUMN;
    private final String NAME_COLUMN;

    private int id;
    private String name;
    private UUID uuid;
    private ArrayList<Sale> saleArrayList = new ArrayList<Sale>();

    private Logger logger = LoggerFactory.getLogger("ru.manunin.kedr.db.model.BudgetObjectTamplate");

    protected BudgetObjectTamplate(String name, String tableName, String uuidColumn, String nameColumn) {

        this.name = name;
        this.uuid = UUID.randomUUID();
        this.TABLE_NAME = tableName;
        this.UUID_COLUMN = uuidColumn;
        this.NAME_COLUMN = nameColumn;
        insert(DbConnector.connection);
    }

    protected BudgetObjectTamplate(Integer id, UUID uuid, String name) {

        this.id = id;
        this.name = name;
        this.uuid = uuid;
        this.TABLE_NAME = null;
        this.UUID_COLUMN = null;
        this.NAME_COLUMN = null;
    }

    protected ArrayList<Sale> getSaleList() {
        return saleArrayList;
    }

    protected int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }


    private void insert(Connection connection) {
        //TODO Add batch update
        //TODO Add reject update
        PreparedStatement stat = null;
        String stringUUID = uuid.toString();//.replaceAll("-", "");
        String insertSql = "INSERT INTO " + TABLE_NAME + " (" +
                UUID_COLUMN +
                "," + NAME_COLUMN +
                ") values(?, ?);";

        try {
            stat = connection.prepareStatement(insertSql);
            stat.setString(1, stringUUID);
            stat.setString(2, name);
            stat.executeUpdate();
            connection.commit();
            connection.rollback();
            logger.info("Added budget object %s, name - %s:", this.getClass().toString(), name);
        } catch (SQLException e) {
            logger.error("Error of inserting " + e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                logger.error("Error of rollbacking " + e1.getMessage());
            }
        } finally {
            try {
                stat.close();
            } catch (SQLException e) {
                logger.error("Error of closing statement " + e.getMessage());
            }
        }
        id = selectId(connection, uuid);
    }

    private int selectId(Connection connection, UUID accountUUID) {

        int id = 0;
        ResultSet rs;
        PreparedStatement stmt = null;
        String stringUUID = accountUUID.toString().replaceAll("-", "");
        String selectString = "SELECT * from " + TABLE_NAME + " WHERE " + UUID_COLUMN + "='" + stringUUID + "';";

        try {
            stmt = connection.prepareStatement(selectString);
            rs = stmt.executeQuery();
            rs.next();
            id = rs.getInt(1);
        } catch (SQLException e) {
            logger.error("Error of selecting " + e.getMessage());
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                logger.error("Error of closing statement " + e.getMessage());
            }
        }
        return id;
    }
}
