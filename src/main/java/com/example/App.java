package com.example;

import java.util.Properties;

import org.eclipse.persistence.config.PersistenceUnitProperties;

import com.ibm.db2.jcc.DB2Driver;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

class App {
    public static void main(String[] args) {
        Properties prop = new Properties();
        prop.setProperty(PersistenceUnitProperties.JDBC_URL, "jdbc:db2://localhost:50000/DB");
        prop.setProperty(PersistenceUnitProperties.JDBC_USER, "db2inst1");
        prop.setProperty(PersistenceUnitProperties.JDBC_PASSWORD, "db2inst1");
        prop.setProperty(PersistenceUnitProperties.DDL_GENERATION, "drop-and-create-tables");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU", prop);
        for(int i = 0; i < 1600; i++) {
            final EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            var ent = new StoredEntity();
            ent.value = 1337L;
            em.persist(ent);
            em.getTransaction().commit();
            em.close();
        }
    }
}
