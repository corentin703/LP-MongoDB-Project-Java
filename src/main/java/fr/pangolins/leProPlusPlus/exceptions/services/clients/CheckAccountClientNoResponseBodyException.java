package fr.pangolins.leProPlusPlus.exceptions.services.clients;

import me.alidg.errors.annotation.ExceptionMapping;
import org.springframework.http.HttpStatus;

@ExceptionMapping(statusCode = HttpStatus.SERVICE_UNAVAILABLE, errorCode = "checkAccountClientService.noResponseBody")
public class CheckAccountClientNoResponseBodyException extends RuntimeException {
    public CheckAccountClientNoResponseBodyException() {
        super("The check account web service responded with empty body");
    }
}
