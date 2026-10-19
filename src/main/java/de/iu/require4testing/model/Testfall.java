package de.iu.require4testing.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Testfall zu genau einer Anforderung
 */
@Entity
public class Testfall implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String titel;
    private String beschreibung;
    private String erwartetesErgebnis;

    @ManyToOne
    private Anforderung anforderung;

    public Testfall() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public String getErwartetesErgebnis() {
        return erwartetesErgebnis;
    }

    public void setErwartetesErgebnis(String erwartetesErgebnis) {
        this.erwartetesErgebnis = erwartetesErgebnis;
    }

    public Anforderung getAnforderung() {
        return anforderung;
    }

    public void setAnforderung(Anforderung anforderung) {
        this.anforderung = anforderung;
    }
}
