package de.iu.require4testing.dao;

import de.iu.require4testing.model.Anforderung;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 * Kapselt alle Datenbankzugriffe auf die Entitaet Anforderung.
 */
public class AnforderungDAO {

    private final EntityManagerFactory emf;

    public AnforderungDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public List<Anforderung> findAll() {
        EntityManager em = emf.createEntityManager();
        Query abfrage = em.createQuery("select a from Anforderung a order by a.titel");
        List<Anforderung> ergebnis = abfrage.getResultList();
        em.close();
        return ergebnis;
    }

    public Anforderung findById(Long id) {
        EntityManager em = emf.createEntityManager();
        Anforderung ergebnis = em.find(Anforderung.class, id);
        em.close();
        return ergebnis;
    }
    public void persist(Anforderung a) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            em.persist(a);
            t.commit();
        } finally {
            if (t.isActive()) {
                t.rollback();
            }
            em.close();
        }
    }

    public Anforderung merge(Anforderung a) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            Anforderung aktualisiert = em.merge(a);
            t.commit();
            return aktualisiert;
        } finally {
            if (t.isActive()) {
                t.rollback();
            }
            em.close();
        }
    }

    public void remove(Anforderung a) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            em.remove(em.merge(a));
            t.commit();
        } finally {
            if (t.isActive()) {
                t.rollback();
            }
            em.close();
        }
    }
}
