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
import com.lms.teste.Models.MaterialTurma;
import com.lms.teste.Repository.MaterialTurmaRepository;
import com.lms.teste.Service.MaterialTurmaService;



public class MaterialTurmaServiceTest {
    @Mock
    private MaterialTurmaRepository turmaRepository;

    @InjectMocks
    private MaterialTurmaService turmaService;

    private MaterialTurma turma;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        turma = new MaterialTurma();
        turma.setIdTurma(1L);
        turma.setNomeMaterial("Material 1");
        turma.setMaterial(new byte[]{1, 2, 3});
    }

    @Test
    public void testSave() {
        when(turmaRepository.save(any(MaterialTurma.class))).thenReturn(turma);

        MaterialTurma savedTurma = turmaService.save(turma);

        assertNotNull(savedTurma);
        assertEquals(turma.getIdTurma(), savedTurma.getIdTurma());
    }

    @Test
    public void testFindAll() {
        List<MaterialTurma> turmas = Arrays.asList(turma);
        when(turmaRepository.findAll()).thenReturn(turmas);

        List<MaterialTurma> foundTurmas = turmaService.findAll();

        assertEquals(1, foundTurmas.size());
        assertEquals(turma.getIdTurma(), foundTurmas.get(0).getIdTurma());
    }

    @Test
    public void testFindById() {
        when(turmaRepository.findById(1L)).thenReturn(Optional.of(turma));

        MaterialTurma foundTurma = turmaService.findById(1L);

        assertNotNull(foundTurma);
        assertEquals(turma.getIdTurma(), foundTurma.getIdTurma());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(turmaRepository).deleteById(1L);

        turmaService.deleteById(1L);

        verify(turmaRepository, times(1)).deleteById(1L);
    }
}
