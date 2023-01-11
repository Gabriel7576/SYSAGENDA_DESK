package br.com.gabriel.sysagenda.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "LIGACAO")
public class Ligacao implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private LigacaoId id;

	@Column(name = "DATA_HORA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHora;

	@Column(name = "OBSERVACAO")
	private String obs;

	public LigacaoId getId() {
		return id;
	}

	public void setId(LigacaoId id) {
		this.id = id;
	}

	public Date getDataHora() {
		return dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	@Override
	public String toString() {
		return "Ligacao [id=" + id + ", dataHora=" + dataHora + ", obs=" + obs + "]";
	}

}
