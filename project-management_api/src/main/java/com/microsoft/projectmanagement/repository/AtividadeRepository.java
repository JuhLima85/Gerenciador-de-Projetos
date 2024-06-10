package com.microsoft.projectmanagement.repository;

import com.microsoft.projectmanagement.entity.Atividade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AtividadeRepository extends JpaRepository<Atividade, Long> {
    List<Atividade> findByProjetoId(Long projetoId);
    List<Atividade> findByStatus(String status);
}
