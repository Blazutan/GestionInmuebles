package co.edu.poli.aplication.service;

import co.edu.poli.aplication.domain.model.Inmueble;
import co.edu.poli.aplication.port.in.ConsultarInmuebleUseCase;
import co.edu.poli.aplication.port.out.InmuebleRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementación del caso de uso para consultar inmuebles.
 */
public class ConsultarInmuebleService implements ConsultarInmuebleUseCase {
    private final InmuebleRepository inmuebleRepository;

    /**
     * Crea el servicio con el repositorio de persistencia.
     *
     * @param inmuebleRepository repositorio de inmuebles
     */
    public ConsultarInmuebleService(InmuebleRepository inmuebleRepository) {
        this.inmuebleRepository = Objects.requireNonNull(inmuebleRepository,
                "El repositorio de inmuebles es obligatorio");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Inmueble> consultarTodos() {
        return inmuebleRepository.buscarTodos();
    }

    /**
     * {@inheritDoc}
     */
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
