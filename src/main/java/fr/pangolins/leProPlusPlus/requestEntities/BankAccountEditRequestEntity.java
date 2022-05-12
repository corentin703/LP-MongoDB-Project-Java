package fr.pangolins.leProPlusPlus.requestEntities;

public class BankAccountEditRequestEntity {

    private String ownerName;
    private Double balance;

    public BankAccountEditRequestEntity() {
        //
    }

    public BankAccountEditRequestEntity(String ownerName) {
        this.ownerName = ownerName;
    }

    public BankAccountEditRequestEntity(Double balance) {
        this.balance = balance;
    }

    public BankAccountEditRequestEntity(String ownerName, Double balance) {
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
