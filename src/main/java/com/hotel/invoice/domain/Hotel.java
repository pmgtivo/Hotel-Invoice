package com.hotel.invoice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Hotel.
 */
@Entity
@Table(name = "hotel")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Hotel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "contact")
    private String contact;

    @Column(name = "owner_name")
    private String ownerName;

    @Column(name = "owner_contact")
    private String ownerContact;

    @Column(name = "gst_no")
    private String gstNo;

    @Column(name = "is_veg")
    private Boolean isVeg;

    @Lob
    @Column(name = "logo")
    private byte[] logo;

    @Column(name = "logo_content_type")
    private String logoContentType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hotel")
    @JsonIgnoreProperties(value = { "hotel" }, allowSetters = true)
    private Set<Menu> menus = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hotel")
    @JsonIgnoreProperties(value = { "items", "hotel" }, allowSetters = true)
    private Set<Bill> bills = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Hotel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Hotel name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public Hotel address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return this.contact;
    }

    public Hotel contact(String contact) {
        this.setContact(contact);
        return this;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getOwnerName() {
        return this.ownerName;
    }

    public Hotel ownerName(String ownerName) {
        this.setOwnerName(ownerName);
        return this;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerContact() {
        return this.ownerContact;
    }

    public Hotel ownerContact(String ownerContact) {
        this.setOwnerContact(ownerContact);
        return this;
    }

    public void setOwnerContact(String ownerContact) {
        this.ownerContact = ownerContact;
    }

    public String getGstNo() {
        return this.gstNo;
    }

    public Hotel gstNo(String gstNo) {
        this.setGstNo(gstNo);
        return this;
    }

    public void setGstNo(String gstNo) {
        this.gstNo = gstNo;
    }

    public Boolean getIsVeg() {
        return this.isVeg;
    }

    public Hotel isVeg(Boolean isVeg) {
        this.setIsVeg(isVeg);
        return this;
    }

    public void setIsVeg(Boolean isVeg) {
        this.isVeg = isVeg;
    }

    public byte[] getLogo() {
        return this.logo;
    }

    public Hotel logo(byte[] logo) {
        this.setLogo(logo);
        return this;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return this.logoContentType;
    }

    public Hotel logoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
        return this;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public Set<Menu> getMenus() {
        return this.menus;
    }

    public void setMenus(Set<Menu> menus) {
        if (this.menus != null) {
            this.menus.forEach(i -> i.setHotel(null));
        }
        if (menus != null) {
            menus.forEach(i -> i.setHotel(this));
        }
        this.menus = menus;
    }

    public Hotel menus(Set<Menu> menus) {
        this.setMenus(menus);
        return this;
    }

    public Hotel addMenu(Menu menu) {
        this.menus.add(menu);
        menu.setHotel(this);
        return this;
    }

    public Hotel removeMenu(Menu menu) {
        this.menus.remove(menu);
        menu.setHotel(null);
        return this;
    }

    public Set<Bill> getBills() {
        return this.bills;
    }

    public void setBills(Set<Bill> bills) {
        if (this.bills != null) {
            this.bills.forEach(i -> i.setHotel(null));
        }
        if (bills != null) {
            bills.forEach(i -> i.setHotel(this));
        }
        this.bills = bills;
    }

    public Hotel bills(Set<Bill> bills) {
        this.setBills(bills);
        return this;
    }

    public Hotel addBill(Bill bill) {
        this.bills.add(bill);
        bill.setHotel(this);
        return this;
    }

    public Hotel removeBill(Bill bill) {
        this.bills.remove(bill);
        bill.setHotel(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Hotel)) {
            return false;
        }
        return id != null && id.equals(((Hotel) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Hotel{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", contact='" + getContact() + "'" +
            ", ownerName='" + getOwnerName() + "'" +
            ", ownerContact='" + getOwnerContact() + "'" +
            ", gstNo='" + getGstNo() + "'" +
            ", isVeg='" + getIsVeg() + "'" +
            ", logo='" + getLogo() + "'" +
            ", logoContentType='" + getLogoContentType() + "'" +
            "}";
    }
}
