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


    /*
        public List<CountryDTO> getAllCountries(String code, String name) {
                List<Map<String, Object>> response = restTemplate.getForObject(COUNTRIES_URL, List.class);
                List<Country> countries = response.stream().map(this::mapToCountry).collect(Collectors.toList());
                countriesGen.addAll(countries);
                List<CountryDTO> countriesDTO =countries.stream().map(this::mapToDTO).collect(Collectors.toList());
                if(code != null){
                        countriesDTO = countriesDTO.stream().filter(countryDTO -> countryDTO.getCode().equals(code)).collect(Collectors.toList());
                }
                if(name != null){
                        countriesDTO = countriesDTO.stream().filter(countryDTO -> countryDTO.getName().equals(name)).collect(Collectors.toList());
                }
                return countriesDTO;
        }
*/
    @Test
    void getAllCountries() {
        String code = "ARG";
        String name = "Argentina";
        List<CountryDTO> expected = new ArrayList<>();
        expected.add(new CountryDTO("ARG", "Argentina"));
        List<Map<String, Object>> responseFromAPI = new ArrayList<>();
        responseFromAPI.add(Map.of("name", (Object)"Argentina",  "cca3",
                "ARG", "languages",
                Map.of("language", "Spanish")));

        responseFromAPI.add(Map.of("name", (Object)"Brazil",  "cca3",
                "BRA", "languages",
                Map.of("language", "Portuguese")));

        when(restTemplate.getForObject(COUNTRIES_URL, List.class)).thenReturn( responseFromAPI);

        List<CountryDTO> result = countryService.getAllCountries(code, name);
        assertEquals(expected, result);
    }

    @Test
    void testGetAllCountries() {
    }

    @Test
    void getCountriesByContinent() {
    }

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