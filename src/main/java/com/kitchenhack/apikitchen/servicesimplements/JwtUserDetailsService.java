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
import java.util.Optional;


// Clase: servicio para cargar UserDetails usado por Spring Security
@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UsuarioRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> opt = repo.findByUsername(username);

        if (opt.isEmpty()) {
            throw new UsernameNotFoundException("User not exists: " + username);
        }

        Usuario user = opt.get();

        List<GrantedAuthority> authorities = new ArrayList<>();
        Rol rol = user.getIdRol();
        if (rol != null && rol.getNombre() != null) {
            // Mantener el nombre tal cual (p. ej. ROLE_USER). Si usas hasRole(), asegúrate del prefijo ROLE_.
            authorities.add(new SimpleGrantedAuthority(rol.getNombre()));
        }

        // Construir UserDetails con el username y el password (contrasenaHash ya almacenada en BD)
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getContrasenaHash(),
                true, // enabled
                true,
                true,
                true,
                authorities
        );
    }
}