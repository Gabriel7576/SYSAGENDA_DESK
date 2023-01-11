package br.com.gabriel.sysagenda.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class LigacaoId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "COD_CONTATO")
	private int codContato;
	
	@Column(name = "COD_LIGACAO")
	private int codLigacao;

	public int getCodContato() {
		return codContato;
	}

	public void setCodContato(int codContato) {
		this.codContato = codContato;
	}

	public int getCodLigacao() {
		return codLigacao;
	}

	public void setCodLigacao(int codLigacao) {
		this.codLigacao = codLigacao;
	}

	@Override
	public String toString() {
		return "LigacaoId [codContato=" + codContato + ", codLigacao=" + codLigacao + "]";
	}

	
}
