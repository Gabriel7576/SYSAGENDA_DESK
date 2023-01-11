package br.com.gabriel.sysagenda.business;

import java.util.List;

import br.com.gabriel.sysagenda.dao.LigacaoDao;
import br.com.gabriel.sysagenda.domain.Contato;
import br.com.gabriel.sysagenda.domain.Ligacao;
import br.com.gabriel.sysagenda.domain.LigacaoId;

public class LigacaoBss {

	LigacaoDao dao = new LigacaoDao();

	public void adiciona(Ligacao ligacao) {

		dao.adiciona(ligacao);
	}

	public List<Ligacao> getLista() {

		return dao.lista();
	}

	public Ligacao getLigacao(LigacaoId id) {

		return dao.getLigacao(id);
	}

	public void alteraLigacao(Ligacao ligacao) {

		dao.alterar(ligacao);
	}

	public void deletaLigacoes(Contato contato) {

		dao.deletaTodas(contato);
	}

	public void deletaLigacao(Ligacao ligacao) {

		dao.deletaUma(ligacao);
	}

	public Integer getCodLigacao(Integer codContato) {

		return dao.getUtlCodLigacao(codContato);
	}

}
