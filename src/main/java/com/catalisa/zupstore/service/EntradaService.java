package com.catalisa.zupstore.service;

import com.catalisa.zupstore.dto.EntradaDTO;
import com.catalisa.zupstore.dto.ProdutoDTO;
import com.catalisa.zupstore.model.EntradaModel;
import com.catalisa.zupstore.model.ProdutoModel;
import com.catalisa.zupstore.repository.EntradaRepository; // Certifique-se de importar o repositório correto
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntradaService {

    @Autowired
    private EntradaRepository entradaRepository;

    @Autowired
    ProdutoService produtoService;

    public List<EntradaModel> buscarTodos() {
        return entradaRepository.findAll();
    }

    public Optional<EntradaModel> buscarPorId(Long id) {
        return entradaRepository.findById(id);
    }

    public EntradaModel cadastrarEntrada(EntradaModel entradaModel) {

        //Lógica que atualiza o atributo quantidadeAtual do produto
        ProdutoModel produto = entradaModel.getProduto();
        produto.setQuantidadeAtual(produto.getQuantidadeAtual() + entradaModel.getQuantidade());
        produtoService.alterar(produto.getId(), produto);

        return entradaRepository.save(entradaModel);
    }

    public EntradaModel alterar(Long id, EntradaModel entradaAtualizada) {

        EntradaModel entrada = entradaRepository.findById(id).get();

        if (entrada != null) {
            entrada.setValorUnidade(entradaAtualizada.getValorUnidade());
            entrada.setQuantidade(entradaAtualizada.getQuantidade());
        }
        return entradaRepository.save(entrada);
    }

    public void deletar(Long id) {
        entradaRepository.deleteById(id);
    }

    public EntradaDTO converterParaDTO(EntradaModel entrada) {
        EntradaDTO dto = new EntradaDTO();

        ProdutoModel produto = entrada.getProduto();

        ProdutoDTO produtoDTO = new ProdutoDTO();

        produtoDTO.setNome(produto.getNome());
        produtoDTO.setPreco(produto.getPreco());
        produtoDTO.setCategoria(produto.getCategoria().getNome());

        dto.setProdutoDTO(produtoDTO);
        dto.setQuantidade(entrada.getQuantidade());

        return dto;
    }
}

