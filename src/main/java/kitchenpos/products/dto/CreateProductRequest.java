package kitchenpos.products.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CreateProductRequest {

    @NotEmpty(message = "상품명은 반드시 포함되어야 합니다.")
    private String name;

    @NotNull(message = "상품 가격은 반드시 포함되어야 합니다.")
    @Min(value = 1L, message = "상품 가격은 0원 이하일 수 없습니다.")
    private Long price;

    private CreateProductRequest() { }

    public CreateProductRequest(String name, Long price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price;
    }
}
