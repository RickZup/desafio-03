package com.catalisa.zupstore.service;

import com.catalisa.zupstore.model.CategoriaModel;
import com.catalisa.zupstore.repository.CategoriaRepository;
import com.catalisa.zupstore.service.CategoriaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBuscarTodos() {
        List<CategoriaModel> categorias = new ArrayList<>();
        categorias.add(new CategoriaModel(1L, "Categoria 1"));
        categorias.add(new CategoriaModel(2L, "Categoria 2"));

        when(categoriaRepository.findAll()).thenReturn(categorias);

        List<CategoriaModel> result = categoriaService.buscarTodos();

        assertEquals(categorias, result);
    }

    @Test
    public void testBuscarPorId() {
        Long categoryId = 1L;
        CategoriaModel categoria = new CategoriaModel(categoryId, "Categoria Teste");

        when(categoriaRepository.findById(categoryId)).thenReturn(Optional.of(categoria));

        Optional<CategoriaModel> result = categoriaService.buscarPorId(categoryId);

        assertTrue(result.isPresent());
        assertEquals(categoria, result.get());
    }

    @Test
    public void testCadastrar() {
        CategoriaModel categoria = new CategoriaModel(1L, "Nova Categoria");

        when(categoriaRepository.save(any(CategoriaModel.class))).thenReturn(categoria);

        CategoriaModel result = categoriaService.cadastrar(categoria);

        assertEquals(categoria, result);
    }

    @Test
    public void testAlterar() {
        Long categoryId = 1L;
        CategoriaModel categoriaOriginal = new CategoriaModel(categoryId, "Categoria Original");
        CategoriaModel categoriaAtualizada = new CategoriaModel(categoryId, "Categoria Atualizada");

        when(categoriaRepository.findById(categoryId)).thenReturn(Optional.of(categoriaOriginal));
        when(categoriaRepository.save(any(CategoriaModel.class))).thenReturn(categoriaAtualizada);

        CategoriaModel result = categoriaService.alterar(categoryId, categoriaAtualizada);

        assertEquals(categoriaAtualizada, result);
    }

    @Test
    public void testDeletar() {
        Long categoryId = 1L;

        categoriaService.deletar(categoryId);

        verify(categoriaRepository, times(1)).deleteById(categoryId);
    }
}

