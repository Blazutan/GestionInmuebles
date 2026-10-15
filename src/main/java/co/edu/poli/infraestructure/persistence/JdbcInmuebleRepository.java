package co.edu.poli.infraestructure.persistence;

import co.edu.poli.aplication.domain.model.Apartamento;
import co.edu.poli.aplication.domain.model.Casa;
import co.edu.poli.aplication.domain.model.Inmueble;
import co.edu.poli.aplication.domain.model.Propietario;
import co.edu.poli.aplication.domain.model.Sotano;
import co.edu.poli.aplication.port.out.InmuebleRepository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Adaptador JDBC del repositorio de inmuebles.
 * <p>
 * Implementa el puerto {@link InmuebleRepository} para persistir entidades del
 * dominio en una base de datos MySQL mediante sentencias SQL explícitas.
 * </p>
 */
public class JdbcInmuebleRepository implements InmuebleRepository {
    private static final String SELECT_INMUEBLES =
            "SELECT i.chip, i.fecha_compra, i.direccion, i.estado, i.tipo, "
                    + "p.identificacion, p.nombre, p.correo, "
                    + "ap.numero_piso, ca.pisos, s.area_m2 AS sotano_area_m2, "
                    + "s.tipo_uso AS sotano_tipo_uso "
                    + "FROM inmueble i "
                    + "JOIN propietario p ON p.identificacion = i.propietario_id "
                    + "LEFT JOIN apartamento ap ON ap.inmueble_chip = i.chip "
                    + "LEFT JOIN casa ca ON ca.inmueble_chip = i.chip "
                    + "LEFT JOIN sotano s ON s.casa_chip = ca.inmueble_chip ";

    private final JdbcConnectionFactory connectionFactory;

    public JdbcInmuebleRepository(JdbcConnectionFactory connectionFactory) {
        this.connectionFactory = Objects.requireNonNull(connectionFactory,
                "La fábrica de conexiones es obligatoria");
    }

