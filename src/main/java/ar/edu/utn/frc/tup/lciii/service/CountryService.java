package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.entities.CountryEntity;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CountryService {
        private final String COUNTRIES_URL = "https://restcountries.com/v3.1/all";
        @Autowired
        private final CountryRepository countryRepository;
        @Autowired
        private final RestTemplate restTemplate;

        private final List<Country> countriesGen = new ArrayList<>();

        private final String[] languagesAllowed = {"English", "Spanish", "French", "German", "Portuguese", "Chinese", "Arabic", "Russian", "Hindi", "Swahili"};

        public CountryService(CountryRepository countryRepository, RestTemplate restTemplate) {
                this.countryRepository = countryRepository;
                this.restTemplate = restTemplate;
        }


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

        public List<Country> getAllCountries() {
                List<Map<String, Object>> response = restTemplate.getForObject(COUNTRIES_URL, List.class);
                List<Country> countries = response.stream().map(this::mapToCountry).collect(Collectors.toList());
               return countries;
                }

        /**
         * Agregar mapeo de campo cca3 (String)
         * Agregar mapeo campos borders ((List<String>))
         */
        public Country mapToCountry(Map<String, Object> countryData) {
                Map<String, Object> nameData = (Map<String, Object>) countryData.get("name");
                return Country.builder()
                        .name((String) nameData.get("common"))
                        .population(((Number) countryData.get("population")).longValue())
                        .area(((Number) countryData.get("area")).doubleValue())
                        .region((String) countryData.get("region"))
                        .languages((Map<String, String>) countryData.get("languages"))
                        .code((String) countryData.get("cca3"))
                        .continent((String) countryData.get("continents").toString())
                        .languages((Map<String, String>) countryData.get("languages"))
                        .borders((List<String>) countryData.get("borders"))
                        .build();
        }


        public CountryDTO mapToDTO(Country country) {
                return new CountryDTO(country.getCode(), country.getName());
        }

        public List<CountryDTO> getCountriesByContinent(String continent) {
                List<Map<String, Object>> response = restTemplate.getForObject(COUNTRIES_URL, List.class);
                List<Country> countries = response.stream().map(this::mapToCountry).collect(Collectors.toList());
                List<Country> countriesToRemove =new ArrayList<>();
                for (Country country : countries) {
                        if(!country.getContinent().equals("["+continent+"]")){
                                countriesToRemove.add(country);
                        }
                }
                countries.removeAll(countriesToRemove);
                List<CountryDTO> countriesDTO =countries.stream().map(this::mapToDTO).collect(Collectors.toList());
                return countriesDTO;
        }

        public List<CountryDTO> getCountriesByLanguage(String language) {
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
        }

        public CountryDTO getWithMostBorders() {
                List<Map<String, Object>> response = restTemplate.getForObject(COUNTRIES_URL, List.class);
                List<Country> countries = response.stream().map(this::mapToCountry).collect(Collectors.toList());
                int maxBorders = 0;
                Country countryWithMostBorders = null;
                for (Country country : countries) {
                        if(maxBorders < country.getBorders().size()){
                                maxBorders = country.getBorders().size();
                                countryWithMostBorders = country;
                        }
                }
                CountryDTO countryDTO = mapToDTO(countryWithMostBorders);
                return countryDTO;
        }

        public List<CountryDTO> getAndSaveCountries(String code, String name, int amountOfCountryToSave) {
                List<Country> response = getAllCountries();
                List<CountryDTO> countriesDTO =getAllCountries(code, name);
                Collections.shuffle(countriesDTO);
                countriesDTO = countriesDTO.stream().limit(amountOfCountryToSave).collect(Collectors.toList());
                for (CountryDTO countryDTO : countriesDTO) {
                        Country country = response.stream().filter(country1 -> country1.getCode().equals(countryDTO.getCode())).findFirst().get();
                        countryRepository.save(new CountryEntity(
                                countryDTO.getCode(), countryDTO.getName(),country.getPopulation(),country.getArea()));
                }
                return countriesDTO;
        }

}