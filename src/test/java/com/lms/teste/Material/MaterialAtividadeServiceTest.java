package com.lms.teste.Material;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.lms.teste.Models.MaterialAtividade;
import com.lms.teste.Repository.MaterialAtividadeRepository;
import com.lms.teste.Service.MaterialAtividadeService;

public class MaterialAtividadeServiceTest {

    @Mock
    private MaterialAtividadeRepository atividadeRepository;

    @InjectMocks
    private MaterialAtividadeService atividadeService;

    private MaterialAtividade atividade;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        atividade = new MaterialAtividade();
        atividade.setIdAtividade(1L);
        atividade.setNomeMaterial("Material 1");
        atividade.setMaterial(new byte[] { 1, 2, 3 });
    }

    @Test
    public void testSave() {
        when(atividadeRepository.save(any(MaterialAtividade.class))).thenReturn(atividade);

        MaterialAtividade savedAtividade = atividadeService.save(atividade);

        assertNotNull(savedAtividade);
        assertEquals(atividade.getIdAtividade(), savedAtividade.getIdAtividade());
    }

    @Test
    public void testFindAll() {
        List<MaterialAtividade> atividades = Arrays.asList(atividade);
        when(atividadeRepository.findAll()).thenReturn(atividades);

        List<MaterialAtividade> foundAtividades = atividadeService.findAll();

        assertEquals(1, foundAtividades.size());
        assertEquals(atividade.getIdAtividade(), foundAtividades.get(0).getIdAtividade());
    }

    @Test
    public void testFindById() {
        when(atividadeRepository.findById(1L)).thenReturn(Optional.of(atividade));

        MaterialAtividade foundAtividade = atividadeService.findById(1L);

        assertNotNull(foundAtividade);
        assertEquals(atividade.getIdAtividade(), foundAtividade.getIdAtividade());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(atividadeRepository).deleteById(1L);

        atividadeService.deleteById(1L);

        verify(atividadeRepository, times(1)).deleteById(1L);
    }
}
