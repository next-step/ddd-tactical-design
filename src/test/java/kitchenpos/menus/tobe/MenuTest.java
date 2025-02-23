package kitchenpos.menus.tobe;

import kitchenpos.menus.tobe.exception.*;
import kitchenpos.products.tobe.domain.FakeProfanities;
import kitchenpos.products.tobe.domain.vo.EmptyProfanities;
import kitchenpos.products.tobe.domain.vo.Profanities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("메뉴 단위 테스트")
public class MenuTest {

    private String name;
    private long price;
    private Profanities profanities;
    private MenuGroup menuGroup;
    private List<MenuProduct> menuProducts;

    @BeforeEach
    void setUp() {
        name = "메뉴";
        price = 1_000L;
        profanities = new EmptyProfanities();
        menuGroup = new MenuGroup(UUID.randomUUID(), "메뉴 그룹");
        menuProducts = new ArrayList<>(
                List.of(
                        new MenuProduct(1L, 1000L, 1L, null, 1L),
                        new MenuProduct(2L, 2000L, 2L, null, 2L)
                )
        );
    }

    @DisplayName("메뉴의 가격은 0원 이상이어야 한다.")
    @ValueSource(longs = {-1, -1000, -100000})
    @ParameterizedTest(name = "{index}. 메뉴 가격 : `{0}`")
    void createWithNegativePrice(final long price) {
        assertThatThrownBy(
                () -> new Menu("메뉴", profanities, price, menuGroup.getId(), menuProducts, true)
        ).isExactlyInstanceOf(InvalidMenuPricePeriodException.class);
    }

