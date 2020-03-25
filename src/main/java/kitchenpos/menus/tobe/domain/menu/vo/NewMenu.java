package kitchenpos.menus.tobe.domain.menu.vo;

import kitchenpos.common.Name;
import kitchenpos.common.PositiveNumber;
import kitchenpos.common.Price;

import java.math.BigDecimal;

public class NewMenu {

    private PositiveNumber menuId;
    private Price price;
    private Name name;
    private Price amount;
    private PositiveNumber menuGroupiId;

    public NewMenu(BigDecimal price, String name, BigDecimal amount, Long menuGroupId) {
        this(null, price, name, amount, menuGroupId);
    }

    public NewMenu(Long menuId, BigDecimal price, String name, BigDecimal amount, Long menuGroupId) {
        this.menuId = new PositiveNumber(menuId);
        this.price = new Price(price);
        this.name = new Name(name);
        this.amount = new Price(amount);
        this.menuGroupiId = new PositiveNumber(menuGroupId);
    }

    public PositiveNumber getMenuId() {
        return menuId;
    }

    public Price getPrice() {
        return price;
    }

    public Name getName() {
        return name;
    }

    public Price getAmount() {
        return amount;
    }

    public Long getMenuGroupiId() {
        return menuGroupiId.valueOf();
    }
}
