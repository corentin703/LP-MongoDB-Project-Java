package fr.coverotNimorin.loanApproval.loanApproval.responses;

import fr.coverotNimorin.loanApproval.loanApproval.models.BankAccountRisk;

public class BankAccountResponse {
    private String id;
    private String ownerName;
    private Double balance;
    private BankAccountRisk risk;

    public BankAccountResponse() {
        //
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

    public BankAccountRisk getRisk() {
        return risk;
    }

    public void setRisk(BankAccountRisk risk) {
        this.risk = risk;
    }
}
