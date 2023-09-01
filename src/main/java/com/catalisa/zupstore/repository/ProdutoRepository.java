package com.catalisa.zupstore.repository;

import com.catalisa.zupstore.model.ProdutoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoModel, Long> {
    ProdutoModel findByNome(String nomeProduto);
}
