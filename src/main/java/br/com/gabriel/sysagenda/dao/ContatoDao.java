package br.com.gabriel.sysagenda.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.gabriel.sysagenda.domain.Contato;
import br.com.gabriel.sysagenda.factory.ConnectionFactory;

public class ContatoDao {

	private EntityManager em;

	public ContatoDao() {
		em = ConnectionFactory.getEntityManager();
	}

	public void adiciona(Contato contato) {

		try {
			em.getTransaction().begin();
			em.persist(contato);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Contato> lista() {

		try {
			Query query = em.createQuery("select o from Contato o order by codContato");
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public Contato getContato(Integer codContato) {

		try {
			return em.find(Contato.class, codContato);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void altera(Contato contato) {

		try {
			em.getTransaction().begin();
			em.merge(contato);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
			throw new RuntimeException(e);
		}
	}

	public void deleta(Contato contato) {

		try {
			em.getTransaction().begin();
			Contato obj = getContato(contato.getCodContato());
			em.remove(obj);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
			throw new RuntimeException(e);
		}
	}

	public Integer getUtlCodContato() {

		try {
			Query query = em.createQuery("select max(codContato) from Contato");
			return (Integer) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
