package com.kitchenhack.apikitchen.controllers;

import com.kitchenhack.apikitchen.dtos.IngredienteDTO;
import com.kitchenhack.apikitchen.entities.Etiqueta;
import com.kitchenhack.apikitchen.entities.Ingrediente;
import com.kitchenhack.apikitchen.servicesinterfaces.IIngredienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
		// Si se pasa tipo, filtrar por tipo; si no, listar todos.
		List<Ingrediente> ingredientes = (tipo == null) ? ingredienteService.list() : ingredienteService.findByTipo(tipo);
		List<IngredienteDTO> listaIngredientes = ingredientes
				.stream().map(this::toDTO)
				.collect(Collectors.toList());
		return ResponseEntity.ok(listaIngredientes);
	}

	@GetMapping("/search")
	public ResponseEntity<List<IngredienteDTO>> buscarPorNombre(@RequestParam(name = "nombre") String nombre) {
		List<Ingrediente> ingredientes = ingredienteService.searchByNombre(nombre);
		List<IngredienteDTO> listaIngredientes = ingredientes
				.stream().map(this::toDTO)
				.collect(Collectors.toList());
		return ResponseEntity.ok(listaIngredientes);
	}

	@GetMapping("/search-advanced")
	public ResponseEntity<List<IngredienteDTO>> buscarPorNombreYTipo(
			@RequestParam(name = "nombre") String nombre,
			@RequestParam(name = "tipo", required = false) Integer tipo) {
		List<Ingrediente> ingredientes = ingredienteService.searchByNombreAndTipo(nombre, tipo);
		List<IngredienteDTO> listaIngredientes = ingredientes
				.stream().map(this::toDTO)
				.collect(Collectors.toList());
		return ResponseEntity.ok(listaIngredientes);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
		Optional<Ingrediente> ingrediente = ingredienteService.listId(id);

		if (ingrediente.isPresent()) {
			IngredienteDTO dto = toDTO(ingrediente.get());
			return ResponseEntity.ok(dto);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Ingrediente no encontrado");
		}
	}

	@PostMapping
	public ResponseEntity<?> crear(@RequestBody IngredienteDTO dto) {
		if (dto.getUnidadMedida() == null || dto.getUnidadMedida().isBlank()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("unidadMedida es requerido");
		}

		Ingrediente ingrediente = new Ingrediente();
		ingrediente.setNombre(dto.getNombre());
		ingrediente.setUnidadMedida(dto.getUnidadMedida());
		if (dto.getIdEtiqueta() != null) {
			Etiqueta etiqueta = new Etiqueta();
			etiqueta.setId(dto.getIdEtiqueta());
			ingrediente.setIdEtiqueta(etiqueta);
		}
		ingrediente.setCalorias100(toBigDecimal(dto.getCalorias100()));
		ingrediente.setProteinas100(toBigDecimal(dto.getProteinas100()));
		ingrediente.setCarbos100(toBigDecimal(dto.getCarbos100()));
		ingrediente.setGrasas100(toBigDecimal(dto.getGrasas100()));

		Ingrediente saved = ingredienteService.insert(ingrediente);
		IngredienteDTO responseDTO = toDTO(saved);
		return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody IngredienteDTO dto) {
		Optional<Ingrediente> existente = ingredienteService.listId(id);
		if (existente.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Ingrediente no encontrado");
		}

		// Reutilizar la entidad encontrada y modificar solo los campos permitidos.
		Ingrediente ingrediente = existente.get();
		if (dto.getNombre() != null) {
			ingrediente.setNombre(dto.getNombre());
		}
		if (dto.getUnidadMedida() != null) {
			ingrediente.setUnidadMedida(dto.getUnidadMedida());
		}
		if (dto.getIdEtiqueta() != null) {
			Etiqueta etiqueta = new Etiqueta();
			etiqueta.setId(dto.getIdEtiqueta());
			ingrediente.setIdEtiqueta(etiqueta);
		}
		if (dto.getCalorias100() != null) {
			ingrediente.setCalorias100(toBigDecimal(dto.getCalorias100()));
		}
		if (dto.getProteinas100() != null) {
			ingrediente.setProteinas100(toBigDecimal(dto.getProteinas100()));
		}
		if (dto.getCarbos100() != null) {
			ingrediente.setCarbos100(toBigDecimal(dto.getCarbos100()));
		}
		if (dto.getGrasas100() != null) {
			ingrediente.setGrasas100(toBigDecimal(dto.getGrasas100()));
		}

		ingredienteService.update(ingrediente);

		return ResponseEntity.ok("Ingrediente actualizado correctamente");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Integer id) {
		Optional<Ingrediente> ingrediente = ingredienteService.listId(id);

		if (ingrediente.isPresent()) {
			ingredienteService.delete(id);
			return ResponseEntity.ok("Ingrediente eliminado correctamente");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Ingrediente no encontrado");
		}
	}

	private BigDecimal toBigDecimal(Double value) {
		return value == null ? null : BigDecimal.valueOf(value);
	}

	private IngredienteDTO toDTO(Ingrediente ingrediente) {
		IngredienteDTO dto = new IngredienteDTO();
		dto.setId(ingrediente.getId());
		dto.setNombre(ingrediente.getNombre());
		dto.setUnidadMedida(ingrediente.getUnidadMedida());
		dto.setIdEtiqueta(ingrediente.getIdEtiqueta() != null ? ingrediente.getIdEtiqueta().getId() : null);
		dto.setCalorias100(ingrediente.getCalorias100());
		dto.setProteinas100(ingrediente.getProteinas100());
		dto.setCarbos100(ingrediente.getCarbos100());
		dto.setGrasas100(ingrediente.getGrasas100());
		return dto;
	}
}


