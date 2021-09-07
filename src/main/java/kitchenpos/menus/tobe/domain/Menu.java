package kitchenpos.menus.tobe.domain;

import kitchenpos.commons.tobe.domain.DisplayedName;
import kitchenpos.commons.tobe.domain.Price;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Menu {

    private static final String PRICE_AMOUNT_EXCEPTION_MESSAGE = "가격은 금액보다 적거나 같아야 합니다";

    private final UUID id;

    private final DisplayedName name;

    private final Price price;

    private final MenuProducts menuProducts;

    private final UUID menuGroupId;

    private boolean displayed;

    public Menu(final UUID id,
                final DisplayedName name,
                final Price price,
                final MenuProducts menuProducts,
                final UUID menuGroupId,
                final boolean displayed) {
        validate(price, menuProducts, menuGroupId, displayed);

        this.id = id;
        this.name = name;
        this.price = price;
        this.menuProducts = menuProducts;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
    }

    private void validate(final Price price,
                          final MenuProducts menuProducts,
                          final UUID menuGroupId,
                          final boolean displayed) {
        if (displayed && isPriceGreaterThanAmount(price, menuProducts)) {
            throw new IllegalArgumentException(PRICE_AMOUNT_EXCEPTION_MESSAGE);
        }
        if (Objects.isNull(menuGroupId)) {
            throw new IllegalArgumentException("메뉴는 메뉴 그룹에 속해야 합니다");
        }
    }

    private boolean isPriceGreaterThanAmount(final Price price, final MenuProducts menuProducts) {
        final BigDecimal menuPrice = price.value();
        final BigDecimal amount = menuProducts.getAmount();
        return menuPrice.compareTo(amount) > 0;
    }

    public void display() {
        if (isPriceGreaterThanAmount(price, menuProducts)) {
            throw new IllegalStateException(PRICE_AMOUNT_EXCEPTION_MESSAGE);
        }

        displayed = true;
    }

    public void hide() {
        displayed = false;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name.value();
    }

    public BigDecimal getPrice() {
        return price.value();
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }
}
