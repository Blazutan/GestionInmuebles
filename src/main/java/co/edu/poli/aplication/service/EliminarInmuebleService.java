package co.edu.poli.aplication.service;

import co.edu.poli.aplication.port.in.EliminarInmuebleUseCase;
import co.edu.poli.aplication.port.out.InmuebleRepository;

import java.util.Objects;

public class EliminarInmuebleService implements EliminarInmuebleUseCase {
    private final InmuebleRepository inmuebleRepository;

    public EliminarInmuebleService(InmuebleRepository inmuebleRepository) {
        this.inmuebleRepository = Objects.requireNonNull(inmuebleRepository,
                "El repositorio de inmuebles es obligatorio");
    }

    @Override
    public void eliminar(String chip) {
        validarChip(chip);
        inmuebleRepository.eliminar(chip);
    }

    private void validarChip(String chip) {
        if (chip == null || chip.isBlank()) {
            throw new IllegalArgumentException("El CHIP del inmueble es obligatorio");
        }
    }
}
