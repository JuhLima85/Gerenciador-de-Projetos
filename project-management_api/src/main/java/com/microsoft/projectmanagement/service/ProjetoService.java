package com.microsoft.projectmanagement.service;

import com.microsoft.projectmanagement.dto.ProjetoDTO;
import com.microsoft.projectmanagement.exceptions.EntityNotFoundException;
import com.microsoft.projectmanagement.entity.Atividade;
import com.microsoft.projectmanagement.entity.Cliente;
import com.microsoft.projectmanagement.entity.Projeto;
import com.microsoft.projectmanagement.entity.enums.StatusEnum;
import com.microsoft.projectmanagement.exceptions.InvalidStatusException;
import com.microsoft.projectmanagement.repository.AtividadeRepository;
import com.microsoft.projectmanagement.repository.ClienteRepository;
import com.microsoft.projectmanagement.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjetoService {

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private AtividadeRepository atividadeRepository;

    @Autowired
    private AtividadeService atividadeService;

    /**
     * Retorna uma lista de todos os projetos.
     *
     * @return lista de ProjetoDTO
     */
    public List<ProjetoDTO> findAll() {
        return projetoRepository.findAll().stream()
                .map(ProjetoDTO::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca um projeto por ID.
     *
     * @param id o ID do projeto a ser buscado
     * @return o ProjetoDTO correspondente, se encontrado
     * @throws EntityNotFoundException se o projeto não for encontrado
     */
    public ProjetoDTO findById(Long id) {
        return projetoRepository.findById(id)
                .map(ProjetoDTO::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado com o ID: " + id));
    }

    /**
     * Salva um novo projeto.
     *
     * @param projetoDTO os dados do projeto a ser salvo
     * @return o ProjetoDTO salvo
     * @throws EntityNotFoundException se o cliente com o ID informado não existir
     */
        public ProjetoDTO save(ProjetoDTO projetoDTO) {
            validateProjetoFields(projetoDTO);
            if (!isValidStatus(projetoDTO.getStatus())) {
                throw new InvalidStatusException("Status inválido: " + projetoDTO.getStatus() + ". Opções válidas: EM_ABERTO, CONCLUIDO, CANCELADO");
            }

            Cliente cliente = clienteRepository.findById(projetoDTO.getClienteId())
                    .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com o ID: " + projetoDTO.getClienteId()));

            Projeto projeto = projetoDTO.toEntity();
            projeto.setCliente(cliente);
            Projeto savedProjeto = projetoRepository.save(projeto);

            if (projetoDTO.getAtividades() != null) {
                List<Atividade> savedAtividades = atividadeService.saveAll(projetoDTO.getAtividades(), savedProjeto);
                savedProjeto.setAtividades(savedAtividades);
            }

            savedProjeto = projetoRepository.findById(savedProjeto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado"));

            return ProjetoDTO.toDTO(savedProjeto);
        }

    /**
     * Atualiza um projeto existente.
     *
     * @param id         o ID do projeto a ser atualizado
     * @param projetoDTO os novos dados do projeto
     * @return o ProjetoDTO atualizado
     * @throws EntityNotFoundException se o projeto não for encontrado
     */
    public ProjetoDTO update(Long id, ProjetoDTO projetoDTO) {
        validateProjetoFields(projetoDTO);
        Cliente cliente = clienteRepository.findById(projetoDTO.getClienteId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com o ID: " + projetoDTO.getClienteId()));

        return projetoRepository.findById(id)
                .map(existingProjeto -> {
                    Projeto projeto = projetoDTO.toEntity();
                    projeto.setId(id);
                    projeto.setCliente(cliente); // Setar o cliente antes de salvar
                    projeto = projetoRepository.save(projeto);
                    return ProjetoDTO.toDTO(projeto);
                })
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado com o ID: " + id));
    }

    /**
     * Deleta um projeto por ID.
     *
     * @param id o ID do projeto a ser deletado
     * @throws EntityNotFoundException se o projeto não for encontrado
     */
    public void delete(Long id) {
        projetoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado com o ID: " + id));
        projetoRepository.deleteById(id);
    }

    /**
     * Retorna uma lista de projetos que estão em aberto.
     *
     * @return lista de ProjetoDTO para projetos em aberto
     */
    public List<ProjetoDTO> findProjetosEmAberto() {
        return projetoRepository.findByStatus(StatusEnum.EM_ABERTO).stream()
                .map(ProjetoDTO::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Valida os campos obrigatórios do projeto.
     *
     * @param projetoDTO os dados do projeto a serem validados
     * @throws EntityNotFoundException se algum campo obrigatório estiver vazio
     */
    private void validateProjetoFields(ProjetoDTO projetoDTO) {
        if (StringUtils.isEmpty(projetoDTO.getNome())) {
            throw new EntityNotFoundException("O campo 'Nome' é obrigatório.");
        }
        if (StringUtils.isEmpty(projetoDTO.getDescricao())) {
            throw new EntityNotFoundException("O campo 'Descrição' é obrigatório.");
        }
        if (StringUtils.isEmpty(projetoDTO.getStatus())) {
            throw new EntityNotFoundException("O campo 'Status' é obrigatório.");
        }
        if (projetoDTO.getClienteId() == null) {
            throw new EntityNotFoundException("O campo 'Cliente' é obrigatório.");
        }
    }

    /**
     * Verifica se o status fornecido é válido de acordo com os valores do enum StatusEnum.
     *
     * @param status O status a ser verificado
     * @return true se o status é válido, false caso contrário
     */
    private boolean isValidStatus(String status) {
        for (StatusEnum statusEnum : StatusEnum.values()) {
            if (statusEnum.name().equalsIgnoreCase(status)) {
                return true;
            }
        }
        return false;
    }

}
