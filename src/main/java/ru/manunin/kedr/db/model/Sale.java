package ru.manunin.kedr.db.model;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Александр on 18.11.2017.
 */
public class Sale {

    private Date date;
    private Double sum;
    private String notes;
    private UUID saleUUID;
    private UUID accountId;
    private UUID customerId;
    private UUID groupId;
    private UUID placeId;

    public UUID getSaleUUID() {
        return saleUUID;
    }

    public void setSaleUUID(UUID saleUUID) {
        this.saleUUID = saleUUID;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public UUID getGroupId() {
        return groupId;
    }

    public void setGroupId(UUID groupId) {
        this.groupId = groupId;
    }

    public UUID getPlaceId() {
        return placeId;
    }

    public void setPlaceId(UUID placeId) {
        this.placeId = placeId;
    }

    public Sale() {
        this.saleUUID = UUID.randomUUID();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

    public void setNotes(String notes) {
        this.notes = notes;
    }



}
