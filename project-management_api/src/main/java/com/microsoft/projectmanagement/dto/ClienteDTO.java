package com.microsoft.projectmanagement.dto;

import com.microsoft.projectmanagement.entity.Cliente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO que guarda os dados de um cliente
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {

    private Long id;
    private String nome;
    private String email;
    private String telefone;

    private List<ProjetoDTO> projetos;

    /**
     * Carrega o DTO com dados da entidade Cliente
     */
    public static ClienteDTO toDTO(Cliente cliente) {
        if (cliente == null) {
            return null;
        }
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setNome(cliente.getNome());
        dto.setEmail(cliente.getEmail());
        dto.setTelefone(cliente.getTelefone());

        if (cliente.getProjetos() != null) {
            dto.setProjetos(cliente.getProjetos().stream().map(ProjetoDTO::toDTO).collect(Collectors.toList()));
        }

        return dto;
    }


    /**
     * Converte o DTO para a entidade Cliente
     */
    public Cliente toEntity() {
        Cliente cliente = new Cliente();
        cliente.setId(this.id);
        cliente.setNome(this.nome);
        cliente.setEmail(this.email);
        cliente.setTelefone(this.telefone);

        if (this.projetos != null) {
            cliente.setProjetos(this.projetos.stream().map(ProjetoDTO::toEntity).collect(Collectors.toList()));
        }

        return cliente;
    }
}
