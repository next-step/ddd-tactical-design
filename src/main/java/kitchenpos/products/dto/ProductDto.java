package kitchenpos.products.dto;

import kitchenpos.common.values.Name;
import kitchenpos.common.values.Price;
import kitchenpos.products.domain.Product;

import java.util.UUID;


public class ProductDto {
    private UUID id;
    private Name name;
    private Price price;

    public ProductDto() {
    }

    public static ProductDto from(Product product) {
        ProductDto result = new ProductDto();
        result.id = product.getId();
        result.name = product.getName();
        result.price = product.getPrice();
        return result;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }
}
