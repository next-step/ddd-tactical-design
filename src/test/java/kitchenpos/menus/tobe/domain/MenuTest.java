package kitchenpos.menus.tobe.domain;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuTest {

    private final MenuProfanityClient menuProfanityClient = new FakeMenuProfanityClient();

    @Test
    @DisplayName("1 개 이상의 등록된 상품으로 메뉴를 등록할 수 있다.")
    void create_menu() {

        UUID productId1 = UUID.randomUUID();
        UUID productId2 = UUID.randomUUID();
        UUID menuGroupId = UUID.randomUUID();

        Menu menu = new Menu(createDisplayName("메뉴 이름"), createMenuGroup(menuGroupId, "메뉴 그룹"), createMenuPrice(10000L),
                createMenuProducts(createMenuProduct(productId1, 1000L, 2),
                createMenuProduct(productId2, 3000L, 3)));

        MenuDisplayedName displayedName = createDisplayName("메뉴 이름");
        MenuGroup menuGroup = createMenuGroup(menuGroupId, "메뉴 그룹");
        MenuPrice menuPrice = createMenuPrice(10000L);
        MenuProducts menuProducts = new MenuProducts(Lists.newArrayList(createMenuProduct(productId1, 1000L, 2),
                createMenuProduct(productId2, 3000L, 3)));

        assertThat(menu.getId()).isNotNull();
        assertThat(menu.getName()).isEqualTo(displayedName);
        assertThat(menu.getPrice()).isEqualTo(menuPrice);
        assertThat(menu.getMenuGroup()).isEqualTo(menuGroup);

        assertThat(menu.getMenuProducts()).isEqualTo(menuProducts);
    }

    @Test
    @DisplayName("상품이 없으면 메뉴를 등록할 수 없다.")
    void can_not_create_menu_without_product() {

        assertThatThrownBy(() -> new Menu(createDisplayName("메뉴 이름"),
                createMenuGroup(UUID.randomUUID(), "메뉴 그룹"),
                createMenuPrice(10000L),
                createMenuProducts())
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품은 1개 이상 등록해야 합니다.");
    }


    @Test
    @DisplayName("메뉴에 속한 상품의 수량은 0 이상이어야 한다.")
    void can_not_create_menu_quantity_less_then_zero() {

        assertThatThrownBy(() -> new Menu(createDisplayName("메뉴 이름"),
                createMenuGroup(UUID.randomUUID(), "메뉴 그룹"),
                createMenuPrice(10000L),
                createMenuProducts(createMenuProduct(UUID.randomUUID(), 1000L, 2),
                        createMenuProduct(UUID.randomUUID(), 3000L, 0)))
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("메뉴에 속한 상품의 수량은 0이상 이어야 합니다.");

    }

    @Test
    @DisplayName("메뉴의 가격은 0원 이상이어야 한다.")
    void can_not_create_menu_price_less_then_zero() {

        assertThatThrownBy(() -> new Menu(createDisplayName("메뉴 이름"),
                createMenuGroup(UUID.randomUUID(), "메뉴 그룹"),
                createMenuPrice(-1L),
                createMenuProducts(createMenuProduct(UUID.randomUUID(), 1000L, 2),
                        createMenuProduct(UUID.randomUUID(), 3000L, 3)))
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    void can_not_create_menu_over_product_price() {

        assertThatThrownBy(() -> new Menu(createDisplayName("메뉴 이름"),
                createMenuGroup(UUID.randomUUID(), "메뉴 그룹"),
                createMenuPrice(20000L),
                createMenuProducts(createMenuProduct(UUID.randomUUID(), 1000L, 2),
                        createMenuProduct(UUID.randomUUID(), 3000L, 3)))
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 합니다.");
    }

    @Test
    @DisplayName("메뉴는 특정 메뉴 그룹에 속해야 한다.")
    void can_not_create_menu_belong_menu_group() {

        assertThatThrownBy(() -> new Menu(createDisplayName("메뉴 이름"),
                null,
                createMenuPrice(20000L),
                createMenuProducts(createMenuProduct(UUID.randomUUID(), 1000L, 2),
                        createMenuProduct(UUID.randomUUID(), 3000L, 3)))
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("메뉴의 이름은 비어있을 수 없다.")
    void can_not_create_menu_empty_name(String menuName) {

        assertThatThrownBy(() -> new Menu(createDisplayName(menuName),
                createMenuGroup(UUID.randomUUID(), "메뉴 그룹"),
                createMenuPrice(20000L),
                createMenuProducts(createMenuProduct(UUID.randomUUID(), 1000L, 2),
                        createMenuProduct(UUID.randomUUID(), 3000L, 3)))
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("메뉴 이름이 비어 있습니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
    @DisplayName("메뉴의 이름에는 비속어가 포함될 수 없다.")
    void can_not_create_menu_use_profanity(String menuName) {

        assertThatThrownBy(() -> new Menu(createDisplayName(menuName),
                createMenuGroup(UUID.randomUUID(), "메뉴 그룹"),
                createMenuPrice(10000L),
                createMenuProducts(createMenuProduct(UUID.randomUUID(), 1000L, 2),
                        createMenuProduct(UUID.randomUUID(), 3000L, 3)))
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("메뉴 이름에는 비속어가 포함될 수 없습니다.");
    }

    @Test
    @DisplayName("메뉴의 가격을 변경할 수 있다.")
    void change_menu_price() {

        UUID productId1 = UUID.randomUUID();
        UUID productId2 = UUID.randomUUID();
        UUID menuGroupId = UUID.randomUUID();

        Menu menu = new Menu(createDisplayName("메뉴 이름"), createMenuGroup(menuGroupId, "메뉴 그룹"), createMenuPrice(10000L),
                createMenuProducts(createMenuProduct(productId1, 1000L, 2),
                        createMenuProduct(productId2, 3000L, 3)));

        menu.changePrice(8000L);

        assertThat(menu.getPrice()).isEqualTo(new MenuPrice(8000L));
    }

    @Test
    @DisplayName("메뉴의 가격은 0원 이상이어야 한다.")
    void can_not_change_menu_price_less_then_0() {

        UUID productId1 = UUID.randomUUID();
        UUID productId2 = UUID.randomUUID();
        UUID menuGroupId = UUID.randomUUID();

        Menu menu = new Menu(createDisplayName("메뉴 이름"), createMenuGroup(menuGroupId, "메뉴 그룹"), createMenuPrice(10000L),
                createMenuProducts(createMenuProduct(productId1, 1000L, 2),
                        createMenuProduct(productId2, 3000L, 3)));

        assertThatThrownBy(() -> menu.changePrice(-1L) )
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    void can_not_change_menu_price_over_product_price() {

        UUID productId1 = UUID.randomUUID();
        UUID productId2 = UUID.randomUUID();
        UUID menuGroupId = UUID.randomUUID();

        Menu menu = new Menu(createDisplayName("메뉴 이름"), createMenuGroup(menuGroupId, "메뉴 그룹"), createMenuPrice(10000L),
                createMenuProducts(createMenuProduct(productId1, 1000L, 2),
                        createMenuProduct(productId2, 3000L, 3)));

        assertThatThrownBy(() -> menu.changePrice(20000L) )
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("메뉴를 노출할 수 있다.")
    void displayed_menu() {

        UUID productId1 = UUID.randomUUID();
        UUID productId2 = UUID.randomUUID();
        UUID menuGroupId = UUID.randomUUID();

        Menu menu = new Menu(createDisplayName("메뉴 이름"), createMenuGroup(menuGroupId, "메뉴 그룹"), createMenuPrice(10000L),
                createMenuProducts(createMenuProduct(productId1, 1000L, 2),
                        createMenuProduct(productId2, 3000L, 3)));

        menu.display();
        assertThat(menu.getDisplayed()).isEqualTo(new MenuDisplayed(Boolean.TRUE));
    }

    @Test
    @DisplayName("메뉴를 숨길 수 있다.")
    void hide_menu() {

        UUID productId1 = UUID.randomUUID();
        UUID productId2 = UUID.randomUUID();
        UUID menuGroupId = UUID.randomUUID();

        Menu menu = new Menu(createDisplayName("메뉴 이름"), createMenuGroup(menuGroupId, "메뉴 그룹"), createMenuPrice(10000L),
                createMenuProducts(createMenuProduct(productId1, 1000L, 2),
                        createMenuProduct(productId2, 3000L, 3)));

        menu.hide();
        assertThat(menu.getDisplayed()).isEqualTo(new MenuDisplayed(Boolean.FALSE));
    }

    public MenuProduct createMenuProduct(UUID productId , long price, long quantity) {
        return new MenuProduct(productId, new MenuProductPrice(price), new MenuQuantity(quantity));
    }

    public MenuDisplayedName createDisplayName(String name) {
        return new MenuDisplayedName(name, menuProfanityClient);
    }

    public MenuPrice createMenuPrice(long price) {
        return new MenuPrice(price);
    }

    public MenuGroup createMenuGroup(UUID menuGroupId, String name) {
        return new MenuGroup(menuGroupId, name, menuProfanityClient);
    }

    private MenuProducts createMenuProducts(MenuProduct... menuProducts) {
        return new MenuProducts(Lists.newArrayList(menuProducts));
    }
}