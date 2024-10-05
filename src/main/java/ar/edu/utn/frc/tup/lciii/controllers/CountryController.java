package ar.edu.utn.frc.tup.lciii.controllers;
import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CountryController {
    @Autowired
    private final CountryService countryService;
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/countries")
    public List<CountryDTO> getAllCountries(@RequestParam(required = false) String code, @RequestParam(required = false) String name) {
        return countryService.getAllCountries(code, name);
    }

    @GetMapping("/countries/{continent}/continent")
    public List<CountryDTO> getCountriesByContinent(@PathVariable String continent) {
        return countryService.getCountriesByContinent(continent);
    }

    @GetMapping("/countries/{language}/language")
    public List<CountryDTO> getCountriesByLanguage(@PathVariable String language) {
        return countryService.getCountriesByLanguage(language);
    }

    @GetMapping("/countries/most-borders")
    public CountryDTO getCountryWithMostBorders() {
        return countryService.getWithMostBorders();
    }

    @PostMapping("/countries")
    public List<CountryDTO> getAndSaveCountries(@RequestParam(required = false) String code, @RequestParam(required = false) String name
    ,@RequestBody(required = true) int amountOfCountryToSave) {
        return countryService.getAndSaveCountries(code, name, amountOfCountryToSave);
    }


}