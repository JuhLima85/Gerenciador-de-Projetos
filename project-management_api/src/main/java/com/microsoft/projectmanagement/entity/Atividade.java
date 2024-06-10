package com.microsoft.projectmanagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tb_atividade")
public class Atividade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
    private String descricao;
    private String status;
    private String prioridade;

    @ManyToOne
    @JoinColumn(name = "projeto_id", nullable = true)
    private Projeto projeto;
}
