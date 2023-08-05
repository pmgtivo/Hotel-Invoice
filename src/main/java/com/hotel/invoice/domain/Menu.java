package com.hotel.invoice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Menu.
 */
@Entity
@Table(name = "menu")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "price", nullable = false)
    private String price;

    @Column(name = "description")
    private String description;

    @Column(name = "is_veg")
    private Boolean isVeg;

    @Column(name = "item_available")
    private Boolean itemAvailable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "menus", "bills" }, allowSetters = true)
    private Hotel hotel;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Menu id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Menu name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return this.price;
    }

    public Menu price(String price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return this.description;
    }

    public Menu description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsVeg() {
        return this.isVeg;
    }

    public Menu isVeg(Boolean isVeg) {
        this.setIsVeg(isVeg);
        return this;
    }

    public void setIsVeg(Boolean isVeg) {
        this.isVeg = isVeg;
    }

    public Boolean getItemAvailable() {
        return this.itemAvailable;
    }

    public Menu itemAvailable(Boolean itemAvailable) {
        this.setItemAvailable(itemAvailable);
        return this;
    }

    public void setItemAvailable(Boolean itemAvailable) {
        this.itemAvailable = itemAvailable;
    }

    public Hotel getHotel() {
        return this.hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Menu hotel(Hotel hotel) {
        this.setHotel(hotel);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Menu)) {
            return false;
        }
        return id != null && id.equals(((Menu) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Menu{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", price='" + getPrice() + "'" +
            ", description='" + getDescription() + "'" +
            ", isVeg='" + getIsVeg() + "'" +
            ", itemAvailable='" + getItemAvailable() + "'" +
            "}";
    }
}
