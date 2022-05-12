package fr.pangolins.leProPlusPlus.exceptions.bankAccount;

import me.alidg.errors.annotation.ExceptionMapping;
import me.alidg.errors.annotation.ExposeAsArg;
import org.springframework.http.HttpStatus;

@ExceptionMapping(statusCode = HttpStatus.NOT_FOUND, errorCode = "bankAccount.notFound.ownerName")
public class BankAccountNotFoundByOwnerNameException extends RuntimeException {
    @ExposeAsArg(0)
    private final String ownerName;

    public BankAccountNotFoundByOwnerNameException(String ownerName) {
        super("Account not found with owner name " + ownerName + ".");
        this.ownerName = ownerName;
    }

    public String getOwnerName() {
        return ownerName;
    }
}
