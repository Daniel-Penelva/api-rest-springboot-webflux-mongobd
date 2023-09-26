package com.daniel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daniel.daos.ClienteDao;
import com.daniel.documentos.Cliente;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ClienteServiceImpl implements ClienteService {

	    @Autowired
	    private ClienteDao clienteDao;

	    @Override
	    public Flux<Cliente> findAll() {
	      return clienteDao.findAll();
	    }

	    @Override
	    public Mono<Cliente> findById(String id) {
	      return clienteDao.findById(id);
	    }

	    @Override
	    public Mono<Cliente> save(Cliente cliente) {
	       return clienteDao.save(cliente);
	    }

	    @Override
	    public Mono<Void> delete(Cliente cliente) {
	      return clienteDao.delete(cliente);
	    }
}
