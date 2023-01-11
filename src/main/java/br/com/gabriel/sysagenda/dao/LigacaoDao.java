package br.com.gabriel.sysagenda.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.gabriel.sysagenda.domain.Contato;
import br.com.gabriel.sysagenda.domain.Ligacao;
import br.com.gabriel.sysagenda.domain.LigacaoId;
import br.com.gabriel.sysagenda.factory.ConnectionFactory;

public class LigacaoDao {

	private EntityManager em;

	public LigacaoDao() {
		em = ConnectionFactory.getEntityManager();
	}

	public void adiciona(Ligacao ligacao) {
		try {
			em.getTransaction().begin();
			em.persist(ligacao);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
			throw new RuntimeException(e);
		}
	}

	public Ligacao getLigacao(LigacaoId id) {

		try {
			return em.find(Ligacao.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Ligacao> lista() {
		try {
			Query query = em.createQuery("select o from Ligacao o");
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void alterar(Ligacao ligacao) {
		try {
			em.getTransaction().begin();
			em.merge(ligacao);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
			throw new RuntimeException(e);
		}
	}

	public void deletaUma(Ligacao ligacao) {
		try {
			em.getTransaction().begin();
			Ligacao obj = getLigacao(ligacao.getId());
			em.remove(obj);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
			throw new RuntimeException(e);
		}
	}

	public void deletaTodas(Contato contato) {
		try {

			em.getTransaction().begin();
			Query query = em.createQuery("delete from Ligacao l where id.codContato = :codContato");
			query.setParameter("codContato", contato.getCodContato());
			query.executeUpdate();
			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
			throw new RuntimeException(e);
		}
	}

	public Integer getUtlCodLigacao(Integer codContato) {
		try {
			Query query = em
					.createQuery("select nvl(max(id.codLigacao), 0) from Ligacao where id.codContato = :codContato");
			query.setParameter("codContato", codContato);
			return (Integer) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
