package kitchenpos.products.application.dto;

public class ChangePriceRequest {
    private Long price;

    public Long getPrice() {
        return price;
    }

    public ChangePriceRequest() {
    }

    public ChangePriceRequest(Long price) {
        this.price = price;
    }
}
