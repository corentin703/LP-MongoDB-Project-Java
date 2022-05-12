package fr.pangolins.leProPlusPlus.models;

public class BankAccountGetRiskResponse {
    private String accountId;
    private BankAccountRisk risk;

    public BankAccountGetRiskResponse() {
        //
    }

    public BankAccountGetRiskResponse(String accountId, BankAccountRisk risk) {
        this.accountId = accountId;
        this.risk = risk;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public BankAccountRisk getRisk() {
        return risk;
    }

    public void setRisk(BankAccountRisk risk) {
        this.risk = risk;
    }
}
