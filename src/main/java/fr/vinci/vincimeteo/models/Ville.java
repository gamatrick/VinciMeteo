package fr.vinci.vincimeteo.models;

// Un "record" est une petite classe immuable : Java génère tout seul
// le constructeur, les getters (nom(), latitude(), longitude()), equals et toString.
public record Ville(String nom, double latitude, double longitude) {
}