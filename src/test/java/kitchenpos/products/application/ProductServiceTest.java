package kitchenpos.products.application;

import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductPrice;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.ui.dto.CreateProductRequest;
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
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class ProductServiceTest {
    private ProductRepository productRepository;
    private MenuRepository menuRepository;
    private PurgomalumClient purgomalumClient;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        menuRepository = new InMemoryMenuRepository();
        purgomalumClient = new FakePurgomalumClient();
        productService = new ProductService(productRepository, menuRepository, purgomalumClient);
    }

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void create2() {
        final CreateProductRequest request = createProductRequest("후라이드", 16_000L);
        final Product actual = productService.create(request);
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(request.getName()),
                () -> assertThat(actual.getPrice()).isEqualTo(new ProductPrice(request.getPrice()))
        );
    }

    @DisplayName("상품의 가격이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void create2(final BigDecimal price) {
        final CreateProductRequest request = createProductRequest("후라이드", price);
        assertThatThrownBy(() -> productService.create(request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 이름이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
    @NullSource
    @ParameterizedTest
    void create2(final String name) {
        final CreateProductRequest request = createProductRequest(name, 16_000L);
        assertThatThrownBy(() -> productService.create(request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 가격을 변경할 수 있다.")
    @Test
    void changePrice2() {
        BigDecimal newPrice = BigDecimal.valueOf(15_000L);
        final UUID productId = productRepository.save(new Product("후라이드", BigDecimal.valueOf(16_000L), purgomalumClient)).getId();
        final Product actual = productService.changePrice(productId, newPrice);
        assertThat(actual.getPrice()).isEqualTo(new ProductPrice(newPrice));
    }

    @DisplayName("상품의 가격이 올바르지 않으면 변경할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void changePrice2(final BigDecimal price) {
        final UUID productId = productRepository.save(new Product("후라이드", BigDecimal.valueOf(16_000L), purgomalumClient)).getId();
        assertThatThrownBy(() -> productService.changePrice(productId, price))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 가격이 변경될 때 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴가 숨겨진다.")
    @Test
    void changePriceInMenu2() {
        final Product product = productRepository.save(new Product("후라이드", BigDecimal.valueOf(16_000L), purgomalumClient));
        final Menu menu = menuRepository.save(menu(19_000L, true, menuProduct(product, 2L)));
        productService.changePrice(product.getId(), BigDecimal.valueOf(8_000L));
        assertThat(menuRepository.findById(menu.getId()).get().isDisplayed()).isFalse();
    }

    @DisplayName("상품의 목록을 조회할 수 있다.")
    @Test
    void findAll2() {
        productRepository.save(new Product("후라이드", BigDecimal.valueOf(16_000L), purgomalumClient));
        productRepository.save(new Product("양념치킨", BigDecimal.valueOf(16_000L), purgomalumClient));
        final List<Product> actual = productService.findAll();
        assertThat(actual).hasSize(2);
    }

    private CreateProductRequest createProductRequest(final String name, final long price) {
        return createProductRequest(name, BigDecimal.valueOf(price));
    }

    private CreateProductRequest createProductRequest(final String name, final BigDecimal price) {
        return new CreateProductRequest(name, price);
    }
}
