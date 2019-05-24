package com.iridiumit.controleos.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.iridiumit.controleos.model.Orcamento;

public interface Orcamentos extends JpaRepository<Orcamento, Long>{
	
	@Modifying
    @Transactional
    void deleteByOs_id(Long id);
}
