package com.kitchenhack.apikitchen.controllers;

import com.kitchenhack.apikitchen.dtos.DiaPlanItemDTO;
import com.kitchenhack.apikitchen.dtos.PlanMaestroDTO;
import com.kitchenhack.apikitchen.entities.DiaPlanItem;
import com.kitchenhack.apikitchen.entities.Ejercicio;
import com.kitchenhack.apikitchen.entities.PlanMaestro;
import com.kitchenhack.apikitchen.entities.Recipe;
import com.kitchenhack.apikitchen.entities.Usuario;
import com.kitchenhack.apikitchen.servicesinterfaces.IDiaPlanItemService;
import com.kitchenhack.apikitchen.servicesinterfaces.IEjercicioService;
import com.kitchenhack.apikitchen.servicesinterfaces.IPlanMaestroService;
import com.kitchenhack.apikitchen.servicesinterfaces.IRecipeService;
import com.kitchenhack.apikitchen.servicesinterfaces.IUsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// US-P4-03, US-P4-04, US-P4-05: Gestión de planes maestros (alimenticio, ejercicio, híbrido)
@RestController
@RequestMapping("/planes")
public class PlanMaestroController {

    // Tipos de plan válidos según las reglas de negocio
    private static final List<String> TIPOS_VALIDOS = List.of("alimenticio", "ejercicio", "hibrido");

    @Autowired
    private IPlanMaestroService planMaestroService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IDiaPlanItemService diaPlanItemService;

    @Autowired
    private IRecipeService recipeService;

    @Autowired
    private IEjercicioService ejercicioService;

