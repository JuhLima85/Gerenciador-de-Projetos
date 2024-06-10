package com.microsoft.projectmanagement.config;
import com.microsoft.projectmanagement.dto.AtividadeDTO;
import com.microsoft.projectmanagement.dto.ClienteDTO;
import com.microsoft.projectmanagement.dto.ProjetoDTO;
import com.microsoft.projectmanagement.entity.Atividade;
import com.microsoft.projectmanagement.entity.Cliente;
import com.microsoft.projectmanagement.entity.Projeto;
import com.microsoft.projectmanagement.repository.AtividadeRepository;
import com.microsoft.projectmanagement.repository.ClienteRepository;
import com.microsoft.projectmanagement.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private AtividadeRepository atividadeRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Excluir todas as informações existentes
        atividadeRepository.deleteAll();
        projetoRepository.deleteAll();
        clienteRepository.deleteAll();

        // Inserir clientes
        ClienteDTO cliente1DTO = new ClienteDTO(null, "Cliente 1", "cliente1@example.com", "(00) 1234-5678", null);
        ClienteDTO cliente2DTO = new ClienteDTO(null, "Cliente 2", "cliente2@example.com", "(00) 9876-5432", null);
        ClienteDTO cliente3DTO = new ClienteDTO(null, "Cliente 3", "cliente3@example.com", "(00) 5555-5555", null);

        Cliente cliente1 = cliente1DTO.toEntity();
        Cliente cliente2 = cliente2DTO.toEntity();
        Cliente cliente3 = cliente3DTO.toEntity();

        clienteRepository.save(cliente1);
        clienteRepository.save(cliente2);
        clienteRepository.save(cliente3);

        // Inserir projetos
        ProjetoDTO projeto1DTO = new ProjetoDTO(null, "Projeto Exemplo 1", "Descrição do Projeto Exemplo 1", "Em Aberto", null,null);
        ProjetoDTO projeto2DTO = new ProjetoDTO(null, "Projeto Exemplo 2", "Descrição do Projeto Exemplo 2", "Em Aberto", null, null);

        Projeto projeto1 = projeto1DTO.toEntity();
        Projeto projeto2 = projeto2DTO.toEntity();
        projeto1.setCliente(cliente1);
        projeto2.setCliente(cliente2);

        projetoRepository.save(projeto1);
        projetoRepository.save(projeto2);

        // Inserir atividades
        AtividadeDTO atividade1DTO = new AtividadeDTO(null, "Atividade 1", "Pendente", "Alta", null);
        AtividadeDTO atividade2DTO = new AtividadeDTO(null, "Atividade 2", "Em Progresso", "Média", null);
        AtividadeDTO atividade3DTO = new AtividadeDTO(null, "Atividade 1", "Pendente", "Alta", null);
        AtividadeDTO atividade4DTO = new AtividadeDTO(null, "Atividade 2", "Em Progresso", "Média", null);

        Atividade atividade1 = atividade1DTO.toEntity();
        Atividade atividade2 = atividade2DTO.toEntity();
        Atividade atividade3 = atividade3DTO.toEntity();
        Atividade atividade4 = atividade4DTO.toEntity();
        atividade1.setProjeto(projeto1);
        atividade2.setProjeto(projeto1);
        atividade3.setProjeto(projeto2);
        atividade4.setProjeto(projeto2);

        atividadeRepository.save(atividade1);
        atividadeRepository.save(atividade2);
        atividadeRepository.save(atividade3);
        atividadeRepository.save(atividade4);
    }
}
