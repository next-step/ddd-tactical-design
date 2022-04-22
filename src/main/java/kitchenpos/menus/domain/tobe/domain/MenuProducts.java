package kitchenpos.menus.domain.tobe.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Embeddable
public class MenuProducts {
    private static final String MENU_PRODUCTS_MUST_NOT_BE_EMPTY = "메뉴 상품 목록은 빈 값이 아니어야 합니다. 입력 값 : %s";

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "menu_id",
            nullable = false,
            columnDefinition = "varbinary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )
    private List<MenuProduct> menuProducts;

    protected MenuProducts() {
    }

    public MenuProducts(List<MenuProduct> menuProducts) {
        validate(menuProducts);
        this.menuProducts = menuProducts;
    }

    private void validate(List<MenuProduct> menuProducts) {
        if (Objects.isNull(menuProducts) || menuProducts.isEmpty()) {
            throw new IllegalArgumentException(String.format(MENU_PRODUCTS_MUST_NOT_BE_EMPTY, menuProducts));
        }
    }

    public BigDecimal getTotalAmount() {
        long sum = menuProducts.stream().mapToLong(MenuProduct::getAmount).sum();
        return BigDecimal.valueOf(sum);
    }
}
