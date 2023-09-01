package com.catalisa.zupstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EntradaDTO {

    private ProdutoDTO produtoDTO;
    private int quantidade;

}
