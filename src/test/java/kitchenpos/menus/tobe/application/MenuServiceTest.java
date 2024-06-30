package kitchenpos.menus.tobe.application;

import static kitchenpos.menus.tobe.MenuFixtures.menu;
import static kitchenpos.menus.tobe.MenuFixtures.menuGroup;
import static kitchenpos.menus.tobe.MenuFixtures.menuProduct;
import static kitchenpos.products.tobe.ProductFixtures.product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import java.util.*;

import kitchenpos.menus.application.MenuService;
import kitchenpos.menus.application.dto.MenuChangePriceRequestDto;
import kitchenpos.menus.application.dto.MenuCreateRequestDto;
import kitchenpos.menus.application.dto.MenuCreateResponse;
import kitchenpos.menus.application.dto.MenuProductCreateRequestDto;
import kitchenpos.menus.domain.menu.MenuPurgomalumClient;
import kitchenpos.menus.domain.menu.MenuRepository;
import kitchenpos.menus.domain.menu.ProductProviderInterface;
import kitchenpos.menus.domain.menugroup.MenuGroupRepository;
import kitchenpos.products.application.InMemoryProductRepository;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.tobe.application.FakePurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class MenuServiceTest {
  private MenuRepository menuRepository;
  private MenuGroupRepository menuGroupRepository;
  private ProductRepository productRepository;
  private MenuPurgomalumClient menuPurgomalumClient;
  private MenuService menuService;
  private Product product;
  private UUID menuGroupId;
  private ProductProviderInterface productProviderInterface;

  private static List<Arguments> menuProducts() {
    return Arrays.asList(
        null,
        Arguments.of(Collections.emptyList()),
        Arguments.of(List.of(createMenuProductCreateRequestDto(1L))));
  }

  private static MenuProductCreateRequestDto createMenuProductCreateRequestDto(
      final long quantity) {
    return new MenuProductCreateRequestDto(UUID.randomUUID(), quantity);
  }

  @BeforeEach
  void setUp() {
    menuRepository = new InMemoryMenuRepository();
    menuGroupRepository = new InMemoryMenuGroupRepository();
    productRepository = new InMemoryProductRepository();
    menuPurgomalumClient = new FakeMenuPurgomalumClient();
    productProviderInterface = new FakeProductConsumerImpl();
    menuService =
        new MenuService(
            menuRepository, menuGroupRepository, menuPurgomalumClient, productProviderInterface);
    menuGroupId = menuGroupRepository.save(menuGroup()).getId();
    product = productRepository.save(product("후라이드", 16_000L, new FakePurgomalumClient()));
  }

  @DisplayName("1개 이상의 등록된 상품으로 메뉴를 등록할 수 있다.")
  @Test
  void create() {
    final MenuCreateRequestDto expected =
        new MenuCreateRequestDto(
            "후라이드+후라이드",
            BigDecimal.valueOf(16_000L),
            List.of(createMenuProductCreateRequestDto(1L)),
            menuGroupId,
            true);

    final MenuCreateResponse actual = menuService.create(expected);
    assertThat(actual).isNotNull();
    assertAll(
        () -> assertThat(actual.getMenuId()).isNotNull(),
        () -> assertThat(actual.getMenuName()).isEqualTo(expected.getName()),
        () -> assertThat(actual.getMenuPrice()).isEqualTo(expected.getPrice()),
        () ->
            assertThat(actual.getMenuGroupCreationResponseDto().getId())
                .isEqualTo(expected.getMenuGroupId()),
        () -> assertThat(actual.isDisplayed()).isEqualTo(expected.getDisplayed()),
        () -> assertThat(actual.getMenuProductCreateResponseDtos()).hasSize(1));
  }

  @DisplayName("상품이 없으면 등록할 수 없다.")
  @MethodSource("menuProducts")
  @ParameterizedTest
  void create(final List<MenuProductCreateRequestDto> menuProductCreateRequestDtos) {
    final MenuCreateRequestDto expected =
        new MenuCreateRequestDto(
            "후라이드+후라이드",
            BigDecimal.valueOf(19_000L),
            menuProductCreateRequestDtos,
            menuGroupId,
            true);

    assertThatThrownBy(() -> menuService.create(expected))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("메뉴에 속한 상품의 수량은 0개 이상이어야 한다.")
  @Test
  void createNegativeQuantity() {

    final MenuCreateRequestDto expected =
        new MenuCreateRequestDto(
            "후라이드+후라이드",
            BigDecimal.valueOf(19_000L),
            List.of(createMenuProductCreateRequestDto(-1L)),
            menuGroupId,
            true);

    assertThatThrownBy(() -> menuService.create(expected))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("메뉴의 가격이 올바르지 않으면 등록할 수 없다.")
  @ValueSource(strings = "-1000")
  @NullSource
  @ParameterizedTest
  void create(final BigDecimal price) {
    final MenuCreateRequestDto expected =
        new MenuCreateRequestDto(
            "후라이드+후라이드", price, List.of(createMenuProductCreateRequestDto(2L)), menuGroupId, true);

    assertThatThrownBy(() -> menuService.create(expected))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
  @Test
  void createExpensiveMenu() {
    final MenuCreateRequestDto expected =
        new MenuCreateRequestDto(
            "후라이드+후라이드",
            BigDecimal.valueOf(33_000L),
            List.of(createMenuProductCreateRequestDto(2L)),
            menuGroupId,
            true);

    assertThatThrownBy(() -> menuService.create(expected))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("메뉴는 특정 메뉴 그룹에 속해야 한다.")
  @NullSource
  @ParameterizedTest
  void create(final UUID menuGroupId) {
    final MenuCreateRequestDto expected =
        new MenuCreateRequestDto(
            "후라이드+후라이드",
            BigDecimal.valueOf(33_000L),
            List.of(createMenuProductCreateRequestDto(2L)),
            menuGroupId,
            true);

    assertThatThrownBy(() -> menuService.create(expected))
        .isInstanceOf(NoSuchElementException.class);
  }

  @DisplayName("메뉴의 이름이 올바르지 않으면 등록할 수 없다.")
  @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
  @NullSource
  @ParameterizedTest
  void create(final String name) {
    final MenuCreateRequestDto expected =
        new MenuCreateRequestDto(
            name,
            BigDecimal.valueOf(33_000L),
            List.of(createMenuProductCreateRequestDto(2L)),
            menuGroupId,
            true);

    assertThatThrownBy(() -> menuService.create(expected))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("메뉴의 가격을 변경할 수 있다.")
  @Test
  void changePrice() {
    final UUID menuId = menuRepository.save(menu(19_000L, menuProduct(product, 2L))).getId();
    final MenuChangePriceRequestDto expected =
        new MenuChangePriceRequestDto(menuId, BigDecimal.valueOf(16_000L));

    final MenuCreateResponse actual = menuService.changePrice(expected);

    assertThat(actual.getMenuPrice()).isEqualTo(expected.getMenuPrice());
  }

  @DisplayName("메뉴의 가격이 올바르지 않으면 변경할 수 없다.")
  @ValueSource(strings = "-1000")
  @NullSource
  @ParameterizedTest
  void changePrice(final BigDecimal price) {
    final UUID menuId = menuRepository.save(menu(19_000L, menuProduct(product, 2L))).getId();
    final MenuChangePriceRequestDto expected = new MenuChangePriceRequestDto(menuId, price);
    assertThatThrownBy(() -> menuService.changePrice(expected))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
  @Test
  void changePriceToExpensive() {
    final UUID menuId = menuRepository.save(menu(19_000L, menuProduct(product, 2L))).getId();
    final MenuChangePriceRequestDto expected =
        new MenuChangePriceRequestDto(menuId, BigDecimal.valueOf(33_000L));
    assertThatThrownBy(() -> menuService.changePrice(expected))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("메뉴를 노출할 수 있다.")
  @Test
  void display() {
    final UUID menuId = menuRepository.save(menu(19_000L, false, menuProduct(product, 2L))).getId();
    final MenuCreateResponse actual = menuService.display(menuId);
    assertThat(actual.isDisplayed()).isTrue();
  }

  /*@DisplayName(
      "메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 높을 경우 메뉴를 노출할 수 없다." + "메뉴를 생성할 때 이미 IllegalArgumentException 발생함")
  @Test
  void displayExpensiveMenu() {
    final UUID menuId = menuRepository.save(menu(33_000L, false, menuProduct(product, 3L))).getId();
    assertThatThrownBy(() -> menuService.display(menuId))
        .isInstanceOf(IllegalArgumentException.class);
  }*/

  @DisplayName("메뉴를 숨길 수 있다.")
  @Test
  void hide() {
    final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct(product, 2L))).getId();
    final MenuCreateResponse actual = menuService.hide(menuId);
    assertThat(actual.isDisplayed()).isFalse();
  }

  @DisplayName("메뉴의 목록을 조회할 수 있다.")
  @Test
  void findAll() {
    menuRepository.save(menu(19_000L, true, menuProduct(product, 2L)));
    List<MenuCreateResponse> actual = menuService.findAll();
    assertThat(actual).hasSize(1);
  }
}
