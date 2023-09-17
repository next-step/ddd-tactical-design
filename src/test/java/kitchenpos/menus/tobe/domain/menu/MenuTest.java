package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroupName;
import kitchenpos.products.infra.FakePurgomalumClient;
import kitchenpos.support.infra.PurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.List;

import static kitchenpos.Fixtures.product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class MenuTest {

    private PurgomalumClient purgomalumClient;

    @BeforeEach
    void setUp() {
        purgomalumClient = new FakePurgomalumClient();
    }

    @DisplayName("메뉴명 테스트")
    @Nested
    class TestMenuName {
        @DisplayName("메뉴명은 비어있을 수 없다")
        @ParameterizedTest(name = "[{index}] productName={0}")
        @NullSource
        @ValueSource(strings = {""})
        void givenMenuName_whenMenuNameIsBlank_thenThrowException(final String menuName) {
            assertThatThrownBy(() -> MenuName.create(menuName, purgomalumClient))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("메뉴명은 비어있을 수 없습니다.");
        }

        @DisplayName("메뉴명에 비속어가 포함되어 있으면 예외가 발생한다.")
        @ParameterizedTest(name = "[{index}] productName={0}")
        @ValueSource(strings = {"비속어", "욕설"})
        void givenMenuName_whenMenuNameContainsProfanity_thenThrowException(final String menuName) {
            assertThatThrownBy(() -> MenuName.create(menuName, purgomalumClient))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("메뉴명에 비속어가 포함되어 있습니다.");
        }
    }

    @DisplayName("메뉴 가격 테스트")
    @Nested
    class TestMenuPrice {
        @DisplayName("메뉴 가격은 비어있을 수 없다")
        @ParameterizedTest(name = "[{index}] productName={0}")
        @NullSource
        void givenMenuPrice_whenMenuPriceIsBlank_thenThrowException(final BigDecimal menuPrice) {
            assertThatThrownBy(() -> MenuPrice.create(menuPrice))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("메뉴 가격은 비어있을 수 없습니다.");
        }

        @DisplayName("메뉴 가격은 음수일 수 없다")
        @ParameterizedTest(name = "[{index}] productName={0}")
        @ValueSource(longs = {-1L, -100L, -1000L})
        void givenMenuPrice_whenMenuPriceIsNegative_thenThrowException(final Long menuPrice) {
            assertThatThrownBy(() -> MenuPrice.create(BigDecimal.valueOf(menuPrice)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("메뉴 가격은 음수일 수 없습니다.");
        }
    }

    @DisplayName("메뉴 숨김 여부 테스트")
    @Nested
    class TestMenuDisplay {
        @DisplayName("메뉴 숨김 여부는 비어있을 수 없다")
        @ParameterizedTest(name = "[{index}] productName={0}")
        @NullSource
        void givenMenuDisplay_whenMenuDisplayIsBlank_thenThrowException(final Boolean menuDisplay) {
            assertThatThrownBy(() -> MenuDisplay.create(menuDisplay))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("메뉴 숨김 여부는 비어있을 수 없습니다.");
        }
    }

    @DisplayName("메뉴 생성 테스트")
    @Nested
    class TestCreateMenu {
        @DisplayName("메뉴 가격은 메뉴 상품 가격의 합보다 작거나 같아야 한다")
        @Test
        void givenMenuPrice_whenMenuPriceIsLowerThanMenuProductPriceSum_thenThrowException() {
            final MenuProduct menuProduct1 = MenuProduct.create(
                    product(), MenuProductQuantity.create(2L)
            );
            final MenuProduct menuProduct2 = MenuProduct.create(
                    product(), MenuProductQuantity.create(3L)
            );
            final BigDecimal menuProductSum = menuProduct1.getProduct().getPrice().multiply(BigDecimal.valueOf(menuProduct1.getQuantity()))
                    .add(menuProduct2.getProduct().getPrice().multiply(BigDecimal.valueOf(menuProduct2.getQuantity())));
            assertThatThrownBy(() -> Menu.create(
                    MenuName.create("후라이드", purgomalumClient),
                    MenuPrice.create(menuProductSum.add(BigDecimal.valueOf(100L))),
                    MenuGroup.create(MenuGroupName.create("두마리메뉴")),
                    MenuDisplay.create(true),
                    List.of(menuProduct1, menuProduct2)
            )).isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("메뉴 가격은 메뉴 상품 가격의 합보다 작거나 같아야 합니다.");
        }

        @DisplayName("메뉴 가격은 메뉴 상품 가격의 합보다 작거나 같아야 한다")
        @Test
        void givenMenuPrice_whenMenuPriceIsLowerThanMenuProductPriceSum_thenSuccess() {
            final MenuProduct menuProduct1 = MenuProduct.create(
                    product(), MenuProductQuantity.create(2L)
            );
            final MenuProduct menuProduct2 = MenuProduct.create(
                    product(), MenuProductQuantity.create(3L)
            );
            final BigDecimal menuProductSum = menuProduct1.getProduct().getPrice().multiply(BigDecimal.valueOf(menuProduct1.getQuantity()))
                    .add(menuProduct2.getProduct().getPrice().multiply(BigDecimal.valueOf(menuProduct2.getQuantity())));

            final MenuName menuName = MenuName.create("후라이드", purgomalumClient);
            final MenuPrice menuPrice = MenuPrice.create(menuProductSum);
            final MenuGroup menuGroup = MenuGroup.create(MenuGroupName.create("두마리메뉴"));
            final MenuDisplay menuDisplay = MenuDisplay.create(true);
            final List<MenuProduct> menuProducts = List.of(menuProduct1, menuProduct2);
            final Menu actual = Menu.create(menuName, menuPrice, menuGroup, menuDisplay, menuProducts);

            assertThat(actual.getName()).isEqualTo(menuName.getValue());
            assertThat(actual.getPrice()).isEqualTo(menuPrice.getValue());
            assertThat(actual.getMenuGroup().getName()).isEqualTo(menuGroup.getName());
            assertThat(actual.isDisplayed()).isTrue();
            assertThat(actual.getMenuProducts()).containsExactlyInAnyOrderElementsOf(menuProducts);
        }
    }
}
