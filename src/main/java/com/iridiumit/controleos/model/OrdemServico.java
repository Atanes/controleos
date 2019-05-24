package com.iridiumit.controleos.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class OrdemServico {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="data_emissao")
	@Temporal(TemporalType.DATE)
	private Date data_emissao;
	
	@Column(name="data_aprovacao")
	@Temporal(TemporalType.DATE)
	private Date data_aprovacao;
	
	@Column(name="data_encerramento")
	@Temporal(TemporalType.DATE)
	private Date data_encerramento;
	
	@NotBlank (message = "{defeito.not.blank}")
	private String defeitoAparelho;
	
	@NotBlank (message = "{estadoAparelho.not.blank}")
	private String estadoAparelho;
	
	private String acessorios;
	
	private String status;
	
	private String emissor;
	
	private String grupoTrabalho;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipamento_id", nullable = false)
	private Equipamento equipamento;

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public Date getData_emissao() {
		return data_emissao;
	}


	public void setData_emissao(Date data_emissao) {
		this.data_emissao = data_emissao;
	}


	public Date getData_aprovacao() {
		return data_aprovacao;
	}


	public void setData_aprovacao(Date data_aprovacao) {
		this.data_aprovacao = data_aprovacao;
	}


	public Date getData_encerramento() {
		return data_encerramento;
	}


	public void setData_encerramento(Date data_encerramento) {
		this.data_encerramento = data_encerramento;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getDefeitoAparelho() {
		return defeitoAparelho;
	}


	public void setDefeitoAparelho(String defeitoAparelho) {
		this.defeitoAparelho = defeitoAparelho;
	}


	public String getEstadoAparelho() {
		return estadoAparelho;
	}


	public void setEstadoAparelho(String estadoAparelho) {
		this.estadoAparelho = estadoAparelho;
	}


	public String getEmissor() {
		return emissor;
	}


	public void setEmissor(String emissor) {
		this.emissor = emissor;
	}
	
	public String getAcessorios() {
		return acessorios;
	}


	public void setAcessorios(String acessorios) {
		this.acessorios = acessorios;
	}
	
	
	public String getGrupoTrabalho() {
		return grupoTrabalho;
	}


	public void setGrupoTrabalho(String grupoTrabalho) {
		this.grupoTrabalho = grupoTrabalho;
	}


	public Cliente getCliente() {
		return cliente;
	}


	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}


	public Equipamento getEquipamento() {
		return equipamento;
	}


	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
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
		OrdemServico other = (OrdemServico) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	

}
