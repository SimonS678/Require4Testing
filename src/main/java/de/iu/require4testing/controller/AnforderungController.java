package de.iu.require4testing.controller;

import de.iu.require4testing.model.Anforderung;
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
 * Controller der Seite anforderungen.xhtml (User Story 1).
 */
@Named
@ViewScoped
public class AnforderungController implements Serializable {

    @Inject
    private Require4TestingService service;

    private Anforderung anforderung = new Anforderung();
    private List<Anforderung> anforderungen;

    @PostConstruct
    public void init() {
        anforderungen = service.getAnforderungen();
    }

    public void speichern() {
        service.speichereAnforderung(anforderung);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Anforderung \"" + anforderung.getTitel() + "\" wurde gespeichert.", ""));
        anforderung = new Anforderung();
        anforderungen = service.getAnforderungen();
    }

    public Anforderung getAnforderung() {
        return anforderung;
    }

    public List<Anforderung> getAnforderungen() {
        return anforderungen;
    }
}
