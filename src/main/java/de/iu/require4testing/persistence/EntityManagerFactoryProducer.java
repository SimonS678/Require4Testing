package de.iu.require4testing.persistence;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Stellt eine anwendungsweit gemeinsam genutzte EntityManagerFactory per CDI bereit.
 * Die JDBC-Verbindungsdaten stammen aus der persistence.xml und koennen ueber die
 * Umgebungsvariablen DB_URL, DB_USER und DB_PASSWORD ueberschrieben werden
 * (z. B. im Docker-Container, in dem die Datenbank unter dem Hostnamen "mysql" erreichbar ist).
 */
@ApplicationScoped
public class EntityManagerFactoryProducer {

    private static final String PERSISTENCE_UNIT = "require4testing";

    private EntityManagerFactory emf;

    @PostConstruct
    public void init() {
        Map<String, String> properties = new HashMap<>();
        uebernehmeUmgebungsvariable(properties, "DB_URL", "javax.persistence.jdbc.url");
        uebernehmeUmgebungsvariable(properties, "DB_USER", "javax.persistence.jdbc.user");
        uebernehmeUmgebungsvariable(properties, "DB_PASSWORD", "javax.persistence.jdbc.password");
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT, properties);
    }

    @Produces
    public EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }

    @PreDestroy
    public void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    private void uebernehmeUmgebungsvariable(Map<String, String> properties, String variable, String property) {
        String wert = System.getenv(variable);
        if (wert != null && !wert.isEmpty()) {
            properties.put(property, wert);
        }
    }
}
