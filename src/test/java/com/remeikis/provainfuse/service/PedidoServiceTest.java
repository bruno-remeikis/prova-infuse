package com.remeikis.provainfuse.service;

import com.remeikis.provainfuse.exceptions.BusinessException;
import com.remeikis.provainfuse.model.Cliente;
import com.remeikis.provainfuse.model.Pedido;
import com.remeikis.provainfuse.repository.PedidoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
            List<Pedido> pedidos = List.of(new Pedido(), new Pedido());

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
    class VerifyNumeroControle
    {
        private static Method verifyNumeroControleMethod;

        static {
            try {
                verifyNumeroControleMethod = PedidoService.class.getDeclaredMethod("verifyNumeroControle", List.class);
                verifyNumeroControleMethod.setAccessible(true);
            }
            catch(NoSuchMethodException e) {
                System.out.println("Erro: Método `verifyNumeroControle` não encontrado.");
            }
        }

        @Test
        @DisplayName("Deve fazer nada com sucesso")
        void deveFazerNadaComSucesso() throws Exception {
            Pedido pedido1 = new Pedido();
            pedido1.setNumeroControle(123);

            Pedido pedido2 = new Pedido();
            pedido2.setNumeroControle(456);

            List<Pedido> pedidos = List.of(pedido1, pedido2);

            when(pedidoRepository.findAllById(List.of(123, 456)))
                .thenReturn(List.of());

            try {
                verifyNumeroControleMethod.invoke(pedidoService, pedidos);
            }
            catch(InvocationTargetException e) {
                fail("Exceção BusinessException foi lançada");
            }
        }

        @Test
        @DisplayName("Deve lançar Exceção quando número de controle estiver duplicado")
        void deveLancarExcecaoNumeroControleDuplicado() throws Exception {
            Pedido pedido1 = new Pedido();
            pedido1.setNumeroControle(123);

            Pedido pedido2 = new Pedido();
            pedido2.setNumeroControle(123);

            List<Pedido> pedidos = List.of(pedido1, pedido2);

            try {
                verifyNumeroControleMethod.invoke(pedidoService, pedidos);
                fail("Deveria ter lançado BusinessException");
            }
            catch(InvocationTargetException e) {
                assertInstanceOf(BusinessException.class, e.getTargetException());
            }
        }

        @Test
        @DisplayName("Deve lançar Exceção quando número de controle for existente")
        void deveLancarExcecaoNumeroControleExistente() throws Exception {
            Pedido pedido1 = new Pedido();
            pedido1.setNumeroControle(123);

            Pedido pedido2 = new Pedido();
            pedido2.setNumeroControle(456);

            List<Pedido> pedidos = List.of(pedido1, pedido2);

            when(pedidoRepository.findAllById(List.of(123, 456)))
                .thenReturn(List.of(pedido1));

            try {
                verifyNumeroControleMethod.invoke(pedidoService, pedidos);
                fail("Deveria ter lançado BusinessException");
            }
            catch(InvocationTargetException e) {
                assertInstanceOf(BusinessException.class, e.getTargetException());
            }
        }
    }

    @Nested
    class VerifyCodigoCliente
    {
        private static Method verifyCodigoClienteMethod;

        static {
            try {
                verifyCodigoClienteMethod = PedidoService.class.getDeclaredMethod("verifyCodigoCliente", List.class);
                verifyCodigoClienteMethod.setAccessible(true);
            }
            catch(NoSuchMethodException e) {
                System.out.println("Erro: Método `verifyCodigoCliente` não encontrado.");
            }
        }

        @Test
        @DisplayName("Deve fazer nada com sucesso")
        void deveFazerNadaComSucesso() throws Exception {
            Pedido pedido1 = new Pedido();
            pedido1.setCodigoCliente(1);

            Pedido pedido2 = new Pedido();
            pedido2.setCodigoCliente(1);

            List<Pedido> pedidos = List.of(pedido1, pedido2);

            Cliente cliente1 = new Cliente();
            cliente1.setCodigo(1);

            List<Cliente> clientes = List.of(cliente1);

            when(clienteService.findAllById(List.of(1)))
                .thenReturn(clientes);

            try {
                verifyCodigoClienteMethod.invoke(pedidoService, pedidos);
            }
            catch(InvocationTargetException e) {
                fail("Exceção BusinessException foi lançada");
            }
        }


        @Test
        @DisplayName("Deve lançar exceção quando código do cliente for inexistente")
        void deveLancarExcecaoCodigoClienteInexistente() throws Exception {
            Pedido pedido1 = new Pedido();
            pedido1.setCodigoCliente(1);

            Pedido pedido2 = new Pedido();
            pedido2.setCodigoCliente(999);

            List<Pedido> pedidos = List.of(pedido1, pedido2);

            Cliente cliente1 = new Cliente();
            cliente1.setCodigo(1);

            List<Cliente> clientes = List.of(cliente1);

            when(clienteService.findAllById(List.of(1, 999)))
                .thenReturn(clientes);

            try {
                verifyCodigoClienteMethod.invoke(pedidoService, pedidos);
                fail("Deveria ter lançado BusinessException");
            }
            catch(InvocationTargetException e) {
                assertInstanceOf(BusinessException.class, e.getTargetException());
            }
        }
    }

    @Nested
    class Save
    {
        @Test
        @DisplayName("Deve lançar exceção quando quantidade máxima de pedidos for excedida")
        public void deveLancarExcecaoQuantidadeMaximaPedidosExcedida() throws BusinessException {
            List<Pedido> pedidos = new ArrayList<>();
            for (int i = 0; i < 11; i++) {
                pedidos.add(new Pedido());
            }

            assertThrows(BusinessException.class, () -> pedidoService.save(pedidos));
        }

        @Test
        @DisplayName("Deve lançar exceção quando números de controle estiverem duplicados")
        public void deveLancarExcecaoQuandoNumerosControleEstiveremDuplicados() {
            Pedido pedido1 = new Pedido();
            pedido1.setNumeroControle(123);

            Pedido pedido2 = new Pedido();
            pedido2.setNumeroControle(124);

            List<Pedido> pedidos = List.of(pedido1, pedido2);

            try {
                pedidoService.save(pedidos);
                fail("Deveria ter lançado BusinessException");
            }
            catch (BusinessException e) {
                assertEquals(HttpStatus.BAD_REQUEST.value(), e.getHttpStatus());
            }
        }

        @Test
        @DisplayName("Deve lançar exceção quando número de controle não existir")
        public void deveLancarExcecaoQuandoNumeroControleNaoExistir() {
            Pedido pedido = new Pedido();
            pedido.setNumeroControle(9999);

            List<Pedido> pedidos = Arrays.asList(pedido);

            try {
                pedidoService.save(pedidos);
                fail("Deveria ter lançado BusinessException");
            } catch (BusinessException e) {
                assertEquals(HttpStatus.BAD_REQUEST.value(), e.getHttpStatus());
            }
        }

        @Test
        @DisplayName("Deve lançar exceção quando código do cliente não existir")
        public void deveLancarExcecaoQuandoCodigoClienteNaoExistir() {
            Pedido pedido = new Pedido();
            pedido.setCodigoCliente(9999);

            List<Pedido> pedidos = Arrays.asList(pedido);

            try {
                pedidoService.save(pedidos);
                fail("Deveria ter lançado BusinessException");
            } catch (BusinessException e) {
                assertEquals(HttpStatus.BAD_REQUEST.value(), e.getHttpStatus());
            }
        }

        @Test
        @DisplayName("Deve setar 1 quando quantidade for `null` com sucesso")
        public void deveSetar1QuandoQuantidadeForNullComSucesso() throws Exception {
            Pedido pedido = new Pedido();
            pedido.setNumeroControle(123);
            pedido.setCodigoCliente(1);

            Cliente cliente1 = new Cliente();
            cliente1.setCodigo(1);

            when(clienteService.findAllById(List.of(1)))
                .thenReturn(List.of(cliente1));

            List<Pedido> pedidos = Arrays.asList(pedido);

            pedidoService.save(pedidos);

            assertEquals(1, pedido.getQuantidade());
        }

        @Test
        @DisplayName("Deve setar data atual quando dataCadastro for `null` com sucesso")
        public void deveSetarDataAtualQuandoDataCadastroForNullComSucesso() throws Exception {
            Pedido pedido = new Pedido();
            pedido.setNumeroControle(123);
            pedido.setCodigoCliente(1);

            Cliente cliente1 = new Cliente();
            cliente1.setCodigo(1);

            when(clienteService.findAllById(List.of(1)))
                .thenReturn(List.of(cliente1));

            List<Pedido> pedidos = Arrays.asList(pedido);

            pedidoService.save(pedidos);

            assertNotNull(pedido.getDataCadastro());
        }

        @Test
        @DisplayName("Deve incrementar 10% ao valor com sucesso")
        void deveIncrementar10PorcentoAoValorComSucesso() throws Exception
        {
            Pedido pedido = new Pedido();
            pedido.setNumeroControle(123);
            pedido.setCodigoCliente(1);
            pedido.setValor(100d);
            pedido.setQuantidade(10);

            Cliente cliente1 = new Cliente();
            cliente1.setCodigo(1);

            when(clienteService.findAllById(List.of(1)))
                    .thenReturn(List.of(cliente1));

            List<Pedido> pedidos = Arrays.asList(pedido);

            pedidoService.save(pedidos);

            assertEquals(110, pedido.getValor(), 0.001);
        }

        @Test
        @DisplayName("Deve incrementar 5% ao valor com sucesso")
        void deveIncrementar5PorcentoAoValorComSucesso() throws Exception
        {
            Pedido pedido = new Pedido();
            pedido.setNumeroControle(123);
            pedido.setCodigoCliente(1);
            pedido.setValor(100d);
            pedido.setQuantidade(6);

            Cliente cliente1 = new Cliente();
            cliente1.setCodigo(1);

            when(clienteService.findAllById(List.of(1)))
                    .thenReturn(List.of(cliente1));

            List<Pedido> pedidos = Arrays.asList(pedido);

            pedidoService.save(pedidos);

            assertEquals(105, pedido.getValor(), 0.001);
        }

        @Test
        @DisplayName("Não deve incrementar ao valor com sucesso")
        void naoDeveIncrementarAoValorComSucesso() throws Exception
        {
            Pedido pedido = new Pedido();
            pedido.setNumeroControle(123);
            pedido.setCodigoCliente(1);
            pedido.setValor(100d);
            pedido.setQuantidade(5);

            Cliente cliente1 = new Cliente();
            cliente1.setCodigo(1);

            when(clienteService.findAllById(List.of(1)))
                    .thenReturn(List.of(cliente1));

            List<Pedido> pedidos = Arrays.asList(pedido);

            pedidoService.save(pedidos);

            assertEquals(100, pedido.getValor(), 0.001);
        }
    }
}