package co.edu.poli.aplication.service;

import co.edu.poli.aplication.domain.model.Inmueble;
import co.edu.poli.aplication.port.in.ActualizarInmuebleUseCase;
import co.edu.poli.aplication.port.out.InmuebleRepository;

import java.util.Objects;

public class ActualizarInmuebleService implements ActualizarInmuebleUseCase {
    private final InmuebleRepository inmuebleRepository;

    public ActualizarInmuebleService(InmuebleRepository inmuebleRepository) {
        this.inmuebleRepository = Objects.requireNonNull(inmuebleRepository,
                "El repositorio de inmuebles es obligatorio");
    }

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
