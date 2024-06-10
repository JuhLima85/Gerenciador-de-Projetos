package com.microsoft.projectmanagement.dto;

import com.microsoft.projectmanagement.entity.Projeto;
import com.microsoft.projectmanagement.entity.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO que guarda os dados de um projeto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjetoDTO {

    private Long id;
    private String nome;
    private String descricao;
    private String status;
    private Long clienteId;
    private List<AtividadeDTO> atividades;

    /**
     * Carrega o DTO com dados da entidade Projeto
     */
    public static ProjetoDTO toDTO(Projeto projeto) {
        if (projeto == null) {
            return null;
        }
        ProjetoDTO dto = new ProjetoDTO();
        dto.setId(projeto.getId());
        dto.setNome(projeto.getNome());
        dto.setDescricao(projeto.getDescricao());
        dto.setStatus(projeto.getStatus().toString());
        dto.setClienteId(projeto.getCliente().getId());

        if(projeto.getAtividades() !=null){
            dto.setAtividades(projeto.getAtividades().stream().map(AtividadeDTO::toDTO).collect(Collectors.toList()));
        }

        return dto;
    }

    /**
     * Converte o DTO para a entidade Projeto
     */
    public Projeto toEntity() {
        Projeto projeto = new Projeto();
        projeto.setId(this.id);
        projeto.setNome(this.nome);
        projeto.setDescricao(this.descricao);
        projeto.setStatus(StatusEnum.fromString(this.status));

        if(this.atividades != null){
            projeto.setAtividades(this.atividades.stream().map(AtividadeDTO::toEntity).collect(Collectors.toList()));
        }

        return projeto;
    }
}
