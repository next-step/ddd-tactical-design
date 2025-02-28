package kitchenpos.menu.tobe.application.dto.request;

public record MenuPriceChangeRequest (Long price) {
    public static MenuPriceChangeRequest of(long price) {
        return new MenuPriceChangeRequest(price);
    }
}
