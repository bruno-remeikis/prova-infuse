package com.remeikis.provainfuse.service;

import com.remeikis.provainfuse.model.Cliente;
import com.remeikis.provainfuse.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Service
public class ClienteService implements CommandLineRunner
{
    @Autowired
    private ClienteRepository clienteRepository;

    // Salva 10 clientes ao inicializar a aplicação
    @Override
    public void run(String... args) throws Exception
    {
        List<Cliente> clientes = new LinkedList();
        for(int i = 1; i <= 10; i++)
            clientes.add(new Cliente(i, "Cliente " + i));

        clienteRepository.saveAll(clientes);
    }

    public Cliente findById(Integer id) {
        return clienteRepository.findById(id).orElse(null);
    }

    public List<Cliente> findAllById(List<Integer> ids) {
        return clienteRepository.findAllById(ids);
    }
}
