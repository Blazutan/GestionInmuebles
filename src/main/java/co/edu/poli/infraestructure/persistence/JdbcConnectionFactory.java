package co.edu.poli.infraestructure.persistence;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Adaptador de infraestructura encargado de crear conexiones JDBC a la base de datos.
 * <p>
 * La clase lee la configuración desde el archivo {@code .env} del proyecto y
 * expone un punto central para obtener conexiones reutilizables por los repositorios.
 * </p>
 */
public class JdbcConnectionFactory {
    private final String url;
    private final String username;
    private final String password;

    /**
     * Crea una fábrica de conexiones con los datos de acceso al motor de base de datos.
     *
     * @param url cadena de conexión JDBC
     * @param username usuario de la base de datos
     * @param password contraseña del usuario
     */
    public JdbcConnectionFactory(String url, String username, String password) {
        this.url = Objects.requireNonNull(url, "La URL de conexión es obligatoria");
        this.username = Objects.requireNonNull(username, "El usuario de conexión es obligatorio");
        this.password = Objects.requireNonNull(password, "La contraseña de conexión es obligatoria");
    }

    /**
     * Crea la fábrica de conexiones a partir de las variables de entorno del proyecto.
     *
     * @return instancia configurada con DB_URL, DB_USER y DB_PASSWORD
     */
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

    /**
     * Abre una nueva conexión JDBC con la configuración actual.
     *
     * @return conexión activa con la base de datos
     * @throws SQLException si ocurre un error al abrir la conexión
     */
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
