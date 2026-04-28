package com.kitchenhack.apikitchen.servicesimplements;

import com.kitchenhack.apikitchen.dtos.IngredienteDTO;
import com.kitchenhack.apikitchen.dtos.IngredienteDetailDTO;
import com.kitchenhack.apikitchen.entities.Ingrediente;
import com.kitchenhack.apikitchen.entities.ProgresoSalud;
import com.kitchenhack.apikitchen.repositories.IngredienteRepository;
import com.kitchenhack.apikitchen.repositories.ProgresoSaludRepository;
import com.kitchenhack.apikitchen.servicesinterfaces.IIngredienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class IngredienteServiceImplement implements IIngredienteService {

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Autowired
    private ProgresoSaludRepository progresoSaludRepository;

    @Value("${external.ingredientes.api:https://api.ingredientes.example/v1/ingrediente}")
    private String externalApiUrl;

    private final RestTemplate rest = new RestTemplate();

    private IngredienteDTO toCrudDTO(Ingrediente ingrediente) {
        IngredienteDTO dto = new IngredienteDTO();
        dto.setId(ingrediente.getId());
        dto.setNombre(ingrediente.getNombre());
        dto.setTipoIngredienteId(ingrediente.getTipoIngredienteId());
        return dto;
    }

    private Ingrediente toEntity(IngredienteDTO dto) {
        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setNombre(dto.getNombre());
        ingrediente.setTipoIngredienteId(dto.getTipoIngredienteId());
        return ingrediente;
    }

    @Override
    public List<IngredienteDTO> listarCrud() {
        return ingredienteRepository.findAll().stream().map(this::toCrudDTO).collect(Collectors.toList());
    }

    @Override
    public IngredienteDTO buscarCrudPorId(Long id) {
        Ingrediente ingrediente = ingredienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingrediente no encontrado con id: " + id));
        return toCrudDTO(ingrediente);
    }

    @Override
    public IngredienteDTO crear(IngredienteDTO dto) {
        return toCrudDTO(ingredienteRepository.save(toEntity(dto)));
    }

    @Override
    public IngredienteDTO actualizar(Long id, IngredienteDTO dto) {
        Ingrediente ingrediente = ingredienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingrediente no encontrado con id: " + id));
        ingrediente.setNombre(dto.getNombre());
        ingrediente.setTipoIngredienteId(dto.getTipoIngredienteId());
        return toCrudDTO(ingredienteRepository.save(ingrediente));
    }

    @Override
    public void eliminar(Long id) {
        ingredienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingrediente no encontrado con id: " + id));
        ingredienteRepository.deleteById(id);
    }

    @Override
    public List<IngredienteDetailDTO> listarPorTipo(Integer tipoIngredienteId) {
        List<Ingrediente> ingredientes = tipoIngredienteId == null
                ? ingredienteRepository.findAll()
                : ingredienteRepository.findByTipoIngredienteId(tipoIngredienteId);
        return ingredientes.stream().map(i -> {
            try {
                return fetchDetailForIngrediente(i, null);
            } catch (RuntimeException ex) {
                // If external api fails for one ingredient, include minimal info without macros
                IngredienteDetailDTO dto = new IngredienteDetailDTO();
                dto.setId(i.getId());
                dto.setNombre(i.getNombre());
                dto.setAlerta(false);
                return dto;
            }
        }).collect(Collectors.toList());
    }

    @Override
    public IngredienteDetailDTO obtenerPorId(Long id, Integer usuarioId) {
        Ingrediente ingrediente = ingredienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingrediente no encontrado con id: " + id));
        return fetchDetailForIngrediente(ingrediente, usuarioId);
    }

    // Lógica principal: consulta API externa y crea DTO con macros y alergenos; determina alerta según progreso_salud
    private IngredienteDetailDTO fetchDetailForIngrediente(Ingrediente ingrediente, Integer usuarioId) {
        String nombre = ingrediente.getNombre();
        try {
            String url = externalApiUrl + "?nombre=" + URLEncoder.encode(nombre, StandardCharsets.UTF_8);
            Map response = rest.getForObject(url, Map.class);

            IngredienteDetailDTO dto = new IngredienteDetailDTO();
            dto.setId(ingrediente.getId());
            dto.setNombre(ingrediente.getNombre());

            if (response != null) {
                // macros esperado en response.get("macros")
                Object macrosObj = response.get("macros");
                if (macrosObj instanceof Map) {
                    Map macros = (Map) macrosObj;
                    dto.setTotalCalories(toBigDecimal(macros.get("calorias")));
                    dto.setProteinGrams(toBigDecimal(macros.get("proteinas")));
                    dto.setCarbsGrams(toBigDecimal(macros.get("carbohidratos")));
                    dto.setFatGrams(toBigDecimal(macros.get("grasas")));
                }

                // alergenos puede venir como lista o string
                Object alergObj = response.get("alergenos");
                List<String> alergenos = new ArrayList<>();
                if (alergObj instanceof List) {
                    for (Object o : (List) alergObj) {
                        if (o != null) alergenos.add(o.toString().toLowerCase());
                    }
                } else if (alergObj instanceof String) {
                    String s = (String) alergObj;
                    String[] parts = s.split(",");
                    for (String p : parts) if (!p.isBlank()) alergenos.add(p.trim().toLowerCase());
                }
                dto.setAlergenos(alergenos);
            }

            // comprobar alergias del usuario (si se pasó usuarioId)
            boolean alerta = false;
            if (usuarioId != null) {
                Optional<ProgresoSalud> prog = progresoSaludRepository.findByUsuarioId(usuarioId);
                if (prog.isPresent() && prog.get().getAlergias() != null) {
                    String alergiasText = prog.get().getAlergias().toLowerCase();
                    Set<String> userAlergens = Arrays.stream(alergiasText.split(","))
                            .map(String::trim)
                            .filter(s -> !s.isEmpty())
                            .collect(Collectors.toSet());
                    List<String> inter = dto.getAlergenos() == null ? Collections.emptyList() : dto.getAlergenos();
                    for (String a : inter) {
                        if (userAlergens.contains(a)) {
                            alerta = true;
                            break;
                        }
                    }
                }
            }
            dto.setAlerta(alerta);
            return dto;

        } catch (RestClientException rex) {
            throw new RuntimeException("EXTERNAL_API_ERROR: error al consultar API externa de ingredientes: " + rex.getMessage());
        }
    }

    private BigDecimal toBigDecimal(Object o) {
        if (o == null) return null;
        try {
            if (o instanceof Number) {
                return new BigDecimal(((Number) o).toString());
            }
            return new BigDecimal(o.toString());
        } catch (Exception ex) {
            return null;
        }
    }
}

