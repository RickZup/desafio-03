package com.catalisa.zupstore.service;

import com.catalisa.zupstore.model.CategoriaModel;
import com.catalisa.zupstore.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    CategoriaRepository categoriaRepository;

    public List<CategoriaModel> buscarTodos() {
        return categoriaRepository.findAll();
    }

    public Optional<CategoriaModel> buscarPorId(Long id) {
        return categoriaRepository.findById(id);
    }

    public CategoriaModel cadastrar(CategoriaModel categoriaModel) {
        return categoriaRepository.save(categoriaModel);
    }

    public CategoriaModel alterar(Long id, CategoriaModel categoriaAtualizada) {

        CategoriaModel categoria = categoriaRepository.findById(id).get();

        if (categoria != null) {
            categoria.setNome(categoriaAtualizada.getNome());
        }
        return categoriaRepository.save(categoria);
    }

    public void deletar(Long id) {
        categoriaRepository.deleteById(id);
    }

}
