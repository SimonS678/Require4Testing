package de.iu.require4testing.model;

/**
 * Moegliche Ergebnisse einer Testdurchfuehrung.
 */
public enum Ergebnis {
    OFFEN("offen"),
    BESTANDEN("bestanden"),
    FEHLGESCHLAGEN("fehlgeschlagen");

    private final String bezeichnung;

    Ergebnis(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }
}
