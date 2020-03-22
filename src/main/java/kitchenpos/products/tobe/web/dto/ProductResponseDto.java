package kitchenpos.products.tobe.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import kitchenpos.commons.domain.Price;
import kitchenpos.products.tobe.domain.entity.Product;

public class ProductResponseDto {

    private Long id;
    private String name;
    private Price price;

    public ProductResponseDto(Long id, String name, Price price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public ProductResponseDto(Product product) {
        this(product.getId(), product.getName(), product.getPrice());
    }

    public Long getId() {
        return id;
    }

    @JsonProperty(value = "price")
    public BigDecimal getPrice() {
        return price.getPrice();
    }
}
