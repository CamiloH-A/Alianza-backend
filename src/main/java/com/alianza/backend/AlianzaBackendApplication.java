package com.alianza.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@SpringBootApplication
public class AlianzaBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlianzaBackendApplication.class, args);
	}

}
