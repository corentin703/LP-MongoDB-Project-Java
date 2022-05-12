package fr.pangolins.leProPlusPlus.services.entityRepositories;

import fr.pangolins.leProPlusPlus.entities.BankAccount;
import fr.pangolins.leProPlusPlus.exceptions.bankAccount.BankAccountNotFoundByIdException;
import fr.pangolins.leProPlusPlus.exceptions.bankAccount.BankAccountNotFoundByOwnerNameException;
import fr.pangolins.leProPlusPlus.exceptions.bankAccount.BankAccountOwnerAlreadyExistsException;
import fr.pangolins.leProPlusPlus.exceptions.database.DatabaseOperationException;
import fr.pangolins.leProPlusPlus.exceptions.database.DatabaseOperationWithoutEffectException;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Validated
public class BankAccountRepository extends BaseRepository {
    private final DatabaseService databaseService;

    public BankAccountRepository(DatabaseService databaseService) {
        this.databaseService = databaseService;
        migrate();
    }

    private void migrate() {
        try (
            Connection connection = databaseService.getConnection();
            Statement statement = connection.createStatement()
        ) {
            statement.execute("CREATE TABLE IF NOT EXISTS accounts (\"id\" VARCHAR(36), \"ownerName\" VARCHAR(255), \"balance\" FLOAT)");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<BankAccount> getAll() {
        List<BankAccount> bankAccounts = new ArrayList<>();

        try (
            Connection connection = databaseService.getConnection();
            Statement statement = connection.createStatement()
        ) {
            ResultSet result = statement.executeQuery("SELECT \"id\", \"ownerName\", \"balance\" FROM accounts");

            while (result.next()) {
                BankAccount account = new BankAccount(
                    result.getString("id"),
                    result.getString("ownerName"),
                    result.getDouble("balance")
                );

                bankAccounts.add(account);
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException();
        }

        return bankAccounts;
    }

    public BankAccount getById(
        @NotBlank(message = "repository.bankAccount.getById.id.notBlank")
        String id
    ) throws BankAccountNotFoundByIdException {
        BankAccount bankAccount;

        try (
            Connection connection = databaseService.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT \"id\", \"ownerName\", \"balance\" FROM accounts WHERE \"id\" = ?")
        ) {
            statement.setString(1, id);
            ResultSet result = statement.executeQuery();

            if (!result.next())
                throw new BankAccountNotFoundByIdException(id);

            bankAccount = new BankAccount(
                result.getString("id"),
                result.getString("ownerName"),
                result.getDouble("balance")
            );
        } catch (SQLException e) {
            throw new DatabaseOperationException();
        }

        return bankAccount;
    }

    public BankAccount getByOwnerName(
        @NotBlank(message = "repository.bankAccount.getByOwnerName.ownerName.notBlank")
        String ownerName
    ) throws BankAccountNotFoundByOwnerNameException {
        BankAccount bankAccount;

        try (
            Connection connection = databaseService.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT \"id\", \"ownerName\", \"balance\" FROM accounts WHERE \"ownerName\" = ?")
        ) {
            statement.setString(1, ownerName);
            ResultSet result = statement.executeQuery();

            if (!result.next())
                throw new BankAccountNotFoundByOwnerNameException(ownerName);

            bankAccount = new BankAccount(
                result.getString("id"),
                result.getString("ownerName"),
                result.getDouble("balance")
            );
        } catch (SQLException e) {
            throw new DatabaseOperationException();
        }

        return bankAccount;
    }

    public BankAccount create(
        @NotBlank(message = "repository.bankAccount.create.ownerName.notBlank") String ownerName,
        @NotNull(message = "repository.bankAccount.create.initialBalance.notNull") double initialBalance
    ) {
        checkOwnerAccountNotExists(ownerName);

        BankAccount bankAccount = new BankAccount(
            UUID.randomUUID().toString(),
            ownerName,
            initialBalance
        );

        // On valide que les modifications soient conformes avant de sauvegarder
        validateEntity(bankAccount);

        try (
            Connection connection = databaseService.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO accounts (\"id\", \"ownerName\", \"balance\") VALUES (?, ?, ?)")
        ) {
            statement.setString(1, bankAccount.getId());
            statement.setString(2, bankAccount.getOwnerName());
            statement.setDouble(3, bankAccount.getBalance());
            int result = statement.executeUpdate();

            if (result != 1)
                throw new DatabaseOperationWithoutEffectException();

        } catch (SQLException e) {
            throw new DatabaseOperationException();
        }

        return bankAccount;
    }

    public BankAccount update(
        @NotBlank(message = "repository.bankAccount.update.id.notBlank") String id,
        @Nullable Double newBalance,
        @Nullable String newOwnerName
        ) throws BankAccountNotFoundByIdException, ConstraintViolationException {

        BankAccount bankAccount = getById(id);

        if (newBalance != null && !Objects.equals(bankAccount.getBalance(), newBalance))
            bankAccount.setBalance(newBalance);

        if (newOwnerName != null && !bankAccount.getOwnerName().equals(newOwnerName))
        {
            checkOwnerAccountNotExists(newOwnerName, id);
            bankAccount.setOwnerName(newOwnerName);
        }

        // On valide que les modifications soient conformes avant de sauvegarder
        validateEntity(bankAccount);
        try (
            Connection connection = databaseService.getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE accounts SET \"ownerName\" = ?, \"balance\" = ? WHERE \"id\" = ?")
        ) {
            statement.setString(1, bankAccount.getOwnerName());
            statement.setDouble(2, bankAccount.getBalance());
            statement.setString(3, bankAccount.getId());

            int result = statement.executeUpdate();

            if (result != 1)
                throw new DatabaseOperationWithoutEffectException();

        } catch (SQLException e) {
            throw new DatabaseOperationException();
        }

        return bankAccount;
    }

    public void delete(@NotBlank(message = "repository.bankAccount.delete.id.notBlank") String id)
            throws BankAccountNotFoundByIdException {
        try (
            Connection connection = databaseService.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM accounts WHERE \"id\" = ?")
        ) {
            statement.setString(1, id);
            int result = statement.executeUpdate();

            if (result != 1)
                throw new BankAccountNotFoundByIdException(id);

        } catch (SQLException e) {
            throw new DatabaseOperationException();
        }
    }

    private void checkOwnerAccountNotExists(@NotBlank String ownerName) {
        checkOwnerAccountNotExists(ownerName, null);
    }

    private void checkOwnerAccountNotExists(@NotBlank String ownerName, @Nullable String existingId) {
        try {
            BankAccount account = getByOwnerName(ownerName);

            // Pas d'erreur si le client rectifie son nom lors d'une mise à jour
            if ((existingId == null || existingId.isEmpty() || !account.getId().equals(existingId)))
                throw new BankAccountOwnerAlreadyExistsException(ownerName);

        } catch (BankAccountNotFoundByOwnerNameException ignored) {
            // Aucun compte n'existe à ce nom : tout va bien !
        }
    }
}
