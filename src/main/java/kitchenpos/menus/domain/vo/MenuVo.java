package kitchenpos.menus.domain.vo;

import java.util.List;
import java.util.UUID;

public class MenuVo {
    private String name;
    private Long price;
    private UUID menuGroupId;
    private boolean displayed;
    private List<MenuProductVo> menuProducts;

    public MenuVo() {
    }

    public MenuVo(String name, Long price, UUID menuGroupId, boolean displayed, List<MenuProductVo> menuProducts) {
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public List<MenuProductVo> getMenuProducts() {
        return menuProducts;
    }
}
