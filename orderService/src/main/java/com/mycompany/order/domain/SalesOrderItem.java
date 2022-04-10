package com.mycompany.order.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.order.domain.enumeration.SalesOrderItemStatus;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SalesOrderItem.
 */
@Entity
@Table(name = "sales_order_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SalesOrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "sku")
    private String sku;

    @Column(name = "taxable")
    private Boolean taxable;

    @Column(name = "gros_weight")
    private Double grosWeight;

    @Column(name = "shipped")
    private LocalDate shipped;

    @Column(name = "delivered")
    private LocalDate delivered;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SalesOrderItemStatus status;

    @Column(name = "quantity", precision = 21, scale = 2)
    private BigDecimal quantity;

    @Column(name = "unit_price", precision = 21, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "amount", precision = 21, scale = 2)
    private BigDecimal amount;

    @ManyToOne
    @JsonIgnoreProperties(value = { "orderItems" }, allowSetters = true)
    private SalesOrder salesOrder;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SalesOrderItem id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public SalesOrderItem name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return this.sku;
    }

    public SalesOrderItem sku(String sku) {
        this.setSku(sku);
        return this;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Boolean getTaxable() {
        return this.taxable;
    }

    public SalesOrderItem taxable(Boolean taxable) {
        this.setTaxable(taxable);
        return this;
    }

    public void setTaxable(Boolean taxable) {
        this.taxable = taxable;
    }

    public Double getGrosWeight() {
        return this.grosWeight;
    }

    public SalesOrderItem grosWeight(Double grosWeight) {
        this.setGrosWeight(grosWeight);
        return this;
    }

    public void setGrosWeight(Double grosWeight) {
        this.grosWeight = grosWeight;
    }

    public LocalDate getShipped() {
        return this.shipped;
    }

    public SalesOrderItem shipped(LocalDate shipped) {
        this.setShipped(shipped);
        return this;
    }

    public void setShipped(LocalDate shipped) {
        this.shipped = shipped;
    }

    public LocalDate getDelivered() {
        return this.delivered;
    }

    public SalesOrderItem delivered(LocalDate delivered) {
        this.setDelivered(delivered);
        return this;
    }

    public void setDelivered(LocalDate delivered) {
        this.delivered = delivered;
    }

    public SalesOrderItemStatus getStatus() {
        return this.status;
    }

    public SalesOrderItem status(SalesOrderItemStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(SalesOrderItemStatus status) {
        this.status = status;
    }

    public BigDecimal getQuantity() {
        return this.quantity;
    }

    public SalesOrderItem quantity(BigDecimal quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return this.unitPrice;
    }

    public SalesOrderItem unitPrice(BigDecimal unitPrice) {
        this.setUnitPrice(unitPrice);
        return this;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public SalesOrderItem amount(BigDecimal amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public SalesOrder getSalesOrder() {
        return this.salesOrder;
    }

    public void setSalesOrder(SalesOrder salesOrder) {
        this.salesOrder = salesOrder;
    }

    public SalesOrderItem salesOrder(SalesOrder salesOrder) {
        this.setSalesOrder(salesOrder);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SalesOrderItem)) {
            return false;
        }
        return id != null && id.equals(((SalesOrderItem) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SalesOrderItem{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", sku='" + getSku() + "'" +
            ", taxable='" + getTaxable() + "'" +
            ", grosWeight=" + getGrosWeight() +
            ", shipped='" + getShipped() + "'" +
            ", delivered='" + getDelivered() + "'" +
            ", status='" + getStatus() + "'" +
            ", quantity=" + getQuantity() +
            ", unitPrice=" + getUnitPrice() +
            ", amount=" + getAmount() +
            "}";
    }
}
