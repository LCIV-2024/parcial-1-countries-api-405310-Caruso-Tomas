package ar.edu.utn.frc.tup.lciii.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;

@Entity
@AllArgsConstructor
public class CountryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /*nombre, código, población (population) y área (Ademas de un id autoincremental)*/
    private String code;
    private String name;
    private long population;
    private double area;

    public CountryEntity() {

    }

    public CountryEntity(String code, String name, long population, double area) {
    }
}
