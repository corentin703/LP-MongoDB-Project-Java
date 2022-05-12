package fr.pangolins.leProPlusPlus.exceptions.database;

import me.alidg.errors.annotation.ExceptionMapping;
import org.springframework.http.HttpStatus;

@ExceptionMapping(statusCode = HttpStatus.INTERNAL_SERVER_ERROR, errorCode = "database.operation.noEffect")
public class DatabaseOperationWithoutEffectException extends RuntimeException {
    public DatabaseOperationWithoutEffectException() {
        super("Database operation executed without effect");
    }
}
