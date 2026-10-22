package fr.vinci.vincimeteo.services;

import fr.vinci.vincimeteo.cache.CacheMaison;
import fr.vinci.vincimeteo.models.Coordonnees;
import fr.vinci.vincimeteo.models.Meteo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Service
public class MeteoService {

    private static final Logger log = LoggerFactory.getLogger(MeteoService.class);

    private final CacheMaison<Coordonnees, Meteo> cache;
    private int nombreAppels = 0;

    // @Value lit la propriété "meteo.cache.duree-de-vie" dans application.properties.
    // Spring convertit tout seul "30s", "5m", "1h" en Duration.
    // ":60s" = valeur par défaut si la propriété est absente.
    public MeteoService(@Value("${meteo.cache.duree-de-vie:60s}") Duration dureeDeVie) {
        this.cache = new CacheMaison<>(dureeDeVie);
        log.info("Cache météo créé, durée de vie = {}", dureeDeVie);
    }

    // Point d'entrée utilisé par les contrôleurs : passe TOUJOURS par le cache.
    public Meteo getMeteo(double latitude, double longitude) {
        return cache.get(new Coordonnees(latitude, longitude), this::appelerApiMeteo);
    }

    // Le "vrai" appel, qui coûte cher. Pour l'instant la météo est fictive (20°C) ;
    // quand tu brancheras une vraie API (ex. Open-Meteo), c'est ICI que tu mettras l'appel HTTP.
    // Le reste du code (cache, contrôleurs) n'aura pas à changer.
    private synchronized Meteo appelerApiMeteo(Coordonnees coord) {
        nombreAppels++;
        log.info("Appel API météo n°{} pour {}", nombreAppels, coord);
        return new Meteo("20°C", LocalTime.now().truncatedTo(ChronoUnit.SECONDS));
    }

    public int getNombreAppels() {
        return nombreAppels;
    }
}