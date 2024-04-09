package com.remeikis.provainfuse.repository;

import com.remeikis.provainfuse.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>
{

}
