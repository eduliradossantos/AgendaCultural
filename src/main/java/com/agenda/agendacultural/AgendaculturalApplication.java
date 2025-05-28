package com.agenda.agendacultural;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Agenda Cultural API", version="1", description="API para gerenciamento de eventos culturais, categorias, comentários e favoritos."))
public class AgendaculturalApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgendaculturalApplication.class, args);
	}

}
