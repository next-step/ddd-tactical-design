package kitchenpos.menus.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import kitchenpos.TobeFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MenuTest {
    @Nested
    class CreateTest {
        @Nested
        class SuccessTest {
            @DisplayName("메뉴 생성 검증")
            @Test
            void create() {
                // given
                DisplayedName displayedName = new DisplayedName("치킨메뉴");
                Price price = new Price(BigDecimal.valueOf(TobeFixtures.MENU_PRICE));
                boolean displayed = true;

                // when
                Menu menu = new Menu(displayedName, price, TobeFixtures.menuGroup(), displayed,
                    new MenuProducts(TobeFixtures.menuProduct(), TobeFixtures.menuProduct()));

                // then
                assertAll(
                    () -> assertThat(menu.getId()).isNotNull(),
                    () -> assertThat(menu.getDisplayedName()).isEqualTo(displayedName),
                    () -> assertThat(menu.getPrice()).isEqualTo(price),
                    () -> assertThat(menu.isDisplayed()).isTrue()
                );
            }
        }

        @Nested
        class FailTest {
            @DisplayName("가격이 메뉴 상품의 금액의 합보다 클 경우 예외 발생")
            @Test
            void create() {
                // given
                long price = TobeFixtures.MENU_PRICE + 1;

                // when then
                assertThatIllegalArgumentException().isThrownBy(() -> TobeFixtures.menu(price));
            }
        }
    }

    @Nested
    class ChangePriceTest {
        @Nested
        class SuccessTest {
            @Test
            void changePrice() {
                // given
                Menu menu = TobeFixtures.menu();
                Price price = new Price(BigDecimal.valueOf(TobeFixtures.MENU_PRICE - 1));

                // when
                menu.changePrice(price);

                // then
                assertThat(menu.getPrice()).isEqualTo(price);
            }
        }

        @Nested
        class FailTest {
            @DisplayName("가격이 메뉴 상품의 금액의 합보다 클 경우 예외 발생")
            @Test
            void changePrice() {
                // given
                Menu menu = TobeFixtures.menu();
                Price price = new Price(BigDecimal.valueOf(TobeFixtures.MENU_PRICE + 1));

                // when then
                assertThatIllegalArgumentException().isThrownBy(() -> menu.changePrice(price));
            }
        }
    }

    @Nested
    class DisplayTest {
        @Nested
        class SuccessTest {
            @DisplayName("노출 처리")
            @Test
            void display() {
                // given
                Menu menu = TobeFixtures.menu(false);

                // when
                menu.display();

                // then
                assertThat(menu.isDisplayed()).isTrue();
            }
        }

//        @Nested
//        class FailTest {
//            @DisplayName("가격이 메뉴 상품의 금액의 합보다 클 경우 예외 발생")
//            @Test
//            void display() {
//                // given
//                Menu menu = TobeFixtures.menu(false);
//                BigDecimal price = BigDecimal.valueOf(TobeFixtures.PRODUCT_PRICE - 1);
//
//                menu.getMenuProducts().get(0).getProduct().changePrice(price);
//
//                // when then
//                assertThatIllegalArgumentException().isThrownBy(menu::display);
//            }
//        }
    }

    @Nested
    class HideTest {
        @Nested
        class SuccessTest {
            @DisplayName("숨김 처리")
            @Test
            void hide() {
                // given
                Menu menu = TobeFixtures.menu(true);

                // when
                menu.hide();

                // then
                assertThat(menu.isDisplayed()).isFalse();
            }
        }
    }
}
