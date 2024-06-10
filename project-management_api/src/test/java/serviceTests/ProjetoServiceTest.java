package serviceTests;

import com.microsoft.projectmanagement.dto.ProjetoDTO;
import com.microsoft.projectmanagement.entity.Cliente;
import com.microsoft.projectmanagement.entity.Projeto;
import com.microsoft.projectmanagement.entity.enums.StatusEnum;
import com.microsoft.projectmanagement.exceptions.EntityNotFoundException;
import com.microsoft.projectmanagement.repository.AtividadeRepository;
import com.microsoft.projectmanagement.repository.ClienteRepository;
import com.microsoft.projectmanagement.repository.ProjetoRepository;
import com.microsoft.projectmanagement.service.AtividadeService;
import com.microsoft.projectmanagement.service.ProjetoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjetoServiceTest {

    @InjectMocks
    private ProjetoService projetoService;

    @Mock
    private ProjetoRepository projetoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private AtividadeRepository atividadeRepository;

    @Mock
    private AtividadeService atividadeService;

    private Projeto projeto;
    private ProjetoDTO projetoDTO;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente(1L, "John Doe", "john.doe@example.com", "123456789", null);
        projeto = new Projeto(1L, "Projeto Teste", "Descrição do Projeto", StatusEnum.EM_ABERTO, cliente, Collections.emptyList());
        projetoDTO = new ProjetoDTO();
        projetoDTO.setId(1L);
        projetoDTO.setNome("Projeto Atividade");
        projetoDTO.setDescricao("Descrição do projeto atividade");
        projetoDTO.setStatus(String.valueOf(StatusEnum.EM_ABERTO));
        projetoDTO.setClienteId(1L);
        projetoDTO.setAtividades(Collections.emptyList());
    }

    @Test
    void testFindAll() {
        when(projetoRepository.findAll()).thenReturn(Collections.singletonList(projeto));

        assertEquals(1, projetoService.findAll().size());
    }

    @Test
    void testFindByIdSuccess() {
        when(projetoRepository.findById(1L)).thenReturn(Optional.of(projeto));

        ProjetoDTO found = projetoService.findById(1L);

        assertNotNull(found);
        assertEquals(projeto.getId(), found.getId());
    }

    @Test
    void testFindByIdNotFound() {
        when(projetoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> projetoService.findById(1L));
    }

    @Test
    void testSaveClienteNotFound() {
        projetoDTO.setStatus("EM_ABERTO");
        when(clienteRepository.findById(projetoDTO.getClienteId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> projetoService.save(projetoDTO));
    }

    @Test
    void testDeleteSuccess() {
        when(projetoRepository.findById(1L)).thenReturn(Optional.of(projeto));

        projetoService.delete(1L);

        verify(projetoRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteNotFound() {
        when(projetoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> projetoService.delete(1L));
    }

    @Test
    void testFindProjetosEmAberto() {
        when(projetoRepository.findByStatus(StatusEnum.EM_ABERTO)).thenReturn(Collections.singletonList(projeto));

        assertEquals(1, projetoService.findProjetosEmAberto().size());
    }
}

