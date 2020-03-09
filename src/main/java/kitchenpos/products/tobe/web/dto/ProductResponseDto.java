package kitchenpos.products.tobe.web.dto;

import java.math.BigDecimal;

public class ProductResponseDto {

    private Long id;
    private String name;
    private BigDecimal price;

    public ProductResponseDto(Long id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
