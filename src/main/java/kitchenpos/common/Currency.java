package kitchenpos.common;

public enum Currency {
    WON("원"),
    USD("달러");
    private String description;

    Currency(String description) {
        this.description = description;
    }

    public static Currency defaultCurrency() {
        return WON;
    }
}
