package fr.pangolins.leProPlusPlus.exceptions.services.clients;

import me.alidg.errors.annotation.ExceptionMapping;
import org.springframework.http.HttpStatus;

@ExceptionMapping(statusCode = HttpStatus.BAD_REQUEST, errorCode = "checkAccountClientService.noUrl")
public class CheckAccountClientServiceNoUrlException extends RuntimeException {
    public CheckAccountClientServiceNoUrlException() {
        super("No url has been provided for consuming the CheckAccount service");
    }
}
