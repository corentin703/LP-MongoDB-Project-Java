package fr.pangolins.leProPlusPlus.domain.exception.database;

import me.alidg.errors.annotation.ExceptionMapping;
import org.springframework.http.HttpStatus;

@ExceptionMapping(statusCode = HttpStatus.INTERNAL_SERVER_ERROR, errorCode = "database.operation")
public class DatabaseOperationException extends RuntimeException {

    public DatabaseOperationException() {
        super("A database exception occurred during operation");
    }
}
