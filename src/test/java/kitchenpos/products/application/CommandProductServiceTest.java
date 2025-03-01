package kitchenpos.products.application;

import kitchenpos.fixture.ProductFixtures;
import kitchenpos.core.products.application.CommandProductService;
import kitchenpos.core.products.application.dto.CreateProductRequest;
import kitchenpos.core.products.tobe.domain.*;
import kitchenpos.core.products.tobe.domain.exception.InvalidProductNameException;
import kitchenpos.core.shared.identifier.ProductId;
import kitchenpos.core.shared.value.Money;
import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.core.menus.domain.Menu;
import kitchenpos.core.menus.domain.MenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static kitchenpos.fixture.Fixtures.menu;
import static kitchenpos.fixture.Fixtures.menuProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CommandProductServiceTest {
    private TobeProductRepository productRepository;
    private MenuRepository menuRepository;
    private CommandProductService productService;


    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        menuRepository = new InMemoryMenuRepository();
        productService = new CommandProductService(productRepository, menuRepository);
    }

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void create() {
        CreateProductRequest expected = ProductFixtures.createProductRequest("후라이드", 16_000L);
        final Product actual = productService.addProduct(expected);
        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName()).isEqualTo(expected.name()),
            () -> assertThat(actual.getPrice()).isEqualTo(expected.price())
        );
    }

    @DisplayName("상품의 가격이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(longs = -1000)
    @ParameterizedTest
    void create(final Long price) {
        assertThrows(IllegalArgumentException.class, () -> productService.addProduct(ProductFixtures.createProductRequest("후라이드", price)));
    }

    @DisplayName("상품의 이름이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
    @NullSource
    @ParameterizedTest
    void create(final String name) {
        assertThatThrownBy(() -> productService.addProduct(ProductFixtures.createProductRequest(name, 16_000L)))
            .isInstanceOf(InvalidProductNameException.class);
    }

    @DisplayName("상품의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        final ProductId productId = productRepository.save(ProductFixtures.product("후라이드", 16_000L)).getId();
        final ProductPrice expected = ProductPrice.of(Money.wons(15_000L));
        final Product actual = productService.changePrice(productId, expected);
        assertThat(actual.getPrice()).isEqualTo(expected);
    }

    @DisplayName("상품의 가격이 올바르지 않으면 변경할 수 없다.")
    @ValueSource(longs = -1000)
    @ParameterizedTest
    void changePrice(final long price) {
        final ProductId productId = productRepository.save(ProductFixtures.product("후라이드", 16_000L)).getId();
        assertThatThrownBy(() -> productService.changePrice(productId, ProductPrice.of(Money.wons(price))))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 가격이 변경될 때 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴가 숨겨진다.")
    @Test
    void changePriceInMenu() {
        final Product product = productRepository.save(ProductFixtures.product("후라이드", 16_000L));
        final Menu menu = menuRepository.save(menu(19_000L, true, menuProduct(product, 2L)));
        productService.changePrice(product.getId(), ProductPrice.of(Money.wons(8_000L)));
        assertThat(menuRepository.findById(menu.getId()).get().isDisplayed()).isFalse();
    }
}
