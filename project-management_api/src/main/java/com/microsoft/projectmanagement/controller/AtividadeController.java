package com.microsoft.projectmanagement.controller;

import com.microsoft.projectmanagement.dto.AtividadeDTO;
import com.microsoft.projectmanagement.service.AtividadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/atividades")
@Tag(name = "Atividade", description = "Controlador de atividade.")
public class AtividadeController {

    @Autowired
    private AtividadeService atividadeService;

    @Operation(summary = "Criar nova atividade", description = "Cria uma nova atividade em um projeto existente.")
    @PostMapping
    public ResponseEntity<AtividadeDTO> createAtividade(@RequestBody AtividadeDTO atividadeDTO) {
        AtividadeDTO savedAtividade = atividadeService.save(atividadeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAtividade);
    }

}
