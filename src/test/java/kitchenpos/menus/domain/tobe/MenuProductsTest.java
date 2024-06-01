package kitchenpos.menus.domain.tobe;

import kitchenpos.products.domain.tobe.FakeProfanities;
import kitchenpos.products.domain.tobe.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MenuProductsTest {
    List<MenuProduct> menuProductList = new ArrayList<>();
    Product product;

    @BeforeEach
    void setUp() {
        product = Product.createProduct("후라이드", BigDecimal.valueOf(10000), new FakeProfanities());
        menuProductList.add(MenuProduct.createMenuProduct(product.getId(), product.getPrice().getPriceValue(), 3));
        menuProductList.add(MenuProduct.createMenuProduct(UUID.randomUUID(), BigDecimal.valueOf(20000), 2));
        menuProductList.add(MenuProduct.createMenuProduct(UUID.randomUUID(), BigDecimal.valueOf(10000), 1));

    }

    @Test
    void totalAmount() {
        MenuProducts menuProducts = new MenuProducts(menuProductList);
        assertThat(menuProducts.totalAmount()).isEqualTo(BigDecimal.valueOf(80000));
    }

    @Test
    void changeMenuProductsPrice() {
        MenuProducts menuProducts = new MenuProducts(menuProductList);
        menuProducts.changeMenuProductsPrice(product.getId(), BigDecimal.valueOf(20000));

        BigDecimal expectedTotalAmount = menuProductList.stream()
                .map(MenuProduct::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        assertThat(menuProducts.totalAmount()).isEqualTo(expectedTotalAmount);
    }

    @Test
    void isExpensiveTotalPrice() {
        MenuProducts menuProducts = new MenuProducts(menuProductList);
        BigDecimal expensivePrice = menuProducts.totalAmount().add(BigDecimal.valueOf(10000));
        Price price = Price.createPrice(expensivePrice);

        assertThat(menuProducts.isExpensiveTotalPrice(price)).isTrue();
    }
}
