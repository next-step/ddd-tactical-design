package kitchenpos.product.application;

import kitchenpos.product.adapter.out.persistence.ProductOutPortCatalog;
import kitchenpos.product.application.port.out.LoadProductPort;
import kitchenpos.product.application.port.out.UpdateProductPort;
import kitchenpos.product.domain.Product;
import kitchenpos.product.domain.ProductId;
import kitchenpos.profanity.application.port.out.FakeProfanityFilterPort;
import kitchenpos.menu.domain.InMemoryMenuRepository;
import kitchenpos.menu.domain.MenuRepository;
import kitchenpos.product.adapter.out.persistence.ProductEntity;
import kitchenpos.profanity.application.port.out.ProfanityFilterPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.List;

import static kitchenpos.product.ProductFixtures.product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class ProductServiceTest {
    private MenuRepository menuRepository;
    private ProfanityFilterPort profanityFilterPort;
    private LoadProductPort loadProductPort;
    private UpdateProductPort updateProductPort;
    private ProductService productService;


    @BeforeEach
    void setUp() {
        final ProductOutPortCatalog productOutPortCatalog = new ProductOutPortCatalog();
        menuRepository = new InMemoryMenuRepository();
        profanityFilterPort = new FakeProfanityFilterPort();
        loadProductPort = productOutPortCatalog.loadProductPort();
        updateProductPort = productOutPortCatalog.updateProductPort();
        productService = new ProductService(loadProductPort, updateProductPort, menuRepository, profanityFilterPort);
    }

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void create() {
        final Product expected = createProductRequest("후라이드", 16_000L);
        final Product actual = productService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice())
        );
    }

    @DisplayName("상품의 이름에 비속어가 포함되면 등록할 수 없다.")
    @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
    @ParameterizedTest
    void create(final String name) {
        final Product expected = createProductRequest(name, 16_000L);
        assertThatThrownBy(() -> productService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        final ProductId productId =
                updateProductPort.updateProduct(product("후라이드", 16_000L)).getId();
        final BigDecimal expected = BigDecimal.valueOf(15_000L);
        final Product actual = productService.changePrice(productId, expected);
        assertThat(actual.getPrice()).isEqualTo(expected);
    }

    @DisplayName("상품의 가격이 올바르지 않으면 변경할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void changePrice(final BigDecimal price) {
        final ProductId productId =
                updateProductPort.updateProduct(product("후라이드", 16_000L)).getId();
        assertThatThrownBy(() -> productService.changePrice(productId, price))
            .isInstanceOf(IllegalArgumentException.class);
    }

    // TODO: 메뉴 리팩토링 후 주석 해제
//    @DisplayName("상품의 가격이 변경될 때 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴가 숨겨진다.")
//    @Test
//    void changePriceInMenu() {
//        final Product product = updateProductPort.updateProduct(ProductFixtures.product("후라이드", 16_000L));
//        final Menu menu = menuRepository.save(menu(19_000L, true, menuProduct(product, 2L)));
//        productService.changePrice(product.getId(), changePriceRequest(8_000L));
//        assertThat(menuRepository.findById(menu.getId()).get().isDisplayed()).isFalse();
//    }

    @DisplayName("상품의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        updateProductPort.updateProduct(product("후라이드", 16_000L));
        updateProductPort.updateProduct(product("양념치킨", 16_000L));
        final List<Product> actual = productService.findAll();
        assertThat(actual).hasSize(2);
    }

    private Product createProductRequest(final String name, final long price) {
        return product(name, price);
    }

    private Product createProductRequest(final String name, final BigDecimal price) {
        return product(name, price);
    }

    private ProductEntity changePriceRequest(final long price) {
        return changePriceRequest(BigDecimal.valueOf(price));
    }

    private ProductEntity changePriceRequest(final BigDecimal price) {
        final ProductEntity product = new ProductEntity();
        product.setPrice(price);
        return product;
    }
}