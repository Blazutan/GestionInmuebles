package co.edu.poli.infraestructure.persistence;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class JdbcConnectionFactory {
    private final String url;
    private final String username;
    private final String password;

    public JdbcConnectionFactory(String url, String username, String password) {
        this.url = Objects.requireNonNull(url, "La URL de conexión es obligatoria");
        this.username = Objects.requireNonNull(username, "El usuario de conexión es obligatorio");
        this.password = Objects.requireNonNull(password, "La contraseña de conexión es obligatoria");
    }

    public static JdbcConnectionFactory fromDotEnv() {
        Dotenv dotenv = Dotenv.configure()
                .directory(System.getProperty("user.dir"))
                .ignoreIfMissing()
                .load();
        return new JdbcConnectionFactory(
                requiredValue(dotenv, "DB_URL"),
                requiredValue(dotenv, "DB_USER"),
                requiredValue(dotenv, "DB_PASSWORD"));
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    private static String requiredValue(Dotenv dotenv, String name) {
        String value = dotenv.get(name);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Debe configurar " + name + " en el archivo .env del proyecto");
        }
        return value;
    }
}
