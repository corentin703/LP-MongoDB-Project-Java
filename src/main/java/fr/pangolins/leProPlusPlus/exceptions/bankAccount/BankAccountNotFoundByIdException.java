package fr.pangolins.leProPlusPlus.exceptions.bankAccount;

import me.alidg.errors.annotation.ExceptionMapping;
import me.alidg.errors.annotation.ExposeAsArg;
import org.springframework.http.HttpStatus;

@ExceptionMapping(statusCode = HttpStatus.NOT_FOUND, errorCode = "bankAccount.notFound.id")
public class BankAccountNotFoundByIdException extends RuntimeException {
    @ExposeAsArg(0)
    private final String id;

    public BankAccountNotFoundByIdException(String id) {
        super("Account not found with id " + id + ".");
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
