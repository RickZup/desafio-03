package com.catalisa.zupstore.controller;

import com.catalisa.zupstore.dto.ProdutoDTO;
import com.catalisa.zupstore.model.ProdutoModel;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ProdutoController.class)
public class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProdutoService produtoService;

    @Test
    public void exibirTodosProdutos() throws Exception {
        ProdutoModel produto1 = new ProdutoModel();
        produto1.setId(1L);
        produto1.setNome("Produto 1");
        produto1.setPreco(50.0);
        // ... configurar outros atributos do produto1

        ProdutoDTO dto1 = new ProdutoDTO();
        dto1.setNome("Produto 1");
        dto1.setPreco(50.0);
        dto1.setCategoria("Trabalho");

        List<ProdutoModel> listaDeProdutos = new ArrayList<>();
        listaDeProdutos.add(produto1);

        List<ProdutoDTO> listaDeDtos = new ArrayList<>();
        listaDeDtos.add(dto1);

        when(produtoService.buscarTodos()).thenReturn(listaDeProdutos);
        when(produtoService.converterParaDTO(produto1)).thenReturn(dto1);

        mockMvc.perform(get("/produtos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].nome").value("Produto 1"))
                .andExpect(jsonPath("$[0].preco").value(50.0))
                .andExpect(jsonPath("$[0].categoria").value("Trabalho"));
    }

    @Test
    public void buscarProdutoPorId() throws Exception {
        ProdutoModel produto = new ProdutoModel();
        produto.setId(1L);
        produto.setNome("Produto Teste");

        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setNome("Produto Teste");

        when(produtoService.buscarPorID(anyLong())).thenReturn(Optional.of(produto));
        when(produtoService.converterParaDTO(produto)).thenReturn(produtoDTO);

        mockMvc.perform(get("/produtos/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nome").value("Produto Teste"));
    }

    @Test
    public void buscarProdutoPorNome() throws Exception {
        ProdutoModel produto = new ProdutoModel();
        produto.setId(1L);
        produto.setNome("Produto Teste");

        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setNome("Produto Teste");

        when(produtoService.buscarPorNome(anyString())).thenReturn(produto);
        when(produtoService.converterParaDTO(produto)).thenReturn(produtoDTO);

        mockMvc.perform(get("/produtos/nome/{nome}", "Produto Teste"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nome").value("Produto Teste"));
    }

    @Test
    public void cadastrarProduto() throws Exception {

        ProdutoModel produtoModel = new ProdutoModel();
        produtoModel.setId(1L);
        produtoModel.setNome("Produto Novo");
        produtoModel.setPreco(50.0);
        // ... configurar outros atributos do produtoModel

        when(produtoService.cadastrar(Mockito.any(ProdutoModel.class))).thenReturn(produtoModel);

        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": \"1\", \"nome\": \"Produto Novo\", \"preco\": 50.0}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Produto Novo"))
                .andExpect(jsonPath("$.preco").value(50.0));

    }

    @Test
    public void testAlterarProduto() throws Exception {
        Long produtoId = 1L;

        ProdutoModel produtoModel = new ProdutoModel();
        produtoModel.setId(produtoId);
        produtoModel.setNome("Produto Atualizado");
        produtoModel.setPreco(75.0);
        // ... configurar outros atributos do produtoModel

        when(produtoService.alterar(eq(produtoId), Mockito.any(ProdutoModel.class))).thenReturn(produtoModel);

        mockMvc.perform(put("/produtos/{id}", produtoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": \"1\", \"nome\": \"Produto Atualizado\", \"preco\": 75.0}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Produto Atualizado"))
                .andExpect(jsonPath("$.preco").value(75.0));
    }

    @Test
    public void testDeletarProduto() throws Exception {
        Long produtoId = 1L;

        mockMvc.perform(delete("/produtos/{id}", produtoId))
                .andExpect(status().isNoContent());

        verify(produtoService, times(1)).deletar(produtoId);
    }
}
