package com.iridiumit.controleos.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Orcamento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank (message = "{parecer.not.blank}")
	private String parecer;
	
	@NotBlank (message = "{pecas.not.blank}")
	private String pecas;
	
	@NotBlank (message = "{tempo.not.blank}")
	private String tempo;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "os_id", nullable = false)
	@JsonBackReference
    private OrdemServico os;

	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Usuario_login", nullable = false)
	@JsonBackReference
    private Usuario usuario;
	
	public Orcamento(){
		
	}

	public Orcamento(Long id, String parecer, String pecas, String tempo, OrdemServico os, Usuario usuario) {
		super();
		this.id = id;
		this.parecer = parecer;
		this.pecas = pecas;
		this.tempo = tempo;
		this.os = os;
		this.usuario = usuario;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getParecer() {
		return parecer;
	}

	public void setParecer(String parecer) {
		this.parecer = parecer;
	}

	public String getPecas() {
		return pecas;
	}

	public void setPecas(String pecas) {
		this.pecas = pecas;
	}

	public String getTempo() {
		return tempo;
	}

	public void setTempo(String tempo) {
		this.tempo = tempo;
	}

	public OrdemServico getOs() {
		return os;
	}

	public void setOs(OrdemServico os) {
		this.os = os;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Orcamento other = (Orcamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}