    @Override
    public Inmueble guardar(Inmueble inmueble) {
        validarInmueble(inmueble);
        return enTransaccion(connection -> {
            guardarPropietario(connection, inmueble.getPropietario());
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO inmueble (chip, fecha_compra, direccion, estado, tipo, propietario_id) "
                            + "VALUES (?, ?, ?, ?, ?, ?)")) {
                establecerDatosBase(statement, inmueble);
                statement.executeUpdate();
            }
            guardarTipo(connection, inmueble);
            return inmueble;
        });
    }

    @Override
    public List<Inmueble> buscarTodos() {
        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_INMUEBLES + " ORDER BY i.chip")) {
            List<Inmueble> inmuebles = new ArrayList<>();
            while (resultSet.next()) {
                inmuebles.add(mapearInmueble(resultSet));
            }
            return inmuebles;
        } catch (SQLException exception) {
            throw new PersistenceException("No se pudieron consultar los inmuebles", exception);
        }
    }

    @Override
    public Optional<Inmueble> buscarPorChip(String chip) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     SELECT_INMUEBLES + " WHERE i.chip = ?")) {
            statement.setString(1, chip);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next()
                        ? Optional.of(mapearInmueble(resultSet))
                        : Optional.empty();
            }
        } catch (SQLException exception) {
            throw new PersistenceException("No se pudo consultar el inmueble por CHIP", exception);
        }
    }

    @Override
    public Inmueble actualizar(Inmueble inmueble) {
        validarInmueble(inmueble);
        return enTransaccion(connection -> {
            guardarPropietario(connection, inmueble.getPropietario());
            try (PreparedStatement statement = connection.prepareStatement(
                    "UPDATE inmueble SET fecha_compra = ?, direccion = ?, estado = ?, tipo = ?, "
                            + "propietario_id = ? WHERE chip = ?")) {
                establecerDatosActualizacion(statement, inmueble);
                if (statement.executeUpdate() == 0) {
                    throw new IllegalArgumentException(
                            "No existe un inmueble con CHIP " + inmueble.getChip());
                }
            }
            eliminarDetalles(connection, inmueble.getChip());
            guardarTipo(connection, inmueble);
            return inmueble;
        });
    }

    @Override
    public void eliminar(String chip) {
        enTransaccion(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM inmueble WHERE chip = ?")) {
                statement.setString(1, chip);
                statement.executeUpdate();
            }
            return null;
        });
    }

    private void guardarPropietario(Connection connection, Propietario propietario)
            throws SQLException {
        if (propietario == null) {
            throw new IllegalArgumentException("El inmueble debe tener un propietario");
        }

        String sql = "INSERT INTO propietario (identificacion, nombre, correo) VALUES (?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE nombre = ?, correo = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, propietario.getIdentificacion());
            statement.setString(2, propietario.getNombre());
            statement.setString(3, propietario.getCorreo());
            statement.setString(4, propietario.getNombre());
            statement.setString(5, propietario.getCorreo());
            statement.executeUpdate();
        }
    }

    private void guardarTipo(Connection connection, Inmueble inmueble) throws SQLException {
        if (inmueble instanceof Apartamento) {
            Apartamento apartamento = (Apartamento) inmueble;
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO apartamento (inmueble_chip, numero_piso) VALUES (?, ?)")) {
                statement.setString(1, inmueble.getChip());
                statement.setInt(2, apartamento.getNumeroPiso());
                statement.executeUpdate();
            }
        } else if (inmueble instanceof Casa) {
            Casa casa = (Casa) inmueble;
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO casa (inmueble_chip, pisos) VALUES (?, ?)")) {
                statement.setString(1, inmueble.getChip());
                statement.setInt(2, casa.getPisos());
                statement.executeUpdate();
            }
            guardarSotano(connection, inmueble.getChip(), casa.getSotano());
        }
    }

    private void guardarSotano(Connection connection, String chip, Sotano sotano)
            throws SQLException {
        if (sotano == null) {
            return;
        }
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO sotano (casa_chip, area_m2, tipo_uso) VALUES (?, ?, ?)")) {
            statement.setString(1, chip);
            statement.setDouble(2, sotano.getAreaM2());
            statement.setString(3, sotano.getTipoUso());
            statement.executeUpdate();
        }
    }

    private void eliminarDetalles(Connection connection, String chip) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM apartamento WHERE inmueble_chip = ?")) {
            statement.setString(1, chip);
            statement.executeUpdate();
        }
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM casa WHERE inmueble_chip = ?")) {
            statement.setString(1, chip);
            statement.executeUpdate();
        }
    }

    private void establecerDatosBase(PreparedStatement statement, Inmueble inmueble)
            throws SQLException {
        statement.setString(1, inmueble.getChip());
        if (inmueble.getFechaCompra() == null) {
            statement.setDate(2, null);
        } else {
            statement.setDate(2, Date.valueOf(inmueble.getFechaCompra()));
        }
        statement.setString(3, inmueble.getDireccion());
        statement.setString(4, inmueble.getEstado());
        statement.setString(5, obtenerTipo(inmueble));
        statement.setInt(6, inmueble.getPropietario().getIdentificacion());
    }

    private void establecerDatosActualizacion(PreparedStatement statement, Inmueble inmueble)
            throws SQLException {
        if (inmueble.getFechaCompra() == null) {
            statement.setDate(1, null);
        } else {
            statement.setDate(1, Date.valueOf(inmueble.getFechaCompra()));
        }
        statement.setString(2, inmueble.getDireccion());
        statement.setString(3, inmueble.getEstado());
        statement.setString(4, obtenerTipo(inmueble));
        statement.setInt(5, inmueble.getPropietario().getIdentificacion());
        statement.setString(6, inmueble.getChip());
    }

    private String obtenerTipo(Inmueble inmueble) {
        if (inmueble instanceof Apartamento) {
            return "APARTAMENTO";
        }
        if (inmueble instanceof Casa) {
            return "CASA";
        }
        return "INMUEBLE";
    }

    private Inmueble mapearInmueble(ResultSet resultSet) throws SQLException {
        Date fechaCompra = resultSet.getDate("fecha_compra");
        Propietario propietario = new Propietario(
                resultSet.getInt("identificacion"),
                resultSet.getString("nombre"),
                resultSet.getString("correo"));

        String chip = resultSet.getString("chip");
        String direccion = resultSet.getString("direccion");
        String estado = resultSet.getString("estado");
        Inmueble inmueble;
        switch (resultSet.getString("tipo")) {
            case "APARTAMENTO":
                inmueble = new Apartamento(chip, fechaCompra == null ? null : fechaCompra.toLocalDate(),
                        direccion, estado, propietario, resultSet.getInt("numero_piso"));
                break;
            case "CASA":
                Sotano sotano = null;
                double areaM2 = resultSet.getDouble("sotano_area_m2");
                if (!resultSet.wasNull()) {
                    sotano = new Sotano(areaM2, resultSet.getString("sotano_tipo_uso"));
                }
                inmueble = new Casa(chip, fechaCompra == null ? null : fechaCompra.toLocalDate(),
                        direccion, estado, propietario, resultSet.getInt("pisos"), sotano);
                break;
            case "INMUEBLE":
                inmueble = new Inmueble(chip, fechaCompra == null ? null : fechaCompra.toLocalDate(),
                        direccion, estado, propietario);
                break;
            default:
                throw new SQLException("Tipo de inmueble inválido en la base de datos");
        }
        return inmueble;
    }

    private void validarInmueble(Inmueble inmueble) {
        Objects.requireNonNull(inmueble, "El inmueble es obligatorio");
        if (inmueble.getChip() == null || inmueble.getChip().isBlank()) {
            throw new IllegalArgumentException("El CHIP del inmueble es obligatorio");
        }
    }

    private <T> T enTransaccion(SqlOperation<T> operation) {
        try (Connection connection = connectionFactory.getConnection()) {
            connection.setAutoCommit(false);
            try {
                T result = operation.execute(connection);
                connection.commit();
                return result;
            } catch (SQLException | RuntimeException exception) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                    exception.addSuppressed(rollbackException);
                }
                throw exception;
            }
        } catch (SQLException exception) {
            throw new PersistenceException("Falló la operación de persistencia del inmueble",
                    exception);
        }
    }

    @FunctionalInterface
    private interface SqlOperation<T> {
        T execute(Connection connection) throws SQLException;
    }
}
