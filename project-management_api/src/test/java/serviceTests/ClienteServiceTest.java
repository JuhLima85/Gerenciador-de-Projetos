package serviceTests;

import com.microsoft.projectmanagement.dto.ClienteDTO;
import com.microsoft.projectmanagement.entity.Cliente;
import com.microsoft.projectmanagement.exceptions.EntityExistingException;
import com.microsoft.projectmanagement.exceptions.EntityNotFoundException;
import com.microsoft.projectmanagement.repository.ClienteRepository;
import com.microsoft.projectmanagement.service.ClienteService;
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
public class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    private Cliente cliente;
    private ClienteDTO clienteDTO;

    @BeforeEach
    void setUp() {
        cliente = new Cliente(1L, "John Doe", "john.doe@example.com", "123456789", null);
        clienteDTO = new ClienteDTO();
        clienteDTO.setId(1L);
        clienteDTO.setNome("John Doe");
        clienteDTO.setEmail("john.doe@example.com");
        clienteDTO.setTelefone("123456789");
    }

    @Test
    void testFindAll() {
        when(clienteRepository.findAll()).thenReturn(Collections.singletonList(cliente));

        assertEquals(1, clienteService.findAll().size());
    }

    @Test
    void testFindByIdSuccess() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        ClienteDTO found = clienteService.findById(1L);

        assertNotNull(found);
        assertEquals(cliente.getId(), found.getId());
    }

    @Test
    void testFindByIdNotFound() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> clienteService.findById(1L));
    }

    @Test
    void testSaveSuccess() {
        when(clienteRepository.findByEmail(clienteDTO.getEmail())).thenReturn(Optional.empty());
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        ClienteDTO saved = clienteService.save(clienteDTO);

        assertNotNull(saved);
        assertEquals(clienteDTO.getEmail(), saved.getEmail());
    }

    @Test
    void testSaveExistingEmail() {
        when(clienteRepository.findByEmail(clienteDTO.getEmail())).thenReturn(Optional.of(cliente));

        assertThrows(EntityExistingException.class, () -> clienteService.save(clienteDTO));
    }

    @Test
    void testUpdateSuccess() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        ClienteDTO updated = clienteService.update(1L, clienteDTO);

        assertNotNull(updated);
        assertEquals(clienteDTO.getNome(), updated.getNome());
    }

    @Test
    void testUpdateNotFound() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> clienteService.update(1L, clienteDTO));
    }

    @Test
    void testDeleteSuccess() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        clienteService.delete(1L);

        verify(clienteRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteNotFound() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> clienteService.delete(1L));
    }
}

