package com.catalisa.zupstore.service;
import com.catalisa.zupstore.dto.EntradaDTO;
import com.catalisa.zupstore.dto.ProdutoDTO;
import com.catalisa.zupstore.model.CategoriaModel;
import com.catalisa.zupstore.model.EntradaModel;
import com.catalisa.zupstore.model.ProdutoModel;
import com.catalisa.zupstore.repository.EntradaRepository;
import com.catalisa.zupstore.service.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EntradaServiceTest {

    @Mock
    private EntradaRepository entradaRepository;

    @Mock
    private ProdutoService produtoService;

    @InjectMocks
    private EntradaService entradaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBuscarTodos() {

        List<EntradaModel> entradas = new ArrayList<>();

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

        entradas.add(entradaTeste);

        when(entradaRepository.findAll()).thenReturn(entradas);

        List<EntradaModel> result = entradaService.buscarTodos();

        assertEquals(entradas, result);
    }

    @Test
    public void testBuscarPorId() {
        Long entradaId = 1L;

        CategoriaModel categoriaModel = new CategoriaModel(1L, "Trabalho");

        ProdutoModel produto1 = new ProdutoModel();
        produto1.setId(1L);
        produto1.setNome("Produto 1");
        produto1.setPreco(50.0);
        produto1.setCategoria(categoriaModel);

        EntradaModel entrada = new EntradaModel();
        entrada.setId(1L);
        entrada.setProduto(produto1);
        entrada.setQuantidade(50);

        when(entradaRepository.findById(entradaId)).thenReturn(Optional.of(entrada));

        Optional<EntradaModel> result = entradaService.buscarPorId(entradaId);

        assertTrue(result.isPresent());
        assertEquals(entrada, result.get());
    }

    @Test
    public void testCadastrarEntrada() {
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

        when(entradaRepository.save(any(EntradaModel.class))).thenReturn(entradaTeste);

        EntradaModel result = entradaService.cadastrarEntrada(entradaTeste);

        assertEquals(entradaTeste, result);
        verify(produtoService, times(1)).alterar(eq(1L), eq(produto1));
    }

    @Test
    public void testAlterar() {
        Long entradaId = 1L;

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
        entradaAtualizada.setId(1L);
        entradaAtualizada.setProduto(produto1);
        entradaAtualizada.setQuantidade(100);


        when(entradaRepository.findById(entradaId)).thenReturn(Optional.of(entradaOriginal));
        when(entradaRepository.save(any(EntradaModel.class))).thenReturn(entradaAtualizada);

        EntradaModel result = entradaService.alterar(entradaId, entradaAtualizada);

        assertEquals(entradaAtualizada, result);
    }

    @Test
    public void testDeletar() {
        Long entradaId = 1L;

        entradaService.deletar(entradaId);

        verify(entradaRepository, times(1)).deleteById(entradaId);
    }

    @Test
    public void testConverterParaDTO() {
        CategoriaModel categoriaModel = new CategoriaModel(1L, "Eletrônicos");

        ProdutoModel produtoModel = new ProdutoModel();
        produtoModel.setId(1L);
        produtoModel.setNome("Smartphone");
        produtoModel.setPreco(999.99);
        produtoModel.setCategoria(categoriaModel);

        EntradaModel entradaModel = new EntradaModel();
        entradaModel.setProduto(produtoModel);
        entradaModel.setQuantidade(5);

        EntradaDTO entradaDTO = entradaService.converterParaDTO(entradaModel);

        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setNome("Smartphone");
        produtoDTO.setPreco(999.99);
        produtoDTO.setCategoria("Eletrônicos");

        assertEquals(produtoDTO.getNome(), entradaDTO.getProdutoDTO().getNome());
        assertEquals(produtoDTO.getPreco(), entradaDTO.getProdutoDTO().getPreco());
        assertEquals(produtoDTO.getCategoria(), entradaDTO.getProdutoDTO().getCategoria());

        assertEquals(5, entradaDTO.getQuantidade());
    }

}

