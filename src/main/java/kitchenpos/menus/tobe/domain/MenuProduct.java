package kitchenpos.menus.tobe.domain;

public class MenuProduct {
    private static final String LESS_THAN_ZERO_MESSAGE = "수량은 0보다 작을 수 없습니다.";

    private long quantity;
    private DisplayedName name;
    private Product product;

    public MenuProduct(final long quantity, final DisplayedName name, final Product product) {
        if (quantity < 0) {
            new IllegalArgumentException(LESS_THAN_ZERO_MESSAGE);
        }
        this.quantity = quantity;
        this.name = name;
        this.product = product;
    }

    public Price getSumOfPrice() {
        return product.getPrice().multiply(quantity);
    }

    public boolean lessThan(final Price price) {
        if (this.getSumOfPrice().compareTo(price) < 0) {
            return true;
        }
        return false;
    }
}
