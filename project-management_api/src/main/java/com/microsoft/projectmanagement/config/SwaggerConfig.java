package com.microsoft.projectmanagement.config;

import com.microsoft.projectmanagement.dto.AtividadeDTO;
import com.microsoft.projectmanagement.dto.ClienteDTO;
import com.microsoft.projectmanagement.dto.ProjetoDTO;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.Schema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        // Definindo esquemas para ClienteDTO e ProjetoDTO
        Map<String, Schema> schemas = new HashMap<>();

        // Definição do esquema para ClienteDTO
        Schema<ClienteDTO> clienteDTOSchema = new Schema<>();
        clienteDTOSchema.type("object")
                .properties(new HashMap<String, Schema>() {{
                    put("nome", new Schema<>().type("string"));
                    put("email", new Schema<>().type("string"));
                    put("telefone", new Schema<>().type("string"));
                }});

        // Definição do esquema para ProjetoDTO
        Schema<ProjetoDTO> projetoDTOSchema = new Schema<>();
        projetoDTOSchema.type("object")
                .properties(new HashMap<String, Schema>() {{
                    put("nome", new Schema<>().type("string"));
                    put("descricao", new Schema<>().type("string"));
                    put("status", new Schema<>().type("string"));
                    put("clienteId", new Schema<>().type("integer"));
                }});

        // Adicionando o esquema de atividades ao ProjetoDTO
        ArraySchema atividadesArraySchema = new ArraySchema().items(
                new Schema<AtividadeDTO>()
                        .addProperties("descricao", new Schema<>().type("string"))
                        .addProperties("status", new Schema<>().type("string"))
                        .addProperties("prioridade", new Schema<>().type("string"))
        );

        projetoDTOSchema.addProperties("atividades", atividadesArraySchema);

        // Definição do esquema para AtividadeDTO
        Schema<AtividadeDTO> atividadeDTOSchema = new Schema<>();
        atividadeDTOSchema.type("object")
                .properties(new HashMap<String, Schema>() {{
                    put("descricao", new Schema<>().type("string"));
                    put("status", new Schema<>().type("string"));
                    put("prioridade", new Schema<>().type("string"));
                    put("projetoId", new Schema<>().type("integer"));
                }});

        // Adicionando os esquemas ao mapa de componentes
        schemas.put("ClienteDTO", clienteDTOSchema);
        schemas.put("ProjetoDTO", projetoDTOSchema);
        schemas.put("AtividadeDTO", atividadeDTOSchema);

        // Construindo e retornando a configuração do OpenAPI
        return new OpenAPI()
                .components(new Components().schemas(schemas))
                .info(new Info().title("Project Management API").version("v0.0.1-SNAPSHOT"));
    }
}



