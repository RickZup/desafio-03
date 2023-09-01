package com.catalisa.zupstore.repository;

import com.catalisa.zupstore.model.EntradaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntradaRepository extends JpaRepository<EntradaModel, Long> {
}
