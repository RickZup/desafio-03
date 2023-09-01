package com.catalisa.zupstore.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "TB_PRODUTOS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "produtoId")
    private Long id;

    private String nome;

    private double preco;

    private String descricao;

    @Column(name = "dataCriacao")
    private LocalDate dataCriacao;

    @ManyToOne
    @JoinColumn(name = "categoriaId")
    private CategoriaModel categoria;

    @Column(name = "quantidadeAtual")
    private int quantidadeAtual;

}
