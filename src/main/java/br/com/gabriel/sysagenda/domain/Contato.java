package br.com.gabriel.sysagenda.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity()
@Table(name = "CONTATO")
public class Contato {

	@Id
	@Column(name = "COD_CONTATO")
	private int codContato;

	@Column(name = "NOME")
	private String nome;

	@Column(name = "TELEFONE")
	private Long telefone;

	public int getCodContato() {
		return codContato;
	}

	public void setCodContato(int codContato) {
		this.codContato = codContato;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getTelefone() {
		return telefone;
	}

	public void setTelefone(Long telefone) {
		this.telefone = telefone;
	}

	@Override
	public String toString() {
		return nome;
	}

}
