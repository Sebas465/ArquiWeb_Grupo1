package com.kitchenhack.apikitchen.servicesinterfaces;

import com.kitchenhack.apikitchen.dtos.CitaRequestDTO;

import java.util.List;
import java.util.Map;

public interface ICalendarioService {
    Map<String, Object> crearCita(CitaRequestDTO dto);

    List<Map<String, Object>> listarMisCitas(String username);
}
