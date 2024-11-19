package cl.library.restful.system.library.controller;

import cl.library.restful.system.library.model.Libro;
import cl.library.restful.system.library.service.impl.LibroService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/libros")
@AllArgsConstructor

public class LibroController {
    private final LibroService libroService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.CREATED)

    public Libro agregarLibro(@RequestBody Libro libro) {
        return libroService.guardarLibro(libro);
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public List<Libro> obtenerTodosLosLibros() {
        return libroService.obtenerTodosLosLibros();
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public Optional<Libro> obtenerLibroPorId(@PathVariable Long id) {
        return libroService.obtenerLibroPorId(id);
    }

    @PutMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public Libro actualizarLibro(@PathVariable Long id, @RequestBody Libro libro) {
        libro.setId(id);
        return libroService.guardarLibro(libro);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void eliminarLibro(@PathVariable Long id) {
        libroService.eliminarLibro(id);
    }
}
