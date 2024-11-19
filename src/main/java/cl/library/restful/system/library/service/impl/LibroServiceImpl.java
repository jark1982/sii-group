package cl.library.restful.system.library.service.impl;

import cl.library.restful.system.library.model.Libro;
import cl.library.restful.system.library.repository.RepositorioLibro;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class LibroServiceImpl implements LibroService {
    private final RepositorioLibro repositorioLibro;

    @Override
    public Libro guardarLibro(Libro libro) {
        return repositorioLibro.save(libro);
    }

    @Override
    public List<Libro> obtenerTodosLosLibros() {
        return repositorioLibro.findAll();
    }

    @Override
    public Optional<Libro> obtenerLibroPorId(Long id) {
        return repositorioLibro.findById(id);
    }

    @Override
    public void eliminarLibro(Long id) {
        repositorioLibro.deleteById(id);
    }
}
