package org.nutritrack.nutritrack.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenFoodSearchResponse {
    @JsonProperty("products")
    private List<OpenFoodProduct> products;

    public List<OpenFoodProduct> getProducts() {
        return products;
    }
}
