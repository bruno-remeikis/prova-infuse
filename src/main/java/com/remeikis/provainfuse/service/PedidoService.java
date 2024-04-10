package com.remeikis.provainfuse.service;

import com.remeikis.provainfuse.exceptions.BusinessException;
import com.remeikis.provainfuse.model.Cliente;
import com.remeikis.provainfuse.model.Pedido;
import com.remeikis.provainfuse.repository.ClienteRepository;
import com.remeikis.provainfuse.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService
{
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteService clienteService;

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    public Pedido findById(int id) {
        return pedidoRepository.findById(id).orElse(null);
    }

    public List<Pedido> findByCodigoCliente(int codigoCliente) {
        return pedidoRepository.findByCodigoCliente(codigoCliente);
    }

    /**
     * Verifica se já não existem pedidos com os {@code numeroControle} informados
     */
    private void verifyNumeroControle(List<Pedido> pedidos) throws BusinessException {
        List<Integer> ids = pedidos.stream()
            .map(Pedido::getNumeroControle)
            .collect(Collectors.toList());

        // Verifica se foram informados números iguais nos novos pedidos
        if(ids.stream().anyMatch(n -> Collections.frequency(ids, n) > 1))
            throw new BusinessException(HttpStatus.BAD_REQUEST, "Informe números de controle diferentes para os novos pedidos");

        List<Pedido> pedidos2 = pedidoRepository.findAllById(ids);

        // Se pedidos2 não estiver vazio, significa que já há pedido(s) registrado(s) com algum dos números dos novos pedidos
        if(!pedidos2.isEmpty()) {
            // Cria String contendo sequência de IDs existentes (Ex.: "1, 2, 3")
            String idsExistentes = String.join(", ", pedidos2.stream()
                .map(Pedido::getNumeroControle)
                .map(String::valueOf)
                .collect(Collectors.toList())
            );
            String exceptionMsg = pedidos2.size() > 1
                ? "Já existem pedidos com os números de controle " + idsExistentes
                : "Já existe um pedido com o número de controle " + idsExistentes;
            throw new BusinessException(HttpStatus.BAD_REQUEST, exceptionMsg);
        }
    }

    /**
     * Verifica se todos os clientes informados existem
     */
    private void verifyCodigoCliente(List<Pedido> pedidos) throws BusinessException {
        List<Integer> ids = pedidos.stream()
                .map(Pedido::getCodigoCliente)
                .distinct()
                .collect(Collectors.toList());

        List<Cliente> clientes = clienteService.findAllById(ids);

        if(clientes.size() < ids.size()) {
            String strIds = String.join(", ", ids.stream() // Concatena IDs com virgula
                .filter(id -> !clientes.stream().anyMatch(c -> c.getCodigo().equals(id))) // Remove IDs de clientes válidos/existentes
                .map(String::valueOf) // Converte os IDs em String
                .collect(Collectors.toList())
            );
            String exceptionMsg = ids.size() - clientes.size() > 1
                ? "Não existem usuários com os códigos " + strIds
                : "Não existe usuário com o código " + strIds;
            throw new BusinessException(HttpStatus.BAD_REQUEST, exceptionMsg);
        }
    }

    public List<Pedido> save(List<Pedido> pedidos) throws BusinessException
    {
        if(pedidos.size() > 10)
            throw new BusinessException(HttpStatus.BAD_REQUEST, "Número máximo de pedidos: 10");

        verifyNumeroControle(pedidos);

        verifyCodigoCliente(pedidos);

        for(Pedido p: pedidos)
        {
            if(p.getQuantidade() == null)
                p.setQuantidade(1);
            else if(p.getQuantidade() >= 10)
                p.setValor(p.getValor() * 1.1);
            else if(p.getQuantidade() > 5)
                p.setValor(p.getValor() * 1.05);

            if(p.getDataCadastro() == null)
                p.setDataCadastro(new Date());
        }

        return pedidoRepository.saveAll(pedidos);
    }

    public List<Pedido> findByDataCadastro(Date dataCadastro) {
        return pedidoRepository.findByDataCadastro(dataCadastro);
    }

    /*public List<Pedido> findByIntervaloDataCadastro(Date de, Date ate) {
        return pedidoRepository.findByIntervaloDataCadastro(de, ate);
    }*/
}
