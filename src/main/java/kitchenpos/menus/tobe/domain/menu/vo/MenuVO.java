package kitchenpos.menus.tobe.domain.menu.vo;

import kitchenpos.common.Index;
import kitchenpos.common.Name;
import kitchenpos.common.PositiveNumber;
import kitchenpos.common.Price;

import java.math.BigDecimal;

public class MenuVO {

    private Index menuId;
    private Price price;
    private Name name;
    private PositiveNumber menuGroupiId;

    public MenuVO(BigDecimal price, String name, Long menuGroupId) {
        this(null, price, name, menuGroupId);
    }

    public MenuVO(Long menuId, BigDecimal price, String name, Long menuGroupId) {
        this.menuId = new Index(menuId);
        this.price = new Price(price);
        this.name = new Name(name);
        this.menuGroupiId = new PositiveNumber(menuGroupId);
    }

    public Long getMenuId() {
        return menuId.valueOf();
    }

    public BigDecimal getPrice() {
        return price.valueOf();
    }

    public String getName() {
        return name.valueOf();
    }

    public Long getMenuGroupId() {
        return menuGroupiId.valueOf();
    }

}
