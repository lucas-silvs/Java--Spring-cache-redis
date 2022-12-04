package com.lucassilvs.rediscacheclient.datasource.repository;

import com.lucassilvs.rediscacheclient.datasource.entity.UsuarioEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends CrudRepository<UsuarioEntity, Long> {

    @Cacheable(cacheNames = "usuarios", cacheManager = "cacheManager", key = "#cpf")
    Optional<UsuarioEntity> findByCpf(String cpf);
}
