package kitchenpos.menus.tobe.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Embeddable
public class MenuProducts {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "menu_id", nullable = false)
    List<TobeMenuProduct> menuProducts;

    protected MenuProducts() {
    }

    private MenuProducts(List<TobeMenuProduct> menuProducts) {
        this.menuProducts = menuProducts;
    }

    public static MenuProducts of(TobeMenuProduct... menuProducts) {
        return new MenuProducts(List.of(menuProducts));
    }

    public static MenuProducts of(List<TobeMenuProduct> menuProducts) {
        return new MenuProducts(menuProducts);
    }


    /**
     * 메뉴의 가격이 메뉴에 속한 상품의 가격보다 적거나 같은지 확인합니다.
     *
     * @param menuPrice the menu price
     * @return the boolean
     */
    public Boolean isOverProductPrice(BigDecimal menuPrice) {
        BigDecimal productTotalPrice = calculateTotalPrice();
        return menuPrice.compareTo(productTotalPrice) > 0;
    }

    /**
     * 메뉴 상품의 총 가격을 계산합니다.
     *
     * @return the total price
     */
    public BigDecimal calculateTotalPrice() {
        return menuProducts.stream()
                .map(TobeMenuProduct::calculatedPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * 식별자를 제공해 해당 메뉴 상품을 찾습니다.
     * 없을 경우 Optional.empty() 를 반환합니다.
     *
     * @param menuProductId the menu product id
     * @return the optional
     */
    public Optional<TobeMenuProduct> get(UUID menuProductId) {
        return menuProducts.stream()
                .filter(menuProduct -> menuProduct.productId().equals(menuProductId))
                .findFirst();
    }

}
