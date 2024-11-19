package cl.library.restful.system.library.service.impl;

import cl.library.restful.system.library.model.Libro;

import java.util.List;
import java.util.Optional;

public interface LibroService {
    public Libro guardarLibro(Libro libro);
    public List<Libro> obtenerTodosLosLibros();
    public Optional<Libro> obtenerLibroPorId(Long id);
    public void eliminarLibro(Long id);
}
