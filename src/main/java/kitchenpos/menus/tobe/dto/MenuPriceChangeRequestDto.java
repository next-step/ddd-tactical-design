package kitchenpos.menus.tobe.dto;

public class MenuPriceChangeRequestDto {
    private Long price;

    public MenuPriceChangeRequestDto() {
    }

    public MenuPriceChangeRequestDto(Long price) {
        this.price = price;
    }

    public Long getPrice() {
        return price;
    }
}
