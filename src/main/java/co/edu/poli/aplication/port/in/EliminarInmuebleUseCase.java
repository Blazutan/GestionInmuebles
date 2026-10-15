package co.edu.poli.aplication.port.in;

/**
 * Caso de uso para eliminar un inmueble del sistema.
 */
public interface EliminarInmuebleUseCase {
    /**
     * Elimina un inmueble identificado por su chip.
     *
     * @param chip identificador del inmueble a eliminar
     */
    void eliminar(String chip);
}
