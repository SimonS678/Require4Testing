package de.iu.require4testing.service;

import de.iu.require4testing.dao.AnforderungDAO;
import de.iu.require4testing.dao.TestdurchfuehrungDAO;
import de.iu.require4testing.dao.TesterDAO;
import de.iu.require4testing.dao.TestfallDAO;
import de.iu.require4testing.dao.TestlaufDAO;
import de.iu.require4testing.model.Anforderung;
import de.iu.require4testing.model.Testdurchfuehrung;
import de.iu.require4testing.model.Tester;
import de.iu.require4testing.model.Testfall;
import de.iu.require4testing.model.Testlauf;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManagerFactory;

/**
 * Anwendungsweites Geschaeftsobjekt: koordiniert die Zugriffe der Controller auf die DAOs.
 */
@Named
@ApplicationScoped
public class Require4TestingService {

    @Inject
    private EntityManagerFactory emf;

    private AnforderungDAO anforderungDao;
    private TestfallDAO testfallDao;
    private TestlaufDAO testlaufDao;
    private TesterDAO testerDao;
    private TestdurchfuehrungDAO testdurchfuehrungDao;

    @PostConstruct
    public void init() {
        anforderungDao = new AnforderungDAO(emf);
        testfallDao = new TestfallDAO(emf);
        testlaufDao = new TestlaufDAO(emf);
        testerDao = new TesterDAO(emf);
        testdurchfuehrungDao = new TestdurchfuehrungDAO(emf);
    }

    // User Story 1: Anforderungen

    public List<Anforderung> getAnforderungen() {
        return anforderungDao.findAll();
    }

    public Anforderung findeAnforderung(Long id) {
        return anforderungDao.findById(id);
    }

    public void speichereAnforderung(Anforderung anforderung) {
        anforderungDao.persist(anforderung);
    }

    // User Story 2: Testlaeufe

    public List<Testlauf> getTestlaeufe() {
        return testlaufDao.findAll();
    }

    public Testlauf findeTestlauf(Long id) {
        return testlaufDao.findById(id);
    }

    public void speichereTestlauf(Testlauf testlauf) {
        testlaufDao.persist(testlauf);
    }

    // User Story 3: Testfaelle zu einer Anforderung

    public List<Testfall> getTestfaelle() {
        return testfallDao.findAll();
    }

    public void speichereTestfall(Testfall testfall, Long anforderungId) {
        testfall.setAnforderung(anforderungDao.findById(anforderungId));
        testfallDao.persist(testfall);
    }

    // Tester:innen (Voraussetzung fuer User Story 4 und 5)

    public List<Tester> getTester() {
        return testerDao.findAll();
    }

    public Tester findeTester(Long id) {
        return testerDao.findById(id);
    }

    public void speichereTester(Tester tester) {
        testerDao.persist(tester);
    }

    // User Story 4: Testfaelle und Tester:in einem Testlauf zuordnen

    public List<Testdurchfuehrung> getTestdurchfuehrungenZuTestlauf(Long testlaufId) {
        return testdurchfuehrungDao.findByTestlauf(testlaufId);
    }

    /**
     * Legt fuer jeden uebergebenen Testfall eine Testdurchfuehrung im Testlauf an.
     * Bereits zugeordnete Testfaelle werden uebersprungen.
     *
     * @return Anzahl der neu angelegten Zuordnungen
     */
    public int ordneTestfaelleZu(Long testlaufId, List<Long> testfallIds, Long testerId) {
        Testlauf testlauf = testlaufDao.findById(testlaufId);
        Tester tester = testerDao.findById(testerId);
        int angelegt = 0;
        for (Long testfallId : testfallIds) {
            if (testdurchfuehrungDao.existiert(testlaufId, testfallId)) {
                continue;
            }
            Testdurchfuehrung durchfuehrung = new Testdurchfuehrung();
            durchfuehrung.setTestlauf(testlauf);
            durchfuehrung.setTestfall(testfallDao.findById(testfallId));
            durchfuehrung.setTester(tester);
            testdurchfuehrungDao.persist(durchfuehrung);
            angelegt++;
        }
        return angelegt;
    }

    public void entferneTestdurchfuehrung(Testdurchfuehrung durchfuehrung) {
        testdurchfuehrungDao.remove(durchfuehrung);
    }

    // User Story 5: Tester:in versieht zugeordnete Testfaelle mit einem Ergebnis

    public List<Testdurchfuehrung> getTestdurchfuehrungenZuTester(Long testerId) {
        return testdurchfuehrungDao.findByTester(testerId);
    }

    public void speichereErgebnis(Testdurchfuehrung durchfuehrung) {
        testdurchfuehrungDao.merge(durchfuehrung);
    }
}
