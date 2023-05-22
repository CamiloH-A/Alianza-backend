package com.alianza.backend;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.alianza.backend.controlador.ClienteControlador;
import com.alianza.backend.excepciones.ResourcesNotFoundException;
import com.alianza.backend.modelo.Cliente;
import com.alianza.backend.repositorio.ClienteRepositorio;

@SpringBootTest
public class ClienteControladorTests {

    @Mock
    private ClienteRepositorio repositorio;

    @InjectMocks
    private ClienteControlador controlador;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        clientes.add(new Cliente(1L, "sharedKey1", "bussinesId1", "cliente1@example.com", "1234567890", new Date()));
        clientes.add(new Cliente(2L, "sharedKey2", "bussinesId2", "cliente2@example.com", "9876543210", new Date()));

        when(repositorio.findAll()).thenReturn(clientes);
        
        List<Cliente> resultado = controlador.listarClientes();
       
        assertThat(resultado).hasSize(2);
        assertThat(resultado.get(0).getBussinesId()).isEqualTo("bussinesId1");
        assertThat(resultado.get(1).getBussinesId()).isEqualTo("bussinesId2");
    }
    
    @Test
    public void testAgregarCliente() {
        Cliente nuevoCliente = new Cliente(null, "sharedKey3", "bussinesId3", "cliente3@example.com", "9876543210", new Date());

        when(repositorio.save(any(Cliente.class))).thenReturn(nuevoCliente);

        Cliente clienteAgregado = controlador.agregarCliente(nuevoCliente);

        assertThat(clienteAgregado).isNotNull();
        assertThat(clienteAgregado.getSharedKey()).isEqualTo("sharedKey3");
        assertThat(clienteAgregado.getBussinesId()).isEqualTo("bussinesId3");
        assertThat(clienteAgregado.getEmail()).isEqualTo("cliente3@example.com");
    }
    
    @Test
    public void testEliminarClienteExistente() {
        String sharedKey = "sharedKey1";

        Cliente clienteExistente = new Cliente(1L, sharedKey, "bussinesId1", "cliente1@example.com", "1234567890", new Date());
        when(repositorio.findBySharedKey(sharedKey)).thenReturn(clienteExistente);

        controlador.eliminarCliente(sharedKey);

        verify(repositorio, times(1)).delete(clienteExistente);
    }
    
    @Test
    public void testObtenerClientePorId() {
        Long id = 1L;

        Cliente clienteExistente = new Cliente(id, "sharedKey1", "bussinesId1", "cliente1@example.com", "1234567890", new Date());
        when(repositorio.findById(id)).thenReturn(Optional.of(clienteExistente));

        ResponseEntity<Cliente> response = controlador.obtenerClientePorId(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clienteExistente, response.getBody());
    }
    
    @Test
    public void testBuscarClientesPorSharedKey() {
        String sharedKey = "sharedKey";
        String valor = "valor1";

        List<Cliente> clientesEncontrados = Arrays.asList(
            new Cliente(1L, sharedKey, "bussinesId1", "cliente1@example.com", "1234567890", new Date()),
            new Cliente(2L, sharedKey, "bussinesId2", "cliente2@example.com", "0987654321", new Date())
        );
        when(repositorio.findBySharedKeyList(valor)).thenReturn(clientesEncontrados);

        List<Cliente> resultado = controlador.buscarClientesPorSharedKey(sharedKey, valor);

        assertEquals(clientesEncontrados.size(), resultado.size());
        assertTrue(resultado.containsAll(clientesEncontrados));
    }
    
    @Test
    public void testEditarClientePorSharedKey() {
        String sharedKey = "sharedKey1";
        Cliente clienteModificado = new Cliente();
        clienteModificado.setBussinesId("nuevoBussinesId");
        clienteModificado.setEmail("nuevoCliente@example.com");
        clienteModificado.setPhone("9876543210");
        clienteModificado.setDateAdded(new Date());

        Cliente clienteExistente = new Cliente(1L, sharedKey, "bussinesId1", "cliente1@example.com", "1234567890", new Date());
        when(repositorio.findBySharedKey(sharedKey)).thenReturn(clienteExistente);
        when(repositorio.save(clienteExistente)).thenReturn(clienteExistente);

        ResponseEntity<Cliente> respuesta = controlador.editarClientePorSharedKey(sharedKey, clienteModificado);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(clienteExistente, respuesta.getBody());
        assertEquals(clienteModificado.getBussinesId(), clienteExistente.getBussinesId());
        assertEquals(clienteModificado.getEmail(), clienteExistente.getEmail());
        assertEquals(clienteModificado.getPhone(), clienteExistente.getPhone());
        assertEquals(clienteModificado.getDateAdded(), clienteExistente.getDateAdded());
    }
}
