package com.mycompany.order.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.order.domain.enumeration.SalesOrderStatus;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SalesOrder.
 */
@Entity
@Table(name = "sales_order")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SalesOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "sales_order_number")
    private String salesOrderNumber;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "placed")
    private Instant placed;

    @Column(name = "cancelled")
    private Instant cancelled;

    @Column(name = "shipped")
    private Instant shipped;

    @Column(name = "completed")
    private Instant completed;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SalesOrderStatus status;

    @OneToMany(mappedBy = "salesOrder")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "salesOrder" }, allowSetters = true)
    private Set<SalesOrderItem> orderItems = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SalesOrder id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSalesOrderNumber() {
        return this.salesOrderNumber;
    }

    public SalesOrder salesOrderNumber(String salesOrderNumber) {
        this.setSalesOrderNumber(salesOrderNumber);
        return this;
    }

    public void setSalesOrderNumber(String salesOrderNumber) {
        this.salesOrderNumber = salesOrderNumber;
    }

    public String getCustomerId() {
        return this.customerId;
    }

    public SalesOrder customerId(String customerId) {
        this.setCustomerId(customerId);
        return this;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Instant getPlaced() {
        return this.placed;
    }

    public SalesOrder placed(Instant placed) {
        this.setPlaced(placed);
        return this;
    }

    public void setPlaced(Instant placed) {
        this.placed = placed;
    }

    public Instant getCancelled() {
        return this.cancelled;
    }

    public SalesOrder cancelled(Instant cancelled) {
        this.setCancelled(cancelled);
        return this;
    }

    public void setCancelled(Instant cancelled) {
        this.cancelled = cancelled;
    }

    public Instant getShipped() {
        return this.shipped;
    }

    public SalesOrder shipped(Instant shipped) {
        this.setShipped(shipped);
        return this;
    }

    public void setShipped(Instant shipped) {
        this.shipped = shipped;
    }

    public Instant getCompleted() {
        return this.completed;
    }

    public SalesOrder completed(Instant completed) {
        this.setCompleted(completed);
        return this;
    }

    public void setCompleted(Instant completed) {
        this.completed = completed;
    }

    public SalesOrderStatus getStatus() {
        return this.status;
    }

    public SalesOrder status(SalesOrderStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(SalesOrderStatus status) {
        this.status = status;
    }

    public Set<SalesOrderItem> getOrderItems() {
        return this.orderItems;
    }

    public void setOrderItems(Set<SalesOrderItem> salesOrderItems) {
        if (this.orderItems != null) {
            this.orderItems.forEach(i -> i.setSalesOrder(null));
        }
        if (salesOrderItems != null) {
            salesOrderItems.forEach(i -> i.setSalesOrder(this));
        }
        this.orderItems = salesOrderItems;
    }

    public SalesOrder orderItems(Set<SalesOrderItem> salesOrderItems) {
        this.setOrderItems(salesOrderItems);
        return this;
    }

    public SalesOrder addOrderItems(SalesOrderItem salesOrderItem) {
        this.orderItems.add(salesOrderItem);
        salesOrderItem.setSalesOrder(this);
        return this;
    }

    public SalesOrder removeOrderItems(SalesOrderItem salesOrderItem) {
        this.orderItems.remove(salesOrderItem);
        salesOrderItem.setSalesOrder(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SalesOrder)) {
            return false;
        }
        return id != null && id.equals(((SalesOrder) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SalesOrder{" +
            "id=" + getId() +
            ", salesOrderNumber='" + getSalesOrderNumber() + "'" +
            ", customerId='" + getCustomerId() + "'" +
            ", placed='" + getPlaced() + "'" +
            ", cancelled='" + getCancelled() + "'" +
            ", shipped='" + getShipped() + "'" +
            ", completed='" + getCompleted() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
