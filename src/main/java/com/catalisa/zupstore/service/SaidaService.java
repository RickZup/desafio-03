package com.catalisa.zupstore.service;

import com.catalisa.zupstore.dto.ProdutoDTO;
import com.catalisa.zupstore.dto.SaidaDTO;
import com.catalisa.zupstore.model.ProdutoModel;
import com.catalisa.zupstore.model.SaidaModel;
import com.catalisa.zupstore.repository.SaidaRepository; // Certifique-se de importar o repositório correto
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SaidaService {

    @Autowired
    private SaidaRepository saidaRepository;

    @Autowired
    private ProdutoService produtoService;

    public List<SaidaModel> buscarTodos() {
        return saidaRepository.findAll();
    }

    public Optional<SaidaModel> buscarPorId(Long id) {
        return saidaRepository.findById(id);
    }

    public SaidaModel cadastrar(SaidaModel saidaModel) {

        ProdutoModel produto = saidaModel.getProduto();
        int quantidadeSaida = saidaModel.getQuantidade();

        if (quantidadeSaida <= 0) {
            throw new IllegalArgumentException("Quantidade de saída inválida.");
        }

        if (produto.getQuantidadeAtual() >= quantidadeSaida) {
            produto.setQuantidadeAtual(produto.getQuantidadeAtual() - quantidadeSaida);
            produtoService.alterar(produto.getId(), produto);

            saidaModel.setDataSaida(LocalDate.now());

            return saidaRepository.save(saidaModel);
        } else {
            throw new IllegalArgumentException("Quantidade de saída maior do que a quantidade atual do produto.");
        }
    }

    public SaidaModel alterar(Long id, SaidaModel saidaAtualizada) {

        SaidaModel saidaExistente = saidaRepository.findById(id).get();

        if (saidaExistente != null) {
            saidaExistente.setValorUnidade(saidaAtualizada.getValorUnidade());
            saidaExistente.setQuantidade(saidaAtualizada.getQuantidade());
        }
        return saidaRepository.save(saidaExistente);
    }

    public void deletar(Long id) {
        saidaRepository.deleteById(id);
    }

    public SaidaDTO converterParaDTO(SaidaModel saida) {
        SaidaDTO dto = new SaidaDTO();

        ProdutoModel produto = saida.getProduto();

        ProdutoDTO produtoDTO = new ProdutoDTO();

        produtoDTO.setNome(produto.getNome());
        produtoDTO.setPreco(produto.getPreco());
        produtoDTO.setCategoria(produto.getCategoria().getNome());

        dto.setProdutoDTO(produtoDTO);
        dto.setQuantidade(saida.getQuantidade());

        return dto;
    }
}

