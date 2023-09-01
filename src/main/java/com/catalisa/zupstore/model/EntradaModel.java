package com.catalisa.zupstore.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "TB_ENTRADAS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EntradaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entradaId")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produtoId")
    private ProdutoModel produto;

    @Column(name = "dataEntrada")
    private LocalDate dataEntrada;

    private double valorUnidade;

    private int quantidade;
}
