package kitchenpos.menu.application;

import static kitchenpos.Fixtures.INVALID_ID;
import static kitchenpos.menu.Fixtures.menu;
import static kitchenpos.menu.Fixtures.menuGroup;
import static kitchenpos.menu.Fixtures.menuProduct;
import static kitchenpos.product.Fixtures.product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.common.name.NameFactory;
import kitchenpos.common.price.Price;
import kitchenpos.common.profanity.FakeProfanityDetectService;
import kitchenpos.common.profanity.domain.ProfanityDetectService;
import kitchenpos.menu.InMemoryMenuGroupRepository;
import kitchenpos.menu.InMemoryMenuRepository;
import kitchenpos.menu.tobe.domain.entity.Menu;
import kitchenpos.menu.tobe.domain.entity.MenuGroup;
import kitchenpos.menu.tobe.domain.repository.MenuGroupRepository;
import kitchenpos.menu.tobe.domain.repository.MenuRepository;
import kitchenpos.menu.tobe.domain.vo.MenuProduct;
import kitchenpos.menu.tobe.domain.vo.MenuProductQuantity;
import kitchenpos.product.InMemoryProductRepository;
import kitchenpos.product.tobe.domain.entity.Product;
import kitchenpos.product.tobe.domain.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

class MenuServiceTest {

    private MenuRepository menuRepository;

    private MenuService menuService;

    private NameFactory nameFactory;

    private UUID menuGroupId;

    private Product product;

    @BeforeEach
    void setUp() {
        menuRepository = new InMemoryMenuRepository();
        final MenuGroupRepository menuGroupRepository = new InMemoryMenuGroupRepository();
        final ProductRepository productRepository = new InMemoryProductRepository();
        menuService = new MenuService(
            menuRepository,
            menuGroupRepository,
            productRepository
        );
        final ProfanityDetectService profanityDetectService = new FakeProfanityDetectService();
        nameFactory = new NameFactory(profanityDetectService);
        menuGroupId = menuGroupRepository.save(menuGroup()).id;
        product = productRepository.save(product("후라이드", 16_000L));
    }

