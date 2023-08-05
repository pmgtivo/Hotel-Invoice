package com.hotel.invoice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A BillItems.
 */
@Entity
@Table(name = "bill_items")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BillItems implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "item_count")
    private Integer itemCount;

    @Column(name = "amount")
    private Integer amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "items", "hotel" }, allowSetters = true)
    private Bill bill;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BillItems id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getItemCount() {
        return this.itemCount;
    }

    public BillItems itemCount(Integer itemCount) {
        this.setItemCount(itemCount);
        return this;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public Integer getAmount() {
        return this.amount;
    }

    public BillItems amount(Integer amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Bill getBill() {
        return this.bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public BillItems bill(Bill bill) {
        this.setBill(bill);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BillItems)) {
            return false;
        }
        return id != null && id.equals(((BillItems) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BillItems{" +
            "id=" + getId() +
            ", itemCount=" + getItemCount() +
            ", amount=" + getAmount() +
            "}";
    }
}
