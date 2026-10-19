package de.iu.require4testing.controller;

import de.iu.require4testing.model.Ergebnis;
import de.iu.require4testing.model.Testdurchfuehrung;
import de.iu.require4testing.model.Tester;
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
 * Controller der Seite tester-ergebnisse.xhtml (User Story 5): Tester:in versieht
 * zugeordnete Testfaelle mit einem Ergebnis.
 */
@Named
@ViewScoped
public class TesterErgebnisseController implements Serializable {

    @Inject
    private Require4TestingService service;

    private Long testerId;
    private List<Tester> tester;
    private List<Testdurchfuehrung> testdurchfuehrungen = new ArrayList<>();

    @PostConstruct
    public void init() {
        tester = service.getTester();
    }

    /**
     * Wird per AJAX bei Auswahl einer Tester:in aufgerufen.
     */
    public void testerGewechselt() {
        if (testerId == null) {
            testdurchfuehrungen = new ArrayList<>();
        } else {
            testdurchfuehrungen = service.getTestdurchfuehrungenZuTester(testerId);
        }
    }

    public void ergebnisSpeichern(Testdurchfuehrung durchfuehrung) {
        service.speichereErgebnis(durchfuehrung);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Ergebnis für \"" + durchfuehrung.getTestfall().getTitel() + "\" gespeichert: "
                        + durchfuehrung.getErgebnis().getBezeichnung() + ".", ""));
    }

    public Ergebnis[] getErgebnisse() {
        return Ergebnis.values();
    }

    public Long getTesterId() {
        return testerId;
    }

    public void setTesterId(Long testerId) {
        this.testerId = testerId;
    }

    public List<Tester> getTester() {
        return tester;
    }

    public List<Testdurchfuehrung> getTestdurchfuehrungen() {
        return testdurchfuehrungen;
    }
}
