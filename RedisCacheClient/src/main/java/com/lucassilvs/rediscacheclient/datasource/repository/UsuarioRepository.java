package com.lucassilvs.rediscacheclient.datasource.repository;

import com.lucassilvs.rediscacheclient.datasource.entity.UsuarioEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends CrudRepository<UsuarioEntity, Long> {

    Optional<UsuarioEntity> findByCpf(String cpf);
}
