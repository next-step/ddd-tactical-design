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
import kitchenpos.menu.tobe.application.dto.ChangeMenuPriceCommand;
import kitchenpos.menu.tobe.application.dto.CreateMenuCommand;
import kitchenpos.menu.tobe.domain.entity.Menu;
import kitchenpos.menu.tobe.domain.repository.MenuGroupRepository;
import kitchenpos.menu.tobe.domain.repository.MenuRepository;
import kitchenpos.menu.tobe.domain.vo.MenuProduct;
import kitchenpos.menu.tobe.domain.vo.MenuProductQuantity;
import kitchenpos.product.InMemoryProductRepository;
import kitchenpos.product.tobe.domain.entity.Product;
import kitchenpos.product.tobe.domain.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

class MenuServiceTest {

    private MenuRepository menuRepository;

    private MenuService menuService;

    private UUID menuGroupId;

    private Product product;

    @BeforeEach
    void setUp() {
        menuRepository = new InMemoryMenuRepository();
        final MenuGroupRepository menuGroupRepository = new InMemoryMenuGroupRepository();
        final ProductRepository productRepository = new InMemoryProductRepository();
        final ProfanityDetectService profanityDetectService = new FakeProfanityDetectService();
        final NameFactory nameFactory = new NameFactory(profanityDetectService);
        menuService = new MenuService(
            menuRepository,
            menuGroupRepository,
            productRepository,
            nameFactory
        );
        menuGroupId = menuGroupRepository.save(menuGroup()).id;
        product = productRepository.save(product("후라이드", 16_000L));
    }

    @DisplayName("1개 이상의 등록된 상품으로 메뉴를 등록할 수 있다.")
    @Test
    void create() {
        final CreateMenuCommand command = createMenuCommand(
            "후라이드+후라이드",
            19_000L,
            menuGroupId,
            true,
            createMenuProductRequest(product.id, 2L)
        );
        final Menu actual = menuService.create(command);
        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.id).isNotNull(),
            () -> assertThat(actual.name.value).isEqualTo(command.name),
            () -> assertThat(actual.price().value).isEqualTo(command.price),
            () -> assertThat(actual.menuGroup.id).isEqualTo(command.menuGroupId),
            () -> assertThat(actual.displayed()).isEqualTo(command.displayed),
            () -> assertThat(actual.menuProducts()).hasSize(1)
        );
    }

    @DisplayName("상품이 없으면 등록할 수 없다.")
    @MethodSource("menuProducts")
    @ParameterizedTest
    void create(final List<MenuProduct> menuProducts) {
        final CreateMenuCommand command = createMenuCommand(
            "후라이드+후라이드",
            19_000L,
            menuGroupId,
            true,
            menuProducts
        );
        assertThatThrownBy(() -> menuService.create(command))
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
        final CreateMenuCommand command = createMenuCommand(
            "후라이드+후라이드",
            33_000L,
            menuGroupId,
            true,
            createMenuProductRequest(product.id, 2L)
        );
        assertThatThrownBy(() -> menuService.create(command))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴는 특정 메뉴 그룹에 속해야 한다.")
    @NullSource
    @ParameterizedTest
    void create(final UUID menuGroupId) {
        final CreateMenuCommand command = createMenuCommand(
            "후라이드+후라이드",
            19_000L,
            menuGroupId,
            true,
            createMenuProductRequest(product.id, 2L)
        );
        assertThatThrownBy(() -> menuService.create(command))
            .isInstanceOf(NoSuchElementException.class);
    }

    @DisplayName("메뉴의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        final UUID menuId = menuRepository.save(
            menu(19_000L, menuProduct(product, 2L))
        ).id;
        final ChangeMenuPriceCommand command = changeMenuPriceCommand(menuId, 16_000L);
        final Menu actual = menuService.changePrice(command);
        assertThat(actual.price().value).isEqualTo(command.price);
    }

    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    @Test
    void changePriceToExpensive() {
        final UUID menuId = menuRepository.save(
            menu(19_000L, menuProduct(product, 2L))
        ).id;
        final ChangeMenuPriceCommand command = changeMenuPriceCommand(menuId, 33_000L);
        assertThatThrownBy(() -> menuService.changePrice(command))
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

    @Disabled
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

    private CreateMenuCommand createMenuCommand(
        final String name,
        final long price,
        final UUID menuGroupId,
        final boolean displayed,
        final MenuProduct... menuProducts
    ) {
        return createMenuCommand(
            name,
            BigDecimal.valueOf(price),
            menuGroupId,
            displayed,
            menuProducts
        );
    }

    private CreateMenuCommand createMenuCommand(
        final String name,
        final BigDecimal price,
        final UUID menuGroupId,
        final boolean displayed,
        final MenuProduct... menuProducts
    ) {
        return createMenuCommand(name, price, menuGroupId, displayed, Arrays.asList(menuProducts));
    }

    private CreateMenuCommand createMenuCommand(
        final String name,
        final long price,
        final UUID menuGroupId,
        final boolean displayed,
        final List<MenuProduct> menuProducts
    ) {
        return createMenuCommand(
            name,
            BigDecimal.valueOf(price),
            menuGroupId,
            displayed,
            menuProducts
        );
    }

    private CreateMenuCommand createMenuCommand(
        final String name,
        final BigDecimal price,
        final UUID menuGroupId,
        final boolean displayed,
        final List<MenuProduct> menuProducts
    ) {
        return new CreateMenuCommand(
            name,
            menuGroupId,
            menuProducts,
            displayed,
            price
        );
    }

    private static MenuProduct createMenuProductRequest(final UUID productId, final long quantity) {
        return new MenuProduct(
            productId,
            new Price(10000),
            new MenuProductQuantity(quantity)
        );
    }

    private ChangeMenuPriceCommand changeMenuPriceCommand(
        final UUID menuId,
        final BigDecimal price
    ) {
        return new ChangeMenuPriceCommand(
            menuId,
            price
        );
    }

    private ChangeMenuPriceCommand changeMenuPriceCommand(
        final UUID menuId,
        final long price
    ) {
        return this.changeMenuPriceCommand(menuId, BigDecimal.valueOf(price));
    }
}
