package fr.pangolins.leProPlusPlus.models;

import fr.pangolins.leProPlusPlus.entities.BankAccount;

public class BankAccountResponse {
    private String id;
    private String ownerName;
    private double balance;
    private BankAccountRisk risk;

    public BankAccountResponse() {
        //
    }

    public BankAccountResponse(String id, String ownerName, double balance, BankAccountRisk risk) {
        this.id = id;
        this.ownerName = ownerName;
        this.balance = balance;
        this.risk = risk;
    }

    public BankAccountResponse(BankAccount account, BankAccountRisk risk) {
        this.id = account.getId();
        this.ownerName = account.getOwnerName();

        if (account.getBalance() != null)
            this.balance = account.getBalance();
        this.risk = risk;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public BankAccountRisk getRisk() {
        return risk;
    }

    public void setRisk(BankAccountRisk risk) {
        this.risk = risk;
    }
}
