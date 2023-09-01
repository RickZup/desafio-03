package com.catalisa.zupstore.controller;

import com.catalisa.zupstore.dto.EntradaDTO;
import com.catalisa.zupstore.dto.ProdutoDTO;
import com.catalisa.zupstore.model.EntradaModel;
import com.catalisa.zupstore.service.EntradaService;
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
@RequestMapping("/entradas")
@Tag(name = "entradas")
public class EntradaController {

    @Autowired
    private EntradaService entradaService;

    @GetMapping
    @Operation(summary = "Busca todoas as entradas", method = "GET")
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"))
    public List<EntradaDTO> buscarTodasEntradas() {
        List<EntradaModel> entradas = entradaService.buscarTodos();
        List<EntradaDTO> dtos = new ArrayList<>();

        for (EntradaModel entrada : entradas) {
            ProdutoDTO produtoDTO = new ProdutoDTO();
            produtoDTO.setNome(entrada.getProduto().getNome());
            produtoDTO.setPreco(entrada.getProduto().getPreco());
            produtoDTO.setCategoria(entrada.getProduto().getCategoria().getNome());

            int quantidade = entrada.getQuantidade();

            EntradaDTO dto = new EntradaDTO(produtoDTO, quantidade);
            dtos.add(dto);
        }
        return dtos;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca todoas as entradas por ID", method = "GET")
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"))
    public ResponseEntity<EntradaDTO> buscarEntradaPorId(@PathVariable Long id) {
        EntradaModel entrada = entradaService.buscarPorId(id).orElse(null);

        if (entrada != null) {
            ProdutoDTO produtoDTO = new ProdutoDTO();
            produtoDTO.setNome(entrada.getProduto().getNome());
            produtoDTO.setPreco(entrada.getProduto().getPreco());
            produtoDTO.setCategoria(entrada.getProduto().getCategoria().getNome());

            EntradaDTO dto = new EntradaDTO(produtoDTO, entrada.getQuantidade());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Cadastra uma entrada", method = "´POST")
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "Cadastro realizado com sucesso"))
    public EntradaModel cadastrarEntrada(@RequestBody EntradaModel entradaModel) {
        return entradaService.cadastrarEntrada(entradaModel);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma entrada", method = "PUT")
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "Atualização realizada com sucesso"))
    public ResponseEntity<EntradaModel> alterarEntrada(
            @PathVariable Long id,
            @RequestBody EntradaModel entradaAtualizada
    ) {
        EntradaModel entrada = entradaService.alterar(id, entradaAtualizada);
        return ResponseEntity.ok(entrada);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma entrada", method = "DELETE")
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"))
    public ResponseEntity<Void> deletarEntrada(@PathVariable Long id) {
        entradaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

