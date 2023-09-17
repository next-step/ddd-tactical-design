package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.support.product.ProductAble;
import kitchenpos.support.product.vo.ProductName;
import kitchenpos.support.product.vo.ProductPrice;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Table(name = "product")
@Entity
public class ProductInMenu implements ProductAble {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private ProductName name;

    @Embedded
    private ProductPrice price;

    protected ProductInMenu() {

    }

    protected ProductInMenu(UUID id, ProductName name, ProductPrice price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public static ProductInMenu create(UUID productId, ProductName productName, ProductPrice productPrice) {
        return new ProductInMenu(
                productId,
                productName,
                productPrice
        );
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getName() {
        return name.getValue();
    }

    @Override
    public BigDecimal getPrice() {
        return price.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductInMenu that = (ProductInMenu) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
