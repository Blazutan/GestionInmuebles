package co.edu.poli.aplication.port.out;

import co.edu.poli.aplication.domain.model.Inmueble;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida que abstrae la persistencia de inmuebles.
 * <p>
 * Implementaciones concretas, como repositorios JDBC, deben encargarse de
 * guardar, consultar, actualizar y eliminar instancias del dominio.
 * </p>
 */
public interface InmuebleRepository {
    /**
     * Persiste un inmueble nuevo.
     *
     * @param inmueble inmueble a guardar
     * @return inmueble guardado
     */
    Inmueble guardar(Inmueble inmueble);

    /**
     * Obtiene todos los inmuebles disponibles.
     *
     * @return lista de inmuebles
     */
    List<Inmueble> buscarTodos();

    /**
     * Busca un inmueble por su chip.
     *
     * @param chip identificador del inmueble
     * @return inmueble encontrado, si existe
     */
    Optional<Inmueble> buscarPorChip(String chip);

    /**
     * Actualiza la información de un inmueble existente.
     *
     * @param inmueble inmueble actualizado
     * @return inmueble modificado
     */
    Inmueble actualizar(Inmueble inmueble);

    /**
     * Elimina un inmueble según su chip.
     *
     * @param chip identificador del inmueble a eliminar
     */
    void eliminar(String chip);
}
