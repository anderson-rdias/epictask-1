package br.com.fiap.epictaskapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.epictaskapi.dto.UsuarioDtoNoPassword;
import br.com.fiap.epictaskapi.model.Usuario;
import br.com.fiap.epictaskapi.service.UsuarioService;

@RestController
@RequestMapping("/api/user")
public class UsuarioController {

    @Autowired
    private UsuarioService service;
    
    @Autowired
    PasswordEncoder passwordEncoder;
    
   
    @PostMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<Usuario> create(@RequestBody @Valid Usuario usuario) {
        String novaSenha = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(novaSenha);
        service.save(usuario);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuario);
    }

   
    @GetMapping
    @PreAuthorize("permitAll()")
    public Page<UsuarioDtoNoPassword> index(@PageableDefault(size = 5) Pageable paginacao){
        Page<Usuario> usuario = service.listAll(paginacao);
        Page<UsuarioDtoNoPassword> dto = usuario.map( d -> new UsuarioDtoNoPassword(d));

        return dto;
    }

    
    @GetMapping("{id}")
    @PreAuthorize("permitAll()")    
    public ResponseEntity<List<UsuarioDtoNoPassword>> show(@PathVariable Long id){
        var lista = service.listDtoUser(id);
        if(lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().body(lista);
    }

   
    @PutMapping("{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Usuario> update(@PathVariable Long id, @RequestBody @Valid UsuarioDtoNoPassword novoUsuario){
        var optional = service.getById(id);

        if(optional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        var usuario = optional.get();
        BeanUtils.copyProperties(novoUsuario, usuario);
        usuario.setId(id);

        service.save(usuario);
        return ResponseEntity.ok(usuario);
    }

    
    @DeleteMapping("{id}")
    @PreAuthorize("isAuthenticated()") 
    public ResponseEntity<Object> destroy(@PathVariable Long id){
        var optional = service.getById(id);

        if(optional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        service.remove(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
