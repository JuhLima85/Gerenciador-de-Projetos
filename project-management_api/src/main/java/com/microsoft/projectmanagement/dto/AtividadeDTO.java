package com.microsoft.projectmanagement.dto;

import com.microsoft.projectmanagement.entity.Atividade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que guarda os dados de uma atividade
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtividadeDTO {

    private Long id;
    private String descricao;
    private String status;
    private String prioridade;
    private Long projetoId;

    /**
     * Carrega o DTO com dados da entidade Atividade
     */
    public static AtividadeDTO toDTO(Atividade atividade) {
        if (atividade == null) {
            return null;
        }
        AtividadeDTO dto = new AtividadeDTO();
        dto.setId(atividade.getId());
        dto.setDescricao(atividade.getDescricao());
        dto.setStatus(atividade.getStatus());
        dto.setPrioridade(atividade.getPrioridade());
        if(atividade.getProjeto() != null){
            dto.setProjetoId(atividade.getProjeto().getId());
        }

        return dto;
    }

    /**
     * Converte o DTO para a entidade Atividade
     */
    public Atividade toEntity() {
        Atividade atividade = new Atividade();
        atividade.setId(this.id);
        atividade.setDescricao(this.descricao);
        atividade.setStatus(this.status);
        atividade.setPrioridade(this.prioridade);
        return atividade;
    }

}
