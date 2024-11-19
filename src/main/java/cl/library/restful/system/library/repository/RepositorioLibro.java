package cl.library.restful.system.library.repository;

import cl.library.restful.system.library.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioLibro extends JpaRepository<Libro, Long> {
}
