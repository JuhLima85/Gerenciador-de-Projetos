package com.microsoft.projectmanagement.service;

import com.microsoft.projectmanagement.dto.ClienteDTO;
import com.microsoft.projectmanagement.exceptions.EntityExistingException;
import com.microsoft.projectmanagement.exceptions.EntityNotFoundException;
import com.microsoft.projectmanagement.entity.Cliente;
import com.microsoft.projectmanagement.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    /**
     * Retorna uma lista de todos os clientes.
     *
     * @return uma lista de ClienteDTO
     */
    public List<ClienteDTO> findAll() {
        return clienteRepository.findAll().stream()
                .map(ClienteDTO::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca um cliente pelo seu ID.
     *
     * @param id o ID do cliente a ser buscado
     * @return ClienteDTO representando o cliente encontrado
     * @throws EntityNotFoundException se o cliente não for encontrado
     */
    public ClienteDTO findById(Long id) {
        return clienteRepository.findById(id)
                .map(ClienteDTO::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com o ID: " + id));
    }

    /**
     * Salva um novo cliente.
     *
     * @param clienteDTO os dados do cliente a ser salvo
     * @return ClienteDTO representando o cliente salvo
     */
    public ClienteDTO save(ClienteDTO clienteDTO) {
        clienteRepository.findByEmail(clienteDTO.getEmail())
                .ifPresent(existingCliente -> {
                    throw new EntityExistingException("Já existe um cliente cadastrado com o e-mail: " + clienteDTO.getEmail());
                });

        validateClienteFields(clienteDTO);
        Cliente cliente = clienteDTO.toEntity();
        cliente = clienteRepository.save(cliente);
        return ClienteDTO.toDTO(cliente);
    }

    /**
     * Atualiza um cliente existente.
     *
     * @param id o ID do cliente a ser atualizado
     * @param clienteDTO os novos dados do cliente
     * @return ClienteDTO representando o cliente atualizado
     * @throws EntityNotFoundException se o cliente não for encontrado
     */
    public ClienteDTO update(Long id, ClienteDTO clienteDTO) {
        validateClienteFields(clienteDTO);
        return clienteRepository.findById(id)
                .map(existingCliente -> {
                    Cliente cliente = clienteDTO.toEntity();
                    cliente.setId(id);
                    cliente = clienteRepository.save(cliente);
                    return ClienteDTO.toDTO(cliente);
                })
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com o ID: " + id));
    }

    /**
     * Deleta um cliente pelo seu ID.
     *
     * @param id o ID do cliente a ser deletado
     * @throws EntityNotFoundException se o cliente não for encontrado
     */
    public void delete(Long id) {
        clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com o ID: " + id));
        clienteRepository.deleteById(id);
    }

    /**
     * Valida os campos obrigatórios do ClienteDTO.
     *
     * @param clienteDTO os dados do cliente a serem validados
     * @throws EntityNotFoundException se algum campo obrigatório estiver vazio
     */
    private void validateClienteFields(ClienteDTO clienteDTO) {
        if (StringUtils.isEmpty(clienteDTO.getNome())) {
            throw new EntityNotFoundException("O campo 'Nome' é obrigatório.");
        }
        if (StringUtils.isEmpty(clienteDTO.getEmail())) {
            throw new EntityNotFoundException("O campo 'Email' é obrigatório.");
        }
        if (StringUtils.isEmpty(clienteDTO.getTelefone())) {
            throw new EntityNotFoundException("O campo 'Telefone' é obrigatório.");
        }
    }
}
