package com.kitchenhack.apikitchen.controllers;

import com.kitchenhack.apikitchen.dtos.IngredienteDTO;
import com.kitchenhack.apikitchen.dtos.IngredienteDetailDTO;
import com.kitchenhack.apikitchen.servicesinterfaces.IIngredienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredientes")
@CrossOrigin(origins = "*")
public class IngredienteController {

	@Autowired
	private IIngredienteService ingredienteService;

	// CRUD base: GET /ingredientes/crud
	@GetMapping("/crud")
	public ResponseEntity<List<IngredienteDTO>> listarCrud() {
		return ResponseEntity.ok(ingredienteService.listarCrud());
	}

	// CRUD base: GET /ingredientes/crud/{id}
	@GetMapping("/crud/{id}")
	public ResponseEntity<IngredienteDTO> buscarCrudPorId(@PathVariable Long id) {
		return ResponseEntity.ok(ingredienteService.buscarCrudPorId(id));
	}

	// CRUD base: POST /ingredientes/crud
	@PostMapping("/crud")
	public ResponseEntity<IngredienteDTO> crear(@RequestBody IngredienteDTO dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(ingredienteService.crear(dto));
	}

	// CRUD base: PUT /ingredientes/crud/{id}
	@PutMapping("/crud/{id}")
	public ResponseEntity<IngredienteDTO> actualizar(@PathVariable Long id, @RequestBody IngredienteDTO dto) {
		return ResponseEntity.ok(ingredienteService.actualizar(id, dto));
	}

	// CRUD base: DELETE /ingredientes/crud/{id}
	@DeleteMapping("/crud/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {
		ingredienteService.eliminar(id);
		return ResponseEntity.noContent().build();
	}

	// GET /ingredientes?tipo=1
	@GetMapping
	public ResponseEntity<List<IngredienteDetailDTO>> listarPorTipo(@RequestParam(name = "tipo", required = false) Integer tipo) {
		return ResponseEntity.ok(ingredienteService.listarPorTipo(tipo));
	}

	// GET /ingredientes/{id}?usuarioId=123
	@GetMapping("/{id}")
	public ResponseEntity<?> obtenerPorId(@PathVariable Long id,
										 @RequestParam(name = "usuarioId", required = false) Integer usuarioId) {
		try {
			IngredienteDetailDTO dto = ingredienteService.obtenerPorId(id, usuarioId);
			return ResponseEntity.ok(dto);
		} catch (RuntimeException ex) {
			String msg = ex.getMessage();
			if (msg != null && msg.startsWith("EXTERNAL_API_ERROR")) {
				return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(msg);
			}
			throw ex;
		}
	}
}


