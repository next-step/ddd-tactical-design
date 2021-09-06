package kitchenpos.products.tobe.domain.model;

import java.math.BigDecimal;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import kitchenpos.products.tobe.domain.service.ProductDomainService;

@Table(name = "product")
@Entity
public class Product {

    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Embedded
    @Column(name = "displayedName", nullable = false)
    private DisplayedName displayedName;

    @Embedded
    @Column(name = "price", nullable = false)
    private Price price;

    public Product() {
    }

    public Product(String displayedName, BigDecimal price, ProductDomainService productDomainService) {
        productDomainService.validateDisplayedName(displayedName);
        this.id = UUID.randomUUID();
        this.displayedName = new DisplayedName(displayedName);
        this.price = new Price(price);
    }

    public String getDisplayedName() {
        return displayedName.getName();
    }

    public BigDecimal getPrice() {
        return price.getPrice();
    }

    public UUID getId() {
        return id;
    }

    public void changePrice(BigDecimal price) {
        this.price = new Price(price);
    }
}
