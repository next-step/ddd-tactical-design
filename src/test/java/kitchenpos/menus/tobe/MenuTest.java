package kitchenpos.menus.tobe;

import kitchenpos.menus.tobe.exception.*;
import kitchenpos.products.tobe.domain.FakeProfanities;
import kitchenpos.products.tobe.domain.vo.EmptyProfanities;
import kitchenpos.products.tobe.domain.vo.Profanities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MenuTest {

    private Profanities profanities;
    private MenuGroup menuGroup;
    private List<MenuProduct> menuProducts;

    @BeforeEach
    void setUp() {
        profanities = new EmptyProfanities();
        menuGroup = new MenuGroup(UUID.randomUUID(), "메뉴 그룹");
        menuProducts = new ArrayList<>(
                List.of(
                        new MenuProduct(1L, 1000L, 1L, 1L),
                        new MenuProduct(2L, 2000L, 2L, 2L)
                )
        );
    }

    @DisplayName("메뉴의 가격은 0원 이상이어야 한다.")
    @ValueSource(longs = {-1, -1000, -100000})
    @ParameterizedTest(name = "{index}. 메뉴 가격 : `{0}`")
    void createWithNegativePrice(final long price) {
        assertThatThrownBy(
                () -> new Menu(null, "메뉴", profanities, price, menuGroup, menuProducts, true)
        ).isExactlyInstanceOf(InvalidMenuPricePeriodException.class);
    }

    @DisplayName("메뉴의 이름이 없거나 비어있으면 생성할 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest(name = "{index}. 메뉴 이름 : `{0}`")
    void createWithInvalidName(final String name) {
        assertThatThrownBy(
                () -> new Menu(null, name, profanities, 1000L, menuGroup, menuProducts, true)
        ).isExactlyInstanceOf(InvalidMenuNameException.class);
    }

    @DisplayName("메뉴의 이름에는 비속어가 포함될 수 없다.")
    @ValueSource(strings = {"비속어", "욕설"})
    @ParameterizedTest(name = "{index}. 상품 이름: {0}")
    void createWithProfanityName(final String name) {
        final FakeProfanities fakeProfanities = new FakeProfanities(List.of("비속어", "욕설"));
        assertThatThrownBy(() ->
                new Menu(null, name, fakeProfanities, 1000L, menuGroup, menuProducts, true)
        ).isInstanceOf(MenuNameContainsProfanityException.class);
    }

    @DisplayName("메뉴는 메뉴 그룹이 있어야 한다.")
    @NullSource
    @ParameterizedTest(name = "{index}. 메뉴 그룹 : `{0}`")
    void createWithNullMenuGroup(final MenuGroup menuGroup) {
        assertThatThrownBy(
                () -> new Menu(null, "메뉴", profanities, 1000L, menuGroup, menuProducts, true)
        ).isExactlyInstanceOf(InvalidMenuArgumentException.class);
    }

    @DisplayName("메뉴는 메뉴 상품이 없거나 비어있으면 생성할 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest(name = "{index}. 메뉴 상품 : `{0}`")
    void createWithInvalidMenuProducts(final List<MenuProduct> menuProducts) {
        assertThatThrownBy(
                () -> new Menu(null, "메뉴", profanities, 1000L, menuGroup, menuProducts, true)
        ).isExactlyInstanceOf(InvalidMenuArgumentException.class);
    }

    @DisplayName("메뉴의 가격은 모든 메뉴 상품 금액의 총합보다 같거나 작아야 한다.")
    @CsvSource(value = {"1_000:1:1_000:1:2_001", "2_000:1:2_000:2:6_001", "1_000:3:3_000:2:9_001"}, delimiter = ':')
    @ParameterizedTest(name = """
                {index}. 첫 번째 메뉴 상품 가격 : `{0}`, 첫 번째 메뉴 상품 수량 : `{1}`, 두 번째 메뉴 상품 가격 : `{2}`, 두 번째 메뉴 상품 수량 : `{3}`, 메뉴 가격 : `{4}`
            """)
    void createWithInvalidPrice(final long firstPrice, final long firstQuantity,
                                final long secondPrice, final long secondQuantity,
                                final long menuPrice) {
        final List<MenuProduct> menuProducts = List.of(
                new MenuProduct(1L, firstPrice, firstQuantity, 1L),
                new MenuProduct(2L, secondPrice, secondQuantity, 2L)
        );
        assertThatThrownBy(
                () -> new Menu(null, "메뉴", profanities, menuPrice, menuGroup, menuProducts, true)
        ).isExactlyInstanceOf(InvalidMenuPriceException.class);
    }

    @DisplayName("메뉴는 모든 메뉴 상품 금액의 총합보다 높은 가격으로 변경할 수 없다.")
    @CsvSource(value = {"1_000:1:1_000:1:2_000:2_001", "2_000:1:2_000:2:6_000:6_001", "1_000:3:3_000:2:9_000:9_001"}, delimiter = ':')
    @ParameterizedTest(name = """
                {index}. 첫 번째 메뉴 상품 가격 : `{0}`, 첫 번째 메뉴 상품 수량 : `{1}`, 두 번째 메뉴 상품 가격 : `{2}`, 두 번째 메뉴 상품 수량 : `{3}`, 메뉴 가격 : `{4}`, 변경할 메뉴 가격 : `{5}`
            """)
    void changePriceWithInvalidPrice(final long firstPrice, final long firstQuantity,
                                     final long secondPrice, final long secondQuantity,
                                     final long menuPrice, final long changedMenuPrice) {
        final List<MenuProduct> menuProducts = List.of(
                new MenuProduct(1L, firstPrice, firstQuantity, 1L),
                new MenuProduct(2L, secondPrice, secondQuantity, 2L)
        );
        final Menu menu = new Menu(null, "메뉴", profanities, menuPrice, menuGroup, menuProducts, true);

        assertThatThrownBy(
                () -> menu.changePrice(changedMenuPrice)
        ).isExactlyInstanceOf(InvalidMenuPriceException.class);
    }

    @DisplayName("메뉴의 가격이 모든 메뉴 상품 금액의 총합보다 높은 경우 메뉴를 노출할 수 없다.")
    @CsvSource(value = {"1_000:1:1_001:1_000:1:2_000", "2_000:1:2_001:2_000:2:6_000", "1_000:3:1_001:3_000:2:9_000"}, delimiter = ':')
    @ParameterizedTest(name = """
                {index}. 첫 번째 메뉴 상품 가격 : `{0}`, 첫 번째 메뉴 상품 수량 : `{1}`, 변경할 첫 번째 메뉴 상품 가격 : `{2}`, 
                두 번째 메뉴 상품 가격 : `{3}`, 두 번째 메뉴 상품 수량 : `{4}`, 메뉴 가격 : `{5}`
            """)
    void displayWithInvalidPrice(final long firstPrice, final long firstQuantity, final long changedFirstPrice,
                                 final long secondPrice, final long secondQuantity,
                                 final long menuPrice) {
        final MenuProduct firstMenuProduct = new MenuProduct(1L, firstPrice, firstQuantity, 1L);
        final List<MenuProduct> menuProducts = List.of(
                firstMenuProduct,
                new MenuProduct(2L, secondPrice, secondQuantity, 2L)
        );
        final Menu menu = new Menu(null, "메뉴", profanities, menuPrice, menuGroup, menuProducts, false);
        final Menu changedMenu = menu.changedProductPrice(1L, changedFirstPrice);

        assertThatThrownBy(
                () -> changedMenu.display()
        ).isExactlyInstanceOf(IllegalStateException.class);
    }
}