    @DisplayName("메뉴의 이름이 없거나 비어있으면 생성할 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest(name = "{index}. 메뉴 이름 : `{0}`")
    void createWithInvalidName(final String name) {
        assertThatThrownBy(
                () -> new Menu(name, profanities, 1000L, menuGroup.getId(), menuProducts, true)
        ).isExactlyInstanceOf(InvalidMenuNameException.class);
    }

    @DisplayName("메뉴의 이름에는 비속어가 포함될 수 없다.")
    @ValueSource(strings = {"비속어", "욕설"})
    @ParameterizedTest(name = "{index}. 상품 이름: {0}")
    void createWithProfanityName(final String name) {
        final FakeProfanities fakeProfanities = new FakeProfanities(List.of("비속어", "욕설"));
        assertThatThrownBy(() ->
                new Menu(name, fakeProfanities, price, menuGroup.getId(), menuProducts, true)
        ).isInstanceOf(MenuNameContainsProfanityException.class);
    }

    @DisplayName("메뉴는 메뉴 그룹이 있어야 한다.")
    @NullSource
    @ParameterizedTest(name = "{index}. 메뉴 그룹 : `{0}`")
    void createWithNullMenuGroup(final UUID menuGroupId) {
        assertThatThrownBy(
                () -> new Menu(name, profanities, price, menuGroupId, menuProducts, true)
        ).isExactlyInstanceOf(InvalidMenuArgumentNullPointException.class);
    }

    @DisplayName("메뉴는 메뉴 상품이 없거나 비어있으면 생성할 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest(name = "{index}. 메뉴 상품 : `{0}`")
    void createWithInvalidMenuProducts(final List<MenuProduct> menuProducts) {
        assertThatThrownBy(
                () -> new Menu(name, profanities, price, menuGroup.getId(), menuProducts, true)
        ).isExactlyInstanceOf(InvalidMenuArgumentException.class);
    }

    @DisplayName("메뉴의 가격은 모든 메뉴 상품 금액의 총합보다 같거나 작아야 한다.")
    @CsvSource(value = {"1_000:1:1_000:1:2_001", "2_000:1:2_000:2:6_001", "1_000:3:3_000:2:9_001"}, delimiter = ':')
    @ParameterizedTest(name = """
                {index}. 첫 번째 메뉴 상품 가격 : `{0}`, 첫 번째 메뉴 상품 수량 : `{1}`, 두 번째 메뉴 상품 가격 : `{2}`, 두 번째 메뉴 상품 수량 : `{3}`, 메뉴 가격 : `{4}`
            """)
    void createWithInvalidPrice(final long firstPrice, final long firstQuantity,
                                final long secondPrice, final long secondQuantity,
                                final long price) {
        final List<MenuProduct> menuProducts = List.of(
                new MenuProduct(1L, firstPrice, firstQuantity, null, 1L),
                new MenuProduct(2L, secondPrice, secondQuantity, null, 2L)
        );
        assertThatThrownBy(
                () -> new Menu(name, profanities, price, menuGroup.getId(), menuProducts, true)
        ).isExactlyInstanceOf(InvalidMenuPriceException.class);
    }

    @DisplayName("메뉴를 생성할 수 있다.")
    @Test
    void create() {
        final Menu menu = new Menu(name, profanities, price, menuGroup.getId(), menuProducts, false);
        assertAll(
                () -> assertThat(menu).isNotNull(),
                () -> assertThat(menu.getIdValue()).isNotNull(),
                () -> assertThat(menu.menuProducts()).hasSize(2),
                () -> assertThat(menu.menuProducts()).anyMatch(menuProduct -> menuProduct.menuIdValue() != null)
        );
    }

    @DisplayName("메뉴는 모든 메뉴 상품 금액의 총합보다 높은 가격으로 변경할 수 없다.")
    @CsvSource(value = {"1_000:1:1_000:1:2_000:2_001", "2_000:1:2_000:2:6_000:6_001", "1_000:3:3_000:2:9_000:9_001"}, delimiter = ':')
    @ParameterizedTest(name = """
                {index}. 첫 번째 메뉴 상품 가격 : `{0}`, 첫 번째 메뉴 상품 수량 : `{1}`, 두 번째 메뉴 상품 가격 : `{2}`, 두 번째 메뉴 상품 수량 : `{3}`, 메뉴 가격 : `{4}`, 변경할 메뉴 가격 : `{5}`
            """)
    void changePriceWithInvalidPrice(final long firstPrice, final long firstQuantity,
                                     final long secondPrice, final long secondQuantity,
                                     final long price, final long changedMenuPrice) {
        final List<MenuProduct> menuProducts = List.of(
                new MenuProduct(1L, firstPrice, firstQuantity, null, 1L),
                new MenuProduct(2L, secondPrice, secondQuantity, null, 2L)
        );
        final Menu menu = new Menu(name, profanities, price, menuGroup.getId(), menuProducts, true);

        assertThatThrownBy(
                () -> menu.changePrice(changedMenuPrice)
        ).isExactlyInstanceOf(InvalidMenuPriceException.class);
    }

    @DisplayName("메뉴의 가격이 모든 메뉴 상품 금액의 총합보다 높은 경우 메뉴를 노출할 수 없다.")
    @CsvSource(value = {"1_000:1:999:1_000:1:2_000", "2_000:1:1_999:2_000:2:6_000", "1_000:3:999:3_000:2:9_000"}, delimiter = ':')
    @ParameterizedTest(name = """
                {index}. 첫 번째 메뉴 상품 가격 : `{0}`, 첫 번째 메뉴 상품 수량 : `{1}`, 변경할 첫 번째 메뉴 상품 가격 : `{2}`, 
                두 번째 메뉴 상품 가격 : `{3}`, 두 번째 메뉴 상품 수량 : `{4}`, 메뉴 가격 : `{5}`
            """)
    void displayWithInvalidPrice(final long firstPrice, final long firstQuantity, final long changedFirstPrice,
                                 final long secondPrice, final long secondQuantity,
                                 final long price) {
        final List<MenuProduct> menuProducts = List.of(
                new MenuProduct(1L, firstPrice, firstQuantity, null, 1L),
                new MenuProduct(2L, secondPrice, secondQuantity, null, 2L)
        );
        final Menu menu = new Menu(name, profanities, price, menuGroup.getId(), menuProducts, false);
        menu.changedProductPrice(1L, changedFirstPrice);

        assertThatThrownBy(menu::display)
                .isExactlyInstanceOf(IllegalStateException.class);
    }

    @DisplayName("메뉴의 가격이 모든 메뉴 상품 금액의 총합보다 같거나 작은 경우 메뉴를 노출할 수 있다.")
    @CsvSource(value = {"1_000:1:1_000:1:2_000", "2_000:1:2_000:2:6_000", "1_000:3:3_000:2:9_000"}, delimiter = ':')
    @ParameterizedTest(name = """
                {index}. 첫 번째 메뉴 상품 가격 : `{0}`, 첫 번째 메뉴 상품 수량 : `{1}`, 두 번째 메뉴 상품 가격 : `{2}`, 두 번째 메뉴 상품 수량 : `{3}`, 메뉴 가격 : `{4}`
            """)
    void display(final long firstPrice, final long firstQuantity,
                 final long secondPrice, final long secondQuantity,
                 final long price) {
        final List<MenuProduct> menuProducts = List.of(
                new MenuProduct(1L, firstPrice, firstQuantity, null, 1L),
                new MenuProduct(2L, secondPrice, secondQuantity, null, 2L)
        );
        final Menu menu = new Menu(name, profanities, price, menuGroup.getId(), menuProducts, false);
        menu.display();

        assertThat(menu.isDisplayed()).isTrue();
    }

    @DisplayName("메뉴를 숨길 수 있다.")
    @CsvSource(value = {"1_000:1:1_000:1:2_000", "2_000:1:2_000:2:6_000", "1_000:3:3_000:2:9_000"}, delimiter = ':')
    @ParameterizedTest(name = """
                {index}. 첫 번째 메뉴 상품 가격 : `{0}`, 첫 번째 메뉴 상품 수량 : `{1}`, 두 번째 메뉴 상품 가격 : `{2}`, 두 번째 메뉴 상품 수량 : `{3}`, 메뉴 가격 : `{4}`
            """)
    void hide(final long firstPrice, final long firstQuantity,
              final long secondPrice, final long secondQuantity,
              final long price) {
        final List<MenuProduct> menuProducts = List.of(
                new MenuProduct(1L, firstPrice, firstQuantity, null, 1L),
                new MenuProduct(2L, secondPrice, secondQuantity, null, 2L));
        final Menu menu = new Menu(name, profanities, price, menuGroup.getId(), menuProducts, true);
        menu.hide();

        assertThat(menu.isDisplayed()).isFalse();
    }
}
