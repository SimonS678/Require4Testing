package de.iu.require4testing.controller;

import de.iu.require4testing.model.Testdurchfuehrung;
import de.iu.require4testing.model.Tester;
import de.iu.require4testing.model.Testfall;
import de.iu.require4testing.model.Testlauf;
import de.iu.require4testing.service.Require4TestingService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Controller der Seite testlauf-detail.xhtml (User Story 4): ordnet einem Testlauf
 * Testfaelle und eine:n Tester:in zu.
 */
@Named
@ViewScoped
public class TestlaufDetailController implements Serializable {

    @Inject
    private Require4TestingService service;

    private Long testlaufId;
    private List<Long> testfallIds = new ArrayList<>();
    private Long testerId;
    private Tester neuerTester = new Tester();

    private List<Testlauf> testlaeufe;
    private List<Testfall> testfaelle;
    private List<Tester> tester;
    private List<Testdurchfuehrung> testdurchfuehrungen = new ArrayList<>();

    @PostConstruct
    public void init() {
        testlaeufe = service.getTestlaeufe();
        testfaelle = service.getTestfaelle();
        tester = service.getTester();
    }

    /**
     * Wird beim Seitenaufruf (Parameter testlaufId) und per AJAX bei Auswahl eines Testlaufs aufgerufen.
     */
    public void testlaufGewechselt() {
        if (testlaufId == null) {
            testdurchfuehrungen = new ArrayList<>();
        } else {
            testdurchfuehrungen = service.getTestdurchfuehrungenZuTestlauf(testlaufId);
        }
    }

    public void zuordnen() {
        int angelegt = service.ordneTestfaelleZu(testlaufId, testfallIds, testerId);
        int uebersprungen = testfallIds.size() - angelegt;
        String text = angelegt + " Testfall/Testfälle zugeordnet.";
        if (uebersprungen > 0) {
            text += " " + uebersprungen + " war(en) diesem Testlauf bereits zugeordnet.";
        }
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, text, ""));
        testfallIds = new ArrayList<>();
        testlaufGewechselt();
    }

    public void entfernen(Testdurchfuehrung durchfuehrung) {
        service.entferneTestdurchfuehrung(durchfuehrung);
        testlaufGewechselt();
    }

    public void testerAnlegen() {
        service.speichereTester(neuerTester);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Tester:in \"" + neuerTester.getName() + "\" wurde angelegt.", ""));
        neuerTester = new Tester();
        tester = service.getTester();
    }

    public Long getTestlaufId() {
        return testlaufId;
    }

    public void setTestlaufId(Long testlaufId) {
        this.testlaufId = testlaufId;
    }

    public List<Long> getTestfallIds() {
        return testfallIds;
    }

    public void setTestfallIds(List<Long> testfallIds) {
        this.testfallIds = testfallIds;
    }

    public Long getTesterId() {
        return testerId;
    }

    public void setTesterId(Long testerId) {
        this.testerId = testerId;
    }

    public Tester getNeuerTester() {
        return neuerTester;
    }

    public List<Testlauf> getTestlaeufe() {
        return testlaeufe;
    }

    public List<Testfall> getTestfaelle() {
        return testfaelle;
    }

    public List<Tester> getTester() {
        return tester;
    }

    public List<Testdurchfuehrung> getTestdurchfuehrungen() {
        return testdurchfuehrungen;
    }
}
