package kitchenpos.menus.tobe.domain;

import static kitchenpos.TobeFixtures.createMenu;
import static kitchenpos.TobeFixtures.createMenuGroup;
import static kitchenpos.TobeFixtures.createMenuProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.UUID;
import kitchenpos.products.tobe.domain.DisplayNameValidator;
import kitchenpos.products.tobe.domain.FakeDisplayedNameValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("메뉴 테스트")
class MenuTest {

  public DisplayNameValidator displayNameValidator;

  @BeforeEach
  void setUp() {
    displayNameValidator = new FakeDisplayedNameValidator();
  }

  @DisplayName("1개 이상의 등록된 상품으로 메뉴를 등록할 수 있다.")
  @Test
  void create() {
    MenuGroup menuGroup = createMenuGroup("점심특선");
    Menu menu = createMenu(
        "후라이드+후라이드",
        19_000L,
        true,
        menuGroup,
        List.of(createMenuProduct(UUID.randomUUID(), 12_000L, 2)),
        displayNameValidator
    );

    assertThat(menu).isNotNull();
    assertAll(
        () -> assertThat(menu.getId()).isNotNull(),
        () -> assertThat(menu.getName()).isEqualTo(DisplayedName.from("후라이드+후라이드")),
        () -> assertThat(menu.getPrice()).isEqualTo(Price.from(19_000L)),
        () -> assertThat(menu.getMenuGroup().getId()).isEqualTo(menuGroup.getId()),
        () -> assertThat(menu.getDisplayState()).isEqualTo(DisplayState.show()),
        () -> assertThat(menu.getMenuProducts().getMenuProducts()).hasSize(1)
    );
  }

  @DisplayName("메뉴에 속한 상품의 수량은 0개 이상이어야 한다.")
  @Test
  void createMenu_NotValidQuantity() {
    assertThatIllegalArgumentException()
        .isThrownBy(() -> createMenu(
            "후라이드+후라이드",
            19_000L,
            true,
            createMenuGroup("점심특선"),
            List.of(createMenuProduct(UUID.randomUUID(), 12_000L, -1)),
            displayNameValidator
        ));
  }

  @DisplayName("메뉴의 가격이 올바르지 않으면 등록할 수 없다.")
  @ValueSource(strings = "-1")
  @ParameterizedTest
  void createMenu_NotValidPrice(final Long price) {
    assertThatIllegalArgumentException()
        .isThrownBy(() -> createMenu(
            "후라이드+후라이드",
            price,
            true,
            createMenuGroup("점심특선"),
            List.of(createMenuProduct(UUID.randomUUID(), 12_000L, 2)),
            displayNameValidator
        ));
  }

  @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
  @Test
  void createMenu_MenuPriceLessThanMenuProductTotalAmount() {
    assertThatIllegalArgumentException()
        .isThrownBy(() -> createMenu(
            "후라이드+후라이드",
            24_001L,
            true,
            createMenuGroup("점심특선"),
            List.of(createMenuProduct(UUID.randomUUID(), 12_000L, 2)),
            displayNameValidator
        ));
  }

  @DisplayName("메뉴의 가격을 변경할 수 있다.")
  @Test
  void changePrice() {
    Menu createdMenu = createMenu(
        "후라이드+후라이드",
        24_000L,
        true,
        createMenuGroup("점심특선"),
        List.of(createMenuProduct(UUID.randomUUID(), 12_000L, 2)),
        displayNameValidator
    );

    createdMenu.changePrice(Price.from(23_000L));
    assertThat(createdMenu.getPrice()).isEqualTo(Price.from(23_000L));
  }

  @DisplayName("메뉴의 가격이 올바르지 않으면 변경할 수 없다.")
  @ValueSource(longs = -1)
  @ParameterizedTest
  void changePrice_NotValidPrice(final long price) {
    Menu createdMenu = createMenu(
        "후라이드+후라이드",
        24_000L,
        true,      createMenuGroup("점심특선"),
        List.of(createMenuProduct(UUID.randomUUID(), 12_000L, 2)),
        displayNameValidator
    );
    assertThatIllegalArgumentException()
        .isThrownBy(() -> createdMenu.changePrice(Price.from(price)));
  }

  @DisplayName("메뉴 가격 변경시 메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
  @Test
  void changePrice_MenuPriceLessThanMenuProductTotalAmount() {
    Menu createdMenu = createMenu(
        "후라이드+후라이드",
        24_000L,
        true,
        createMenuGroup("점심특선"),
        List.of(createMenuProduct(UUID.randomUUID(), 12_000L, 2)),
        displayNameValidator
    );

    assertThatIllegalArgumentException()
        .isThrownBy(() -> createdMenu.changePrice(Price.from(24_001L)));
  }

  @DisplayName("메뉴를 숨길 수 있다.")
  @Test
  void hide() {
    Menu createdMenu = createMenu(
        "후라이드+후라이드",
        24_000L,
        true,
        createMenuGroup("점심특선"),
        List.of(createMenuProduct(UUID.randomUUID(), 12_000L, 2)),
        displayNameValidator
    );
    createdMenu.hide();
    assertThat(createdMenu.getDisplayState()).isEqualTo(DisplayState.hide());
  }

  @DisplayName("메뉴를 공개할 수 있다.")
  @Test
  void show() {
    Menu createdMenu = createMenu(
        "후라이드+후라이드",
        24_000L,
        false,
        createMenuGroup("점심특선"),
        List.of(createMenuProduct(UUID.randomUUID(), 12_000L, 2)),
        displayNameValidator
    );
    createdMenu.show();
    assertThat(createdMenu.getDisplayState()).isEqualTo(DisplayState.show());
  }

  @DisplayName("메뉴는 특정 메뉴 그룹에 속해야 한다.")
  @Test
  void createMenu_haveToMenuGroup() {
    assertThatIllegalArgumentException()
        .isThrownBy(() -> createMenu(
            "후라이드+후라이드",
            24_000L,
            true,
            null,
            List.of(createMenuProduct(UUID.randomUUID(), 12_000L, 2)),
            displayNameValidator
        ));
  }

  @DisplayName("메뉴의 이름이 올바르지 않으면 등록할 수 없다.")
  @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
  @NullSource
  @ParameterizedTest
  void create(final String name) {
    assertThatIllegalArgumentException()
        .isThrownBy(() -> createMenu(
            name,
            24_000L,
            true,
            createMenuGroup("점심특선"),
            List.of(createMenuProduct(UUID.randomUUID(), 12_000L, 2)),
            displayNameValidator
        ));
  }

}