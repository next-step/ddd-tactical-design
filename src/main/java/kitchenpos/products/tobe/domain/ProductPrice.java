package kitchenpos.products.tobe.domain;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuRepository;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static java.util.Objects.isNull;

@Embeddable
public final class ProductPrice {
    @Column(name = "price", nullable = false)
    private BigDecimal value;

    protected ProductPrice() {
    }

    public static ProductPrice create(BigDecimal value) {
        validateProductPriceIsNull(value);
        validateProductPriceIsNegative(value);

        ProductPrice productPrice = new ProductPrice();
        productPrice.value = value;
        return productPrice;
    }

    public static ProductPrice update(UUID productId, BigDecimal changedValue, MenuRepository menuRepository) {
        validateProductPriceIsNull(changedValue);
        validateProductPriceIsNegative(changedValue);

        checkMenuCouldBeDisplayedAfterProductPriceChanged(productId, menuRepository);

        ProductPrice productPrice = new ProductPrice();
        productPrice.value = changedValue;
        return productPrice;
    }

    private static void checkMenuCouldBeDisplayedAfterProductPriceChanged(UUID productId, MenuRepository menuRepository) {
        List<Menu> menusWhichContainsProduct = menuRepository.findAllByProductId(productId);
        menusWhichContainsProduct.forEach(menu -> {
            BigDecimal totalMenuProductPrice = menu.getMenuProducts()
                    .parallelStream()
                    .map(menuProduct -> menuProduct.getProduct().getPrice())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            if (isMenuPriceGreaterThanSumOfMenuProducts(menu, totalMenuProductPrice)) {
                menu.setDisplayed(false);
            }
        });
    }

    private static boolean isMenuPriceGreaterThanSumOfMenuProducts(Menu menu, BigDecimal totalMenuProductPrice) {
        return menu.getPrice().compareTo(totalMenuProductPrice) > 0;
    }

    private static void validateProductPriceIsNegative(BigDecimal value) {
        if (isNegative(value)) {
            throw new IllegalArgumentException("상품 가격은 음수일 수 없습니다.");
        }
    }

    private static void validateProductPriceIsNull(BigDecimal value) {
        if (isNull(value)) {
            throw new IllegalArgumentException("상품 가격은 비어있을 수 없습니다.");
        }
    }

    private static boolean isNegative(BigDecimal value) {
        return BigDecimal.ZERO.compareTo(value) > 0;
    }

    BigDecimal getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductPrice that = (ProductPrice) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
