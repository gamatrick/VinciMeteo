package fr.vinci.vincimeteo.controllers;

import fr.vinci.vincimeteo.models.Ville;
import fr.vinci.vincimeteo.services.MeteoService;
import fr.vinci.vincimeteo.services.VilleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

// @Controller (et pas @RestController) : les méthodes renvoient le NOM d'une page
// Thymeleaf, que Spring va chercher dans src/main/resources/templates.
@Controller
public class HomeController {

    private final VilleService villeService;
    private final MeteoService meteoService;

    // Injection par constructeur : Spring nous passe les services qu'il a créés.
    public HomeController(VilleService villeService, MeteoService meteoService) {
        this.villeService = villeService;
        this.meteoService = meteoService;
    }

    // Page d'accueil : http://localhost:8080/
    @GetMapping("/")
    public String accueil(Model model) {
        // On met la liste des villes dans le "Model" pour que la page puisse l'afficher.
        model.addAttribute("villes", villeService.getToutesLesVilles());
        return "index"; // -> templates/index.html
    }

    // Page résultat : http://localhost:8080/resultat?ville=Paris
    @GetMapping("/resultat")
    public String resultat(@RequestParam("ville") String nomVille, Model model) {
        Optional<Ville> ville = villeService.trouverParNom(nomVille);

        if (ville.isEmpty()) {
            // Ville inconnue : on réaffiche l'accueil avec un message d'erreur.
            model.addAttribute("villes", villeService.getToutesLesVilles());
            model.addAttribute("erreur", "Ville inconnue : " + nomVille);
            return "index";
        }

        Ville v = ville.get();
        model.addAttribute("ville", v);
        // La météo passe maintenant par le service, donc par le cache.
        model.addAttribute("meteo", meteoService.getMeteo(v.latitude(), v.longitude()));
        model.addAttribute("nombreAppels", meteoService.getNombreAppels());
        return "resultat"; // -> templates/resultat.html
    }
}