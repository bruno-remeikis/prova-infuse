package com.remeikis.provainfuse.service;

import com.remeikis.provainfuse.exceptions.BusinessException;
import com.remeikis.provainfuse.model.Cliente;
import com.remeikis.provainfuse.model.Pedido;
import com.remeikis.provainfuse.repository.PedidoRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest
{
    @InjectMocks
    private PedidoService pedidoService;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ClienteService clienteService;

    @Nested
    class FindAll
    {
        @Test
        public void deveRetornarPedidosComSucesso() {
            List<Pedido> pedidos = Arrays.asList(new Pedido(), new Pedido());

            // Mock comportamento do repositório
            when(pedidoRepository.findAll()).thenReturn(pedidos);

            // Executa o método
            List<Pedido> resultado = pedidoService.findAll();

            // Verifica o resultado
            assertEquals(pedidos, resultado);
        }
    }

    @Nested
    class FindById
    {
        @Test
        public void deveRetornarUmPedidoComSucesso() {
            int id = 1;
            Pedido pedido = new Pedido();

            // Mock comportamento do repositório
            when(pedidoRepository.findById(id)).thenReturn(Optional.of(pedido));

            // Executa o método
            Pedido resultado = pedidoService.findById(id);

            // Verifica o resultado
            assertEquals(pedido, resultado);
        }

        @Test
        public void deveRetornarNullComSucesso() {
            int id = 1;

            // Mock comportamento do repositório
            when(pedidoRepository.findById(id)).thenReturn(Optional.empty());

            // Executa o método
            Pedido resultado = pedidoService.findById(id);

            // Verifica o resultado
            assertNull(resultado);
        }
    }

    @Nested
    class Save
    {
        @Test
        public void testSave_ExcedeLimite() throws BusinessException {
            List<Pedido> pedidos = new ArrayList<>();
            for (int i = 0; i < 11; i++) {
                pedidos.add(new Pedido());
            }

            assertThrows(BusinessException.class, () -> pedidoService.save(pedidos));
        }

        /*@Test
        public void testSave_ValidaNumeroControle() throws BusinessException {
            List<Pedido> pedidos = Arrays.asList(new Pedido(), new Pedido());

            // Mock comportamento de verificação de numeroControle
            pedidoService.verifyNumeroControle(pedidos);

            // Verifica se o método foi chamado (opcional)
            verify(pedidoRepository, times(1)).findAllById(anyList());
        }*/

        @Test
        public void testSave_NumeroControleDuplicado() throws BusinessException {
            List<Pedido> pedidos = Arrays.asList(new Pedido(), new Pedido());

            // Mock comportamento para encontrar pedidos duplicados
            when(pedidoRepository.findAllById(anyList())).thenReturn(Collections.singletonList(new Pedido()));

            assertThrows(BusinessException.class, () -> pedidoService.save(pedidos));
        }

        /*@Test
        public void testSave_ValidaCodigoCliente() throws BusinessException {
            List<Pedido> pedidos = Arrays.asList(new Pedido(), new Pedido());

            // Mock comportamento do ClienteService
            when(clienteService.findAllById(anyList())).thenReturn(Arrays.asList(new Cliente(), new Cliente()));

            pedidoService.verifyCodigoCliente(pedidos);

            // Verifica se o método do ClienteService foi chamado (opcional)
            verify(clienteService, times(1)).findAllById(anyList());
        }*/

        @Test
        public void testSave_ClienteInexistente() throws BusinessException {
            List<Pedido> pedidos = Arrays.asList(new Pedido(), new Pedido());

            // Mock comportamento para encontrar clientes inexistentes
            when(clienteService.findAllById(anyList())).thenReturn(Collections.singletonList(new Cliente()));

            assertThrows(BusinessException.class, () -> pedidoService.save(pedidos));
        }

        @Test
        public void testSave_SetaQuantidadePadrao() throws BusinessException {
            Pedido pedido = new Pedido();
            List<Pedido> pedidos = Collections.singletonList(pedido);

            pedidoService.save(pedidos);

            assertEquals(1, pedido.getQuantidade());
        }

        @Test
        public void testSave_AumentaValorQuantidadeAlta() throws BusinessException {
            Pedido pedido = new Pedido();
            pedido.setQuantidade(10);
            pedido.setValor(10f);
            List<Pedido> pedidos = Collections.singletonList(pedido);

            pedidoService.save(pedidos);

            assertEquals(11f, pedido.getValor(), 0.001f);
        }
    }
}