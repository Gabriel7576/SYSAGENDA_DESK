package br.com.gabriel.sysagenda.factory;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConnectionFactory {

	public static EntityManager getEntityManager() {

		try {
			disableLogging();
			EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("sysagenda_jpa");
			return entityManagerFactory.createEntityManager();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	private static void disableLogging() {
		
		  LogManager logManager = LogManager.getLogManager();
		  Logger logger = logManager.getLogger("");
		  logger.setLevel(Level.SEVERE); //could be Level.OFF
		}
}
