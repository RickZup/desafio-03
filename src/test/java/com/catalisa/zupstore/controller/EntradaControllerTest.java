package com.catalisa.zupstore.controller;

import com.catalisa.zupstore.dto.EntradaDTO;
import com.catalisa.zupstore.dto.ProdutoDTO;
import com.catalisa.zupstore.model.CategoriaModel;
import com.catalisa.zupstore.model.EntradaModel;
import com.catalisa.zupstore.model.ProdutoModel;
import com.catalisa.zupstore.service.EntradaService;
import com.catalisa.zupstore.service.ProdutoService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(EntradaController.class)
public class EntradaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EntradaService entradaService;

    @Test
    public void buscarTodasEntradas() throws Exception {

        CategoriaModel categoriaModel = new CategoriaModel(1L, "Trabalho");

        ProdutoModel produto1 = new ProdutoModel();
        produto1.setId(1L);
        produto1.setNome("Produto 1");
        produto1.setPreco(50.0);
        produto1.setCategoria(categoriaModel);

        ProdutoDTO dto1 = new ProdutoDTO();
        dto1.setNome("Produto 1");
        dto1.setPreco(50.0);
        dto1.setCategoria("Trabalho");

        EntradaModel entradaTeste = new EntradaModel();
        entradaTeste.setId(1L);
        entradaTeste.setProduto(produto1);
        entradaTeste.setQuantidade(50);

        EntradaDTO entradaDTO = new EntradaDTO(dto1, 50);

        List<EntradaModel> listaDeEntradas = new ArrayList<>();
        listaDeEntradas.add(entradaTeste);

        List<EntradaDTO> listaDeDTO = new ArrayList<>();
        listaDeDTO.add(entradaDTO);

        when(entradaService.buscarTodos()).thenReturn(listaDeEntradas);
        when(entradaService.converterParaDTO(entradaTeste)).thenReturn(entradaDTO);

        mockMvc.perform(get("/entradas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].produtoDTO.nome").value("Produto 1"))
                .andExpect(jsonPath("$[0].produtoDTO.preco").value(50.0))
                .andExpect(jsonPath("$[0].quantidade").value(50));

    }

    @Test
    public void buscarEntradaPorId() throws Exception {
        CategoriaModel categoriaModel = new CategoriaModel(1L, "Trabalho");

        ProdutoModel produto1 = new ProdutoModel();
        produto1.setId(1L);
        produto1.setNome("Produto 1");
        produto1.setPreco(50.0);
        produto1.setCategoria(categoriaModel);

        EntradaModel entradaTeste = new EntradaModel();
        entradaTeste.setId(1L);
        entradaTeste.setProduto(produto1);
        entradaTeste.setQuantidade(50);

        when(entradaService.buscarPorId(eq(1L))).thenReturn(Optional.of(entradaTeste));

        mockMvc.perform(get("/entradas/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.produtoDTO.nome").value("Produto 1"))
                .andExpect(jsonPath("$.produtoDTO.preco").value(50.0))
                .andExpect(jsonPath("$.quantidade").value(50));

        verify(entradaService, times(1)).buscarPorId(eq(1L));
    }

    @Test
    public void testCadastrarEntrada() throws Exception {
        CategoriaModel categoriaModel = new CategoriaModel(1L, "Trabalho");

        ProdutoModel produto1 = new ProdutoModel();
        produto1.setId(1L);
        produto1.setNome("Produto 1");
        produto1.setPreco(50.0);
        produto1.setCategoria(categoriaModel);

        EntradaModel entradaModel = new EntradaModel();
        entradaModel.setId(1L);
        entradaModel.setProduto(produto1);
        entradaModel.setQuantidade(50);

        when(entradaService.cadastrarEntrada(any(EntradaModel.class))).thenReturn(entradaModel);

        String json = "{\"id\":1,\"produto\":{\"id\":1,\"nome\":\"Produto 1\",\"preco\":50.0,\"categoria\":{\"id\":1,\"nome\":\"Trabalho\"}},\"quantidade\":50}";

        mockMvc.perform(MockMvcRequestBuilders.post("/entradas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.produto.id").value(1L))
                .andExpect(jsonPath("$.quantidade").value(50));

        verify(entradaService, times(1)).cadastrarEntrada(any(EntradaModel.class));
    }

    @Test
    public void alterarEntrada() throws Exception {
        CategoriaModel categoriaModel = new CategoriaModel(1L, "Trabalho");

        ProdutoModel produto1 = new ProdutoModel();
        produto1.setId(1L);
        produto1.setNome("Produto 1");
        produto1.setPreco(50.0);
        produto1.setCategoria(categoriaModel);

        EntradaModel entradaOriginal = new EntradaModel();
        entradaOriginal.setId(1L);
        entradaOriginal.setProduto(produto1);
        entradaOriginal.setQuantidade(50);

        EntradaModel entradaAtualizada = new EntradaModel();
        entradaAtualizada.setProduto(produto1);
        entradaAtualizada.setQuantidade(100);

        when(entradaService.alterar(eq(1L), any(EntradaModel.class))).thenReturn(entradaAtualizada);

        String json = "{"
                + "\"produto\": {"
                + "\"id\": 1,"
                + "\"nome\": \"Produto 1\","
                + "\"preco\": 50.0,"
                + "\"categoria\": {"
                + "\"id\": 1,"
                + "\"nome\": \"Trabalho\""
                + "}"
                + "},"
                + "\"quantidade\": 100"
                + "}";

        mockMvc.perform(put("/entradas/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.produto.id").value(1L))
                .andExpect(jsonPath("$.quantidade").value(100));

        verify(entradaService, times(1)).alterar(eq(1L), any(EntradaModel.class));
    }

    @Test
    public void deletarEntrada() throws Exception {
        Long entradaId = 1L;

        mockMvc.perform(delete("/entradas/{id}", entradaId))
                .andExpect(status().isNoContent());

        verify(entradaService, times(1)).deletar(entradaId);
    }

}
