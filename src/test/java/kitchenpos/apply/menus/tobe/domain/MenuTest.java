package kitchenpos.apply.menus.tobe.domain;

import kitchenpos.apply.products.tobe.domain.InMemoryProductRepository;
import kitchenpos.apply.products.tobe.domain.Product;
import kitchenpos.apply.products.tobe.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static kitchenpos.apply.fixture.MenuFixture.menu;
import static kitchenpos.apply.fixture.MenuFixture.menuProduct;
import static kitchenpos.apply.fixture.TobeFixtures.product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuTest {

    private ProductRepository productRepository;
    private MenuRepository menuRepository;
    private MenuPriceCalculator menuPriceCalculator;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        menuRepository = new InMemoryMenuRepository();
        menuPriceCalculator = new MenuPriceCalculator(productRepository);
    }

    @Test
    void changePrice() {
        Product product = product();
        productRepository.save(product);
        MenuProduct menuProduct = menuProduct(product, 1);
        Menu menu = menu(1000, menuProduct);
        menuRepository.save(menu);
        assertThat(menu.isDisplayed()).isTrue();

        BigDecimal totalPrice = menuPriceCalculator.getTotalPriceFrom(menu);

        assertThatThrownBy(() -> menu.changePrice(BigDecimal.valueOf(Double.MAX_VALUE), totalPrice))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

}