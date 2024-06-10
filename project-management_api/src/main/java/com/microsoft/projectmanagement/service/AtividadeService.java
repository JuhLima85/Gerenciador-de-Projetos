package com.microsoft.projectmanagement.service;

import com.microsoft.projectmanagement.dto.AtividadeDTO;
import com.microsoft.projectmanagement.exceptions.EntityExistingException;
import com.microsoft.projectmanagement.exceptions.EntityNotFoundException;
import com.microsoft.projectmanagement.entity.Atividade;
import com.microsoft.projectmanagement.entity.Projeto;
import com.microsoft.projectmanagement.repository.AtividadeRepository;
import com.microsoft.projectmanagement.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AtividadeService {

    @Autowired
    private AtividadeRepository atividadeRepository;

    @Autowired
    private ProjetoRepository projetoRepository;

    /**
     * Retorna uma lista de todas as atividades.
     *
     * @return lista de AtividadeDTO
     */
    public List<AtividadeDTO> findAll() {
        return atividadeRepository.findAll().stream()
                .map(AtividadeDTO::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca uma atividade por ID.
     *
     * @param id o ID da atividade a ser buscada
     * @return a AtividadeDTO correspondente, se encontrada
     * @throws EntityNotFoundException se a atividade não for encontrada
     */
    public AtividadeDTO findById(Long id) {
        return atividadeRepository.findById(id)
                .map(AtividadeDTO::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Atividade não encontrada com o ID: " + id));
    }

    /**
     * Salva uma nova atividade em projeto existente.
     *
     * @param atividadeDTO os dados da atividade a ser salva
     * @return a AtividadeDTO salva
     * @throws EntityExistingException se uma atividade com o mesmo ID já existir
     */
    public AtividadeDTO save(AtividadeDTO atividadeDTO) {
        validateAtividadeFields(atividadeDTO);
        Projeto projeto = projetoRepository.findById(atividadeDTO.getProjetoId())
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado com o ID: " + atividadeDTO.getProjetoId()));
        Atividade atividade = atividadeDTO.toEntity();
        atividade.setProjeto(projeto);
        atividade = atividadeRepository.save(atividade);
        return AtividadeDTO.toDTO(atividade);
    }

    /**
     * Salva uma lista de objetos AtividadeDTO como entidades Atividade e as associa a um Projeto específico.
     *
     * @param atividadeDTOs a lista de objetos AtividadeDTO a serem salvos
     * @param projeto a entidade Projeto a ser associada a cada Atividade
     * @return uma lista de entidades Atividade salvas
     */
    public List<Atividade> saveAll(List<AtividadeDTO> atividadeDTOs, Projeto projeto) {
        return atividadeDTOs.stream()
                .map(atividadeDTO -> {
                    Atividade atividade = atividadeDTO.toEntity();
                    atividade.setProjeto(projeto);
                    return atividadeRepository.save(atividade);
                })
                .collect(Collectors.toList());
    }

    /**
     * Atualiza uma atividade existente.
     *
     * @param id           o ID da atividade a ser atualizada
     * @param atividadeDTO os novos dados da atividade
     * @return a AtividadeDTO atualizada
     * @throws EntityNotFoundException se a atividade não for encontrada
     */
    public AtividadeDTO update(Long id, AtividadeDTO atividadeDTO) {
                        validateAtividadeFields(atividadeDTO);
                        Projeto projeto = projetoRepository.findById(atividadeDTO.getProjetoId())
                                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado com o ID: " + atividadeDTO.getProjetoId()));
                        return atividadeRepository.findById(id)
                                .map(existingAtividade -> {
                                    Atividade atividade = atividadeDTO.toEntity();
                                    atividade.setId(id);
                                    atividade.setProjeto(projeto);
                                    atividade = atividadeRepository.save(atividade);
                                    return AtividadeDTO.toDTO(atividade);
                                })
                .orElseThrow(() -> new EntityNotFoundException("Atividade não encontrada com o ID: " + id));
    }

    /**
     * Deleta uma atividade por ID.
     *
     * @param id o ID da atividade a ser deletada
     * @throws EntityNotFoundException se a atividade não for encontrada
     */
    public void delete(Long id) {
        atividadeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Atividade não encontrada com o ID: " + id));
        atividadeRepository.deleteById(id);
    }

    /**
     * Valida os campos obrigatórios da atividade.
     *
     * @param atividadeDTO os dados da atividade a serem validados
     * @throws EntityNotFoundException se algum campo obrigatório estiver vazio
     */
    private void validateAtividadeFields(AtividadeDTO atividadeDTO) {
        if (StringUtils.isEmpty(atividadeDTO.getDescricao())) {
            throw new EntityNotFoundException("O campo 'Descrição' é obrigatório.");
        }
        if (StringUtils.isEmpty(atividadeDTO.getStatus())) {
            throw new EntityNotFoundException("O campo 'Status' é obrigatório.");
        }
        if (StringUtils.isEmpty(atividadeDTO.getPrioridade())) {
            throw new EntityNotFoundException("O campo 'Prioridade' é obrigatório.");
        }
        if (atividadeDTO.getProjetoId() == null) {
            throw new EntityNotFoundException("O campo 'Projeto' é obrigatório.");
        }
    }
}
