package kitchenpos.apply.menus.tobe.domain;

import kitchenpos.apply.menus.tobe.ui.MenuProductRequest;
import kitchenpos.apply.menus.tobe.ui.MenuRequest;
import kitchenpos.apply.products.tobe.domain.InMemoryProductRepository;
import kitchenpos.apply.products.tobe.domain.Product;
import kitchenpos.apply.products.tobe.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static kitchenpos.apply.fixture.MenuFixture.*;
import static kitchenpos.apply.fixture.TobeFixtures.product;
import static org.assertj.core.api.Assertions.assertThat;


@SuppressWarnings("NonAsciiCharacters")
class MenuPriceCalculatorTest {

    private ProductRepository productRepository;
    private MenuPriceCalculator menuPriceCalculator;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        menuPriceCalculator = new MenuPriceCalculator(productRepository);
    }

    @Test
    @DisplayName("메뉴에 포함된 구성품의 가격 총합을 가져온다")
    void getTotalTestFromMenu() {
        Product 타코 = product("타코", 6_000);
        Product 브리또 = product("브리또", 5_000);
        Product 나쵸 = product("나쵸", 2_000);

        productRepository.save(타코);
        productRepository.save(브리또);
        productRepository.save(나쵸);
        Menu menu = menu(3_000, menuProduct(타코, 3L), menuProduct(브리또, 1L), menuProduct(나쵸, 1L));

        BigDecimal totalPriceFrom = menuPriceCalculator.getTotalPriceFrom(menu);
        assertThat(totalPriceFrom.intValue()).isEqualTo(25_000);
    }

    @Test
    @DisplayName("메뉴 요청에 포함된 구성품의 가격 총합을 가져온다")
    void getTotalTestFromMenuRequest() {
        Product taco = product("타코", 6_000);
        Product burrito = product("브리또", 5_000);

        Product 타코스 = productRepository.save(taco);
        Product 브리또 = productRepository.save(burrito);

        MenuProductRequest 타코_세개 = menuProductRequest(UUID.fromString(타코스.getId()), 3);
        MenuProductRequest 브리또_다섯개 = menuProductRequest(UUID.fromString(브리또.getId()), 5);

        MenuRequest menu = menuRequest(3_000, 타코_세개, 브리또_다섯개);

        BigDecimal totalPriceFrom = menuPriceCalculator.getTotalPriceFrom(menu);
        assertThat(totalPriceFrom.intValue()).isEqualTo(43_000);
    }
}