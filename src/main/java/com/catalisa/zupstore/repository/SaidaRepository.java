package com.catalisa.zupstore.repository;

import com.catalisa.zupstore.model.SaidaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaidaRepository extends JpaRepository<SaidaModel, Long> {
}
