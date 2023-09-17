package kitchenpos.menus.tobe.application.dto;

import kitchenpos.menus.tobe.domain.menu.MenuName;
import kitchenpos.menus.tobe.domain.menu.MenuPrice;
import kitchenpos.menus.tobe.domain.menugroup.TobeMenuGroup;
import kitchenpos.menus.tobe.domain.menugroup.TobeMenuGroupName;
import kitchenpos.menus.tobe.domain.menuproduct.TobeMenuProduct;

import javax.persistence.Embedded;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class TobeMenuCreateRequest {
    private String name;
    private BigDecimal price;
    private ToBeMenuGroupRequest menuGroup;
    private boolean displayed;
    private List<TobeMenuProductRequest> tobeMenuProducts;
    private UUID menuGroupId;

    public TobeMenuCreateRequest(String name, BigDecimal price, ToBeMenuGroupRequest menuGroup, boolean displayed,
                                 List<TobeMenuProductRequest> tobeMenuProducts, UUID menuGroupId) {
        this.name = name;
        this.price = price;
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.tobeMenuProducts = tobeMenuProducts;
        this.menuGroupId = menuGroupId;
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

    public List<TobeMenuProductRequest> getTobeMenuProducts() {
        return tobeMenuProducts;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }
}
