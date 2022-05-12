package fr.pangolins.leProPlusPlus.services.entityRepositories;

import fr.pangolins.leProPlusPlus.exceptions.database.DatabaseNoUrlException;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Service
public class DatabaseService {
    private final Environment env;
    private final String dbUrl;
    private final String dbUsername;
    private final String dbPassword;

    public DatabaseService(Environment env) throws URISyntaxException, DatabaseNoUrlException, ClassNotFoundException {
        this.env = env;

        Class.forName("org.postgresql.Driver");

        String dbUrlEnv = env.getProperty("DATABASE_URL");
        if (dbUrlEnv == null)
            throw new DatabaseNoUrlException();

        URI dbUri = new URI(dbUrlEnv);
        dbUsername = dbUri.getUserInfo().split(":")[0];
        dbPassword = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ":" + dbUri.getPort() + dbUri.getPath();

        if (System.getenv("DATABASE_ENABLE_SSL") != null) {
            dbUrl += "?sslmode=require";
        }

        this.dbUrl = dbUrl;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
    }
}
