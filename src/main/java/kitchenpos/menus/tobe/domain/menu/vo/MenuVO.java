package kitchenpos.menus.tobe.domain.menu.vo;

import kitchenpos.common.Name;
import kitchenpos.common.PositiveNumber;
import kitchenpos.common.Price;

import java.math.BigDecimal;

public class MenuVO {

    private PositiveNumber menuId;
    private Price price;
    private Name name;
    private PositiveNumber menuGroupiId;

    public MenuVO(BigDecimal price, String name, BigDecimal amount, Long menuGroupId) {
        this(null, price, name, amount, menuGroupId);
    }

    public MenuVO(Long menuId, BigDecimal price, String name, BigDecimal amount, Long menuGroupId) {
        this.menuId = new PositiveNumber(menuId);
        this.price = new Price(price);
        this.name = new Name(name);
        this.menuGroupiId = new PositiveNumber(menuGroupId);
    }

    public PositiveNumber getMenuId() {
        return menuId;
    }

    public BigDecimal getPrice() {
        return price.valueOf();
    }

    public String getName() {
        return name.valueOf();
    }

    public Long getMenuGroupiId() {
        return menuGroupiId.valueOf();
    }
}
