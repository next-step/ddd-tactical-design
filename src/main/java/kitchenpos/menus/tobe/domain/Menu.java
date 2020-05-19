package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;
import kitchenpos.common.model.Price;

public class Menu {

    private Long id;
    private String name;
    private Price price;
    private Long menuGroupId;
    private MenuProducts menuProducts;

    public Menu(String name, BigDecimal price, Long menuGroupId, MenuProducts menuProducts) {
        this(null, name, Price.of(price), menuGroupId, menuProducts);
    }

    public Menu(Long id, String name, BigDecimal price, Long menuGroupId, MenuProducts menuProducts) {
        this(id, name, Price.of(price), menuGroupId, menuProducts);
    }

    public Menu(Long id, String name, Price price, Long menuGroupId, MenuProducts menuProducts) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.menuProducts = menuProducts;
    }

    public Price computeMenuProductsPriceSum() {
        return menuProducts.computePriceSum();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    public Long getMenuGroupId() {
        return menuGroupId;
    }

    public MenuProducts getMenuProducts() {
        return menuProducts;
    }
}
