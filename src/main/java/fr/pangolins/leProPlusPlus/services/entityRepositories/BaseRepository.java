package fr.pangolins.leProPlusPlus.services.entityRepositories;

import fr.pangolins.leProPlusPlus.entities.BankAccount;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import java.util.Set;

public abstract class BaseRepository {

    protected final void validateEntity(@NotNull BankAccount entity) throws ConstraintViolationException {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<BankAccount>> violations = validator.validate(entity);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
