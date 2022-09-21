package br.com.fiap.epictaskapi.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.fiap.epictaskapi.dto.UsuarioDtoNoPassword;
import br.com.fiap.epictaskapi.model.Usuario;
import br.com.fiap.epictaskapi.repository.UsuarioRepository;

@Service
public class UsuarioService {
    
    @Autowired
    UsuarioRepository usuarioRepository;
    
    public Page<Usuario> listAll(Pageable pagina) {
        return usuarioRepository.findAll(pagina);
    }


    public void save(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public void remove(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Optional<Usuario> getById(Long id) {
        return usuarioRepository.findById(id);
    }

    public List<UsuarioDtoNoPassword> listDtoUser(Long id) {
        return usuarioRepository.findById(id)
                                .stream()
                                .map(this::convertDto)
                                .collect(Collectors.toList());
    }

    private UsuarioDtoNoPassword convertDto(Usuario usuario) {
        UsuarioDtoNoPassword dto = new UsuarioDtoNoPassword();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());

        return dto;
    }

}
