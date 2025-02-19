package kitchenpos.products.application.dto;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.products.tobe.domain.model.Product;

public class FindProductResponseDto {
    private UUID id;
    private String name;
    private BigDecimal price;

    protected FindProductResponseDto() {

    }

    public FindProductResponseDto(
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

    public static FindProductResponseDto from(Product product) {
        return new FindProductResponseDto(
            product.getId(),
            product.getName().getValue(),
            product.getPrice().getValue()
        );
    }
}