    // US-P4-04 — Listar todos los planes maestros disponibles
    // GET /planes → 200 con lista, o 404 si no hay ninguno
    @GetMapping
    public ResponseEntity<?> listarPlanes() {
        List<PlanMaestro> lista = planMaestroService.list();

        // Si no hay planes disponibles, se informa al cliente
        if (lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No hay planes disponibles");
        }

        // ModelMapper mapea los campos simples; idAutor se sobreescribe manualmente
        // porque el campo en la entidad es un objeto Usuario, no un Integer
        ModelMapper m = new ModelMapper();
        List<PlanMaestroDTO> listaDTO = lista.stream()
                .map(p -> {
                    PlanMaestroDTO dto = m.map(p, PlanMaestroDTO.class);
                    // getId() retorna Long — se asigna directo sin conversión
                    dto.setIdAutor(p.getIdAutor() != null ? p.getIdAutor().getId() : null);
                    return dto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(listaDTO);
    }

    // US-P4-03 — Registrar un nuevo plan maestro
    // POST /planes/nuevo → 201 con el plan creado, 400 si tipo inválido, 404 si autor no existe
    @PostMapping("/nuevo")
    public ResponseEntity<?> registrarPlan(@RequestBody PlanMaestroDTO dto) {

        // Validar que tipo_plan sea uno de los valores permitidos
        if (dto.getTipoPlan() == null || !TIPOS_VALIDOS.contains(dto.getTipoPlan())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("tipo_plan debe ser alimenticio, ejercicio o hibrido");
        }

        // Verificar que el autor (usuario) exista en la BD antes de continuar
        if (dto.getIdAutor() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El id_autor es obligatorio");
        }
        Optional<Usuario> autorOpt = usuarioService.listId(dto.getIdAutor());
        if (autorOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado");
        }

        // Construir la entidad manualmente porque idAutor en la entidad es un objeto Usuario
        // y ModelMapper no puede convertir Integer → Usuario automáticamente
        PlanMaestro plan = new PlanMaestro(
                null,
                dto.getTitulo(),
                autorOpt.get(),
                dto.getTipoPlan(),
                dto.getDuracionDias(),
                dto.getObjetivo() != null ? dto.getObjetivo() : ""
        );

        PlanMaestro guardado = planMaestroService.insert(plan);

        // ModelMapper convierte la entidad guardada → DTO; idAutor se sobreescribe manualmente
        ModelMapper m = new ModelMapper();
        PlanMaestroDTO respuesta = m.map(guardado, PlanMaestroDTO.class);
        respuesta.setIdAutor(guardado.getIdAutor().getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    // CRUD — Buscar plan maestro por ID
    // GET /planes/detalle/{id} → 200 con el plan, o 404 si no existe
    @GetMapping("/detalle/{id}")
    public ResponseEntity<?> buscarPlan(@PathVariable Integer id) {
        Optional<PlanMaestro> opt = planMaestroService.listId(id);

        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Plan no encontrado");
        }

        // ModelMapper mapea campos simples; idAutor se sobreescribe manualmente
        ModelMapper m = new ModelMapper();
        PlanMaestroDTO dto = m.map(opt.get(), PlanMaestroDTO.class);
        dto.setIdAutor(opt.get().getIdAutor() != null ? opt.get().getIdAutor().getId() : null);
        return ResponseEntity.ok(dto);
    }

    // CRUD — Actualizar un plan maestro existente
    // PUT /planes/actualizar/{id} → 200 con mensaje, 400 si tipo inválido, 404 si no existe
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarPlan(@PathVariable Integer id, @RequestBody PlanMaestroDTO dto) {
        Optional<PlanMaestro> opt = planMaestroService.listId(id);

        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Plan no encontrado");
        }

        PlanMaestro plan = opt.get();

        // Solo actualizar los campos que vienen en el body
        if (dto.getTitulo() != null) {
            plan.setTitulo(dto.getTitulo());
        }
        if (dto.getTipoPlan() != null) {
            // Validar que el nuevo tipo_plan sea un valor permitido
            if (!TIPOS_VALIDOS.contains(dto.getTipoPlan())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("tipo_plan debe ser alimenticio, ejercicio o hibrido");
            }
            plan.setTipoPlan(dto.getTipoPlan());
        }
        if (dto.getDuracionDias() != null) {
            plan.setDuracionDias(dto.getDuracionDias());
        }
        if (dto.getObjetivo() != null) {
            plan.setObjetivo(dto.getObjetivo());
        }
        // Si viene un nuevo autor, verificar que exista en la BD
        if (dto.getIdAutor() != null) {
            Optional<Usuario> autorOpt = usuarioService.listId(dto.getIdAutor());
            if (autorOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Usuario no encontrado");
            }
            plan.setIdAutor(autorOpt.get());
        }

        planMaestroService.update(plan);
        return ResponseEntity.ok("Plan actualizado correctamente");
    }

    // US-P4-S2-01 — Agregar un ítem (receta o ejercicio) a un día del plan
    // POST /planes/{id}/items → 201 con ítem creado, 400 si faltan ambos ids, 404 si no existen
    @PostMapping("/{id}/items")
    public ResponseEntity<?> agregarItem(@PathVariable Integer id, @RequestBody DiaPlanItemDTO dto) {

        // Verificar que el plan exista
        Optional<PlanMaestro> planOpt = planMaestroService.listId(id);
        if (planOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plan no encontrado");
        }

        // La CONSTRAINT de BD exige que haya receta O ejercicio — validar antes de persistir
        if (dto.getIdReceta() == null && dto.getIdEjercicio() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Debe indicar una receta o un ejercicio");
        }

        Recipe receta = null;
        Ejercicio ejercicio = null;

        // Si viene idReceta, verificar que exista en la BD
        if (dto.getIdReceta() != null) {
            Optional<Recipe> recetaOpt = recipeService.listId(dto.getIdReceta());
            if (recetaOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Receta no encontrada");
            }
            receta = recetaOpt.get();
        }

        // Si viene idEjercicio, verificar que exista en la BD
        if (dto.getIdEjercicio() != null) {
            Optional<Ejercicio> ejercicioOpt = ejercicioService.listId(dto.getIdEjercicio());
            if (ejercicioOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ejercicio no encontrado");
            }
            ejercicio = ejercicioOpt.get();
        }

        // Construir la entidad con las relaciones ya validadas
        DiaPlanItem item = new DiaPlanItem(
                null,
                planOpt.get(),
                dto.getNumDia(),
                receta,
                ejercicio,
                dto.getMomento(),
                dto.getOrden()
        );

        DiaPlanItem guardado = diaPlanItemService.insert(item);

        // Construir DTO de respuesta extrayendo solo los IDs de las relaciones
        DiaPlanItemDTO respuesta = new DiaPlanItemDTO();
        respuesta.setId(guardado.getId());
        respuesta.setIdPlan(guardado.getIdPlan().getId());
        respuesta.setNumDia(guardado.getNumDia());
        respuesta.setIdReceta(guardado.getIdReceta() != null ? guardado.getIdReceta().getId() : null);
        respuesta.setIdEjercicio(guardado.getIdEjercicio() != null ? guardado.getIdEjercicio().getId() : null);
        respuesta.setMomento(guardado.getMomento());
        respuesta.setOrden(guardado.getOrden());

        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    // US-P4-05 — Eliminar un plan maestro por ID
    // DELETE /planes/eliminar/{id} → 204 si se eliminó, 404 si no existe
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarPlan(@PathVariable Integer id) {
        Optional<PlanMaestro> existente = planMaestroService.listId(id);

        if (existente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Plan no encontrado");
        }

        planMaestroService.delete(id);

        // 204 No Content: eliminación exitosa, sin cuerpo en la respuesta
        return ResponseEntity.noContent().build();
    }
}
