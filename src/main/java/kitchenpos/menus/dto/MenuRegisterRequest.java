package kitchenpos.menus.dto;

import kitchenpos.menus.domain.tobe.domain.TobeMenuProduct;
import kitchenpos.menus.domain.tobe.domain.vo.MenuGroupId;
import kitchenpos.support.dto.DTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.List;

public class MenuRegisterRequest extends DTO {
    @NotBlank
    private String name;
    @PositiveOrZero
    private BigDecimal price;
    @NotNull
    private MenuGroupId menuGroupId;
    @NotNull
    private List<TobeMenuProduct> menuProducts;
    @NotBlank
    private boolean displayed;

    public MenuRegisterRequest(String name, BigDecimal price, MenuGroupId menuGroupId, List<TobeMenuProduct> menuProducts, boolean displayed) {
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.menuProducts = menuProducts;
        this.displayed = displayed;
    }

    public MenuRegisterRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public MenuGroupId getMenuGroupId() {
        return menuGroupId;
    }

    public void setMenuGroupId(MenuGroupId menuGroupId) {
        this.menuGroupId = menuGroupId;
    }

    public List<TobeMenuProduct> getMenuProducts() {
        return menuProducts;
    }

    public void setMenuProducts(List<TobeMenuProduct> menuProducts) {
        this.menuProducts = menuProducts;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public void setDisplayed(boolean displayed) {
        this.displayed = displayed;
    }

    @Override
    public String toString() {
        return "MenuRegisterRequest{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", menuGroupId=" + menuGroupId +
                ", menuProducts=" + menuProducts +
                ", displayed=" + displayed +
                '}';
    }
}
