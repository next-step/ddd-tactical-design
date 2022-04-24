package kitchenpos.menus.dto;

import kitchenpos.menus.domain.MenuProducts;
import kitchenpos.menus.domain.tobe.domain.vo.MenuGroupId;
import kitchenpos.support.dto.DTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public class MenuRegisterRequest extends DTO {
    @NotBlank(message = "메뉴의 이름은 필수값 입니다")
    private String name;
    @NotNull(message = "메뉴의 가격은 0 이상이어야 합니다")
    @PositiveOrZero(message = "메뉴의 가격은 0 이상이어야 합니다")
    private BigDecimal price;
    @NotNull(message = "메뉴는 반드시 하나의 메뉴그룹에 속해야 합니다")
    private MenuGroupId menuGroupId;
    @NotNull(message = "메뉴는 반드시 하나 이상의 상품을 포함하고 있어야 합니다")
    private MenuProducts menuProducts;
    @NotNull(message = "메뉴의 전시상태를 선택해 주세요")
    private Boolean displayed;

    public MenuRegisterRequest(String name, BigDecimal price, MenuGroupId menuGroupId, MenuProducts menuProducts, Boolean displayed) {
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

    public MenuProducts getMenuProducts() {
        return menuProducts;
    }

    public void setMenuProducts(MenuProducts menuProducts) {
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
