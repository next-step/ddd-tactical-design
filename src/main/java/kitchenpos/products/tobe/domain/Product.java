package kitchenpos.products.tobe.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.shared.client.PurgomalumClient;
import kitchenpos.shared.domain.ProductPrice;

@Table(name = "product")
@Entity(name = "TobeProduct")
public class Product {

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id"))
    private ProductId id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "name"))
    private ProductName name;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "price"))
    private ProductPrice price;

    protected Product() {
    }

    public Product(final UUID id, final String name, final PurgomalumClient purgomalumClient,
        final BigDecimal price) {
        this.id = new ProductId(id);
        this.name = new ProductName(name, purgomalumClient);
        this.price = new ProductPrice(price);
    }

    public UUID getId() {
        return id.getValue();
    }

    public String getName() {
        return name.getValue();
    }

    public BigDecimal getPrice() {
        return price.getValue();
    }

    public void changePrice(BigDecimal price) {
        this.price = new ProductPrice(price);
    }
}
