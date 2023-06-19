package proyectodeinvestigacion.GRUPO2.controllers;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import proyectodeinvestigacion.GRUPO2.entities.Carrera;
import proyectodeinvestigacion.GRUPO2.services.CarreraService;

@RestController
@RequestMapping("api/carreras")
@CrossOrigin("*")
@Tag(name = "Controller Carrera (Tabla Carrera)")
public class CarreraController {

    @Autowired
    private CarreraService carreraService;

    @PreAuthorize("hasAnyAuthority('Carrera_Crear')")
    @Operation(summary = "Crear nueva Carrera, requiere el permiso Carrera_Crear")
    @PostMapping("/create")
    public ResponseEntity<Carrera> crearCarrera(@RequestBody Carrera carrera) {
        Carrera nuevaCarrera = carreraService.crearCarrera(carrera);
        return new ResponseEntity<>(nuevaCarrera, CREATED);
    }

    @PreAuthorize("hasAnyAuthority('Carrera_LeerTodos')")
    @Operation(summary = "Obtener la lista de carreras, requiere el permiso Carrera_Leer")
    @GetMapping("/lista")
    public List<Carrera> getAll() {
        return carreraService.getAll();
    }


    @PreAuthorize("hasAnyAuthority('Carrera_Leer')")
    @Operation(summary = "Obtener una carrera, requiere el permiso Carrera_Leer")
    @GetMapping("buscar/{id}/")
    public Carrera findById(@PathVariable long id) {
        return carreraService.findById(id);
    }


    @PreAuthorize("hasAnyAuthority('Carrera_Eliminar')")
    @Operation(summary = "Eliminar una carrera por ID, requiere permiso Carrera_Eliminar")
    @DeleteMapping("eliminar/{id}")
    public void deleteById(@PathVariable long id) {
        carreraService.deleteById(id);
    }


    @PreAuthorize("hasAnyAuthority('Carrera_Actualizar')")
    @Operation(summary = "Actualizar una Carrea por ID, requiere el permiso Carrera_Actualizar")
    @PutMapping("/update/{id}")
    public ResponseEntity<Carrera> actualizarCarrera(@PathVariable("id") Long id, @RequestBody Carrera carrera) {
        Carrera carreraExistente = carreraService.findById(id);

        if (carreraExistente == null) {
            return new ResponseEntity<>(NOT_FOUND);
        }

        carreraExistente.setNombre(carrera.getNombre());
        carreraExistente.setDescripcion(carrera.getDescripcion());

        Carrera carreraActualizada = carreraService.actualizarCarrera(carreraExistente);
        if (carreraActualizada == null) {
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(carreraActualizada, OK);
    }
    
}
