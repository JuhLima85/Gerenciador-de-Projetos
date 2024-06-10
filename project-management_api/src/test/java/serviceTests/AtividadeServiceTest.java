package serviceTests;

import com.microsoft.projectmanagement.dto.AtividadeDTO;
import com.microsoft.projectmanagement.entity.Atividade;
import com.microsoft.projectmanagement.entity.Projeto;
import com.microsoft.projectmanagement.entity.enums.StatusEnum;
import com.microsoft.projectmanagement.exceptions.EntityNotFoundException;
import com.microsoft.projectmanagement.repository.AtividadeRepository;
import com.microsoft.projectmanagement.repository.ProjetoRepository;
import com.microsoft.projectmanagement.service.AtividadeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AtividadeServiceTest {

    @Mock
    private AtividadeRepository atividadeRepository;

    @Mock
    private ProjetoRepository projetoRepository;

    @InjectMocks
    private AtividadeService atividadeService;

    private AtividadeDTO atividadeDTO;
    private Atividade atividade;
    private Projeto projeto;

    @BeforeEach
    void setUp() {
        projeto = new Projeto();
        projeto.setId(1L);
        projeto.setNome("Projeto Teste");

        atividadeDTO = new AtividadeDTO();
        atividadeDTO.setId(1L);
        atividadeDTO.setDescricao("Atividade Teste");
        atividadeDTO.setStatus(String.valueOf(StatusEnum.EM_ABERTO));
        atividadeDTO.setPrioridade("Alta");
        atividadeDTO.setProjetoId(1L);

        atividade = new Atividade();
        atividade.setId(1L);
        atividade.setDescricao("Atividade Teste");
        atividade.setStatus(String.valueOf(StatusEnum.EM_ABERTO));
        atividade.setPrioridade("Alta");
        atividade.setProjeto(projeto);
    }

    @Test
    void testFindAll() {
        when(atividadeRepository.findAll()).thenReturn(Arrays.asList(atividade));

        List<AtividadeDTO> atividades = atividadeService.findAll();

        assertNotNull(atividades);
        assertEquals(1, atividades.size());
    }

    @Test
    void testFindByIdSuccess() {
        when(atividadeRepository.findById(1L)).thenReturn(Optional.of(atividade));

        AtividadeDTO found = atividadeService.findById(1L);

        assertNotNull(found);
        assertEquals(atividade.getDescricao(), found.getDescricao());
    }

    @Test
    void testFindByIdNotFound() {
        when(atividadeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> atividadeService.findById(1L));
    }

    @Test
    void testSaveSuccess() {
        when(projetoRepository.findById(atividadeDTO.getProjetoId())).thenReturn(Optional.of(projeto));
        when(atividadeRepository.save(any(Atividade.class))).thenReturn(atividade);

        AtividadeDTO saved = atividadeService.save(atividadeDTO);

        assertNotNull(saved);
        assertEquals(atividadeDTO.getDescricao(), saved.getDescricao());
    }

    @Test
    void testSaveProjectNotFound() {
        when(projetoRepository.findById(atividadeDTO.getProjetoId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> atividadeService.save(atividadeDTO));
    }

    @Test
    void testUpdateSuccess() {
        when(projetoRepository.findById(atividadeDTO.getProjetoId())).thenReturn(Optional.of(projeto));
        when(atividadeRepository.findById(1L)).thenReturn(Optional.of(atividade));
        when(atividadeRepository.save(any(Atividade.class))).thenReturn(atividade);

        AtividadeDTO updated = atividadeService.update(1L, atividadeDTO);

        assertNotNull(updated);
        assertEquals(atividadeDTO.getDescricao(), updated.getDescricao());
    }

    @Test
    void testUpdateNotFound() {
        when(projetoRepository.findById(atividadeDTO.getProjetoId())).thenReturn(Optional.of(projeto));
        when(atividadeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> atividadeService.update(1L, atividadeDTO));
    }

    @Test
    void testDeleteSuccess() {
        when(atividadeRepository.findById(1L)).thenReturn(Optional.of(atividade));

        assertDoesNotThrow(() -> atividadeService.delete(1L));
    }

    @Test
    void testDeleteNotFound() {
        when(atividadeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> atividadeService.delete(1L));
    }
}
