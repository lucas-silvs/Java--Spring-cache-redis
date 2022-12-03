package com.lucassilvs.rediscacheclient.models.response;

import com.lucassilvs.rediscacheclient.datasource.entity.UsuarioEntity;

public class UsuarioContractResponse {

    private final String nome;
    private final String cpf;
    private final String email;


    public UsuarioContractResponse(UsuarioEntity usuario) {
        this.nome = usuario.getNome();
        this.cpf = usuario.getCpf();
        this.email = usuario.getEmail();
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }
}
