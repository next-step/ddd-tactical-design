package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.menus.dto.MenuProductCreateRequest;
import kitchenpos.menus.exception.InvalidMenuProductsException;
import kitchenpos.menus.exception.MenuProductsNoSuchElementException;
import kitchenpos.menus.tobe.infra.ProductClientImpl;
import kitchenpos.products.application.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.support.domain.ProductPrice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static kitchenpos.fixture.Fixtures.INVALID_ID;
import static kitchenpos.fixture.ProductFixture.product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("메뉴구성상품 목록")
class MenuProductsTest {
    private static MenuProductCreateRequest menuProductA;
    private static UUID productA_ID;

    List<MenuProductCreateRequest> menuProductsRequest;
    private ProductClient productClient;

    @BeforeEach
    void setUp() {
        ProductRepository productRepository = new InMemoryProductRepository();
        productClient = new ProductClientImpl(productRepository);

        productA_ID = productRepository.save(product("후라이드치킨", 10_000)).getId();
        UUID productB_ID = productRepository.save(product("양념치킨", 12_000)).getId();
        menuProductA = new MenuProductCreateRequest(productA_ID, 1L);
        MenuProductCreateRequest menuProductB = new MenuProductCreateRequest(productB_ID, 2L);
        menuProductsRequest = List.of(menuProductA, menuProductB);
    }

    @DisplayName("[성공] 이미 생성된 1개이상의 메뉴구성상품으로 구성된 메뉴구성상품 목록을 생성한다.")
    @Test
    void create() {
        MenuProducts actual = MenuProducts.from(menuProductsRequest, productClient);

        assertAll(
                () -> assertThat(actual).isEqualTo(MenuProducts.from(menuProductsRequest, productClient)),
                () -> assertThat(actual.getValues()).hasSize(2)
        );
    }

    @DisplayName("[실패] 메뉴구성상품 목록을 생성할 때, 메뉴구성상품이 1개 이상 구성되어야 한다.")
    @MethodSource("nullOrEmptyMenuProducts")
    @ParameterizedTest
    void fail_create(final List<MenuProductCreateRequest> menuProducts) {
        assertThatThrownBy(() -> MenuProducts.from(menuProducts, productClient))
                .isInstanceOf(InvalidMenuProductsException.class);
    }


    @DisplayName("[실패] 메뉴구성상품 목록을 생성할 때, 생성되지 않은 상품이거나 같은상품을 중복해서 구성하지 않는다.")
    @MethodSource("invalidMenuProducts")
    @ParameterizedTest
    void fail2_create(final List<MenuProductCreateRequest> menuProducts) {
        assertThatThrownBy(() -> MenuProducts.from(menuProducts, productClient))
                .isInstanceOf(InvalidMenuProductsException.class);
    }

    @DisplayName("[성공] 메뉴구성상품 목록의 가격의 총합을 구할 수 있다.")
    @Test
    void sumMenuProductsPrice() {
        MenuProducts menuProducts = MenuProducts.from(menuProductsRequest, productClient);

        BigDecimal actual = menuProducts.sumPrice();

        assertThat(actual).isEqualTo(BigDecimal.valueOf(34_000));
    }

    @DisplayName("[실패] 메뉴구성상품 목록을 구성하는 해당 메뉴구성상품을 찾을 수 있다.")
    @Test
    void fail_getMenuProductByProductId() {
        MenuProducts menuProducts = MenuProducts.from(menuProductsRequest, productClient);

        assertThatThrownBy(() -> menuProducts.getMenuProductByProductId(INVALID_ID))
                .isInstanceOf(MenuProductsNoSuchElementException.class);
    }

    @DisplayName("[성공] 메뉴구성상품 목록에 해당 상품의 가격을 변경한다.")
    @Test
    void changeProductsPrice() {
        MenuProducts menuProducts = MenuProducts.from(menuProductsRequest, productClient);

        menuProducts.changeProductsPrice(productA_ID, ProductPrice.from(11_000));
        MenuProduct actual = menuProducts.getMenuProductByProductId(productA_ID);

        assertThat(actual.getPrice()).isEqualTo(ProductPrice.from(11_000));
    }


    private static List<Arguments> nullOrEmptyMenuProducts() {
        return Arrays.asList(
                null,
                Arguments.of(Collections.emptyList()),
                Arguments.of(List.of(new MenuProductCreateRequest(INVALID_ID, 2L)))
        );
    }

    private static List<Arguments> invalidMenuProducts() {
        return List.of(
                Arguments.of(List.of(new MenuProductCreateRequest(INVALID_ID, 2L))),
                Arguments.of(List.of(menuProductA, menuProductA, menuProductA))
        );
    }
}
