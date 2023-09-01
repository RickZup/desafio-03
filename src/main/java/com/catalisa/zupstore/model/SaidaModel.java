package com.catalisa.zupstore.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "TB_SAIDAS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaidaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "saidaId")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "produtoId")
    private ProdutoModel produto;

    @Column(name = "dataSaida")
    private LocalDate dataSaida;

    private double valorUnidade;

    private int quantidade;

}
