package co.edu.poli.infraestructure.persistence;

/**
 * Excepción de infraestructura para errores de persistencia.
 * <p>
 * Se usa cuando fallan las operaciones de acceso a datos y se desea encapsular
 * la causa técnica en una excepción de dominio de infraestructura.
 * </p>
 */
public class PersistenceException extends RuntimeException {
    /**
     * Crea una excepción de persistencia con un mensaje y la causa original.
     *
     * @param message descripción del error
     * @param cause causa técnica del problema
     */
    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
