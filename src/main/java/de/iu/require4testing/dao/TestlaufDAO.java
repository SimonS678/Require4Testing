package de.iu.require4testing.dao;

import de.iu.require4testing.model.Testlauf;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 * Kapselt alle Datenbankzugriffe auf die Entitaet Testlauf.
 */
public class TestlaufDAO {

    private final EntityManagerFactory emf;

    public TestlaufDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public List<Testlauf> findAll() {
        EntityManager em = emf.createEntityManager();
        Query abfrage = em.createQuery("select tl from Testlauf tl order by tl.startdatum");
        List<Testlauf> ergebnis = abfrage.getResultList();
        em.close();
        return ergebnis;
    }

    public Testlauf findById(Long id) {
        EntityManager em = emf.createEntityManager();
        Testlauf ergebnis = em.find(Testlauf.class, id);
        em.close();
        return ergebnis;
    }
    public void persist(Testlauf tl) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            em.persist(tl);
            t.commit();
        } finally {
            if (t.isActive()) {
                t.rollback();
            }
            em.close();
        }
    }

    public Testlauf merge(Testlauf tl) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            Testlauf aktualisiert = em.merge(tl);
            t.commit();
            return aktualisiert;
        } finally {
            if (t.isActive()) {
                t.rollback();
            }
            em.close();
        }
    }

    public void remove(Testlauf tl) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            em.remove(em.merge(tl));
            t.commit();
        } finally {
            if (t.isActive()) {
                t.rollback();
            }
            em.close();
        }
    }
}
