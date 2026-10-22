package fr.vinci.vincimeteo.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/meteo")
public class MeteoController {

    @RequestMapping("/coord/{latitude}/{longitude}")
    public Result giveMeteo(@PathVariable String latitude, @PathVariable String longitude) {

        return new Result(latitude, longitude, "20°C");

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