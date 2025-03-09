package org.nutritrack.nutritrack.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenFoodNutriments {
    @JsonProperty("energy-kcal_100g")
    private double energyKcal;

    @JsonProperty("proteins_100g")
    private double proteins;

    @JsonProperty("fat_100g")
    private double fat;

    @JsonProperty("carbohydrates_100g")
    private double carbohydrates;

    public double getEnergyKcal() {
        return energyKcal;
    }

    public double getProteins() {
        return proteins;
    }

    public double getFat() {
        return fat;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }
}
