package fr.pangolins.leProPlusPlus.exceptions.bankAccount;

import me.alidg.errors.annotation.ExceptionMapping;
import me.alidg.errors.annotation.ExposeAsArg;
import org.springframework.http.HttpStatus;

@ExceptionMapping(statusCode = HttpStatus.UNPROCESSABLE_ENTITY, errorCode = "bankAccount.owner.alreadyExists")
public class BankAccountOwnerAlreadyExistsException extends RuntimeException {
    @ExposeAsArg(0)
    private final String ownerName;

    public BankAccountOwnerAlreadyExistsException(String ownerName) {
        super("An account is already owned by \"" + ownerName + "\"");
        this.ownerName = ownerName;
    }

    public String getOwnerName() {
        return ownerName;
    }
}
