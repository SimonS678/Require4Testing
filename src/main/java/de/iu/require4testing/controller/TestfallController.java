package de.iu.require4testing.controller;

import de.iu.require4testing.model.Anforderung;
import de.iu.require4testing.model.Testfall;
import de.iu.require4testing.service.Require4TestingService;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Controller der Seite testfaelle.xhtml (User Story 3).
 */
@Named
@ViewScoped
public class TestfallController implements Serializable {

    @Inject
    private Require4TestingService service;

    private Testfall testfall = new Testfall();
    private Long anforderungId;
    private List<Anforderung> anforderungen;
    private List<Testfall> testfaelle;

    @PostConstruct
    public void init() {
        anforderungen = service.getAnforderungen();
        testfaelle = service.getTestfaelle();
    }

    public void speichern() {
        service.speichereTestfall(testfall, anforderungId);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Testfall \"" + testfall.getTitel() + "\" wurde angelegt.", ""));
        testfall = new Testfall();
        testfaelle = service.getTestfaelle();
    }

    public Testfall getTestfall() {
        return testfall;
    }

    public Long getAnforderungId() {
        return anforderungId;
    }

    public void setAnforderungId(Long anforderungId) {
        this.anforderungId = anforderungId;
    }

    public List<Anforderung> getAnforderungen() {
        return anforderungen;
    }

    public List<Testfall> getTestfaelle() {
        return testfaelle;
    }
}
