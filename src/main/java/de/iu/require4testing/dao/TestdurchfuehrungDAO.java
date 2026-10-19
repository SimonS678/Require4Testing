package de.iu.require4testing.dao;

import de.iu.require4testing.model.Testdurchfuehrung;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 * Kapselt alle Datenbankzugriffe auf die Entitaet Testdurchfuehrung.
 */
public class TestdurchfuehrungDAO {

    private final EntityManagerFactory emf;

    public TestdurchfuehrungDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public List<Testdurchfuehrung> findAll() {
        EntityManager em = emf.createEntityManager();
        Query abfrage = em.createQuery("select d from Testdurchfuehrung d order by d.id");
        List<Testdurchfuehrung> ergebnis = abfrage.getResultList();
        em.close();
        return ergebnis;
    }

    public Testdurchfuehrung findById(Long id) {
        EntityManager em = emf.createEntityManager();
        Testdurchfuehrung ergebnis = em.find(Testdurchfuehrung.class, id);
        em.close();
        return ergebnis;
    }

    public List<Testdurchfuehrung> findByTestlauf(Long testlaufId) {
        EntityManager em = emf.createEntityManager();
        Query abfrage = em.createQuery(
                "select d from Testdurchfuehrung d where d.testlauf.id = :testlaufId order by d.testfall.titel");
        abfrage.setParameter("testlaufId", testlaufId);
        List<Testdurchfuehrung> ergebnis = abfrage.getResultList();
        em.close();
        return ergebnis;
    }

    public List<Testdurchfuehrung> findByTester(Long testerId) {
        EntityManager em = emf.createEntityManager();
        Query abfrage = em.createQuery(
                "select d from Testdurchfuehrung d where d.tester.id = :testerId"
                        + " order by d.testlauf.startdatum, d.testfall.titel");
        abfrage.setParameter("testerId", testerId);
        List<Testdurchfuehrung> ergebnis = abfrage.getResultList();
        em.close();
        return ergebnis;
    }

    public boolean existiert(Long testlaufId, Long testfallId) {
        EntityManager em = emf.createEntityManager();
        Query abfrage = em.createQuery(
                "select count(d) from Testdurchfuehrung d"
                        + " where d.testlauf.id = :testlaufId and d.testfall.id = :testfallId");
        abfrage.setParameter("testlaufId", testlaufId);
        abfrage.setParameter("testfallId", testfallId);
        long anzahl = (Long) abfrage.getSingleResult();
        em.close();
        return anzahl > 0;
    }
    public void persist(Testdurchfuehrung d) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            em.persist(d);
            t.commit();
        } finally {
            if (t.isActive()) {
                t.rollback();
            }
            em.close();
        }
    }

    public Testdurchfuehrung merge(Testdurchfuehrung d) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            Testdurchfuehrung aktualisiert = em.merge(d);
            t.commit();
            return aktualisiert;
        } finally {
            if (t.isActive()) {
                t.rollback();
            }
            em.close();
        }
    }

    public void remove(Testdurchfuehrung d) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            em.remove(em.merge(d));
            t.commit();
        } finally {
            if (t.isActive()) {
                t.rollback();
            }
            em.close();
        }
    }
}
