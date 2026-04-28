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
	public ResponseEntity<List<IngredienteDTO>> listar(@RequestParam(name = "tipo", required = false) Integer tipo) {
		ModelMapper m = new ModelMapper();
		// Si se pasa tipo, filtrar por tipo; si no, listar todos.
		List<Ingrediente> ingredientes = (tipo == null) ? ingredienteService.list() : ingredienteService.findByTipo(tipo);
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
	public ResponseEntity<IngredienteDTO> crear(@RequestBody IngredienteDTO dto) {
		ModelMapper m = new ModelMapper();
		// El DTO de entrada se convierte a entidad para que el servicio la persista.
		Ingrediente ingrediente = m.map(dto, Ingrediente.class);
		Ingrediente saved = ingredienteService.insert(ingrediente);
		// La entidad guardada se vuelve a mapear a DTO para la respuesta HTTP.
		IngredienteDTO responseDTO = m.map(saved, IngredienteDTO.class);
		return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
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
		ingrediente.setTipoIngredienteId(dto.getTipoIngredienteId());

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


