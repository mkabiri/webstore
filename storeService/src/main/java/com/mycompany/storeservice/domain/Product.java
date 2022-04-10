package com.mycompany.storeservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.storeservice.domain.enumeration.ProductStatus;
import com.mycompany.storeservice.domain.enumeration.UnitOfMeasurement;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 2)
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Size(min = 6)
    @Column(name = "sku", nullable = false)
    private String sku;

    @Column(name = "description")
    private String description;

    @Column(name = "srp", precision = 21, scale = 2)
    private BigDecimal srp;

    @Column(name = "taxable")
    private Boolean taxable;

    @Enumerated(EnumType.STRING)
    @Column(name = "sales_unit")
    private UnitOfMeasurement salesUnit;

    @Column(name = "sales_quantity", precision = 21, scale = 2)
    private BigDecimal salesQuantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProductStatus status;

    @Column(name = "gros_weight")
    private Double grosWeight;

    @Column(name = "net_weight")
    private Double netWeight;

    @Column(name = "length")
    private Double length;

    @Column(name = "width")
    private Double width;

    @Column(name = "height")
    private Double height;

    @OneToMany(mappedBy = "product")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "product" }, allowSetters = true)
    private Set<Photo> photos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Product id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Product name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return this.sku;
    }

    public Product sku(String sku) {
        this.setSku(sku);
        return this;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getDescription() {
        return this.description;
    }

    public Product description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getSrp() {
        return this.srp;
    }

    public Product srp(BigDecimal srp) {
        this.setSrp(srp);
        return this;
    }

    public void setSrp(BigDecimal srp) {
        this.srp = srp;
    }

    public Boolean getTaxable() {
        return this.taxable;
    }

    public Product taxable(Boolean taxable) {
        this.setTaxable(taxable);
        return this;
    }

    public void setTaxable(Boolean taxable) {
        this.taxable = taxable;
    }

    public UnitOfMeasurement getSalesUnit() {
        return this.salesUnit;
    }

    public Product salesUnit(UnitOfMeasurement salesUnit) {
        this.setSalesUnit(salesUnit);
        return this;
    }

    public void setSalesUnit(UnitOfMeasurement salesUnit) {
        this.salesUnit = salesUnit;
    }

    public BigDecimal getSalesQuantity() {
        return this.salesQuantity;
    }

    public Product salesQuantity(BigDecimal salesQuantity) {
        this.setSalesQuantity(salesQuantity);
        return this;
    }

    public void setSalesQuantity(BigDecimal salesQuantity) {
        this.salesQuantity = salesQuantity;
    }

    public ProductStatus getStatus() {
        return this.status;
    }

    public Product status(ProductStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    public Double getGrosWeight() {
        return this.grosWeight;
    }

    public Product grosWeight(Double grosWeight) {
        this.setGrosWeight(grosWeight);
        return this;
    }

    public void setGrosWeight(Double grosWeight) {
        this.grosWeight = grosWeight;
    }

    public Double getNetWeight() {
        return this.netWeight;
    }

    public Product netWeight(Double netWeight) {
        this.setNetWeight(netWeight);
        return this;
    }

    public void setNetWeight(Double netWeight) {
        this.netWeight = netWeight;
    }

    public Double getLength() {
        return this.length;
    }

    public Product length(Double length) {
        this.setLength(length);
        return this;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return this.width;
    }

    public Product width(Double width) {
        this.setWidth(width);
        return this;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return this.height;
    }

    public Product height(Double height) {
        this.setHeight(height);
        return this;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Set<Photo> getPhotos() {
        return this.photos;
    }

    public void setPhotos(Set<Photo> photos) {
        if (this.photos != null) {
            this.photos.forEach(i -> i.setProduct(null));
        }
        if (photos != null) {
            photos.forEach(i -> i.setProduct(this));
        }
        this.photos = photos;
    }

    public Product photos(Set<Photo> photos) {
        this.setPhotos(photos);
        return this;
    }

    public Product addPhotos(Photo photo) {
        this.photos.add(photo);
        photo.setProduct(this);
        return this;
    }

    public Product removePhotos(Photo photo) {
        this.photos.remove(photo);
        photo.setProduct(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", sku='" + getSku() + "'" +
            ", description='" + getDescription() + "'" +
            ", srp=" + getSrp() +
            ", taxable='" + getTaxable() + "'" +
            ", salesUnit='" + getSalesUnit() + "'" +
            ", salesQuantity=" + getSalesQuantity() +
            ", status='" + getStatus() + "'" +
            ", grosWeight=" + getGrosWeight() +
            ", netWeight=" + getNetWeight() +
            ", length=" + getLength() +
            ", width=" + getWidth() +
            ", height=" + getHeight() +
            "}";
    }
}
