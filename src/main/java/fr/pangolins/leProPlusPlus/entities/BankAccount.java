package fr.pangolins.leProPlusPlus.entities;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BankAccount {
    private String id;

    @NotBlank(message = "bankAccount.ownerName.required")
    private String ownerName;

    @NotNull(message = "bankAccount.balance.required")
    private Double balance;

    public BankAccount() {
        //
    }

    public BankAccount(String id, String ownerName, double balance) {
        this.id = id;
        this.ownerName = ownerName;
        this.balance = balance;
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

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
