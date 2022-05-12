package fr.pangolins.leProPlusPlus.requestEntities;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BankAccountCreateRequestEntity {

    @NotBlank(message = "bankAccount.ownerName.required")
    private String ownerName;

    @NotNull(message = "bankAccount.balance.required")
    private Double balance;

    public BankAccountCreateRequestEntity() {
        //
    }

    public BankAccountCreateRequestEntity(String ownerName) {
        this.ownerName = ownerName;
    }

    public BankAccountCreateRequestEntity(Double balance) {
        this.balance = balance;
    }

    public BankAccountCreateRequestEntity(String ownerName, Double balance) {
        this.ownerName = ownerName;
        this.balance = balance;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public Double getBalance() {
        return balance;
    }
}
