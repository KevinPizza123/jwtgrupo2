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
import proyectodeinvestigacion.GRUPO2.entities.Proyecto;
import proyectodeinvestigacion.GRUPO2.services.ProyectoService;

@RestController
@RequestMapping("api/proyecto")
@CrossOrigin("*")
@Tag( name = "Contolador de Proyecto( Tabla Proyecto)")
public class ProyectoController {

    @Autowired
    private ProyectoService proyectoService;

    // crear
    // metod delimitador de acceso,tipo de dato que retorna,nombre del
    // metodo/funcion
    @PreAuthorize("hasAuthority('Proyecto_Crear')")
    @PostMapping("/crear")
    @Operation(summary = "Guardar un nuevo proyecto,requiere el permiso Proyecto_Crear")
    public Proyecto save(@RequestBody Proyecto entity) {
        return proyectoService.save(entity);
    }

    // traer por id
    @PreAuthorize("hasAuthority('Proyecto_Listar_Id')")
    @GetMapping("/listar/{id}")
    @Operation(summary = "Listar los proyectos solo por id,requiere el permiso Programa_Listar_Id")
    public Proyecto findById(@PathVariable long id) {
        return proyectoService.findById(id);
    }

    // traer todo
    @PreAuthorize("hasAuthority('Proyecto_Listar')")
    @GetMapping("/listar")
    @Operation(summary = "Listar todos los proyectos,requiere el permiso Proyecto_Listar")

    public List<Proyecto> findAll() {
        return proyectoService.findAll();
    }

    // actualizar
    @PreAuthorize("hasAuthority('Proyecto_Actualizar')")
    @PutMapping("/actualizar")
    @Operation(summary = "Actualiza los proyectos,requiere el permiso Proyecto_Actualizar")
    public Proyecto update(@RequestBody Proyecto entity) {
        return proyectoService.save(entity);
    }

    // actualizar parcial
    @PreAuthorize("hasAuthority('Proyecto_Actualizar_Id')")
    @PatchMapping("/actualizar/{id}")
    @Operation(summary = "Actualizar los proyecto por id,requiere el permiso Proyecto_Actualizar_Id")

    public Proyecto partialUpdate(@PathVariable long id, @RequestBody Map<String, Object> fields) {
        Proyecto proyecto = findById(id);
        for (Map.Entry<String, Object> field : fields.entrySet()) {
            String fieldName = field.getKey();
            Object fieldValue = field.getValue();
            try {
                Field campoEntidad = Proyecto.class.getDeclaredField(fieldName);
                campoEntidad.setAccessible(true);
                campoEntidad.set(proyecto, fieldValue);
            } catch (NoSuchFieldException | IllegalAccessException ex) {
                
            }
        }
        return update(proyecto);
    }

    // eliminar
    @PreAuthorize("hasAuthority('Proyecto_Eliminar_Id')")
    @DeleteMapping("/eliminar/{id}")
    @Operation(summary = "Eliminar proyecto por id,requiere el permiso Proyecto_Eliminar_Id")

    public void deleteById(@PathVariable long id) {
        proyectoService.deleteById(id);
    }

}