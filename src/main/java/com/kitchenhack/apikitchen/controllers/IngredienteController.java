package com.kitchenhack.apikitchen.controllers;

import com.kitchenhack.apikitchen.dtos.IngredienteDTO;
import com.kitchenhack.apikitchen.entities.Ingrediente;
import com.kitchenhack.apikitchen.servicesinterfaces.IIngredienteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ingredientes")
@CrossOrigin(origins = "*")
public class IngredienteController {

	@Autowired
	private IIngredienteService ingredienteService;

	@GetMapping
	public ResponseEntity<?> listar(@RequestParam(name = "idEtiqueta", required = false) Integer idEtiqueta) {
		ModelMapper m = new ModelMapper();
		List<Ingrediente> ingredientes = (idEtiqueta == null)
				? ingredienteService.list()
				: ingredienteService.findByTipo(idEtiqueta);

		if (ingredientes.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("No hay ingredientes registrados");
		}

		List<IngredienteDTO> listaIngredientes = ingredientes
				.stream()
				.map(x -> m.map(x, IngredienteDTO.class))
				.collect(Collectors.toList());
		return ResponseEntity.ok(listaIngredientes);
	}

	@GetMapping("/search")
	public ResponseEntity<List<IngredienteDTO>> buscarPorNombre(@RequestParam(name = "nombre", required = true) String nombre) {
		ModelMapper m = new ModelMapper();
		List<Ingrediente> ingredientes = ingredienteService.searchByNombre(nombre);
		List<IngredienteDTO> listaIngredientes = ingredientes
				.stream().map(x -> m.map(x, IngredienteDTO.class))
				.collect(Collectors.toList());
		return ResponseEntity.ok(listaIngredientes);
	}

	@GetMapping("/search-advanced")
	public ResponseEntity<List<IngredienteDTO>> buscarPorNombreYTipo(
			@RequestParam(name = "nombre", required = true) String nombre,
			@RequestParam(name = "tipo", required = false) Long tipo) {
		ModelMapper m = new ModelMapper();
		List<Ingrediente> ingredientes = ingredienteService.searchByNombreAndTipo(nombre, tipo);
		List<IngredienteDTO> listaIngredientes = ingredientes
				.stream().map(x -> m.map(x, IngredienteDTO.class))
				.collect(Collectors.toList());
		return ResponseEntity.ok(listaIngredientes);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
		ModelMapper m = new ModelMapper();
		Optional<Ingrediente> ingrediente = ingredienteService.listId(id);

		if (ingrediente.isPresent()) {
			IngredienteDTO dto = m.map(ingrediente.get(), IngredienteDTO.class);
			return ResponseEntity.ok(dto);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Ingrediente no encontrado");
		}
	}

	@PostMapping
	public ResponseEntity<?> crear(@RequestBody IngredienteDTO dto) {
		if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("El nombre es obligatorio");
		}

		ModelMapper m = new ModelMapper();

		try {
			Ingrediente ingrediente = m.map(dto, Ingrediente.class);
			ingrediente.setId(0);

			Ingrediente saved = ingredienteService.insert(ingrediente);

			IngredienteDTO responseDTO = m.map(saved, IngredienteDTO.class);
			return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);

		} catch (Exception e) {
			Throwable rootCause = org.springframework.core.NestedExceptionUtils.getMostSpecificCause(e);
			String message = rootCause.getMessage();

			if (message != null && (message.contains("id_etiqueta") || message.contains("fkey") || message.contains("foreign key"))) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("Etiqueta no encontrada");
			}

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error interno al procesar el ingrediente: " + (message != null ? message : "Desconocido"));
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody IngredienteDTO dto) {
		Optional<Ingrediente> existente = ingredienteService.listId(id);
		if (existente.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Ingrediente no encontrado");
		}

		// Reutilizar la entidad encontrada y modificar solo los campos permitidos.
		Ingrediente ingrediente = existente.get();
		ingrediente.setNombre(dto.getNombre());
		ingrediente.setIdEtiqueta(dto.getIdEtiqueta());

		ingredienteService.update(ingrediente);

		return ResponseEntity.ok("Ingrediente actualizado correctamente");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id) {
		Optional<Ingrediente> ingrediente = ingredienteService.listId(id);

		if (ingrediente.isPresent()) {
			ingredienteService.delete(id);
			return ResponseEntity.ok("Ingrediente eliminado correctamente");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Ingrediente no encontrado");
		}
	}
}


