package fr.vinci.vincimeteo.services;

import fr.vinci.vincimeteo.models.Ville;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// @Service dit à Spring : "crée UN objet de cette classe au démarrage
// et donne-le à ceux qui en ont besoin" (injection de dépendances).
@Service
public class VilleService {

    // Table des villes connues, écrite en dur.
    // LinkedHashMap garde l'ordre d'insertion : la liste déroulante sera dans cet ordre.
    private final Map<String, Ville> villes = new LinkedHashMap<>();

    public VilleService() {
        ajouter(new Ville("Paris", 48.8566, 2.3522));
        ajouter(new Ville("Lyon", 45.7640, 4.8357));
        ajouter(new Ville("Marseille", 43.2965, 5.3698));
        ajouter(new Ville("Toulouse", 43.6047, 1.4442));
        ajouter(new Ville("Nice", 43.7102, 7.2620));
        ajouter(new Ville("Nantes", 47.2184, -1.5536));
        ajouter(new Ville("Strasbourg", 48.5734, 7.7521));
        ajouter(new Ville("Bordeaux", 44.8378, -0.5792));
        ajouter(new Ville("Lille", 50.6292, 3.0573));
        ajouter(new Ville("Rennes", 48.1173, -1.6778));
    }

    private void ajouter(Ville ville) {
        // La clé est en minuscules pour que "paris", "Paris" ou "PARIS" marchent tous.
        villes.put(ville.nom().toLowerCase(), ville);
    }

    // Renvoie toutes les villes (pour remplir la liste de la page d'accueil).
    public List<Ville> getToutesLesVilles() {
        return new ArrayList<>(villes.values());
    }

    // Convertit un nom de ville en coordonnées.
    // Optional = "peut-être une ville, peut-être rien" : on évite de renvoyer null.
    public Optional<Ville> trouverParNom(String nom) {
        if (nom == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(villes.get(nom.trim().toLowerCase()));
    }
}