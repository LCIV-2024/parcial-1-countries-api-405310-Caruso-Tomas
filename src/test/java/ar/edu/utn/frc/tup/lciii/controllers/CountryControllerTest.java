package ar.edu.utn.frc.tup.lciii.controllers;
import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.model.Country;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import ar.edu.utn.frc.tup.lciii.service.CountryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CountryController.class)
class CountryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CountryService service;
/*
    @GetMapping("/countries")
    public List<CountryDTO> getAllCountries(@RequestParam(required = false) String code, @RequestParam(required = false) String name) {
        return countryService.getAllCountries(code, name);
    }*/

    @Test
    void getAllCountries() throws Exception {
        List<CountryDTO> countries = new ArrayList<>();
        countries.add(new CountryDTO("ARG", "Argentina"));
        given(service.getAllCountries("ARG", "Argentina")).willReturn(countries);
        mockMvc.perform(get("/api/countries?code=ARG&name=Argentina"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    /*
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
*/

    @Test
    void getCountriesByContinent() throws Exception {
        List<CountryDTO> countries = new ArrayList<>();
        countries.add(new CountryDTO("CHE", "Switzerland"));
        given(service.getAllCountries("CHE", "Switzerland")).willReturn(countries);
        mockMvc.perform(get("/api/countries?continent=Europe"))
                .andDo(print())
                //.andExpect(content().json("[{\"code\":\"CHE\",\"name\":\"Switzerland\"}]"))
                .andExpect(status().isOk());

    }

    @Test
    void getCountriesByLanguage() {

        

    }

    @Test
    void getCountryWithMostBorders() {
    }

    @Test
    void getAndSaveCountries() {
    }
}