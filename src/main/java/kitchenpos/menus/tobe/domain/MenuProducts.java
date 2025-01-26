package kitchenpos.menus.tobe.domain;

import org.springframework.util.Assert;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class MenuProducts {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "menu_id",
            nullable = false,
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )
    private List<MenuProduct> menuProducts;

    public static MenuProducts newOne(List<MenuProduct> menuProducts) {
        Assert.isTrue(!Objects.isNull(menuProducts) && !menuProducts.isEmpty(), "메뉴 상품 정보가 누락되었습니다.");
        return new MenuProducts(menuProducts);
    }

    public MenuProducts() {

    }

    public MenuProducts(List<MenuProduct> menuProducts) {
        this.menuProducts = menuProducts;
    }

    public List<MenuProduct> getMenuProducts() {
        return this.menuProducts;
    }

    public BigDecimal getAmount() {
        return this.menuProducts.stream()
                .map(MenuProduct::getProductPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void updateProductPrice(UUID productId, BigDecimal productPrice) {
        this.menuProducts.stream()
                .filter(it -> it.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(NotFoundProductException::new)
                .updateProductPrice(productPrice);
    }
}
