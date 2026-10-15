package co.edu.poli.aplication.port.in;

import co.edu.poli.aplication.domain.model.Inmueble;

import java.util.List;
import java.util.Optional;

public interface ConsultarInmuebleUseCase {
    List<Inmueble> consultarTodos();

    Optional<Inmueble> consultarPorChip(String chip);
}
