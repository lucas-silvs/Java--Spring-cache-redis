package com.lucassilvs.rediscacheclient.controllers;

import com.lucassilvs.rediscacheclient.models.response.UsuarioContractResponse;

public interface UsuarioController {

    UsuarioContractResponse buscarUsuario(String cpf);
}
