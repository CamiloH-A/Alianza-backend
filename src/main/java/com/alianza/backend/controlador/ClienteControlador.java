package com.alianza.backend.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alianza.backend.repositorio.ClienteRepositorio;
import com.alianza.backend.excepciones.ResourcesNotFoundException;
import com.alianza.backend.modelo.Cliente;

/**
 * Controlador que contiene los metodos de consumo de la API
 * 
 * @author achernandeza
 *
 */
@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200")
public class ClienteControlador {
	
	@Autowired
	private ClienteRepositorio repositorio;
	
	
	/**
	 * Metodo que lista todos los clientes de la base de datos
	 * @return
	 */
	@GetMapping("/clientes")
	public List<Cliente> listarClientes(){
		return repositorio.findAll();
	}
	
	/**
	 * Metodo que guarda un cliente que se añade desde la vista.
	 * @return
	 */
	@PostMapping("/clientes")
	public Cliente agregarCliente(@RequestBody Cliente cliente) {
		return repositorio.save(cliente);
	}

	/**
	 * Metodo que elimina un cliente con el id recibido.
	 * @param id
	 */
	@DeleteMapping("/clientes/{sharedKey}")
	public void eliminarCliente(@PathVariable String sharedKey) {
	    Cliente cliente = repositorio.findBySharedKey(sharedKey);
	    if (cliente != null) {
	        repositorio.delete(cliente);
	    } else {
	        throw new ResourcesNotFoundException("No se encontró un cliente con la Shared Key: " + sharedKey);
	    }
	}

	
	/** Metodo que busca un cliente por el id
	 * @param id
	 * @return
	 */
	@GetMapping("/clientes/{id}")
	public ResponseEntity<Cliente> obtenerClientePorId(@PathVariable Long id){
		Cliente cliente = repositorio.findById(id).orElseThrow(() -> new ResourcesNotFoundException(("No existe el cliente con el ID: ")+ id));
		return ResponseEntity.ok(cliente);
	}
	
	/**
	 * Metodo que consulta un cliente por el campo sharedKey
	 * @param sharedKey
	 * @param valor
	 * @return
	 */
	@GetMapping("/clientes/busqueda")
	public List<Cliente> buscarClientesPorSharedKey(@RequestParam("sharedKey") String sharedKey, @RequestParam("valor") String valor) {
		if (sharedKey.equals("sharedKey")) {
	        return repositorio.findBySharedKeyList(valor);
	    }
	    return null;
	}

	
	/**
	 * @param id
	 * @param clientSummary
	 * @return
	 */
	@PutMapping("/clientes/{sharedKey}")
	public ResponseEntity<Cliente> editarClientePorSharedKey(@PathVariable String sharedKey, @RequestBody Cliente clienteModificado) {
	    Cliente cliente = repositorio.findBySharedKey(sharedKey);
	    if (cliente != null) {
	    	cliente.setSharedKey(clienteModificado.getBussinesId());
	        cliente.setBussinesId(clienteModificado.getBussinesId());
	        cliente.setEmail(clienteModificado.getEmail());
	        cliente.setPhone(clienteModificado.getPhone());
	        cliente.setDateAdded(clienteModificado.getDateAdded());
	        Cliente clienteActualizado = repositorio.save(cliente);
	        return ResponseEntity.ok(clienteActualizado);
	    }
	    return ResponseEntity.notFound().build();
	}
}
