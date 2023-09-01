package com.catalisa.zupstore.controller;

import com.catalisa.zupstore.model.CategoriaModel;
import com.catalisa.zupstore.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categorias")
@Tag(name = "categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    @Operation(summary = "Busca todoas as categorias", method = "GET")
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"))
    public ResponseEntity<List<CategoriaModel>> buscarTodasCategorias() {
        List<CategoriaModel> categorias = categoriaService.buscarTodos();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca todas as categorias por ID", method = "GET")
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"))
    public ResponseEntity<CategoriaModel> buscarCategoriaPorId(@PathVariable Long id) {
        Optional<CategoriaModel> categoria = categoriaService.buscarPorId(id);
        return categoria.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Cadastra uma categoria", method = "POST")
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "Cadastro realizado com sucesso"))
    public ResponseEntity<CategoriaModel> cadastrarCategoria(@RequestBody CategoriaModel categoriaModel) {
        CategoriaModel novaCategoria = categoriaService.cadastrar(categoriaModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaCategoria);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma categoria por ID", method = "PUT")
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "Atualização realizada com sucesso"))
    public ResponseEntity<CategoriaModel> atualizarCategoria(@PathVariable Long id, @RequestBody CategoriaModel categoriaAtualizada) {
        CategoriaModel categoriaAtualizadaEntity = categoriaService.alterar(id, categoriaAtualizada);
        return ResponseEntity.ok(categoriaAtualizadaEntity);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma categoria por ID", method = "DELETE")
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"))
    public ResponseEntity<Void> deletarCategoria(@PathVariable Long id) {
        categoriaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
