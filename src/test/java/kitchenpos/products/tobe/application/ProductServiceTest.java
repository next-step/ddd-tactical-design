package kitchenpos.products.tobe.application;

import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.products.tobe.domain.DisplayNamePolicy;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.products.tobe.dto.ProductDto;
import kitchenpos.products.tobe.infra.Profanities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.UUID;


import kitchenpos.menus.domain.MenuRepository;

import static kitchenpos.products.tobe.application.MenuFixture.createMenuRequest;
import static kitchenpos.products.tobe.application.MenuProductFixture.createMenuProductRequest;
import static kitchenpos.products.tobe.application.ProductFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {
    private ProductRepository productRepository;
    private MenuRepository menuRepository;
    private Profanities profanities;
    private ProductService productService;
    private DisplayNamePolicy displayNamePolicy;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        menuRepository = new InMemoryMenuRepository();
        profanities = new FakePurgomalumClient();
        displayNamePolicy = new DisplayNamePolicy(profanities);
        productService = new ProductService(productRepository, menuRepository, displayNamePolicy);
    }

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void create() {
        //given
        final ProductDto expected = createProductDtoRequest("후라이드", 16_000L);

        //when
        final ProductDto actual = productService.create(expected);

        //then
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
                () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice())
        );
    }

    @DisplayName("상품의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        //given
        final Product originProduct = createProductRequest("후라이드", 16_000L, displayNamePolicy);
        final UUID originProductId = productRepository.save(originProduct).getId();
        final ProductDto expected = changePriceDtoRequest(15_000L, displayNamePolicy);

        // when
        final ProductDto actual = productService.changePrice(originProductId, expected);

        // then
        assertThat(actual.getPrice()).isEqualTo(expected.getPrice());
    }

    @DisplayName("상품의 가격이 올바르지 않으면 변경할 수 없다.")
    @ValueSource(strings = "-1000")
    @ParameterizedTest
    void changePrice(final long price) {
        // given
        final Product originProduct = createProductRequest("후라이드", 16_000L, displayNamePolicy);
        final UUID originProductId = productRepository.save(originProduct).getId();

        // when, then
        assertThatThrownBy(() -> productService.changePrice(originProductId, changePriceDtoRequest(price, displayNamePolicy)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 가격이 변경될 때 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴가 숨겨진다.")
    @Test
    void changePriceInMenu() {
        // given
        final Product originProduct = createProductRequest("후라이드", 16_000L, displayNamePolicy);
        final Product savedProduct = productRepository.save(originProduct);

        final MenuProduct menuProductRequest = createMenuProductRequest(savedProduct, 2L);
        final Menu requestMenu = createMenuRequest(16_000L, true, menuProductRequest);

        final Menu menu = menuRepository.save(requestMenu);

        productService.changePrice(savedProduct.getId(), changePriceDtoRequest(8_000L, displayNamePolicy));

        assertThat(menuRepository.findById(menu.getId()).get().isDisplayed()).isTrue();
    }

    @DisplayName("상품의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        productRepository.save(createProductRequest("후라이드", 16_000L, displayNamePolicy));
        productRepository.save(createProductRequest("양념치킨", 16_000L, displayNamePolicy));
        final List<ProductDto> actual = productService.findAll();
        assertThat(actual).hasSize(2);
    }

}
