package kitchenpos.menus.tobe.application;

import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import kitchenpos.menus.tobe.dto.menu.ChangeMenuPriceRequest;
import kitchenpos.products.tobe.application.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static kitchenpos.ToBeFixtures.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("메뉴 가격 서비스")
class MenuPriceServiceTest {

    private MenuPriceService menuPriceService;
    private Menu menu;

    @BeforeEach
    void setUp() {
        ProductRepository productRepository = new InMemoryProductRepository();
        MenuRepository menuRepository = new InMemoryMenuRepository();
        menuPriceService = new MenuPriceService(menuRepository);
        Product product = productRepository.save(product("후라이드", 16_000L));
        menu = menuRepository.save(menu("후라이드치킨", 19_000L, true, menuProduct(product, 2L)));
    }

    @DisplayName("메뉴의 가격이 올바르지 않으면 변경할 수 없다.")
    @ParameterizedTest
    @CsvSource({"-1"})
    void changeNegativeMenuPrice(BigDecimal menuPrice) {
        assertThatThrownBy(() -> menuPriceService.changePrice(menu.getId(), new ChangeMenuPriceRequest(menuPrice)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴의 가격을 변경할 수 있다.")
    @ParameterizedTest
    @CsvSource({"300"})
    void changeMenuPrice(BigDecimal menuPrice) {
        assertDoesNotThrow(() -> menuPriceService.changePrice(menu.getId(), new ChangeMenuPriceRequest(menuPrice)));
    }

}
