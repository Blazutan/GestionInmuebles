package co.edu.poli.aplication.port.in;

import co.edu.poli.aplication.domain.model.Inmueble;

import java.util.List;
import java.util.Optional;

/**
 * Caso de uso para consultar inmuebles almacenados en el sistema.
 */
public interface ConsultarInmuebleUseCase {
    /**
     * Obtiene la lista completa de inmuebles registrados.
     *
     * @return colección de inmuebles
     */
    List<Inmueble> consultarTodos();

    /**
     * Busca un inmueble por su identificador único.
     *
     * @param chip identificador del inmueble
     * @return inmueble encontrado, si existe
     */
    Optional<Inmueble> consultarPorChip(String chip);
}
