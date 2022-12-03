package com.lucassilvs.rediscacheclient.controllers.impl;

import com.lucassilvs.rediscacheclient.controllers.UsuarioController;
import com.lucassilvs.rediscacheclient.models.response.UsuarioContractResponse;
import com.lucassilvs.rediscacheclient.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UsuarioControllerImpl implements UsuarioController {

    private  final UsuarioService usuarioService;

    @Autowired
    public UsuarioControllerImpl(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public UsuarioContractResponse buscarUsuario(@RequestParam(name = "cpf") String cpf) {
        return usuarioService.buscarUsuario(cpf);
    }
}
