package kitchenpos.menus.tobe.application.dto;

import kitchenpos.menus.tobe.domain.menu.TobeMenu;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class TobeMenuCreateResponse {
    private UUID id;
    private String name;
    private BigDecimal price;
    private ToBeMenuGroupRequest menuGroup;
    private boolean displayed;
    private List<TobeMenuProductRequest> menuProducts;

    public TobeMenuCreateResponse(UUID id, String name, BigDecimal price, ToBeMenuGroupRequest menuGroup,
                                  boolean displayed, List<TobeMenuProductRequest> menuProducts) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public static TobeMenuCreateResponse of(TobeMenu tobeMenu) {
        return new TobeMenuCreateResponse(tobeMenu.getId(), tobeMenu.getMenuName().getName(),
                                          tobeMenu.getPrice().getPrice(),
                                          ToBeMenuGroupRequest.from(tobeMenu.getTobeMenuGroup()),
                                          tobeMenu.isDisplayed(),
                                          TobeMenuProductRequest.from(tobeMenu.getTobeMenuProducts()));
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ToBeMenuGroupRequest getMenuGroup() {
        return menuGroup;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public List<TobeMenuProductRequest> getMenuProducts() {
        return menuProducts;
    }
}
