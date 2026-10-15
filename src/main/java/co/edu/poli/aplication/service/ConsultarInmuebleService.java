package co.edu.poli.aplication.service;

import co.edu.poli.aplication.domain.model.Inmueble;
import co.edu.poli.aplication.port.in.ConsultarInmuebleUseCase;
import co.edu.poli.aplication.port.out.InmuebleRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ConsultarInmuebleService implements ConsultarInmuebleUseCase {
    private final InmuebleRepository inmuebleRepository;

    public ConsultarInmuebleService(InmuebleRepository inmuebleRepository) {
        this.inmuebleRepository = Objects.requireNonNull(inmuebleRepository,
                "El repositorio de inmuebles es obligatorio");
    }

    @Override
    public List<Inmueble> consultarTodos() {
        return inmuebleRepository.buscarTodos();
    }

    @Override
    public Optional<Inmueble> consultarPorChip(String chip) {
        validarChip(chip);
        return inmuebleRepository.buscarPorChip(chip);
    }

    private void validarChip(String chip) {
        if (chip == null || chip.isBlank()) {
            throw new IllegalArgumentException("El CHIP del inmueble es obligatorio");
        }
    }
}
