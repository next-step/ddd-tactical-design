package kitchenpos.menus.domain;

import kitchenpos.ToBeFixtures;
import kitchenpos.menus.application.FakeMenuPurgomalumClient;
import kitchenpos.menus.tobe.domain.entity.Menu;
import kitchenpos.menus.tobe.domain.service.MenuNameValidationService;
import kitchenpos.menus.tobe.domain.entity.MenuProduct;
import kitchenpos.products.infra.PurgomalumClient;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@DisplayName("메뉴 도메인 테스트")
public class MenuTest {
    private ToBeFixtures toBeFixtures;
    private PurgomalumClient purgomalumClient;
    private MenuNameValidationService menuNameValidationService;

    @BeforeEach
    void setUp() {
        toBeFixtures = new ToBeFixtures();
        purgomalumClient = new FakeMenuPurgomalumClient();
        menuNameValidationService = new MenuNameValidationService(purgomalumClient);
    }

    @Nested
    @DisplayName("메뉴 생성 테스트")
    class CreateTest {
        @NullSource
        @ValueSource(strings = "-1")
        @ParameterizedTest
        @DisplayName("메뉴의 금액은 0보다 작을 수 없다")
        void create_exception_price(BigDecimal price) {
            Assertions.assertThatThrownBy(
                    () -> createMenu("튀김", price, true)
            ).isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("메뉴에 속한 상품의 합은 메뉴의 금액보다 크거나 같아야 한다.")
        void create_sum_all_price() {
            Assertions.assertThatThrownBy(
                    () -> createMenu("튀김", BigDecimal.valueOf(400_001), true)
            ).isInstanceOf(IllegalArgumentException.class);
        }

        @ValueSource(strings = {"비속어", "욕설"})
        @ParameterizedTest
        @DisplayName("메뉴의 이름에는 비속어가 들어갈 수 없다.")
        void create_exception_name(String name) {
            Assertions.assertThatThrownBy(
                    () -> createMenu(name, BigDecimal.valueOf(400_000), true)
            ).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("메뉴 금액 변경 테스트")
    class ChangePriceTest {
        @Test
        @DisplayName("메뉴의 금액을 변경한다.")
        void changePrice() {
            Menu 메뉴 = createMenu("튀김", BigDecimal.valueOf(400_000), true);
            BigDecimal 변경_금액 = BigDecimal.valueOf(300_000);
            메뉴.changePrice(변경_금액);

            Assertions.assertThat(메뉴.getPrice().compareTo(변경_금액)).isZero();
        }

        @Test
        @DisplayName("변경 메뉴의 금액은 0 이상이어야 한다.")
        void changePrice_exception_invalidPrice() {
            Menu 메뉴 = createMenu("튀김", BigDecimal.valueOf(400_000), true);
            BigDecimal 변경_금액 = BigDecimal.valueOf(-1);

            Assertions.assertThatThrownBy(
                    () -> 메뉴.changePrice(변경_금액)
            ).isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("메뉴 변경 금액은 메뉴의 속한 상품의 합보다 작아야 한다.")
        void changePrice_exception_sum_all_price() {
            Menu 메뉴 = createMenu("튀김", BigDecimal.valueOf(400_000), true);
            BigDecimal 변경_금액 = BigDecimal.valueOf(500_000);

            Assertions.assertThatThrownBy(
                    () -> 메뉴.changePrice(변경_금액)
            ).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("메뉴 노출 여부 테스트")
    class DisplayedTest {
        @Test
        @DisplayName("메뉴를 노출할 수 있다.")
        void display() {
            Menu 메뉴 = createMenu("튀김", BigDecimal.valueOf(400_000), false);
            메뉴.display();

            Assertions.assertThat(메뉴.isDisplayed()).isTrue();
        }

        @Test
        @DisplayName("메뉴를 숨길 수 있다.")
        void display_exception_sum_all_price() {
            Menu 메뉴 = createMenu("튀김", BigDecimal.valueOf(400_000), true);
            메뉴.hide();

            Assertions.assertThat(메뉴.isDisplayed()).isFalse();
        }
    }

    private Menu createMenu(String name, BigDecimal price, boolean displayed) {
        List<MenuProduct> menuProducts = createMenuProducts();
        Menu menu = ToBeFixtures.menuCreateOf(
                name, menuNameValidationService, price, displayed, menuProducts
        );
        return menu;
    }

    private List<MenuProduct> createMenuProducts() {
        MenuProduct 치킨_후라이드_10개 = ToBeFixtures.menuProductOf(10, toBeFixtures.후라이드_20000.getPrice());
        MenuProduct 치킨_양념치킨_10개 = ToBeFixtures.menuProductOf(10, toBeFixtures.양념치킨_20000.getPrice());
        List<MenuProduct> 치킨_상품_목록 = List.of(치킨_후라이드_10개, 치킨_양념치킨_10개);
        return 치킨_상품_목록;
    }
}
