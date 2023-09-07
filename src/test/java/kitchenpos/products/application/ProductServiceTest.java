package kitchenpos.products.application;

import kitchenpos.menus.application.InMemoryMenuGroupRepository;
import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.application.MenuService;
import kitchenpos.menus.domain.Menu;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.dto.ProductRequest;
import kitchenpos.products.exception.DisplayedNameException;
import kitchenpos.products.exception.ProductPriceException;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static kitchenpos.Fixtures.menu;
import static kitchenpos.Fixtures.menuProduct;
import static kitchenpos.products.fixture.ProductFixture.product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class ProductServiceTest {
    private ProductRepository productRepository;
    private MenuService menuService;
    private PurgomalumClient purgomalumClient;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        purgomalumClient = new FakePurgomalumClient();
        productService = new ProductService(productRepository, menuService, purgomalumClient);
        this.menuService = new MenuService(
                new InMemoryMenuRepository()
                , new InMemoryMenuGroupRepository()
                , productRepository
                , purgomalumClient
        );
    }

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void create() {
        final ProductRequest expected = createProductRequest("후라이드", 16_000L);
        final Product actual = productService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getNameValue()).isEqualTo(expected.getName()),
                () -> assertThat(actual.getPriceValue()).isEqualTo(expected.getPrice())
        );
    }

    @DisplayName("상품의 가격이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void create(final BigDecimal price) {
        final ProductRequest expected = createProductRequest("후라이드", price);
        assertThatThrownBy(() -> productService.create(expected))
                .isInstanceOf(ProductPriceException.class);
    }

    @DisplayName("상품의 이름이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
    @NullSource
    @ParameterizedTest
    void create(final String name) {
        final ProductRequest expected = createProductRequest(name, 16_000L);
        assertThatThrownBy(() -> productService.create(expected))
                .isInstanceOf(DisplayedNameException.class);
    }

    @DisplayName("상품의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        final UUID productId = productRepository.save(product("후라이드", 16_000L)).getId();
        final BigDecimal changePrice = BigDecimal.valueOf(15_000L);

        final Product actual = productService.changePrice(productId, changePrice);

        assertThat(actual.getPriceValue()).isEqualTo(changePrice);
    }

    @DisplayName("상품의 가격이 올바르지 않으면 변경할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void changePrice(final BigDecimal price) {
        final UUID productId = productRepository.save(product("후라이드", 16_000L)).getId();
        assertThatThrownBy(() -> productService.changePrice(productId, price))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 가격이 변경될 때 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴가 숨겨진다.")
    @Test
    void changePriceInMenu() {
        final Product product = productRepository.save(product("후라이드", 16_000L));
        final Menu menu = menuService.create(menu(19_000L, true, menuProduct(product, 2L)));
        productService.changePrice(product.getId(), BigDecimal.valueOf(8_000L));
        assertThat(menuService.findById(menu.getId()).isDisplayed()).isFalse();
    }

    @DisplayName("상품의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        productRepository.save(product("후라이드", 16_000L));
        productRepository.save(product("양념치킨", 16_000L));
        final List<Product> actual = productService.findAll();
        assertThat(actual).hasSize(2);
    }

    private ProductRequest createProductRequest(final String name, final long price) {
        return new ProductRequest(name, BigDecimal.valueOf(price));
    }

    private ProductRequest createProductRequest(final String name, BigDecimal price) {
        return new ProductRequest(name, price);
    }

}
