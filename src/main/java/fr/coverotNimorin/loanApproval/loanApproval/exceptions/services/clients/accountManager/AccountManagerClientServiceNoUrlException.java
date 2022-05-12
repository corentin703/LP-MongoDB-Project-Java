package fr.coverotNimorin.loanApproval.loanApproval.exceptions.services.clients.accountManager;

import me.alidg.errors.annotation.ExceptionMapping;
import org.springframework.http.HttpStatus;

@ExceptionMapping(statusCode = HttpStatus.BAD_REQUEST, errorCode = "accountManagerClientService.noUrl")
public class AccountManagerClientServiceNoUrlException extends RuntimeException {
    public AccountManagerClientServiceNoUrlException() {
        super("No url has been provided for consuming the CheckAccount service");
    }
}
