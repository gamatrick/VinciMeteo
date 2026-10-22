package fr.vinci.vincimeteo.cache;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CacheMaisonTest {

    @Test
    void memeCleAppelleLeChargeurUneSeuleFois() {
        CacheMaison<String, String> cache = new CacheMaison<>(Duration.ofMinutes(1));
        AtomicInteger appels = new AtomicInteger();

        cache.get("Paris", cle -> "valeur " + appels.incrementAndGet());
        String resultat = cache.get("Paris", cle -> "valeur " + appels.incrementAndGet());

        assertEquals("valeur 1", resultat);
        assertEquals(1, appels.get());
    }

    @Test
    void cleDifferenteAppelleLeChargeur() {
        CacheMaison<String, String> cache = new CacheMaison<>(Duration.ofMinutes(1));
        AtomicInteger appels = new AtomicInteger();

        cache.get("Paris", cle -> "p" + appels.incrementAndGet());
        cache.get("Lyon", cle -> "l" + appels.incrementAndGet());

        assertEquals(2, appels.get());
        assertEquals(2, cache.taille());
    }

    @Test
    void valeurExpireeEstRechargee() throws InterruptedException {
        CacheMaison<String, String> cache = new CacheMaison<>(Duration.ofMillis(100));
        AtomicInteger appels = new AtomicInteger();

        cache.get("Paris", cle -> "valeur " + appels.incrementAndGet());
        Thread.sleep(200); // on attend que la donnée expire
        String resultat = cache.get("Paris", cle -> "valeur " + appels.incrementAndGet());

        assertEquals("valeur 2", resultat);
        assertEquals(2, appels.get());
    }
}