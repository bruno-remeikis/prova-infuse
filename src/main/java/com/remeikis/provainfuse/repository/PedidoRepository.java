package com.remeikis.provainfuse.repository;

import com.remeikis.provainfuse.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Integer>
{
    List<Pedido> findByCodigoCliente(int codigoCliente);

    @Query("SELECT p FROM Pedido p WHERE DATE(p.dataCadastro) = ?1")
    List<Pedido> findByDataCadastro(Date dataCadastro);

    @Query("SELECT p FROM Pedido p WHERE p.dataCadastro BETWEEN ?1 AND ?2")
    List<Pedido> findByIntervaloDataCadastro(Date de, Date ate);
}
