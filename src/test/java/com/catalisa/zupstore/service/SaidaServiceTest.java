package com.catalisa.zupstore.service;

import com.catalisa.zupstore.dto.ProdutoDTO;
import com.catalisa.zupstore.dto.SaidaDTO;
import com.catalisa.zupstore.model.CategoriaModel;
import com.catalisa.zupstore.model.ProdutoModel;
import com.catalisa.zupstore.model.SaidaModel;
import com.catalisa.zupstore.repository.SaidaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SaidaServiceTest {

    @Mock
    private SaidaRepository saidaRepository;

    @Mock
    private ProdutoService produtoService;

    @InjectMocks
    private SaidaService saidaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBuscarTodos() {
        List<SaidaModel> saidas = new ArrayList<>();

        SaidaModel saidaTeste = new SaidaModel();
        saidaTeste.setId(1L);

        saidas.add(saidaTeste);

        when(saidaRepository.findAll()).thenReturn(saidas);

        List<SaidaModel> result = saidaService.buscarTodos();

        assertEquals(saidas, result);
    }

    @Test
    public void testBuscarPorId() {
        Long saidaId = 1L;

        SaidaModel saida = new SaidaModel();
        saida.setId(1L);

        when(saidaRepository.findById(saidaId)).thenReturn(Optional.of(saida));

        Optional<SaidaModel> result = saidaService.buscarPorId(saidaId);

        assertTrue(result.isPresent());
        assertEquals(saida, result.get());
    }

    @Test
    public void testCadastrar() {
        CategoriaModel categoriaModel = new CategoriaModel(1L, "Trabalho");

        ProdutoModel produto1 = new ProdutoModel();
        produto1.setId(1L);
        produto1.setNome("Produto 1");
        produto1.setPreco(50.0);
        produto1.setCategoria(categoriaModel);
        produto1.setQuantidadeAtual(100);

        SaidaModel saidaTeste = new SaidaModel();
        saidaTeste.setId(1L);
        saidaTeste.setProduto(produto1);
        saidaTeste.setQuantidade(10);

        when(saidaRepository.save(any(SaidaModel.class))).thenReturn(saidaTeste);

        SaidaModel result = saidaService.cadastrar(saidaTeste);

        assertEquals(saidaTeste, result);
        verify(produtoService, times(1)).alterar(eq(1L), eq(produto1));
    }

    @Test
    public void testAlterar() {
        Long saidaId = 1L;

        SaidaModel saidaOriginal = new SaidaModel();
        saidaOriginal.setId(1L);

        SaidaModel saidaAtualizada = new SaidaModel();
        saidaAtualizada.setId(1L);

        when(saidaRepository.findById(saidaId)).thenReturn(Optional.of(saidaOriginal));
        when(saidaRepository.save(any(SaidaModel.class))).thenReturn(saidaAtualizada);

        SaidaModel result = saidaService.alterar(saidaId, saidaAtualizada);

        assertEquals(saidaAtualizada, result);
    }

    @Test
    public void testDeletar() {
        Long saidaId = 1L;

        saidaService.deletar(saidaId);

        verify(saidaRepository, times(1)).deleteById(saidaId);
    }

    @Test
    public void testConverterParaDTO() {
        CategoriaModel categoriaModel = new CategoriaModel(1L, "Eletrônicos");

        ProdutoModel produtoModel = new ProdutoModel();
        produtoModel.setId(1L);
        produtoModel.setNome("Smartphone");
        produtoModel.setPreco(999.99);
        produtoModel.setCategoria(categoriaModel);

        SaidaModel saidaModel = new SaidaModel();
        saidaModel.setProduto(produtoModel);
        saidaModel.setQuantidade(5);

        SaidaDTO saidaDTO = saidaService.converterParaDTO(saidaModel);

        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setNome("Smartphone");
        produtoDTO.setPreco(999.99);
        produtoDTO.setCategoria("Eletrônicos");

        assertEquals(produtoDTO.getNome(), saidaDTO.getProdutoDTO().getNome());
        assertEquals(produtoDTO.getPreco(), saidaDTO.getProdutoDTO().getPreco());
        assertEquals(produtoDTO.getCategoria(), saidaDTO.getProdutoDTO().getCategoria());

        assertEquals(5, saidaDTO.getQuantidade());
    }
}

