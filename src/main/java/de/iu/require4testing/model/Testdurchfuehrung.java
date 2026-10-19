package de.iu.require4testing.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Verknuepfung "Testfall X wird im Testlauf Y von Tester:in Z durchgefuehrt, Ergebnis W"
 */
@Entity
public class Testdurchfuehrung implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Ergebnis ergebnis = Ergebnis.OFFEN;

    @ManyToOne
    private Testlauf testlauf;

    @ManyToOne
    private Testfall testfall;

    @ManyToOne
    private Tester tester;

    public Testdurchfuehrung() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ergebnis getErgebnis() {
        return ergebnis;
    }

    public void setErgebnis(Ergebnis ergebnis) {
        this.ergebnis = ergebnis;
    }

    public Testlauf getTestlauf() {
        return testlauf;
    }

    public void setTestlauf(Testlauf testlauf) {
        this.testlauf = testlauf;
    }

    public Testfall getTestfall() {
        return testfall;
    }

    public void setTestfall(Testfall testfall) {
        this.testfall = testfall;
    }

    public Tester getTester() {
        return tester;
    }

    public void setTester(Tester tester) {
        this.tester = tester;
    }
}
