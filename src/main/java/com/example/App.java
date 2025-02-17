package com.example;

import java.io.PrintWriter;
import java.util.Properties;

import org.eclipse.persistence.config.PersistenceUnitProperties;

import com.ibm.db2.jcc.DB2BaseDataSource;
import com.ibm.db2.jcc.DB2TraceManager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

class App {
    public static void main(String[] args) throws Exception {
        DB2TraceManager.getTraceManager().setLogWriter(new PrintWriter(System.out), DB2BaseDataSource.TRACE_STATEMENT_CALLS | DB2BaseDataSource.TRACE_CONNECTION_CALLS);
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
