package fr.vinci.vincimeteo.cache;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

// Cache "fait maison" générique : K = type de la clé, V = type de la valeur.
// Chaque valeur est stockée avec sa date d'expiration.
public class CacheMaison<K, V> {

    // Une entrée du cache : la valeur + le moment où elle n'est plus valable.
    private record Entree<V>(V valeur, Instant expireA) {
        boolean estExpiree() {
            return Instant.now().isAfter(expireA);
        }
    }

    // ConcurrentHashMap plutôt que HashMap : Spring traite plusieurs requêtes
    // en même temps (plusieurs threads), cette Map le supporte sans se corrompre.
    private final Map<K, Entree<V>> donnees = new ConcurrentHashMap<>();
    private final Duration dureeDeVie;

    public CacheMaison(Duration dureeDeVie) {
        this.dureeDeVie = dureeDeVie;
    }

    // Renvoie la valeur en cache si elle existe et n'est pas expirée.
    // Sinon appelle "chargeur" (le vrai calcul / appel API), stocke le résultat et le renvoie.
    public V get(K cle, Function<K, V> chargeur) {
        Entree<V> entree = donnees.get(cle);

        if (entree != null && !entree.estExpiree()) {
            return entree.valeur(); // HIT : pas d'appel
        }

        // MISS (absente ou expirée) : on charge et on remplace l'ancienne entrée.
        V valeur = chargeur.apply(cle);
        donnees.put(cle, new Entree<>(valeur, Instant.now().plus(dureeDeVie)));
        return valeur;
    }

    public void vider() {
        donnees.clear();
    }

    public int taille() {
        return donnees.size();
    }
}