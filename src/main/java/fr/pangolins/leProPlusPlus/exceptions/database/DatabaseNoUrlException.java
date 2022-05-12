package fr.pangolins.leProPlusPlus.exceptions.database;

import me.alidg.errors.annotation.ExceptionMapping;
import org.springframework.http.HttpStatus;

@ExceptionMapping(statusCode = HttpStatus.INTERNAL_SERVER_ERROR, errorCode = "database.noUrl")
public class DatabaseNoUrlException extends RuntimeException {

    public DatabaseNoUrlException() {
        super("No url for database in environment variables");
    }
}
