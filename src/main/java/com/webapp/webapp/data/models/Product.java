package com.webapp.webapp.data.models;

import java.time.LocalDateTime;

import javax.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "description")
    private String description;
    @JsonProperty(value = "sku")
    private String sku;
    @JsonProperty(value = "manufacturer")
    private String manufacturer;
    @JsonProperty(value = "quantity")
    private int quantity;
    @CreatedDate
    private LocalDateTime date_added;
    @LastModifiedDate
    private LocalDateTime date_last_updated;
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int owner_user_id;

    /**
     * @return int return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return String return the name
     */
    public String getName() {
        return name;
    }

    /**
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
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return LocalDateTime return the date_added
     */
    public LocalDateTime getDate_added() {
        return date_added;
    }

    /**
     * @param date_added the date_added to set
     */
    public void setDate_added(LocalDateTime date_added) {
        this.date_added = date_added;
    }

    /**
     * @return LocalDateTime return the date_last_updated
     */
    public LocalDateTime getDate_last_updated() {
        return date_last_updated;
    }

    /**
     * @param date_last_updated the date_last_updated to set
     */
    public void setDate_last_updated(LocalDateTime date_last_updated) {
        this.date_last_updated = date_last_updated;
    }

    /**
     * @return int return the owner_user_id
     */
    public int getOwner_user_id() {
        return owner_user_id;
    }

    /**
     * @param owner_user_id the owner_user_id to set
     */
    public void setOwner_user_id(int owner_user_id) {
        this.owner_user_id = owner_user_id;
    }

}
