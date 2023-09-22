package kitchenpos.menus.application.dto;

public class ChangeMenuPriceRequest {
    private Long price;

    public ChangeMenuPriceRequest() {
    }

    public ChangeMenuPriceRequest(Long price) {
        this.price = price;
    }

    public Long getPrice() {
        return price;
    }
}
