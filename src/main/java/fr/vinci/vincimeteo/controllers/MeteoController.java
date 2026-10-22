package fr.vinci.vincimeteo.controllers;

import fr.vinci.vincimeteo.models.Meteo;
import fr.vinci.vincimeteo.services.MeteoService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/meteo")
public class MeteoController {

    private final MeteoService meteoService;

    public MeteoController(MeteoService meteoService) {
        this.meteoService = meteoService;
    }

    @RequestMapping("/coord/{latitude}/{longitude}")
    public Result giveMeteo(@PathVariable double latitude, @PathVariable double longitude) {

        // Même service, donc même cache que la page résultat.
        Meteo meteo = meteoService.getMeteo(latitude, longitude);
        return new Result(String.valueOf(latitude), String.valueOf(longitude), meteo.temperature());

    }

    public static class Result {

        private String latitude;
        private String longitude;
        private String meteo;

        public Result(String latitude, String longitude, String meteo) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.meteo = meteo;
        }

        public String getLatitude() {
            return latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public String getMeteo() {
            return meteo;
        }

    }

}