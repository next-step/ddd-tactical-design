package kitchenpos.menus.tobe.domain;

public final class MenuProductBuilder {
    private Long menuId;
    private Long productId;
    private long quantity;
    private MenuProductPrice menuProductPrice;

    private MenuProductBuilder() {
    }

    public static MenuProductBuilder aMenuProduct() {
        return new MenuProductBuilder();
    }

    public MenuProductBuilder withMenuId(Long menuId) {
        this.menuId = menuId;
        return this;
    }

    public MenuProductBuilder withProductId(Long productId) {
        this.productId = productId;
        return this;
    }

    public MenuProductBuilder withQuantity(long quantity) {
        this.quantity = quantity;
        return this;
    }

    public MenuProductBuilder withMenuProductPrice(MenuProductPrice menuProductPrice) {
        this.menuProductPrice = menuProductPrice;
        return this;
    }

    public MenuProduct build() {
        return new MenuProduct(menuId, productId, quantity, menuProductPrice);
    }
}
