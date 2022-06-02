package fr.pangolins.leProPlusPlus.domain.exception.entities;

import me.alidg.errors.annotation.ExceptionMapping;
import me.alidg.errors.annotation.ExposeAsArg;
import org.springframework.http.HttpStatus;

@ExceptionMapping(statusCode = HttpStatus.NOT_FOUND, errorCode = "entity.not_found")
public class EntityNotFoundException extends RuntimeException {
    @ExposeAsArg(0) private String id;

    public EntityNotFoundException(String id) {
        super(String.format("Entity not found with id %s", id));
        this.id = id;
    }
}
