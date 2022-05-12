package fr.pangolins.leProPlusPlus.exceptions.bankAccount;

import me.alidg.errors.annotation.ExceptionMapping;
import org.springframework.http.HttpStatus;

@ExceptionMapping(statusCode = HttpStatus.BAD_REQUEST, errorCode = "bankAccount.request.noBody")
public class BankAccountRequestNoBodyException extends RuntimeException {
    public BankAccountRequestNoBodyException() {
        super("You must submit a body");
    }
}
