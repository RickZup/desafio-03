package com.catalisa.zupstore.controller;

import com.catalisa.zupstore.dto.ProdutoDTO;
import com.catalisa.zupstore.model.ProdutoModel;
import com.catalisa.zupstore.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/produtos")
@Tag(name = "produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    @Operation(summary = "Busca todos os produtos", method = "GET")
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"))
    public List<ProdutoDTO> buscarTodos() {
        List<ProdutoModel> produtos = produtoService.buscarTodos();

        List<ProdutoDTO> dtos = new ArrayList<>();
        for (ProdutoModel produto : produtos) {
            ProdutoDTO dto = produtoService.converterParaDTO(produto);
            dtos.add(dto);
        }
        return dtos;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca produto por ID", method = "GET")
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"))
    public ResponseEntity<ProdutoDTO> buscarPorID(@PathVariable Long id) {
        ProdutoModel produto = produtoService.buscarPorID(id).orElse(null);

        if (produto != null) {
            ProdutoDTO dto = produtoService.converterParaDTO(produto);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nome/{nome}")
    @Operation(summary = "Busca produto por nome", method = "GET")
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"))
    public ResponseEntity<ProdutoDTO> buscarPorNome(@PathVariable String nome) {
        ProdutoModel produto = produtoService.buscarPorNome(nome);

        if (produto != null) {
            ProdutoDTO dto = produtoService.converterParaDTO(produto);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Cadastra um produto", method = "POST")
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "Cadastro realizado com sucesso"))
    public ProdutoModel cadastrar(@RequestBody ProdutoModel produtoModel) {
        return produtoService.cadastrar(produtoModel);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza produto", method = "PUT")
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "Atualização realizada com sucesso"))
    public ResponseEntity<ProdutoModel> alterar(@PathVariable Long id, @RequestBody ProdutoModel produtoAtualizado) {
        ProdutoModel produto = produtoService.alterar(id, produtoAtualizado);
        return ResponseEntity.ok(produto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta produto", method = "DELETE")
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"))
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
