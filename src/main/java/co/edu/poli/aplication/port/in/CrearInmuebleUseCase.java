package co.edu.poli.aplication.port.in;

import co.edu.poli.aplication.domain.model.Inmueble;

/**
 * Caso de uso para registrar un nuevo inmueble en el sistema.
 * <p>
 * Define la operación que permite crear una propiedad y devolver la instancia
 * persistida con la información validada y almacenada.
 * </p>
 */
public interface CrearInmuebleUseCase {
    /**
     * Crea un inmueble nuevo.
     *
     * @param inmueble información del inmueble a registrar
     * @return inmueble persistido
     */
    Inmueble crear(Inmueble inmueble);
}
