package com.catalisa.zupstore.controller;

import com.catalisa.zupstore.dto.ProdutoDTO;
import com.catalisa.zupstore.dto.SaidaDTO;
import com.catalisa.zupstore.model.SaidaModel;
import com.catalisa.zupstore.service.SaidaService;
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
@RequestMapping("/saidas")
@Tag(name = "saidas")
public class SaidaController {

    @Autowired
    private SaidaService saidaService;

    @GetMapping
    @Operation(summary = "Busca todoas as saidas", method = "GET")
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"))
    public List<SaidaDTO> buscarTodasSaidas() {
        List<SaidaModel> saidas = saidaService.buscarTodos();
        List<SaidaDTO> dtos = new ArrayList<>();

        for (SaidaModel saida : saidas) {
            ProdutoDTO produtoDTO = new ProdutoDTO();
            produtoDTO.setNome(saida.getProduto().getNome());
            produtoDTO.setPreco(saida.getProduto().getPreco());
            produtoDTO.setCategoria(saida.getProduto().getCategoria().getNome());

            int quantidade = saida.getQuantidade();

            SaidaDTO dto = new SaidaDTO(produtoDTO, quantidade);
            dtos.add(dto);
        }
        return dtos;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca saida por ID", method = "GET")
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"))
    public ResponseEntity<SaidaDTO> buscarSaidaPorId(@PathVariable Long id) {
        SaidaModel saida = saidaService.buscarPorId(id).orElse(null);

        if (saida != null) {
            ProdutoDTO produtoDTO = new ProdutoDTO();
            produtoDTO.setNome(saida.getProduto().getNome());
            produtoDTO.setPreco(saida.getProduto().getPreco());
            produtoDTO.setCategoria(saida.getProduto().getCategoria().getNome());

            SaidaDTO dto = new SaidaDTO(produtoDTO, saida.getQuantidade());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Cadastra saida", method = "POST   ")
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "Cadastro realizado com sucesso"))
    public SaidaModel cadastrarSaida(@RequestBody SaidaModel saidaModel) {
        return saidaService.cadastrar(saidaModel);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza saída", method = "PUT")
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "Atualização realizada com sucesso"))
    public ResponseEntity<SaidaModel> alterarSaida(@PathVariable Long id, @RequestBody SaidaModel saidaAtualizada) {
        SaidaModel saida = saidaService.alterar(id, saidaAtualizada);
        return ResponseEntity.ok(saida);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta saída", method = "DELETE")
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"))
    public ResponseEntity<Void> deletarSaida(@PathVariable Long id) {
        saidaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

