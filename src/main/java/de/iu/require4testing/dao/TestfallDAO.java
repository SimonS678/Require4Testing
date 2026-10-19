package de.iu.require4testing.dao;

import de.iu.require4testing.model.Testfall;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 * Kapselt alle Datenbankzugriffe auf die Entitaet Testfall.
 */
public class TestfallDAO {

    private final EntityManagerFactory emf;

    public TestfallDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public List<Testfall> findAll() {
        EntityManager em = emf.createEntityManager();
        Query abfrage = em.createQuery("select tf from Testfall tf order by tf.titel");
        List<Testfall> ergebnis = abfrage.getResultList();
        em.close();
        return ergebnis;
    }

    public Testfall findById(Long id) {
        EntityManager em = emf.createEntityManager();
        Testfall ergebnis = em.find(Testfall.class, id);
        em.close();
        return ergebnis;
    }
    public void persist(Testfall tf) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            em.persist(tf);
            t.commit();
        } finally {
            if (t.isActive()) {
                t.rollback();
            }
            em.close();
        }
    }

    public Testfall merge(Testfall tf) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            Testfall aktualisiert = em.merge(tf);
            t.commit();
            return aktualisiert;
        } finally {
            if (t.isActive()) {
                t.rollback();
            }
            em.close();
        }
    }

    public void remove(Testfall tf) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            em.remove(em.merge(tf));
            t.commit();
        } finally {
            if (t.isActive()) {
                t.rollback();
            }
            em.close();
        }
    }
}
