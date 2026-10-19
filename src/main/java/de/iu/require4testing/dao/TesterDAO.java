package de.iu.require4testing.dao;

import de.iu.require4testing.model.Tester;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 * Kapselt alle Datenbankzugriffe auf die Entitaet Tester.
 */
public class TesterDAO {

    private final EntityManagerFactory emf;

    public TesterDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public List<Tester> findAll() {
        EntityManager em = emf.createEntityManager();
        Query abfrage = em.createQuery("select te from Tester te order by te.name");
        List<Tester> ergebnis = abfrage.getResultList();
        em.close();
        return ergebnis;
    }

    public Tester findById(Long id) {
        EntityManager em = emf.createEntityManager();
        Tester ergebnis = em.find(Tester.class, id);
        em.close();
        return ergebnis;
    }
    public void persist(Tester te) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            em.persist(te);
            t.commit();
        } finally {
            if (t.isActive()) {
                t.rollback();
            }
            em.close();
        }
    }

    public Tester merge(Tester te) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            Tester aktualisiert = em.merge(te);
            t.commit();
            return aktualisiert;
        } finally {
            if (t.isActive()) {
                t.rollback();
            }
            em.close();
        }
    }

    public void remove(Tester te) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            em.remove(em.merge(te));
            t.commit();
        } finally {
            if (t.isActive()) {
                t.rollback();
            }
            em.close();
        }
    }
}
