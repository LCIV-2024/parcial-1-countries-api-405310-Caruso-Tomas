package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.model.Country;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static javax.management.Query.eq;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class CountryServiceTest {
    @SpyBean
    private CountryService countryService;

    @MockBean
    private RestTemplate restTemplate;

    private final String COUNTRIES_URL = "https://restcountries.com/v3.1/all";


    @Test
    void getAllCountries() {
        String code = "ARG";
        String name = "Argentina";
        List<CountryDTO> expected = new ArrayList<>();
        expected.add(new CountryDTO("ARG", "Argentina"));
        List<Map<String, Object>> responseFromAPI = new ArrayList<>();
        /*responseFromAPI.add(Map.of("name", Map.of("common","Argentina"),  "cca3","ARG", "population", 45,
                 "languages", "region", "sadfasfas", "continents", "South America",
                Map.of("language", "Spanish")));*/


        Country country = new Country("Argentina", 45, 45.0, "ARG", "South America", List.of("BRA", "CHI"), Map.of("Spanish", "Spanish"), "South America");

        when(restTemplate.getForObject(COUNTRIES_URL, List.class)).thenReturn(null);
       // when(restTemplate.getForObject(COUNTRIES_URL, List.class)).thenReturn(responseFromAPI);
        when(countryService.mapToCountry(any())).thenReturn(country);
        when(countryService.mapToDTO(any())).thenReturn(new CountryDTO("ARG", "Argentina"));
       // when(countryService.mapearACountry(any())).thenReturn(List.of(country));

        List<CountryDTO> result = countryService.getAllCountries(code, name);
        assertEquals(expected, result);
    }


    @Test
    void mapToCountry() {

    }
    @Test
    void testGetAllCountries() {

    }

    @Test
    void getCountriesByContinent() {
    }


    /*    public List<CountryDTO> getCountriesByLanguage(String language) {
                if (!List.of(languagesAllowed).contains(language)) {
                        throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Lenguaje no permitido");
                }
                List<Map<String, Object>> response = restTemplate.getForObject(COUNTRIES_URL, List.class);
                List<Country> countries = response.stream().map(this::mapToCountry).collect(Collectors.toList());
                List<Country> countriesToRemove =new ArrayList<>();
                for (Country country : countries) {
                        if(!country.getLanguages().containsValue(language)){
                                countriesToRemove.add(country);
                        }
                }
                countries.removeAll(countriesToRemove);
                List<CountryDTO> countriesDTO =countries.stream().map(this::mapToDTO).collect(Collectors.toList());
                return countriesDTO;
        }*/
    @Test
    void getCountriesByLanguage() {
    }

    @Test
    void getWithMostBorders() {
    }

    @Test
    void getAndSaveCountries() {
    }

}