package kitchenpos.products.domain.dtos;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.products.domain.Product;

public class ProductInfo {

    private final UUID Id;
    private final String name;
    private final BigDecimal price;

    public ProductInfo(UUID Id, String name, BigDecimal price) {
        this.Id = Id;
        this.name = name;
        this.price = price;
    }

    public static ProductInfo from(Product product) {
        return new ProductInfo(product.getId(), product.getName().getName(), product.getPrice().getPrice());
    }

    public UUID getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
