package com.kitchenhack.apikitchen.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.kitchenhack.apikitchen.entities.Usuario;
import com.kitchenhack.apikitchen.entities.Rol;
import com.kitchenhack.apikitchen.repositories.UsuarioRepository;


import java.util.ArrayList;
import java.util.List;


@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UsuarioRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = repo.findOneByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("User not exists: %s", username));
        }

        List<GrantedAuthority> roles = new ArrayList<>();

        Rol rol = user.getIdRol();
        if (rol != null && rol.getNombre() != null) {
            roles.add(new SimpleGrantedAuthority(rol.getNombre()));
        }

        UserDetails ud = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getContrasenaHash(), true, true, true, true, roles);

        return ud;
    }
}