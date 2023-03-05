package com.webapp.webapp.data.payloads.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductRequest {
    @NotBlank
    @NotNull
    @JsonProperty(value = "name")
    private String name;
    @NotBlank
    @NotNull
    @JsonProperty(value = "description")
    private String description;
    @NotBlank
    @NotNull
    @JsonProperty(value = "sku")
    private String sku;
    @NotBlank
    @NotNull
    @JsonProperty(value = "manufacturer")
    private String manufacturer;
    private String quantity;

    /**
     * @return String return the name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return String return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return String return the sku
     */
    public String getSku() {
        return sku;
    }

    /**
     * @param sku the sku to set
     */
    public void setSku(String sku) {
        this.sku = sku;
    }

    /**
     * @return String return the manufacturer
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * @param manufacturer the manufacturer to set
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * @return int return the quantity
     */
    public String getQuantity() {
        return quantity;
    }

    // /**
    // * @param quantity the quantity to set
    // */
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

}
