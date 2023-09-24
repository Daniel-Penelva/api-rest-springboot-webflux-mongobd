package com.daniel.daos;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.daniel.documentos.Cliente;

public interface ClienteDao extends ReactiveMongoRepository<Cliente, String>{

}
