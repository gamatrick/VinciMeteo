package fr.vinci.vincimeteo.models;

// Sert de CLÉ dans le cache. Un record génère equals() et hashCode() à partir
// de latitude et longitude : deux Coordonnees avec les mêmes valeurs sont "égales",
// donc la HashMap du cache les considère comme la même clé.
public record Coordonnees(double latitude, double longitude) {
}