package co.edu.poli.aplication.port.in;

import co.edu.poli.aplication.domain.model.Inmueble;

/**
 * Caso de uso para actualizar la información de un inmueble existente.
 */
public interface ActualizarInmuebleUseCase {
    /**
     * Actualiza los datos de un inmueble ya registrado.
     *
     * @param inmueble inmueble con la información modificada
     * @return inmueble actualizado
     */
    Inmueble actualizar(Inmueble inmueble);
}
