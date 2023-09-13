package kitchenpos.apply.menu.tobe.application;

import kitchenpos.apply.menus.tobe.application.DefaultMenuPriceChecker;
import kitchenpos.apply.menus.tobe.domain.Menu;
import kitchenpos.apply.menus.tobe.domain.MenuProduct;
import kitchenpos.apply.menus.tobe.domain.MenuRepository;
import kitchenpos.apply.products.tobe.domain.Product;
import kitchenpos.apply.products.tobe.domain.ProductRepository;
import kitchenpos.menus.tobe.application.InMemoryMenuRepository;
import kitchenpos.products.tobe.InMemoryProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static kitchenpos.apply.fixture.MenuFixture.menu;
import static kitchenpos.apply.fixture.MenuFixture.menuProduct;
import static kitchenpos.apply.fixture.TobeFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NonAsciiCharacters")
class DefaultMenuPriceCheckerTest {

    private ProductRepository productRepository;

    private MenuRepository menuRepository;

    private DefaultMenuPriceChecker priceChecker;


    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        menuRepository = new InMemoryMenuRepository();
        priceChecker = new DefaultMenuPriceChecker(productRepository, menuRepository);
    }


    @Test
    void isTotalPriceLowerThanMenuTest() {
        DefaultMenuPriceChecker priceChecker = new DefaultMenuPriceChecker(productRepository, menuRepository);
        Product product = product();
        productRepository.save(product);

        List<MenuProduct> 삼만이천원메뉴 = List.of(menuProduct(product, 2));

        assertThat(priceChecker.isTotalPriceLowerThanMenu(BigDecimal.ZERO, 삼만이천원메뉴)).isFalse();
        assertThat(priceChecker.isTotalPriceLowerThanMenu(new BigDecimal(Long.MAX_VALUE), 삼만이천원메뉴)).isTrue();
    }

    @Test
    void checkMenuPriceAndHideMenuIfTotalPriceLowerTest() {
        Product product = product();
        productRepository.save(product);

        List<MenuProduct> 삼만이천원메뉴 = List.of(menuProduct(product, 2));
        Menu 삼천원 = menu(3_000L, 삼만이천원메뉴.toArray(new MenuProduct[0]));
        Menu 이만원 = menu(20_000L, 삼만이천원메뉴.toArray(new MenuProduct[0]));
        menuRepository.save(삼천원);
        menuRepository.save(이만원);

        assertThat(삼천원.isDisplayed()).isTrue();
        assertThat(이만원.isDisplayed()).isTrue();

        product.changePrice(new BigDecimal(5_000));

        priceChecker.checkMenuPriceAndHideMenuIfTotalPriceLower(UUID.fromString(product.getId()));


        assertThat(삼천원.isDisplayed()).isTrue();
        assertThat(이만원.isDisplayed()).isFalse();
    }
}
