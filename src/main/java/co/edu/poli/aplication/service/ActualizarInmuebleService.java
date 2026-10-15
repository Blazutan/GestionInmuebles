package co.edu.poli.aplication.service;

import co.edu.poli.aplication.domain.model.Inmueble;
import co.edu.poli.aplication.port.in.ActualizarInmuebleUseCase;
import co.edu.poli.aplication.port.out.InmuebleRepository;

import java.util.Objects;

/**
 * Implementación del caso de uso para actualizar inmuebles.
 */
public class ActualizarInmuebleService implements ActualizarInmuebleUseCase {
    private final InmuebleRepository inmuebleRepository;

    /**
     * Crea el servicio con el repositorio de persistencia.
     *
     * @param inmuebleRepository repositorio de inmuebles
     */
    public ActualizarInmuebleService(InmuebleRepository inmuebleRepository) {
        this.inmuebleRepository = Objects.requireNonNull(inmuebleRepository,
                "El repositorio de inmuebles es obligatorio");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Inmueble actualizar(Inmueble inmueble) {
        Objects.requireNonNull(inmueble, "El inmueble es obligatorio");
        validarChip(inmueble.getChip());
        return inmuebleRepository.actualizar(inmueble);
    }

    private void validarChip(String chip) {
        if (chip == null || chip.isBlank()) {
            throw new IllegalArgumentException("El CHIP del inmueble es obligatorio");
        }
    }
}