    @DisplayName("1개 이상의 등록된 상품으로 메뉴를 등록할 수 있다.")
    @Test
    void create() {
        final Menu expected = createMenuRequest(
            "후라이드+후라이드",
            19_000L,
            menuGroupId,
            true,
            createMenuProductRequest(product.id, 2L)
        );
        final Menu actual = menuService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.id).isNotNull(),
            () -> assertThat(actual.name).isEqualTo(expected.name),
            () -> assertThat(actual.price()).isEqualTo(expected.price()),
            () -> assertThat(actual.menuGroup.id).isEqualTo(expected.menuGroup.id),
            () -> assertThat(actual.displayed()).isEqualTo(expected.displayed()),
            () -> assertThat(actual.menuProducts()).hasSize(1)
        );
    }

    @DisplayName("상품이 없으면 등록할 수 없다.")
    @MethodSource("menuProducts")
    @ParameterizedTest
    void create(final List<MenuProduct> menuProducts) {
        final Menu expected = createMenuRequest(
            "후라이드+후라이드",
            19_000L,
            menuGroupId,
            true,
            menuProducts
        );
        assertThatThrownBy(() -> menuService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    private static List<Arguments> menuProducts() {
        return Arrays.asList(
            null,
            Arguments.of(Collections.emptyList()),
            Arguments.of(List.of(createMenuProductRequest(INVALID_ID, 2L)))
        );
    }

    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    @Test
    void createExpensiveMenu() {
        final Menu expected = createMenuRequest(
            "후라이드+후라이드",
            33_000L,
            menuGroupId,
            true,
            createMenuProductRequest(product.id, 2L)
        );
        assertThatThrownBy(() -> menuService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴는 특정 메뉴 그룹에 속해야 한다.")
    @NullSource
    @ParameterizedTest
    void create(final UUID menuGroupId) {
        final Menu expected = createMenuRequest(
            "후라이드+후라이드",
            19_000L,
            menuGroupId,
            true,
            createMenuProductRequest(product.id, 2L)
        );
        assertThatThrownBy(() -> menuService.create(expected))
            .isInstanceOf(NoSuchElementException.class);
    }

    @DisplayName("메뉴의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        final UUID menuId = menuRepository.save(
            menu(19_000L, menuProduct(product, 2L))
        ).id;
        final Menu expected = changePriceRequest(16_000L);
        final Menu actual = menuService.changePrice(menuId, expected);
        assertThat(actual.price()).isEqualTo(expected.price());
    }

    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    @Test
    void changePriceToExpensive() {
        final UUID menuId = menuRepository.save(
            menu(19_000L, menuProduct(product, 2L))
        ).id;
        final Menu expected = changePriceRequest(33_000L);
        assertThatThrownBy(() -> menuService.changePrice(menuId, expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴를 노출할 수 있다.")
    @Test
    void display() {
        final UUID menuId = menuRepository.save(
            menu(19_000L, false, menuProduct(product, 2L))
        ).id;
        final Menu actual = menuService.display(menuId);
        assertThat(actual.displayed()).isTrue();
    }

    @DisplayName("메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 높을 경우 메뉴를 노출할 수 없다.")
    @Test
    void displayExpensiveMenu() {
        final UUID menuId = menuRepository.save(
            menu(33_000L, false, menuProduct(product, 2L))
        ).id;
        assertThatThrownBy(() -> menuService.display(menuId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("메뉴를 숨길 수 있다.")
    @Test
    void hide() {
        final UUID menuId = menuRepository.save(
            menu(19_000L, true, menuProduct(product, 2L))
        ).id;
        final Menu actual = menuService.hide(menuId);
        assertThat(actual.displayed()).isFalse();
    }

    @DisplayName("메뉴의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        menuRepository.save(menu(19_000L, true, menuProduct(product, 2L)));
        final List<Menu> actual = menuService.findAll();
        assertThat(actual).hasSize(1);
    }

    private Menu createMenuRequest(
        final String name,
        final long price,
        final UUID menuGroupId,
        final boolean displayed,
        final MenuProduct... menuProducts
    ) {
        return createMenuRequest(
            name,
            BigDecimal.valueOf(price),
            menuGroupId,
            displayed,
            menuProducts
        );
    }

    private Menu createMenuRequest(
        final String name,
        final BigDecimal price,
        final UUID menuGroupId,
        final boolean displayed,
        final MenuProduct... menuProducts
    ) {
        return createMenuRequest(name, price, menuGroupId, displayed, Arrays.asList(menuProducts));
    }

    private Menu createMenuRequest(
        final String name,
        final long price,
        final UUID menuGroupId,
        final boolean displayed,
        final List<MenuProduct> menuProducts
    ) {
        return createMenuRequest(
            name,
            BigDecimal.valueOf(price),
            menuGroupId,
            displayed,
            menuProducts
        );
    }

    private Menu createMenuRequest(
        final String name,
        final BigDecimal price,
        final UUID menuGroupId,
        final boolean displayed,
        final List<MenuProduct> menuProducts
    ) {
        return new Menu(
            null,
            this.nameFactory.create(name),
            displayed,
            new Price(price),
            new MenuGroup(
                menuGroupId,
                null
            ),
            menuProducts
        );
    }

    private static MenuProduct createMenuProductRequest(final UUID productId, final long quantity) {
        return new MenuProduct(
            productId,
            new Price(10000),
            new MenuProductQuantity(quantity)
        );
    }

    private Menu changePriceRequest(final long price) {
        return changePriceRequest(BigDecimal.valueOf(price));
    }

    private Menu changePriceRequest(final BigDecimal price) {
        return new Menu(null, null, null, new Price(price), null, null);
    }
}
