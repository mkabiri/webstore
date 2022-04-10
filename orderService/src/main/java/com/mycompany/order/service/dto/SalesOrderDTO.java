package com.mycompany.order.service.dto;

import com.mycompany.order.domain.enumeration.SalesOrderStatus;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.order.domain.SalesOrder} entity.
 */
public class SalesOrderDTO implements Serializable {

    private Long id;

    private String salesOrderNumber;

    private String customerId;

    private Instant placed;

    private Instant cancelled;

    private Instant shipped;

    private Instant completed;

    private SalesOrderStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSalesOrderNumber() {
        return salesOrderNumber;
    }

    public void setSalesOrderNumber(String salesOrderNumber) {
        this.salesOrderNumber = salesOrderNumber;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Instant getPlaced() {
        return placed;
    }

    public void setPlaced(Instant placed) {
        this.placed = placed;
    }

    public Instant getCancelled() {
        return cancelled;
    }

    public void setCancelled(Instant cancelled) {
        this.cancelled = cancelled;
    }

    public Instant getShipped() {
        return shipped;
    }

    public void setShipped(Instant shipped) {
        this.shipped = shipped;
    }

    public Instant getCompleted() {
        return completed;
    }

    public void setCompleted(Instant completed) {
        this.completed = completed;
    }

    public SalesOrderStatus getStatus() {
        return status;
    }

    public void setStatus(SalesOrderStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SalesOrderDTO)) {
            return false;
        }

        SalesOrderDTO salesOrderDTO = (SalesOrderDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, salesOrderDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SalesOrderDTO{" +
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
