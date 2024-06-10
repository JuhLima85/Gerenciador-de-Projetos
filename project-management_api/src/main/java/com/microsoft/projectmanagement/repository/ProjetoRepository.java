package com.microsoft.projectmanagement.repository;

import com.microsoft.projectmanagement.entity.Projeto;
import com.microsoft.projectmanagement.entity.enums.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
    List<Projeto> findByStatus(StatusEnum status);
}
