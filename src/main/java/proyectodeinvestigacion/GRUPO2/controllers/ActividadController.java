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
import proyectodeinvestigacion.GRUPO2.entities.Actividad;
import proyectodeinvestigacion.GRUPO2.services.ActividadService;
    
    @RestController
    @RequestMapping("api/actividad")
    @CrossOrigin("*")
    @Tag( name = "Contolador de Actividad( Tabla Actividad)")
    public class ActividadController {
    
        @Autowired
        private ActividadService actividadService;
    
        // crear
        // metod delimitador de acceso,tipo de dato que retorna,nombre del
        // metodo/funcion
        @PreAuthorize("hasAuthority('Actividad_Crear')")
        @PostMapping("/crear")
        @Operation(summary = "Guardar una nueva actividad, requiere permiso Actividad_Crear")
        public Actividad save(@RequestBody Actividad entity) {
            return actividadService.save(entity);
        }
    
        // traer por id
        @PreAuthorize("hasAnyAuthority('Actividad_Leer')")
        @Operation(summary = "Obtener la lista de Actividades, requiere el permiso Actividad_Leer")
        @GetMapping("/listar/{id}")
        public Actividad findById(@PathVariable long id) {
            return actividadService.findById(id);
        }
    
        // traer todo
         @PreAuthorize("hasAnyAuthority('Actividad_LeerTodos')")
        @Operation(summary = "Obtener la lista de Actividades, requiere el permiso Actividad_LeerTodos")
        @GetMapping("/listar")
        public List<Actividad> findAll() {
            return actividadService.findAll();
        }
    
        // actualizar
        @PutMapping("/actualizar")
         @PreAuthorize("hasAnyAuthority('Actividad_Actualizar')")
        @Operation(summary = "Obtener la lista de Actividades, requiere el permiso Actividad_Actualizar")
        public Actividad update(@RequestBody Actividad entity) {
            return actividadService.save(entity);
        }
    
        // actualizar parcial
        @PreAuthorize("hasAnyAuthority('Actividad_Actualizar')")       
        @Operation(summary = "Actualizar una Actividad por ID, requiere el permiso Actividad_Actualizar") 
        @PatchMapping("/actualizar/{id}")
        public Actividad partialUpdate(@PathVariable long id, @RequestBody Map<String, Object> fields) {
            Actividad actividad = findById(id);
            for (Map.Entry<String, Object> field : fields.entrySet()) {
                String fieldName = field.getKey();
                Object fieldValue = field.getValue();
                try {
                    Field campoEntidad = Actividad.class.getDeclaredField(fieldName);
                    campoEntidad.setAccessible(true);
                    campoEntidad.set(actividad, fieldValue);
                } catch (NoSuchFieldException | IllegalAccessException ex) {
                    
                }
            }
            return update(actividad);
        }
    
        // eliminar
        @PreAuthorize("hasAnyAuthority('Actividad_Eliminar')")
        @Operation(summary = "Obtener la lista de Actividades, requiere el permiso Actividad_Eliminar")
        @DeleteMapping("/eliminar/{id}")
        public void deleteById(@PathVariable long id) {
            actividadService.deleteById(id);
        }
    
    }
