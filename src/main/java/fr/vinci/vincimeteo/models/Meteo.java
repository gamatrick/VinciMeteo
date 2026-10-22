package fr.vinci.vincimeteo.models;

import java.time.LocalTime;

// La donnée météo qu'on met en cache.
// recupereeA = l'heure à laquelle on a VRAIMENT fait l'appel :
// si elle ne change pas quand on recharge la page, c'est que la donnée vient du cache.
public record Meteo(String temperature, LocalTime recupereeA) {
}