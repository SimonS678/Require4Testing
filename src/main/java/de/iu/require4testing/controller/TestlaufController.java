package de.iu.require4testing.controller;

import de.iu.require4testing.model.Testlauf;
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
 * Controller der Seite testlaeufe.xhtml (User Story 2).
 */
@Named
@ViewScoped
public class TestlaufController implements Serializable {

    @Inject
    private Require4TestingService service;

    private Testlauf testlauf = new Testlauf();
    private List<Testlauf> testlaeufe;

    @PostConstruct
    public void init() {
        testlaeufe = service.getTestlaeufe();
    }

    public void speichern() {
        service.speichereTestlauf(testlauf);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Testlauf \"" + testlauf.getBezeichnung() + "\" wurde angelegt.", ""));
        testlauf = new Testlauf();
        testlaeufe = service.getTestlaeufe();
    }

    /**
     * Navigation zur Detailseite, auf der dem Testlauf Testfaelle zugeordnet werden.
     */
    public String zuordnen(Testlauf ausgewaehlt) {
        return "testlauf-detail?faces-redirect=true&testlaufId=" + ausgewaehlt.getId();
    }

    public Testlauf getTestlauf() {
        return testlauf;
    }

    public List<Testlauf> getTestlaeufe() {
        return testlaeufe;
    }
}
