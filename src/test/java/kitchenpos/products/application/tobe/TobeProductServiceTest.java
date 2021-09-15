package kitchenpos.products.application.tobe;

import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.tobe.application.TobeProductService;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.TobeProduct;
import kitchenpos.products.tobe.domain.TobeProductRepository;
import kitchenpos.products.tobe.ui.ProductForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static kitchenpos.products.application.tobe.TobeFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class TobeProductServiceTest {
    private TobeProductRepository productRepository;
    private MenuRepository menuRepository;
    private PurgomalumClient purgomalumClient;
    private TobeProductService productService;

    @BeforeEach
    void setUp() {
        productRepository = new TobeInMemoryProductRepository();
        menuRepository = new InMemoryMenuRepository();
        purgomalumClient = new FakePurgomalumClient();
        productService = new TobeProductService(productRepository, menuRepository, purgomalumClient);
    }

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void create() {
        final ProductForm expected = createProductRequest("후라이드", 16_000L);
        final ProductForm actual = productService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice())
        );
    }

    @DisplayName("상품의 가격이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void create(final BigDecimal price) {
        final ProductForm expected = createProductRequest("후라이드", price);
        assertThatThrownBy(() -> productService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 이름이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
    @NullSource
    @ParameterizedTest
    void create(final String name) {
        final ProductForm expected = createProductRequest(name, 16_000L);
        assertThatThrownBy(() -> productService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        final UUID productId = productRepository.save(product("후라이드", 16_000L)).getId();
        final ProductForm expected = changePriceRequest(15_000L);
        final ProductForm actual = productService.changePrice(productId, expected);
        assertThat(actual.getPrice()).isEqualTo(expected.getPrice());
    }

    @DisplayName("상품의 가격이 올바르지 않으면 변경할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void changePrice(final BigDecimal price) {
        final UUID productId = productRepository.save(product("후라이드", 16_000L)).getId();
        final ProductForm expected = changePriceRequest(price);
        assertThatThrownBy(() -> productService.changePrice(productId, expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    // TODO : 메뉴 리팩토링 할 때 변경 예정
//    @DisplayName("상품의 가격이 변경될 때 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴가 숨겨진다.")
//    @Test
//    void changePriceInMenu() {
//        final Product product = productRepository.save(product("후라이드", 16_000L));
//        final Menu menu = menuRepository.save(menu(19_000L, true, menuProduct(product, 2L)));
//        productService.changePrice(product.getId(), changePriceRequest(8_000L));
//        assertThat(menuRepository.findById(menu.getId()).get().isDisplayed()).isFalse();
//    }

    @DisplayName("상품의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        productRepository.save(product());
        productRepository.save(product());
        final List<ProductForm> actual = productService.findAll();
        assertThat(actual).hasSize(2);
    }

    private ProductForm createProductRequest(final String name, final long price) {
        return createProductRequest(name, BigDecimal.valueOf(price));
    }

    private ProductForm createProductRequest(final String name, final BigDecimal price) {
        final ProductForm product = new ProductForm();
        product.setName(name);
        product.setPrice(price);
        return product;
    }

    private ProductForm changePriceRequest(final long price) {
        return changePriceRequest(BigDecimal.valueOf(price));
    }

    private ProductForm changePriceRequest(final BigDecimal price) {
        final ProductForm product = new ProductForm();
        product.setPrice(price);
        return product;
    }
}
