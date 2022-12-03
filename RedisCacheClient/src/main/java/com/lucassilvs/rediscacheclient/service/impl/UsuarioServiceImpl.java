package com.lucassilvs.rediscacheclient.service.impl;

import com.lucassilvs.rediscacheclient.datasource.entity.UsuarioEntity;
import com.lucassilvs.rediscacheclient.datasource.repository.UsuarioRepository;
import com.lucassilvs.rediscacheclient.models.response.UsuarioContractResponse;
import com.lucassilvs.rediscacheclient.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioContractResponse buscarUsuario(String cpf) {
        Optional<UsuarioEntity> usuario = usuarioRepository.findByCpf(cpf);

        if(usuario.isEmpty()){
            throw new RuntimeException("Usuario de cpf "+ cpf +" não encontrado");
        }
        return new UsuarioContractResponse(usuario.get());

    }
}
