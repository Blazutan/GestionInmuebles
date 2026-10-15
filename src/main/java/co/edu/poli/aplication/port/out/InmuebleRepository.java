package co.edu.poli.aplication.port.out;

import co.edu.poli.aplication.domain.model.Inmueble;

import java.util.List;
import java.util.Optional;

public interface InmuebleRepository {
    Inmueble guardar(Inmueble inmueble);

    List<Inmueble> buscarTodos();

    Optional<Inmueble> buscarPorChip(String chip);

    Inmueble actualizar(Inmueble inmueble);

    void eliminar(String chip);
}
