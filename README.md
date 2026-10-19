# Require4Testing

Prototyp einer Web-App zur Organisation manueller Anwendertests (Fallstudie IPWA02-01, Aufgabenstellung 2).

## Umgesetzte User Stories

| Nr. | Priorität | User Story | Seite |
|---|---|---|---|
| 1 | MUST | Als Requirements Engineer möchte ich zu testende Anforderungen erstellen können. | `anforderungen.xhtml` |
| 2 | MUST | Als Testmanager:in möchte ich Testläufe anlegen können. | `testlaeufe.xhtml` |
| 3 | MUST | Als Testfallersteller:in möchte ich zu einer Anforderung Testfälle erstellen können. | `testfaelle.xhtml` |
| 4 | SHOULD | Als Testmanager:in möchte ich einem Testlauf verschiedene Testfälle und eine:n Tester:in zuordnen können. | `testlauf-detail.xhtml` |
| 5 | SHOULD | Als Tester:in möchte ich mir zugeordnete Testfälle mit einem Ergebnis versehen können. | `tester-ergebnisse.xhtml` |

## Technologiestack

- JSF 2.3 (Facelets/XHTML) + CDI (`@Named`, `@ViewScoped`, `@ApplicationScoped`, `@Inject`, `@Produces`)
- PrimeFaces 15.0.18 (letzte javax-kompatible Version)
- JPA 2.2 mit Hibernate 5.6 als Persistence Provider (`RESOURCE_LOCAL`, Application-Managed `EntityManagerFactory`)
- MySQL 8.0
- Apache TomEE 8.0.16 (webprofile, Java 17)
- Maven (Build läuft im Docker-Multi-Stage-Build, keine lokale Installation nötig)

## Architektur

```
src/main/java/de/iu/require4testing/
├── model/        Entitäten: Anforderung, Testfall, Testlauf, Tester, Testdurchfuehrung (+ Enum Ergebnis)
├── dao/          je Entität ein DAO (findAll, findById, persist, merge, remove) mit expliziten Transaktionen
├── persistence/  EntityManagerFactoryProducer (@ApplicationScoped, @Produces EntityManagerFactory)
├── service/      Require4TestingService (@Named @ApplicationScoped), instanziiert und koordiniert die DAOs
└── controller/   je Seite ein @Named @ViewScoped Controller
src/main/webapp/
├── WEB-INF/      web.xml, faces-config.xml, beans.xml, template.xhtml
├── resources/css/style.css
└── index, anforderungen, testfaelle, testlaeufe, testlauf-detail, tester-ergebnisse (.xhtml)
src/main/resources/
├── META-INF/persistence.xml
└── nachrichten.properties   eigene deutschsprachige Validierungsmeldungen
```

Hibernate legt das Schema per `hibernate.hbm2ddl.auto=update` beim ersten Zugriff selbst an
(5 Tabellen: `Anforderung`, `Testfall`, `Testlauf`, `Tester`, `Testdurchfuehrung`).

## Starten mit Docker (empfohlen)

Voraussetzung: Docker Desktop läuft.

```bash
docker compose up --build
```

Danach im Browser öffnen: <http://localhost:8080/require4testing/>

- Der Container `r4t-mysql` startet zuerst; `r4t-app` (TomEE) startet erst, wenn die Datenbank per Healthcheck bereit ist.
- Die Daten liegen im Docker-Volume `r4t-db` und bleiben über Neustarts erhalten.
- Beide Ports sind nur lokal (`127.0.0.1`) erreichbar.
- Beenden: `Strg+C` bzw. `docker compose down`. Datenbank zusätzlich leeren: `docker compose down -v`.
- Nach Codeänderungen erneut `docker compose up --build` ausführen.

Zugangsdaten der Datenbank (nur für den lokalen Prototyp): Datenbank `require4testing`, Benutzer `r4t`, Passwort `r4t`
(root-Passwort: `root`).

## Konfiguration der Datenbankverbindung

`persistence.xml` enthält Standardwerte für einen lokalen Start (`localhost:3306`). Der
`EntityManagerFactoryProducer` überschreibt diese, wenn die Umgebungsvariablen `DB_URL`, `DB_USER`
bzw. `DB_PASSWORD` gesetzt sind. Das nutzt `docker-compose.yml`, da die Datenbank im Container-Netzwerk
unter dem Hostnamen `mysql` erreichbar ist.

## Ablauf zum Ausprobieren

1. **Anforderungen** → Anforderung erfassen.
2. **Testfälle** → Anforderung wählen, Testfall mit erwartetem Ergebnis anlegen.
3. **Testläufe** → Testlauf mit Startdatum anlegen, dann „Testfälle zuordnen“ klicken.
4. **Zuordnung** → ggf. unten eine:n Tester:in anlegen, Testfälle ankreuzen, Tester:in wählen, „Zuordnen“.
   Ein Wechsel des Testlaufs aktualisiert die Tabelle per AJAX.
5. **Ergebnisse erfassen** → Tester:in wählen (AJAX) und je Testfall ein Ergebnis wählen; es wird sofort gespeichert.
