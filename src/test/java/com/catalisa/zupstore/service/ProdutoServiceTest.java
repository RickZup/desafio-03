package com.catalisa.zupstore.service;
import com.catalisa.zupstore.dto.ProdutoDTO;
import com.catalisa.zupstore.model.CategoriaModel;
import com.catalisa.zupstore.model.ProdutoModel;
import com.catalisa.zupstore.repository.ProdutoRepository;
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

public class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBuscarTodos() {
        List<ProdutoModel> produtos = new ArrayList<>();

        ProdutoModel produtoTeste = new ProdutoModel();
        produtoTeste.setId(1L);
        produtos.add(produtoTeste);

        when(produtoRepository.findAll()).thenReturn(produtos);

        List<ProdutoModel> result = produtoService.buscarTodos();

        assertEquals(produtos, result);
    }

    @Test
    public void testBuscarPorId() {
        Long produtoId = 1L;

        ProdutoModel produto = new ProdutoModel();
        produto.setId(1L);

        when(produtoRepository.findById(produtoId)).thenReturn(Optional.of(produto));

        Optional<ProdutoModel> result = produtoService.buscarPorID(produtoId);

        assertTrue(result.isPresent());
        assertEquals(produto, result.get());
    }

    @Test
    public void testBuscarPorNome() {
        String nomeProduto = "ProdutoTeste";

        ProdutoModel produto = new ProdutoModel();
        produto.setId(1L);
        produto.setNome(nomeProduto);

        when(produtoRepository.findByNome(nomeProduto)).thenReturn(produto);

        ProdutoModel result = produtoService.buscarPorNome(nomeProduto);

        assertEquals(produto, result);
    }

    @Test
    public void testCadastrar() {

        ProdutoModel produtoTeste = new ProdutoModel();
        produtoTeste.setId(1L);

        when(produtoRepository.save(any(ProdutoModel.class))).thenReturn(produtoTeste);

        ProdutoModel result = produtoService.cadastrar(produtoTeste);

        assertEquals(produtoTeste, result);
    }

    @Test
    public void testAlterar() {
        Long produtoId = 1L;

        ProdutoModel produtoOriginal = new ProdutoModel();
        produtoOriginal.setId(1L);

        ProdutoModel produtoAtualizado = new ProdutoModel();
        produtoAtualizado.setId(1L);

        when(produtoRepository.findById(produtoId)).thenReturn(Optional.of(produtoOriginal));
        when(produtoRepository.save(any(ProdutoModel.class))).thenReturn(produtoAtualizado);

        ProdutoModel result = produtoService.alterar(produtoId, produtoAtualizado);

        assertEquals(produtoAtualizado, result);
    }

    @Test
    public void testDeletar() {
        Long produtoId = 1L;
        produtoService.deletar(produtoId);
        verify(produtoRepository, times(1)).deleteById(produtoId);
    }

    @Test
    public void testConverterParaDTO() {
        CategoriaModel categoriaModel = new CategoriaModel(1L, "Eletrônicos");

        ProdutoModel produtoModel = new ProdutoModel();
        produtoModel.setNome("Smartphone");
        produtoModel.setPreco(999.99);
        produtoModel.setCategoria(categoriaModel);

        ProdutoDTO produtoDTO = produtoService.converterParaDTO(produtoModel);

        assertEquals(produtoModel.getNome(), produtoDTO.getNome());
        assertEquals(produtoModel.getPreco(), produtoDTO.getPreco());
        assertEquals(produtoModel.getCategoria().getNome(), produtoDTO.getCategoria());
    }
}

