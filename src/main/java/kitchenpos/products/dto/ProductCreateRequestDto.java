package kitchenpos.products.dto;

import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.infra.PurgomalumClient;

import java.math.BigDecimal;

public class ProductCreateRequestDto {
    private String name;
    private BigDecimal price;

    public ProductCreateRequestDto(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Product toEntity(PurgomalumClient profanityClient){
        return Product.of(this.name, profanityClient, this.price);
    }
}
