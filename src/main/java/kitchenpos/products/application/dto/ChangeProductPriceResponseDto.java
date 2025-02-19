package kitchenpos.products.application.dto;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.products.tobe.domain.model.Product;

public class ChangeProductPriceResponseDto {

    private UUID id;
    private String name;
    private BigDecimal price;

    protected ChangeProductPriceResponseDto() {

    }

    public ChangeProductPriceResponseDto(
        UUID id,
        String name,
        BigDecimal price
    ) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public static ChangeProductPriceResponseDto from(Product product) {
        return new ChangeProductPriceResponseDto(
            product.getId(),
            product.getName().getValue(),
            product.getPrice().getValue()
        );
    }
}
