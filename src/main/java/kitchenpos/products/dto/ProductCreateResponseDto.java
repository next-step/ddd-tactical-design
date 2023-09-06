package kitchenpos.products.dto;

import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.Product;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductCreateResponseDto {
    private UUID id;
    private String name;
    private BigDecimal price;

    public ProductCreateResponseDto(UUID id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public static ProductCreateResponseDto fromEntity(Product product){
        return new ProductCreateResponseDto(
                product.getId()
                , product.getNameValue()
                , product.getPriceValue());
    }
}
