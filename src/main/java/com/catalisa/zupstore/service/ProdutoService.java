package com.catalisa.zupstore.service;

import com.catalisa.zupstore.dto.ProdutoDTO;
import com.catalisa.zupstore.model.ProdutoModel;
import com.catalisa.zupstore.repository.ProdutoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;

    public List<ProdutoModel> buscarTodos(){
        return produtoRepository.findAll();
    }

    public Optional<ProdutoModel> buscarPorID(Long id){
        return produtoRepository.findById(id);
    }

    public ProdutoModel buscarPorNome(String nome){
        return produtoRepository.findByNome(nome);
    }

    public ProdutoModel cadastrar(ProdutoModel produtoModel){
        return produtoRepository.save(produtoModel);
    }

    public ProdutoModel alterar(Long id, ProdutoModel produtoAtualizado){

        ProdutoModel produto =buscarPorID(id).get();

        if (produto.getNome() != null){
            produto.setNome(produtoAtualizado.getNome());
        }
        if (produto.getDataCriacao() != null){
            produto.setDescricao(produtoAtualizado.getDescricao());
        }

        produto.setPreco(produtoAtualizado.getPreco());

        produto.setQuantidadeAtual(produtoAtualizado.getQuantidadeAtual());

        return produtoRepository.save(produto);
    }

    public void deletar(Long id){
        produtoRepository.deleteById(id);
    }

    public ProdutoDTO converterParaDTO(ProdutoModel produto) {
        ProdutoDTO dto = new ProdutoDTO();
        dto.setNome(produto.getNome());
        dto.setPreco(produto.getPreco());
        dto.setCategoria(produto.getCategoria().getNome());
        return dto;
    }
}

