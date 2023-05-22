package com.alianza.backend.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alianza.backend.modelo.Cliente;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {
	
	@Query("SELECT c FROM Cliente c WHERE c.sharedKey = :sharedKey")
    List<Cliente> findBySharedKeyList(@Param("sharedKey") String sharedKey);
	
	Cliente findBySharedKey(String sharedKey);

}
