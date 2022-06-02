package fr.pangolins.leProPlusPlus.domain.exception.entities;

import me.alidg.errors.annotation.ExceptionMapping;
import org.springframework.http.HttpStatus;

@ExceptionMapping(statusCode = HttpStatus.BAD_REQUEST, errorCode = "entity.object_id.invalid")
public class InvalidObjectIdException extends RuntimeException {
    public InvalidObjectIdException(String submitted) {
        super(
            String.format(
                "Invalid hexadecimal representation of an ObjectId (%s)", submitted
            )
        );
    }
}
