package com.catalisa.zupstore.controller;

import com.catalisa.zupstore.model.CategoriaModel;
import com.catalisa.zupstore.model.ProdutoModel;
import com.catalisa.zupstore.service.CategoriaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CategoriaController.class)
public class CategoraControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoriaController categoriaController;

    @MockBean
    CategoriaModel categoriaModel;

    @MockBean
    private CategoriaService categoriaService;

    @Test
    public void testBuscarTodasCategorias() throws Exception {

        CategoriaModel categoria1 = new CategoriaModel();
        categoria1.setId(1L);
        categoria1.setNome("Eletrônicos");

        CategoriaModel categoria2 = new CategoriaModel();
        categoria2.setId(2L);
        categoria2.setNome("Roupas");

        List<CategoriaModel> listaDeCategorias = new ArrayList<>();
        listaDeCategorias.add(categoria1);
        listaDeCategorias.add(categoria2);

        when(categoriaService.buscarTodos()).thenReturn(listaDeCategorias);

        mockMvc.perform(get("/categorias"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("Eletrônicos"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nome").value("Roupas"));
    }

    @Test
    public void testBuscarCategoriaPorId() throws Exception {
        CategoriaModel categoriaTeste = new CategoriaModel();
        categoriaTeste.setId(1L);
        categoriaTeste.setNome("Categoria de Teste");

        when(categoriaService.buscarPorId(1L)).thenReturn(Optional.of(categoriaTeste));

        mockMvc.perform(get("/categorias/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Categoria de Teste"));
    }

    @Test
    public void testCadastrarCategoria() throws Exception {
        CategoriaModel categoriaTeste = new CategoriaModel();
        categoriaTeste.setId(1L);
        categoriaTeste.setNome("Categoria de Teste");

        when(categoriaService.cadastrar(Mockito.any(CategoriaModel.class))).thenReturn(categoriaTeste);

        mockMvc.perform(post("/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"nome\": \"Nova Categoria\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Categoria de Teste"));
    }

    @Test
    public void testAlterarCategoria() throws Exception {
        Long categoriaId = 1L;

        CategoriaModel categoriaModel = new CategoriaModel();
        categoriaModel.setId(1L);
        categoriaModel.setNome("Categoria Atualizada");

        when(categoriaService.alterar(eq(categoriaId), Mockito.any(CategoriaModel.class))).thenReturn(categoriaModel);

        mockMvc.perform(put("/categorias/{id}", categoriaId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"nome\": \"Categoria Atualizada\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Categoria Atualizada"));

    }

    @Test
    public void testDeletarCategoria() throws Exception {
        Long categoriaId = 1L;

        mockMvc.perform(delete("/categorias/{id}", categoriaId))
                .andExpect(status().isNoContent());

        verify(categoriaService, times(1)).deletar(categoriaId);
    }
}
