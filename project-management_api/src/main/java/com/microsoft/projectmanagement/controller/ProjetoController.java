package com.microsoft.projectmanagement.controller;

import com.microsoft.projectmanagement.dto.ProjetoDTO;
import com.microsoft.projectmanagement.service.ProjetoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projetos")
@Tag(name = "Projeto", description = "Controlador de projeto.")
public class ProjetoController {

    @Autowired
    private ProjetoService projetoService;

    @Operation(summary = "Obter todos os projetos", description = "Retorna uma lista de todos os projetos")
    @GetMapping
    public ResponseEntity<List<ProjetoDTO>> getAllProjetos() {
        List<ProjetoDTO> projetos = projetoService.findAll();
        return ResponseEntity.ok(projetos);
    }

    @Operation(summary = "Obter projeto por ID", description = "Retorna um projeto pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProjetoDTO> getProjetoById(@PathVariable Long id) {
        return ResponseEntity.ok(projetoService.findById(id));
    }

    @Operation(summary = "Obter projetos em aberto", description = "Retorna uma lista de projetos em aberto")
    @GetMapping("/em-aberto")
    public ResponseEntity<List<ProjetoDTO>> getProjetosEmAberto() {
        List<ProjetoDTO> projetos = projetoService.findProjetosEmAberto();
        return ResponseEntity.ok(projetos);
    }

    @Operation(summary = "Criar novo projeto", description = "Cria um novo projeto. As opções válidas para o status do projeto são: 'Em Aberto', 'Concluído' e 'Cancelado'.")
    @PostMapping
    public ResponseEntity<ProjetoDTO> createProjeto(@RequestBody ProjetoDTO projetoDTO) {
        ProjetoDTO savedProjeto = projetoService.save(projetoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProjeto);
    }

    @Operation(summary = "Atualizar projeto", description = "Atualiza um projeto existente pelo ID")
    @PutMapping("/{id}")
    public ResponseEntity<ProjetoDTO> updateProjeto(@PathVariable Long id, @RequestBody ProjetoDTO projetoDTO) {
        return ResponseEntity.ok(projetoService.update(id, projetoDTO));
    }

    @Operation(summary = "Deletar projeto", description = "Deleta um projeto existente pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProjeto(@PathVariable Long id) {
        projetoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
