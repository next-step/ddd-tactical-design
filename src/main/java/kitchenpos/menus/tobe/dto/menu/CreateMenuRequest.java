package kitchenpos.menus.tobe.dto.menu;

import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CreateMenuRequest {
    private final UUID menuGroupId;
    private final BigDecimal price;
    private String menuName;
    @Size(min = 1, message = "메뉴 상품을 등록해주세요.")
    private List<MenuProductRequest> menuProducts = new ArrayList<>();

    public CreateMenuRequest(UUID menuGroupId, String menuName, BigDecimal price, List<MenuProductRequest> menuProducts) {
        this.menuGroupId = menuGroupId;
        this.menuName = menuName;
        this.price = price;
        this.menuProducts = menuProducts;
    }

    public List<MenuProductRequest> getMenuProducts() {
        return this.menuProducts;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getMenuName() {
        return this.menuName;
    }

    public UUID getMenuGroupId() {
        return this.menuGroupId;
    }
}
