package br.com.fiap.epictaskapi.dto;

import br.com.fiap.epictaskapi.model.Usuario;

public class UsuarioDtoNoPassword {
    
    private Long id;
    private String nome;
    private String email;

    public UsuarioDtoNoPassword () {

    }

    public UsuarioDtoNoPassword (Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
