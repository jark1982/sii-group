package cl.library.restful.system.library.controller;

import cl.library.restful.system.library.model.Libro;
import cl.library.restful.system.library.service.impl.LibroService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@WebMvcTest(LibroController.class)
@ExtendWith(MockitoExtension.class)
public class LibroControllerTest {

    @MockBean
    private LibroService libroService;
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    // Test para el POST /api/libros
    @Test
    public void testAgregarLibro() throws Exception {
        // Crear un objeto Libro
        Libro libro = new Libro(null, "El Hobbit", "J.R.R. Tolkien", "Fantasía", 1937);

        // Simular que el servicio devuelve el libro guardado con ID asignado
        when(libroService.guardarLibro(libro)).thenReturn(new Libro(1L, "El Hobbit", "J.R.R. Tolkien", "Fantasía", 1937));

        // Realizar la petición POST
        mockMvc.perform(MockMvcRequestBuilders.post("/api/libros")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(libro)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.titulo").value("El Hobbit"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.autor").value("J.R.R. Tolkien"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoria").value("Fantasía"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.añoPublicacion").value(1937));
    }

    // Test para el GET /api/libros
    @Test
    public void testObtenerTodosLosLibros() throws Exception {
        // Crear una lista de libros simulada
        Libro libro1 = new Libro(1L, "El Hobbit", "J.R.R. Tolkien", "Fantasía", 1937);
        Libro libro2 = new Libro(2L, "1984", "George Orwell", "Distopía", 1949);

        when(libroService.obtenerTodosLosLibros()).thenReturn(Arrays.asList(libro1, libro2));

        // Realizar la petición GET
        mockMvc.perform(MockMvcRequestBuilders.get("/api/libros"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].titulo").value("El Hobbit"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].titulo").value("1984"));
    }

    // Test para el GET /api/libros/{id}
    @Test
    public void testObtenerLibroPorId() throws Exception {
        // Crear un libro simulado
        Libro libro = new Libro(1L, "El Hobbit", "J.R.R. Tolkien", "Fantasía", 1937);

        // Simular la respuesta del servicio
        when(libroService.obtenerLibroPorId(1L)).thenReturn(Optional.of(libro));

        // Realizar la petición GET
        mockMvc.perform(MockMvcRequestBuilders.get("/api/libros/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.titulo").value("El Hobbit"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.autor").value("J.R.R. Tolkien"));
    }

    // Test para el PUT /api/libros/{id}
    @Test
    public void testActualizarLibro() throws Exception {
        // Crear un libro original
        Libro libroOriginal = new Libro(1L, "El Hobbit", "J.R.R. Tolkien", "Fantasía", 1937);

        // Crear un libro actualizado
        Libro libroActualizado = new Libro(1L, "El Hobbit - Edición Especial", "J.R.R. Tolkien", "Fantasía", 1937);

        // Simular que el servicio devuelve el libro actualizado
        when(libroService.guardarLibro(libroActualizado)).thenReturn(libroActualizado);

        // Realizar la petición PUT
        mockMvc.perform(MockMvcRequestBuilders.put("/api/libros/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(libroActualizado)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.titulo").value("El Hobbit - Edición Especial"));
    }

    // Test para el DELETE /api/libros/{id}
    @Test
    public void testEliminarLibro() throws Exception {
        // Realizar la petición DELETE
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/libros/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verificar que el servicio fue llamado para eliminar el libro
        verify(libroService).eliminarLibro(1L);
    }
}