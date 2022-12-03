package com.lucassilvs.rediscacheclient.service;

import com.lucassilvs.rediscacheclient.models.response.UsuarioContractResponse;

public interface UsuarioService {

    UsuarioContractResponse buscarUsuario(String cpf);
}
