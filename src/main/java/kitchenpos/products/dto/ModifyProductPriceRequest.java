package kitchenpos.products.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ModifyProductPriceRequest {

    @NotNull
    @Min(value = 1L, message = "상품 가격은 0원 이하일 수 없습니다.")
    private Long price;

    private ModifyProductPriceRequest() { }

    public ModifyProductPriceRequest(Long price) {
        this.price = price;
    }

    public Long getPrice() {
        return price;
    }
}
