package kitchenpos.menus.tobe.domain.model;

import kitchenpos.commons.tobe.domain.model.DisplayedName;
import kitchenpos.commons.tobe.domain.model.Price;
import kitchenpos.commons.tobe.domain.service.Validator;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class Menu {

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
                final boolean displayed,
                final Validator<Menu> validator) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuProducts = menuProducts;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;

        validator.validate(this);
    }

    public boolean isPriceGreaterThanAmount() {
        final BigDecimal menuPrice = price.value();
        final BigDecimal amount = menuProducts.getAmount();
        return menuPrice.compareTo(amount) > 0;
    }

    public void display() {
        if (isPriceGreaterThanAmount()) {
            throw new IllegalStateException("가격은 금액보다 적거나 같아야 합니다");
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

    public List<UUID> getProductIds() {
        return menuProducts.getProductIds();
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public boolean isDisplayed() {
        return displayed;
    }
}
