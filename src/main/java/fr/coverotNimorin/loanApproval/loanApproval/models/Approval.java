package fr.coverotNimorin.loanApproval.loanApproval.models;

import java.time.LocalDateTime;

public class Approval {
    private String id;
    private String accountId;
    private double amount;
    private LocalDateTime dateTime;

    public Approval() {
        //
    }

    public Approval(String id, String accountId, double amount, LocalDateTime dateTime) {
        this.id = id;
        this.accountId = accountId;
        this.amount = amount;
        this.dateTime = dateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
