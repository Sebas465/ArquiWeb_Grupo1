package com.kitchenhack.apikitchen.servicesinterfaces;

import com.kitchenhack.apikitchen.dtos.UsuarioDTO;

import java.util.List;

public interface IUsuarioService {

    List<UsuarioDTO> listarTodos();

    UsuarioDTO buscarPorId(Integer id);

    UsuarioDTO crear(UsuarioDTO dto);

    UsuarioDTO actualizar(Integer id, UsuarioDTO dto);

    void eliminar(Integer id);
}

