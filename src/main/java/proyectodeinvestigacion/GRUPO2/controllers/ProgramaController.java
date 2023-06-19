package proyectodeinvestigacion.GRUPO2.controllers;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import proyectodeinvestigacion.GRUPO2.entities.Programa;
import proyectodeinvestigacion.GRUPO2.services.ProgramaService;

@RestController
@RequestMapping("api/programa")
@CrossOrigin("*")
@Tag(name = "Controlador de Programa  (Tabla Programa)")
public class ProgramaController {

    @Autowired
    private ProgramaService programaService;

    // crear
    // metod delimitador de acceso,tipo de dato que retorna,nombre del
    // metodo/funcion
    @PreAuthorize("hasAuthority('Programa_Crear')")
    @PostMapping("/crear")
    @Operation(summary = "Guarda un nuevo programa, requiere el permiso Programa_Crear")
    public Programa save(@RequestBody Programa entity) {
        return programaService.save(entity);
    }

    // traer por id
    @PreAuthorize("hasAuthority('Programa_Leer')")
    @GetMapping("/listar/{id}")
    @Operation(summary = "Obtiene un programa por su id, requiere el permiso Programa_Leer")
    public Programa findById(@PathVariable long id) {
        return programaService.findById(id);
    }

    // traer todo
    @PreAuthorize("hasAuthority('Programa_LeerTodos')")
    @GetMapping("/listar")
    @Operation(summary = "Obtiene todos los programas, requiere el permiso Programa_LeerTodos")
    public List<Programa> findAll() {
        return programaService.findAll();
    }

    // actualizar
    @PreAuthorize("hasAuthority('Programa_Actualizar')")
    @PutMapping("/actualizar")
    @Operation(summary = "Actualiza un programa  por su id, requiere el permiso Programa_Actualizar")
    public Programa update(@RequestBody Programa entity) {
        return programaService.save(entity);
    }

    // actualizar parcial
    @PatchMapping("/actualizar/{id}")
    public Programa partialUpdate(@PathVariable long id, @RequestBody Map<String, Object> fields) {
        Programa programa = findById(id);
        for (Map.Entry<String, Object> field : fields.entrySet()) {
            String fieldName = field.getKey();
            Object fieldValue = field.getValue();
            try {
                Field campoEntidad = Programa.class.getDeclaredField(fieldName);
                campoEntidad.setAccessible(true);
                campoEntidad.set(programa, fieldValue);
            } catch (NoSuchFieldException | IllegalAccessException ex) {
                
            }
        }
        return update(programa);
    }

    // eliminar
    @PreAuthorize("hasAuthority('Programa_Eliminar')")
    @DeleteMapping("/eliminar/{id}")
    @Operation(summary = "Elimina un programa por su id, requiere el permiso Programa_Eliminar")
    public void deleteById(@PathVariable long id) {
        programaService.deleteById(id);
    }

}