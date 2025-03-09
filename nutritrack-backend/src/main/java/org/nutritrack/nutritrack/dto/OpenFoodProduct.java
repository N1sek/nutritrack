package org.nutritrack.nutritrack.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenFoodProduct {
    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("generic_name")
    private String genericName;

    @JsonProperty("nutriments")
    private OpenFoodNutriments nutriments;

    public String getProductName() {
        return productName;
    }

    public String getGenericName() {
        return genericName;
    }

    public OpenFoodNutriments getNutriments() {
        return nutriments;
    }
}
