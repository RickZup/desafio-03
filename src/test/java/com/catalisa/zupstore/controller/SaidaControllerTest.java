package com.catalisa.zupstore.controller;

import com.catalisa.zupstore.dto.SaidaDTO;
import com.catalisa.zupstore.dto.ProdutoDTO;
import com.catalisa.zupstore.model.CategoriaModel;
import com.catalisa.zupstore.model.SaidaModel;
import com.catalisa.zupstore.model.ProdutoModel;
import com.catalisa.zupstore.service.SaidaService;
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
@WebMvcTest(SaidaController.class)
public class SaidaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SaidaService saidaService;

    @Test
    public void buscarTodasSaidas() throws Exception {

        CategoriaModel categoriaModel = new CategoriaModel(1L, "Trabalho");

        ProdutoModel produto1 = new ProdutoModel();
        produto1.setId(1L);
        produto1.setNome("Produto 1");
        produto1.setPreco(50.0);
        produto1.setCategoria(categoriaModel);
        // ... configurar outros atributos do produto1

        ProdutoDTO dto1 = new ProdutoDTO();
        dto1.setNome("Produto 1");
        dto1.setPreco(50.0);
        dto1.setCategoria("Trabalho");

        SaidaModel saidaTeste = new SaidaModel();
        saidaTeste.setId(1L);
        saidaTeste.setProduto(produto1);
        saidaTeste.setQuantidade(50);

        SaidaDTO saidaDTO = new SaidaDTO(dto1, 50);

        List<SaidaModel> listaDeSaidas = new ArrayList<>();
        listaDeSaidas.add(saidaTeste);

        List<SaidaDTO> listaDeDTO = new ArrayList<>();
        listaDeDTO.add(saidaDTO);

        when(saidaService.buscarTodos()).thenReturn(listaDeSaidas);
        when(saidaService.converterParaDTO(saidaTeste)).thenReturn(saidaDTO);

        mockMvc.perform(get("/saidas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].produtoDTO.nome").value("Produto 1"))
                .andExpect(jsonPath("$[0].produtoDTO.preco").value(50.0))
                .andExpect(jsonPath("$[0].quantidade").value(50));

    }

    @Test
    public void buscarSaidaPorId() throws Exception {
        CategoriaModel categoriaModel = new CategoriaModel(1L, "Trabalho");

        ProdutoModel produto1 = new ProdutoModel();
        produto1.setId(1L);
        produto1.setNome("Produto 1");
        produto1.setPreco(50.0);
        produto1.setCategoria(categoriaModel);

        SaidaModel saidaTeste = new SaidaModel();
        saidaTeste.setId(1L);
        saidaTeste.setProduto(produto1);
        saidaTeste.setQuantidade(50);

        when(saidaService.buscarPorId(eq(1L))).thenReturn(Optional.of(saidaTeste));

        mockMvc.perform(get("/saidas/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.produtoDTO.nome").value("Produto 1"))
                .andExpect(jsonPath("$.produtoDTO.preco").value(50.0))
                .andExpect(jsonPath("$.quantidade").value(50));

        verify(saidaService, times(1)).buscarPorId(eq(1L));
    }

    @Test
    public void testCadastrarSaida() throws Exception {
        CategoriaModel categoriaModel = new CategoriaModel(1L, "Trabalho");

        ProdutoModel produto1 = new ProdutoModel();
        produto1.setId(1L);
        produto1.setNome("Produto 1");
        produto1.setPreco(50.0);
        produto1.setCategoria(categoriaModel);

        SaidaModel saidaModel = new SaidaModel();
        saidaModel.setId(1L);
        saidaModel.setProduto(produto1);
        saidaModel.setQuantidade(50);

        when(saidaService.cadastrar(any(SaidaModel.class))).thenReturn(saidaModel);

        String json = "{\"id\":1,\"produto\":{\"id\":1,\"nome\":\"Produto 1\",\"preco\":50.0,\"categoria\":{\"id\":1,\"nome\":\"Trabalho\"}},\"quantidade\":50}";

        mockMvc.perform(MockMvcRequestBuilders.post("/saidas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.produto.id").value(1L))
                .andExpect(jsonPath("$.quantidade").value(50));

        verify(saidaService, times(1)).cadastrar(any(SaidaModel.class));
    }

    @Test
    public void alterarSaida() throws Exception {
        CategoriaModel categoriaModel = new CategoriaModel(1L, "Trabalho");

        ProdutoModel produto1 = new ProdutoModel();
        produto1.setId(1L);
        produto1.setNome("Produto 1");
        produto1.setPreco(50.0);
        produto1.setCategoria(categoriaModel);

        SaidaModel saidaOriginal = new SaidaModel();
        saidaOriginal.setId(1L);
        saidaOriginal.setProduto(produto1);
        saidaOriginal.setQuantidade(50);

        SaidaModel saidaAtualizada = new SaidaModel();
        saidaAtualizada.setProduto(produto1);
        saidaAtualizada.setQuantidade(100);

        when(saidaService.alterar(eq(1L), any(SaidaModel.class))).thenReturn(saidaAtualizada);

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

        mockMvc.perform(put("/saidas/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.produto.id").value(1L))
                .andExpect(jsonPath("$.quantidade").value(100));

        verify(saidaService, times(1)).alterar(eq(1L), any(SaidaModel.class));
    }

    @Test
    public void deletarSaida() throws Exception {
        Long saidaId = 1L;

        mockMvc.perform(delete("/saidas/{id}", saidaId))
                .andExpect(status().isNoContent());

        verify(saidaService, times(1)).deletar(saidaId);
    }
}

