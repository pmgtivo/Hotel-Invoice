package com.hotel.invoice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A Bill.
 */
@Entity
@Table(name = "bill")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Bill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "table_no")
    private String tableNo;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_contact")
    private String customerContact;

    @Column(name = "cgst")
    private String cgst;

    @Column(name = "sgst")
    private String sgst;

    @NotNull
    @Column(name = "total_amount", nullable = false)
    private Integer totalAmount;

    @Column(name = "is_parcel")
    private Boolean isParcel;

    @Column(name = "parcel_charges")
    private Integer parcelCharges;

    @Column(name = "adjust_amount")
    private Integer adjustAmount;

    @Column(name = "discount_percentage")
    private Double discountPercentage;

    @Column(name = "paid_by")
    private String paidBy;

    @Column(name = "create_date_time")
    private ZonedDateTime createDateTime;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bill")
    @JsonIgnoreProperties(value = { "bill" }, allowSetters = true)
    private Set<BillItems> items = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "menus", "bills" }, allowSetters = true)
    private Hotel hotel;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Bill id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTableNo() {
        return this.tableNo;
    }

    public Bill tableNo(String tableNo) {
        this.setTableNo(tableNo);
        return this;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public Bill customerName(String customerName) {
        this.setCustomerName(customerName);
        return this;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerContact() {
        return this.customerContact;
    }

    public Bill customerContact(String customerContact) {
        this.setCustomerContact(customerContact);
        return this;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public String getCgst() {
        return this.cgst;
    }

    public Bill cgst(String cgst) {
        this.setCgst(cgst);
        return this;
    }

    public void setCgst(String cgst) {
        this.cgst = cgst;
    }

    public String getSgst() {
        return this.sgst;
    }

    public Bill sgst(String sgst) {
        this.setSgst(sgst);
        return this;
    }

    public void setSgst(String sgst) {
        this.sgst = sgst;
    }

    public Integer getTotalAmount() {
        return this.totalAmount;
    }

    public Bill totalAmount(Integer totalAmount) {
        this.setTotalAmount(totalAmount);
        return this;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Boolean getIsParcel() {
        return this.isParcel;
    }

    public Bill isParcel(Boolean isParcel) {
        this.setIsParcel(isParcel);
        return this;
    }

    public void setIsParcel(Boolean isParcel) {
        this.isParcel = isParcel;
    }

    public Integer getParcelCharges() {
        return this.parcelCharges;
    }

    public Bill parcelCharges(Integer parcelCharges) {
        this.setParcelCharges(parcelCharges);
        return this;
    }

    public void setParcelCharges(Integer parcelCharges) {
        this.parcelCharges = parcelCharges;
    }

    public Integer getAdjustAmount() {
        return this.adjustAmount;
    }

    public Bill adjustAmount(Integer adjustAmount) {
        this.setAdjustAmount(adjustAmount);
        return this;
    }

    public void setAdjustAmount(Integer adjustAmount) {
        this.adjustAmount = adjustAmount;
    }

    public Double getDiscountPercentage() {
        return this.discountPercentage;
    }

    public Bill discountPercentage(Double discountPercentage) {
        this.setDiscountPercentage(discountPercentage);
        return this;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getPaidBy() {
        return this.paidBy;
    }

    public Bill paidBy(String paidBy) {
        this.setPaidBy(paidBy);
        return this;
    }

    public void setPaidBy(String paidBy) {
        this.paidBy = paidBy;
    }

    public ZonedDateTime getCreateDateTime() {
        return this.createDateTime;
    }

    public Bill createDateTime(ZonedDateTime createDateTime) {
        this.setCreateDateTime(createDateTime);
        return this;
    }

    public void setCreateDateTime(ZonedDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public Set<BillItems> getItems() {
        return this.items;
    }

    public void setItems(Set<BillItems> billItems) {
        if (this.items != null) {
            this.items.forEach(i -> i.setBill(null));
        }
        if (billItems != null) {
            billItems.forEach(i -> i.setBill(this));
        }
        this.items = billItems;
    }

    public Bill items(Set<BillItems> billItems) {
        this.setItems(billItems);
        return this;
    }

    public Bill addItems(BillItems billItems) {
        this.items.add(billItems);
        billItems.setBill(this);
        return this;
    }

    public Bill removeItems(BillItems billItems) {
        this.items.remove(billItems);
        billItems.setBill(null);
        return this;
    }

    public Hotel getHotel() {
        return this.hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Bill hotel(Hotel hotel) {
        this.setHotel(hotel);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bill)) {
            return false;
        }
        return id != null && id.equals(((Bill) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bill{" +
            "id=" + getId() +
            ", tableNo='" + getTableNo() + "'" +
            ", customerName='" + getCustomerName() + "'" +
            ", customerContact='" + getCustomerContact() + "'" +
            ", cgst='" + getCgst() + "'" +
            ", sgst='" + getSgst() + "'" +
            ", totalAmount=" + getTotalAmount() +
            ", isParcel='" + getIsParcel() + "'" +
            ", parcelCharges=" + getParcelCharges() +
            ", adjustAmount=" + getAdjustAmount() +
            ", discountPercentage=" + getDiscountPercentage() +
            ", paidBy='" + getPaidBy() + "'" +
            ", createDateTime='" + getCreateDateTime() + "'" +
            "}";
    }
}
